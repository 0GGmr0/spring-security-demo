package com.demo.spring.security.security;

import com.demo.spring.security.dao.UserMapper;
import com.demo.spring.security.model.ConstCorrespond;
import com.demo.spring.security.model.entity.User;
import com.demo.spring.security.model.entity.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: spring-security-demo
 * @description: 配置用户权限认证, 根据用户登录输入的用户名，去读取用户信息.
 *               验证成功后会被保存在当前回话的principal对象中用户信息的获取逻辑
 * @author: 0GGmr0
 * @create: 2019-01-08 09:17
 */
@Component
public class JwtUserDetailService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 根据用户输入的用户名到存储数据库读取用户信息，并且封装到一个UserDetails接口实现类中，给后面处理和校验，
    // 如果处理通过了，spring就会把它放到session中 ，即登录成功
    // 如果找不到用户，那么会抛出用户名不存在异常
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("用户：" + username + " 正在登录");
        //从数据库中获取这个用户
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        User user = userMapper.selectByExample(example).get(0);
        if(user == null) {
            //后台抛出的异常是：org.springframework.security.authentication.BadCredentialsException:
            logger.info("登录用户：" + username + " 不存在.");
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        //获取用户的身份
        String identity = ConstCorrespond.USER_AUTHORIZATION[user.getAuthority()];
        Set<GrantedAuthority> grantedAuths = new HashSet<>();
        grantedAuths.add( new SimpleGrantedAuthority(identity) );

        return new JwtUser(user.getUsername(), user.getPassword(), user.getAuthority(), grantedAuths);
    }
}
