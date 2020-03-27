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

    @Update({"update ", table_name, " set has_read = 1 where conversation_id =#{conversationId} and to_id=#{userId}"})
    void setStatus(@Param("conversationId") String conversationId, @Param("userId") int userId);        //要接受者查看才能改为已读

//select * from message a where exists( select * from message group by conversation_id having max(created_date) = a.created_date )
// order by created_date desc           每组数据最新一条消息
//    @Select({"select ", insert_fields, "  from message a inner join(select max(created_date) 'lasted' from ", table_name,
//            " where from_id=#{userId} or to_id=#{userId} group by conversation_id order by created_date desc) tt where a.created_date=tt.lasted limit #{offset},#{limit}"})
    @Select({"select ", insert_fields, " , count(id) as id from ( select * from ", table_name,
        " where from_id=#{userId} or to_id=#{userId} order by created_date desc) tt group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Select({"select count(id) from ", table_name, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);


    //查询某conversation_id最新的一条数据跟日期
//    select * from  message a  where exists(select * from message where conversation_id='32_33' having a.created_date= max(created_date) )
    @Select({"select ", insert_fields," from  ", table_name, " a where exists(select * from message where conversation_id=#{conversationId} having a.created_date = max(created_date))"})
    Message getLastedByConversationId(String conversationId);
}
