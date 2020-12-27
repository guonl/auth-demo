package com.guonl.mapper;


import com.guonl.model.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserEntity findByUsername(@Param("username") String username);

}
