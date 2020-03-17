package com.nowcoder.wenda.dao;

//动态sql
public class QuestionSQLProvider {
    public String selectQuestionwhere(int userId, int offset, int limit){
        String sql = "select * from question ";
        if(userId != 0){
            sql=sql+"where user_id="+userId;
        }
        sql+=" order by created_date desc limit "+offset+","+limit;
        return sql;
    }
}
