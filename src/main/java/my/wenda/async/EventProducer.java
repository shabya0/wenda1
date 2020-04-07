package my.wenda.async;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import my.wenda.util.JedisAdapter;
import my.wenda.util.RedisKeyUtil;
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
