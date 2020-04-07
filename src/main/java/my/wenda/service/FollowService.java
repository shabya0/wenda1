package my.wenda.service;

import my.wenda.util.JedisAdapter;
import my.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;

    //关注
    public boolean follow(int userId, int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);     //用户关注的列表
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);       //对象的被关注列表

        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));

        List<Object> ret = jedisAdapter.exec(tx, jedis);

        return ret.size() == 2 && (long) ret.get(0) > 0 && (long)ret.get(1) > 0;
    }

    //取消关注
    public boolean unfollow(int userId, int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);     //用户关注的列表
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);       //对象的被关注列表

        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zrem(followerKey, String.valueOf(userId));
        tx.zrem(followeeKey, String.valueOf(entityId));

        List<Object> ret = jedisAdapter.exec(tx, jedis);

        return ret.size() == 2 && (long) ret.get(0) > 0 && (long)ret.get(1) > 0;
    }

    private List<Integer> getIdsFromSet(Set<String> idset){     //set转list<integer>
        List<Integer> ids = new ArrayList<>();
        for(String str : idset){
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    //粉丝
    public List<Integer> getFollowers(int entityType, int entityId, int count){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrange(followerKey, 0, count));
    }

    //分页用
    public List<Integer> getFollowers(int entityType, int entityId,int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrange(followerKey, offset, count));
    }


    //我的关注
    public List<Integer> getFollowees(int entityType, int entityId, int count){
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));        //最新在最前面
    }

    //分页用
    public List<Integer> getFollowees(int entityType, int entityId,int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        return getIdsFromSet(jedisAdapter.zrevrange(followeeKey, offset, count));
    }

    //我总关注的个数
    public long getFolloweeCount(int entityTpye, int entityId){
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityTpye, entityId);
        return jedisAdapter.zcard(followeeKey);
    }

    //the number of fans
    public long getFollowerCount(int entityTpye, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityTpye, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    public boolean isFollower(int userId, int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId))!= null; //获取得到权重则说明存在队列中
    }
}
