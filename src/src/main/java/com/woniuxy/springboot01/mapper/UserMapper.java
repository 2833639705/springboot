package com.woniuxy.springboot01.mapper;

import com.woniuxy.springboot01.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface UserMapper {
    List<User> all();
}
