package com.otoomo.easycard.cloud.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * 认证服务的身份验证配置类
 */
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 把身份认证器注入到容器中，供OAuthServerConfigurer配置类使用
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置密码编码器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 处理验证客户端的验证事宜；
     * <p>
     * username和password会存储在数据库中的⽤户表中
     * 根据⽤户表中数据，验证当前传递过来的⽤户信息的合法性
     *
     * @param auth
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails userDetails = new User(
//                "admin",    //用户名
//                "123456",   //密码
//                new ArrayList<>()   //权限列表
//        );
//
//        auth
//                //配置用户认证的存储在哪里
//                .inMemoryAuthentication()
//                //指定要验证的用户
//                .withUser(userDetails)
//                //指定密码编码器，会使用这个编码器处理后进行密码对比
//                .passwordEncoder(passwordEncoder);

        /*
        从数据库中获取用户详情信息
         */
        auth.userDetailsService(userDetailsService)
                //指定密码编码器
                .passwordEncoder(passwordEncoder);
    }
}
