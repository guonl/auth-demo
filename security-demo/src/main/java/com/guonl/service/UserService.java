package com.guonl.service;

import com.guonl.mapper.UserMapper;
import com.guonl.model.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public UserEntity findByUsername(String username) {
        return userMapper.findByUsername(username);
    }


}
