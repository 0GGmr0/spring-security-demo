package com.demo.spring.security.security;

import com.demo.spring.security.dao.UserMapper;
import com.demo.spring.security.model.ConstCorrespond;
import com.demo.spring.security.model.entity.User;
import com.demo.spring.security.model.entity.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: spring-security-demo
 * @description:
 * @author: 0GGmr0
 * @create: 2019-01-08 09:08
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserMapper userMapper;

    private UserDetailsService myUserDetailService;
    private JwtUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public JwtAuthenticationTokenFilter(@Qualifier("jwtUserDetailService") UserDetailsService userDetailsService,
                                        JwtUtil jwtTokenUtil) {
        this.myUserDetailService   = userDetailsService;
        this.jwtUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        logger.info("对请求 '{}' 进行token验证", request.getRequestURL());
        final String requestHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(requestHeader != null && requestHeader.startsWith(ConstCorrespond.TOKEN_HEAD)) {
            token = requestHeader.substring(ConstCorrespond.TOKEN_HEAD.length());
            logger.info("请求" + request.getRequestURI() + "携带的token值为" + token);
            try {
                username = jwtUtil.parseJwt(token);
            } catch (IllegalArgumentException e) {
                logger.error("获取用户的权限失败，发生错误", e);
            }
        }

        logger.info("获取用户token成功，获取用户 '{}' 的权限", username);
        boolean saved = false;
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("security context 为空, 所以需要从用户表中获取");

            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("设置用户 '{}' 的security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String usernameFromToken = jwtUtil.getUserIdFromToken(token);
                UserExample example = new UserExample();
                example.createCriteria().andUsernameEqualTo(usernameFromToken);
                User user = userMapper.selectByExample(example).get(0);
                try (UserContext ignored = new UserContext(user)) {
                    /*之所以这样写是为了有一个线程级别的一个token的信息，这样如果在controller中需要获取到
                    /一个新的内容，那么就还得查询一次，创建线程级变量可以避免这个情况的发生*/
                    logger.info("对用户 '{}' , 创建了一个线程级 security context", username);
                    saved = true;
                    chain.doFilter(request, response);
                }
            }
        }
        if(!saved) {
            chain.doFilter(request, response);
        }
    }
}

