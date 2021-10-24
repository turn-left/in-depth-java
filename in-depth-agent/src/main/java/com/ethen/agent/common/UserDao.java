package com.ethen.agent.common;

import com.ethen.agent.annotation.Select;

public interface UserDao {
    @Select("select * from user")
    default void selectAll() {
    }
}