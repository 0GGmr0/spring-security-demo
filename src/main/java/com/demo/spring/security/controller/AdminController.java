package com.demo.spring.security.controller;

import com.demo.spring.security.model.vo.Result;
import com.demo.spring.security.tools.ResultTool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-security-demo
 * @description: 用户相关操作的Controller
 * @author: 0GGmr0
 * @create: 2019-01-08 08:36
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/hello")
    public Result helloWorld() {
        return ResultTool.success("管理员权限验证成功");
    }
}
