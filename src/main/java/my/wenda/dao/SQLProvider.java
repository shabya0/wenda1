package my.wenda.dao;

import my.wenda.controller.FeedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//动态sql
public class SQLProvider {
    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    //获取question 分页用  usegas questionDAO
    public String selectQuestionwhere(int userId, int offset, int limit){
        String sql = "select * from question ";
        if(userId != 0){
            sql=sql+"where user_id="+userId;
        }
        sql+=" order by created_date desc limit "+offset+","+limit;
        return sql;
    }

    //获取feed流   usegas feeddao
    //select * from feed where id< maxId and user_id in (.. , ..) order by id desc limit count
    public String selectUserFeeds(int maxId, List<Integer> userIds,int offset, int count){
        logger.info("SQLProvider_selectUserFeeds.method had done");
        String sql = "select * from feed where id < "+maxId;
        if(userIds.size() != 0){
            sql += " and user_id in (" + userIds.get(0);
            for(int i=1; i< userIds.size(); ++i){
                sql += ", "+userIds.get(i);
            }
            sql+= ") ";
        }
        sql+= " order by id desc limit "+offset+","+count;
        return sql;
    }

}
