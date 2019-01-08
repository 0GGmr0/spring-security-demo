package com.demo.spring.security.controller;

import com.demo.spring.security.dao.UserMapper;
import com.demo.spring.security.model.entity.User;
import com.demo.spring.security.model.entity.UserExample;
import com.demo.spring.security.model.jsonrequest.LoginInfo;
import com.demo.spring.security.model.vo.Result;
import com.demo.spring.security.security.JwtUtil;
import com.demo.spring.security.tools.ResultTool;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: spring-security-demo
 * @description: 登录controller
 * @author: 0GGmr0
 * @create: 2019-01-08 08:40
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JwtUtil jwtUtil;

    @PostMapping
    public Result login(@RequestBody LoginInfo loginInfo, HttpServletResponse response) {

        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        try {
            Objects.requireNonNull(username);
            Objects.requireNonNull(password);
            UserExample example = new UserExample();
            example.createCriteria()
                    .andUsernameEqualTo(username);
            User user = userMapper.selectByExample(example).get(0);
            if (password.equals(user.getPassword())) {
                Map<String, String> res = new HashMap<>();
                res.put("token", jwtUtil.createJwt(username, user.getAuthority()));
                return ResultTool.success(res);
            } else {
                return ResultTool.clientError(response, "密码错误");
            }

        } catch (NullPointerException e1) {
            return ResultTool.clientError(response, "账号或密码不能为空");
        } catch (IndexOutOfBoundsException e2) {
            return ResultTool.clientError(response, "并不存在这个用户");
        } catch (AuthenticationException e3) {
            return ResultTool.serverError(response, "账户验证出现问题");
        }
    }

}
