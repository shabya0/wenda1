package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Comment;
import com.nowcoder.wenda.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
@Mapper
public interface CommentDAO {
    String table_name = " comment ";
    String insert_fields=" user_id, content, created_date, entity_id, entity_type, status ";
    String insert_values="#{userId}, #{content}, #{createdDate}, #{entityId}, #{entityType}, #{status}";
    String select_fields= " id, "+insert_fields;

    @Insert({"insert into ",table_name, "(" , insert_fields ,           //插入问题
            ") values(", insert_values, ")"})
    int addComment(Comment comment);


    @Update({"update ",table_name," set status=#{status}  where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);


    @Delete({"delete from ",table_name," where id=#{id}"})          //根据id删除问题
    void deleteCommentById(int id);
    //动态sql
//    @SelectProvider(type = QuestionSQLProvider.class, method = "selectQuestionwhere")
//    List<Comment> selectLatestQuestions(int userId, int offset, int limit);

    @Select({"select ", select_fields, " from ", table_name,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc" })
    List<Comment> selectCommentByzentity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ", table_name,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})        //使用entityid entityType查找问题
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select * from ", table_name, " where id =#{id}"})
    Comment getCommentById(int id);

    @Select({"select count(id) from ", table_name, " where user_id=#{userId}"})
    int getUserCommentCount(int userId);

}
