package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Comment;
import com.nowcoder.wenda.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.annotation.security.PermitAll;
import java.util.List;

@Mapper
@Repository
public interface MessageDAO {
    String table_name = " message ";
    String insert_fields=" from_id, to_id, content, has_read, conversation_id, created_date ";
    String insert_values="#{fromId}, #{toId}, #{content}, #{hasRead}, #{conversationId}, #{createdDate} ";
    String select_fields= " id, "+insert_fields;

    @Insert({"insert into ",table_name, "(" , insert_fields ,           //插入问题
            ") values(", insert_values, ")"})
    int addMessage(Message message);


    @Delete({"delete from ",table_name," where id=#{id}"})          //根据id删除问题
    void deleteMessageById(int id);
    //动态sql
//    @SelectProvider(type = QuestionSQLProvider.class, method = "selectQuestionwhere")
//    List<Comment> selectLatestQuestions(int userId, int offset, int limit);

    @Select({"select ", select_fields, " from ", table_name,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}" })
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

//    select *,count(id) as cnt from (select * from message order by created_date desc) tt group by conversation_id order by created_date desc limit 0,2;
    @Select({"select ", insert_fields, " ,count(id) as id from (select * from ", table_name,
            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({"select count(id) from ", table_name, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);
}
