package com.demo.spring.security.tools;

import com.demo.spring.security.model.vo.Result;
import lombok.Data;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: spring-security-demo
 * @description: 标准返回格式的工具类
 * @author: 0GGmr0
 * @create: 2019-01-08 08:50
 */
public class ResultTool {

    /**
     * @Description:
     * @Param: [object]
     * @Return: com.demo.spring.security.model.vo.Result
     * @Author: 0GGmr0
     * @Date: 2019-01-08
     */
    @SuppressWarnings("unchecked")
    public static Result success(List<Object> object){
        Result result = new Result();
        result.setCode(HttpServletResponse.SC_OK);
        result.setData(object);
        return result;
    }

    /**
     * @Description:
     * @Param: [object]
     * @Return: com.demo.spring.security.model.vo.Result
     * @Author: 0GGmr0
     * @Date: 2019-01-08
     */
    @SuppressWarnings("unchecked")
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(HttpServletResponse.SC_OK);
        result.setData(object);
        return result;
    }

    /**
     * @Description:
     * @Param: []
     * @Return: com.demo.spring.security.model.vo.Result
     * @Author: 0GGmr0
     * @Date: 2019-01-08
     */
    public static Result success(){
        Result result = new Result();
        result.setCode(HttpServletResponse.SC_OK);
        return result;
    }

    /**
     * @Description: 客户端数据问题
     * @Param: [message]
     * @Return: com.demo.spring.security.model.vo.Result
     * @Author: 0GGmr0
     * @Date: 2019-01-08
     */
    public static Result clientError(HttpServletResponse response, String message){
        Result result = new Result();
        result.setMessage(message);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        result.setCode(HttpServletResponse.SC_BAD_REQUEST);
        return result;
    }

    /**
     * @Description: 服务器资源出现问题
     * @Param: [message]
     * @Return: com.demo.spring.security.model.vo.Result
     * @Author: 0GGmr0
     * @Date: 2019-01-08
     */
    public static Result serverError(HttpServletResponse response, String message){
        Result result = new Result();
        result.setMessage(message);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        result.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return result;
    }

    /**
     * @Description: 通用报错格式
     * @Param: [code, message]
     * @Return: com.demo.spring.security.model.vo.Result
     * @Author: 0GGmr0
     * @Date: 2019-01-08
     */
    public static Result error(int code, String message){
        Result result = new Result();
        result.setMessage(message);
        result.setCode(code);
        return result;
    }
}
