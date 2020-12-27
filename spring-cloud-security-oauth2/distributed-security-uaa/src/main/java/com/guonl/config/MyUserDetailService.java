package com.guonl.config;

import com.guonl.model.Permission;
import com.guonl.model.Role;
import com.guonl.model.UserEntity;
import com.guonl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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


        Set<String> permissionSet = new HashSet<>();//权限
        Set<String> roleSet = new HashSet<>();//角色
        Set<Role> roles = userEntity.getRoles();
        roles.forEach(x -> {
            roleSet.add("ROLE_" + x.getRname().toUpperCase());
            Set<Permission> permissions = x.getPermissions();
            List<String> collect = permissions.stream().map(p -> p.getName()).collect(Collectors.toList());
            permissionSet.addAll(collect);
        });

        permissionSet.addAll(roleSet);

        UserDetails userDetails = User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(permissionSet.toArray(new String[permissionSet.size()]))
//                .roles(roleSet.toArray(new String[roleSet.size()]))
                .build();
        return userDetails;
    }

}
