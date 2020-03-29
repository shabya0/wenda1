package com.nowcoder.wenda.service;

import com.nowcoder.wenda.controller.FeedController;
import com.nowcoder.wenda.dao.FeedDAO;
import com.nowcoder.wenda.model.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;
    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    //拉模式
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count){
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    public int addFeed(Feed feed){
        feedDAO.addFeed(feed);
        logger.info("feed.getId():  "+feed.getId());
        return feed.getId();
    }

    public Feed getById(int id){
        return feedDAO.getFeedById(id);
    }

    public Feed getFeedByCrDate(Date createdDate) {
        return feedDAO.getFeedByCrDate(createdDate);
    }
}
