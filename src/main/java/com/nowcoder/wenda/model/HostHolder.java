package com.nowcoder.wenda.model;

import org.springframework.stereotype.Component;

//当前用户标识
@Component
public class HostHolder {
    //为每个线程分配一个对象，使用get，set，clear时会根据线程找到线程保存关联的变量
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser(){
        return users.get();
    }

    public void setUsers(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
