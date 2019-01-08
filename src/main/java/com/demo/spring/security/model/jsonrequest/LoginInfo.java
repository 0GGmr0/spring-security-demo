package com.demo.spring.security.model.jsonrequest;

import lombok.Data;

/**
 * @program: spring-security-demo
 * @description: 请求登录接口的json数据格式
 * @author: 0GGmr0
 * @create: 2019-01-08 08:42
 */
@Data
public class LoginInfo {

    private String username;

    private String password;
}
