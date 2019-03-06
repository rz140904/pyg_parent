package cn.itcast.core.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

/*
* 实现此接口目的是为了给用户赋予权限操作,cas框架中登陆的用户才会进到这里,在这里我们给用户赋予对应的权限,那么在springsecurity
* 配置的拦截器就会放行,因为有权限访问
* */
public class UserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.创建权限集合
        ArrayList<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
        //2.向权限集合中添加对应的权限
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(username,"",authList);
    }
}
