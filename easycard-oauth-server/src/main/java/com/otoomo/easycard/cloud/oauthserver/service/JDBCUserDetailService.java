package com.otoomo.easycard.cloud.oauthserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现从数据库中获取用户详细信息
 */
@Service
public class JDBCUserDetailService implements UserDetailsService {

//    @Autowired
//    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //从数据库获取用户信息
//        Users users = usersService.findByUserName(userName);

        //用户权限列表，这个可以根据具体业务实现从数据库中获取具体的权限列表
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user.card.list"));

        //创建userDetail，User实现了UserDetails接口。
        User userDetails = new User("admin", "123456", authorities);
        return userDetails;
    }
}
