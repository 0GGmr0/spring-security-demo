package com.demo.spring.security.security;

import com.demo.spring.security.model.entity.User;

/**
 * @program: spring-security-demo
 * @description: UserContext
 * @author: 0GGmr0
 * @create: 2019-01-08 00:26
 */
public class UserContext implements AutoCloseable {

    public static final ThreadLocal<User> current = new ThreadLocal<>();

    public UserContext(User user) {
        current.set(user);
    }

    public static User getCurrentUser() {
        return current.get();
    }

    @Override
    public void close() {
        current.remove();
    }
}

