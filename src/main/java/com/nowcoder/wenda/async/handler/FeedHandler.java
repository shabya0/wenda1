package com.nowcoder.wenda.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.wenda.async.EventHandler;
import com.nowcoder.wenda.async.EventModel;
import com.nowcoder.wenda.async.EventType;
import com.nowcoder.wenda.controller.FeedController;
import com.nowcoder.wenda.dao.FeedDAO;
import com.nowcoder.wenda.model.*;
import com.nowcoder.wenda.service.*;
import com.nowcoder.wenda.util.JedisAdapter;
import com.nowcoder.wenda.util.RedisKeyUtil;
import com.nowcoder.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FeedHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FeedService feedService;
    @Autowired
    FollowService followService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    HostHolder hostHolder;
    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    //model序列化
    private String buildFeedData(EventModel model){
        Map<String, String> map = new HashMap<String, String>();
        logger.info("feedHandler44 getActorId: "+model.getActorId());
        User actor = userService.getUser(model.getActorId());
        if(actor == null){
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("username", actor.getName());

        if(model.getType() == EventType.COMMENT ||
                model.getType() == EventType.FOLLOW && model.getEntityType() == EntityType.ENTITY_QUESTION){
            Question question  = questionService.selectQuestionById(model.getEntityId());
            if(question == null)
                return null;
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandle(EventModel model) throws ParseException {

        Feed feed = new Feed();

        Date now = new Date();
        feed.setCreatedDate(now);
        feed.setUserId(model.getActorId());
        feed.setType(model.getType().getValue());
        feed.setData(buildFeedData(model));
        if(feed.getData() == null){
            return ;
        }

        int feed_id = feedService.addFeed(feed);    //返回插入数据自增列的值
        logger.info("addFeed: return id: "+feed_id);
        logger.info("model.getActorId: "+model.getActorId());
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, model.getActorId(), Integer.MAX_VALUE);
        // 系统队列
        followers.add(0);
        // 给所有粉丝推事件
        for (int follower : followers) {
            logger.info("feedHandler follower: "+ follower);
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            logger.info("feedTimelineKey(follower): "+timelineKey+"   getId: "+String.valueOf( feed_id ));
            jedisAdapter.lpush(timelineKey, String.valueOf( feed_id ));
            // 限制最长长度，如果timelineKey的长度过大，就删除后面的新鲜事
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        //评论，关注更新feed
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }

    public static void main(String args[]) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar date = Calendar.getInstance();
        Date d = new Date();
        date.setTime(d);
//        String time = format.format(date);
//        System.out.println(time);
        System.out.println(date.getTime());
//        System.out.println(time);
//        FeedService feedService = new FeedService();
//        java.sql.Date now = format.parse("2020-03-28 01:05:37");
//        Feed f = feedService.getFeedByCrDate(now);
//        System.out.println(f.toString());
    }
}
