package com.nowcoder.wenda.util;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.wenda.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import javax.naming.BinaryRefAddr;

@Service
public class JedisAdapter implements InitializingBean {
    private JedisPool pool;
    private Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    public static void print(int index, Object object){
        System.out.println(String.format("%d,%s",index, object.toString()));
    }

    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }
    public long sadd(String key, String value){     //插入键值对
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        }catch (Exception e){
            logger.error("sadd发生错误 "+e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key){          //key的元素的总个数
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("scard发生错误 "+e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value){         //删除
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.srem(key, value);
        }catch (Exception e){
            logger.error("sismember发生错误 "+e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value){     // 查询是否包含这个键值对
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        }catch (Exception e){
            logger.error("sismember发生错误 "+e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://localhost:6379/9");
        jedis.flushDB();

        //get set
        jedis.set("hello","world");
//        jedis.del("hello");
        print(1,jedis.get("hello"));
        jedis.rename("hello","Hello");
        print(1,jedis.get("Hello"));
        jedis.setex("hello2",15,"world");       //加入带有存活时限的key value

        jedis.set("pv","100");
        jedis.incr("pv");   //+1
        jedis.incrBy("pv",5);   //增加5
        jedis.decr("pv");   //-1
        jedis.decrBy("pv",2);   //减2
        print(2,jedis.get("pv"));

        print(3,jedis.keys("*"));

        //list
        String listName = "list";
        jedis.del(listName);
        for(int i=0; i<15; ++i){
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(4,jedis.lrange(listName,0,12));   //  >=0   <=12
        print(5,jedis.llen(listName));
//        print(6,jedis.lpop(listName));
        print(7,jedis.llen(listName));
        print(8,jedis.lindex(listName,3));
        print(9,jedis.linsert(listName, ListPosition.AFTER,"a4","xxx"));    //在a4后插入xxx
        print(9,jedis.linsert(listName,ListPosition.BEFORE,"a4","bbb"));    //在a4前插入bbb
        print(11,jedis.lrange(listName,0,20));

        //hash
        String userKey="userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"age","12");
        jedis.hset(userKey,"phone","899516513");
        print(12, jedis.hget(userKey,"name"));
        print(13,jedis.hgetAll(userKey));
        jedis.hdel(userKey,"phone");
        print(14, jedis.hgetAll(userKey));
        print(15,jedis.hexists(userKey,"email"));
        print(16,jedis.hexists(userKey,"name"));
        print(17,jedis.hkeys(userKey));
        print(18,jedis.hvals(userKey));
        jedis.hsetnx(userKey,"school","gc");    //若school字段不存在，则插入 key为school，value为gc的键值对

        //set
        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";
        for(int i=0; i<10; ++i){
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2,String.valueOf(i*i));
        }
        print(20, jedis.smembers(likeKey1));
        print(21,jedis.smembers(likeKey2));         //全部元素
        print(22,jedis.sunion(likeKey1,likeKey2));     //并集
        print(23, jedis.sdiff(likeKey1,likeKey2));      //第一个集合有，第二个集合没有的
        print(24, jedis.sinter(likeKey1,likeKey2));     //交集
        print(25,jedis.sismember(likeKey1,"12"));       //集合中是否包含12， 否false 是true
        print(26,jedis.smove(likeKey2,likeKey1,"25"));      //25从likeKey2移动到likeKey1
        print(27, jedis.scard(likeKey1));   //集合元素个数
        print(28,jedis.srandmember(likeKey1,3));    //从集合中随机取3个出来

        //sort sets
        String ranKey = "ranKey";
        jedis.zadd(ranKey,15,"jim");
        jedis.zadd(ranKey,60,"Ben");
        jedis.zadd(ranKey,90,"Lee");
        jedis.zadd(ranKey,80,"Mei");
        jedis.zadd(ranKey,75,"Luci");
        print(29,jedis.zcard(ranKey));
        print(30,jedis.zcount(ranKey,61,100));  //61（包括）-- 100 的个数
        print(31,jedis.zscore(ranKey,"Luci"));  //查询某个成员的score
        jedis.zincrby(ranKey,2,"Luci");     //Luci增加2分,若Luci不存在，默认0分

        print(32,jedis.zrange(ranKey,1,3));    //第1名到第3名（排序从小到打）
        print(33,jedis.zrevrange(ranKey,1,3));  //1-3, （反过来）从大到小
        for(Tuple tuple : jedis.zrangeByScoreWithScores(ranKey,"60","100")){        //根据分值取元素
            print(34,tuple.getElement()+" : "+String.valueOf(tuple.getScore()));
        }

        print(35, jedis.zrank(ranKey,"Ben"));       //Ben的排名.从小到大
        print(36, jedis.zrevrank(ranKey,"Ben"));     //Ben的排名，从大到小


        String setKey = "zset";
        jedis.zadd(setKey, 1,"a");
        jedis.zadd(setKey, 1,"b");
        jedis.zadd(setKey, 1,"c");
        jedis.zadd(setKey, 1,"d");
        jedis.zadd(setKey, 1,"e");

        print(37,jedis.zlexcount(setKey,"-","+"));  //计算-到+的范围内元素的个数  -  负无穷， +  正无穷
        print(38,jedis.zlexcount(setKey,"[b","[d"));    //b-d(都包括)中间元素的个数  ( 不包括  [ 包括

        jedis.zrem(setKey,"b");     //删除 b
        jedis.zremrangeByLex(setKey,"(c","+");  //c以上的全部删除



//        JedisPool pool = new JedisPool("redis://localhost:6379/9");       //链接池 默认8个线程
//        for(int i=0; i<100; ++i){
//            Jedis j = pool.getResource();
//            print(39,j.get("pv"));
//            j.close();
//        }

        User user = new User();
        user.setName("xx");
        user.setPassword("ppp");
        user.setHeadUrl("a.png");
        user.setSalt("salt");
        user.setId(1);
        print(40,JSONObject.toJSONString(user));
        jedis.set("user1", JSONObject.toJSONString(user));

        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value, User.class);       //user1数据读取到user2
        print(41,user);
        print(42,user.toString());
    }

}
