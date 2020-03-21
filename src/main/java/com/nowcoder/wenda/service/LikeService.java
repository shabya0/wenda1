package com.nowcoder.wenda.service;

import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.util.JedisAdapter;
import com.nowcoder.wenda.util.RedisKeyUtil;
import com.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    HostHolder hostHolder;
    private Logger logger = LoggerFactory.getLogger(LikeService.class);
    public int getLikeStatus(int userId, int entityType, int entityId){     //获取当前对这个问题或回复的喜欢与否
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))){        //若表示喜欢的set中已经包含这个对象 返回 1
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;     //若表示不喜欢的set中已经包含这个对象，返回 -1  ，否则返回 0
    }

    public long getLikeCount(int entityType, int entityId){         //返回有多少用户like这个问题或回复
        String likeKey= RedisKeyUtil.getLikeKey(entityType, entityId);
        logger.error("likeKey: "+likeKey);
        return jedisAdapter.scard(likeKey);
    }

    public long like(int userId, int entityType, int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entityType, entityId);
        if(getLikeStatus(userId,entityType, entityId) == 1){    //已经点击了喜欢，再次点击取消
            jedisAdapter.srem(likeKey, String.valueOf(userId));
            return jedisAdapter.scard(likeKey);
        }
        //未点击过喜欢
        jedisAdapter.sadd(likeKey, String.valueOf(userId)); //用户加入点赞 set

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);    //从踩中删除
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId){
        String dislikeKey= RedisKeyUtil.getDisLikeKey(entityType, entityId);
        if(getLikeStatus(userId,entityType, entityId) == 1){    //已经点击了不喜欢，再次点击取消
            jedisAdapter.srem(dislikeKey, String.valueOf(userId));
            return jedisAdapter.scard(dislikeKey);
        }
        jedisAdapter.sadd(dislikeKey, String.valueOf(userId));      //用户加入点赞 set

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);    //从踩中删除
        jedisAdapter.srem(likeKey,String.valueOf(userId));

        return jedisAdapter.scard(likeKey);
    }

}
