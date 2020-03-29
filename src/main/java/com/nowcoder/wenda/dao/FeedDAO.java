package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Comment;
import com.nowcoder.wenda.model.Feed;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface FeedDAO {
    String table_name = " feed ";
    String insert_fields=" user_id, data, created_date, type ";
    String insert_values="#{userId}, #{data}, #{createdDate}, #{type} ";
    String select_fields= " id, "+insert_fields;

    @Insert({"insert into ",table_name, "(" , insert_fields ,
            ") values(", insert_values, ")"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addFeed(Feed feed);

    //动态sql
    @SelectProvider(type = SQLProvider.class, method = "selectUserFeeds")
    List<Feed> selectUserFeeds( int maxId, List<Integer> userIds, int count);

    @Select({"select * from ", table_name," where id=#{id}"})
    Feed getFeedById(@Param("id") int id);

    @Select({"select * from ",table_name, " where created_date=#{createdDate}"})
    Feed getFeedByCrDate(Date createdDate);
}
