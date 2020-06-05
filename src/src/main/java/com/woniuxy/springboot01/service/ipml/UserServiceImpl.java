package com.woniuxy.springboot01.service.ipml;

import com.woniuxy.springboot01.entity.User;
import com.woniuxy.springboot01.mapper.UserMapper;
import com.woniuxy.springboot01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> all() {
        return userMapper.all();
    }
}
