package my.wenda.dao;

import my.wenda.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDAO {
    String table_name = " user ";
    String insert_fields=" name, password, salt, head_url ";
    String insert_values="#{name}, #{password}, #{salt}, #{headUrl}";
    String select_fields= " id, "+insert_fields;

    @Insert({"insert into ",table_name, "(" , insert_fields ,           //插入用户
            ") values(", insert_values,")"})
    int addUser(User user);

    @Select({"select * from ",table_name," where id=#{id}"})        //使用id查找用户
    User getUserById(int id);

    @Select({"select * from ",table_name," where name=#{name}"})
    User selectUserByName(String name);

    @Update({"update ",table_name," set password=#{password} where id=#{id}"})     //修改用户密码
    void updateUserPassword(User user);


    @Delete({"delete from ",table_name," where id=#{id}"})          //根据id删除用户
    void deleteUserById(int id);

    @Update({"update user set password=#{newpwd},salt=#{newSalt} where id=#{userId}"})
    int updatePwd(@Param("userId")int userId, @Param("newpwd")String newpwd, @Param("newSalt")String newSalt);

    @Update({"update user set head_url=#{url} where id=#{userId}"})
    int updateImg(@Param("userId")int userId, @Param("url")String url);
}
