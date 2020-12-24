package com.guonl.config;

import com.guonl.model.Role;
import com.guonl.model.UserEntity;
import com.guonl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity userEntity = userService.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        Set<Role> roles = userEntity.getRoles();
        roles.forEach(x -> {
            authorities.add(new SimpleGrantedAuthority(x.getRname()));
        });

        User user = new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
        return user;
    }

}
