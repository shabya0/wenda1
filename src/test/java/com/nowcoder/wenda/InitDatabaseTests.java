package com.nowcoder.wenda;

import com.nowcoder.wenda.dao.UserDAO;
import com.nowcoder.wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

//@Sql("/ini-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();

        for(int i=0;i<11;++i){
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("User%d",i));
            user.setPassword("987654");
            user.setSalt("tt");
           userDAO.addUser(user);
        }
    }
}