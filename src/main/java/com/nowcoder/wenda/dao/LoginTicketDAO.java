package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginTicketDAO {
    String table_name = " login_ticket ";
    String insert_fields = " user_id, expired, status, ticket";
    String select_fields = " id, "+ insert_fields;

    @Insert({"insert into ", table_name, "(", insert_fields, ") values(#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ", select_fields, " from ", table_name, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ",table_name, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);//status=0登录状态  1登出

    @Delete({"delete from ",table_name," where ticket=#{ticket}"})
    void deleteLoginTicketByTicket(String ticket);
}
