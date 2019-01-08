package com.demo.spring.security.model.vo;

import lombok.Data;

/**
 * @program: spring-security-demo
 * @description: 返回标准接口
 * @author: 0GGmr0
 * @create: 2019-01-08 08:41
 */
@Data
public class Result<T> {
    /**
     * 标识码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;
}
