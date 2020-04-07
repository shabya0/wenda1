package my.wenda.dao;

import my.wenda.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionDAO {
        String table_name = " question ";
        String insert_fields=" title, content, created_date, user_id, comment_count ";
        String insert_values="#{title}, #{content}, #{createdDate}, #{userId}, #{commentCount}";
        String select_fields= " id, "+insert_fields;

        @Insert({"insert into ",table_name, "(" , insert_fields ,           //插入问题
                ") values(", insert_values, ")"})
        int addQuestion(Question question);

        @Select({"select ", select_fields, " from ",table_name," where id=#{id}"})        //使用id查找问题
        Question selectQuestionById(int id);



        @Update({"update ",table_name," set title=#{title},content=#{content}," +
                " created_date=#{createdDate},user_id=#{userId},comment_count=#{commentCount} where id=#{id}"})     //修改问题密码
        void updateQuestion(Question question);

        @Update({"update ", table_name, "set comment_count=#{commentCount} where id=#{id}"})
        int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

        @Delete({"delete from ",table_name," where id=#{id}"})          //根据id删除问题
        void deleteQuestionById(int id);
        //动态sql
        @SelectProvider(type = SQLProvider.class, method = "selectQuestionwhere")
        List<Question> selectLatestQuestions(int userId, int offset, int limit);
}
