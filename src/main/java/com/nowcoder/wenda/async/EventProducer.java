package com.nowcoder.wenda.async;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.wenda.util.JedisAdapter;
import com.nowcoder.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@ComponentScan
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public EventProducer(){

    }
    public boolean fireEvent(EventModel eventModel){
        try {
//            BlockingQueue<EventModel> q = new ArrayBlockingQueue<EventModel>();
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
