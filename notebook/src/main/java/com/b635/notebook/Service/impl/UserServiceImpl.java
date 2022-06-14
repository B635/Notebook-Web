package com.b635.notebook.Service.impl;

import com.b635.notebook.Mapper.UserMapper;
import com.b635.notebook.Model.entity.User;
import com.b635.notebook.Service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int updatePassword(int userId, String newPassword) {
        return userMapper.update(null,
                Wrappers.lambdaUpdate(User.class)
                        .eq(User::getId, userId)
                        .set(User::getPassword, newPassword)
        );
    }

    @Override
    public User getUser(String username) {
        return userMapper.selectOne(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getUsername, username)
        );
    }


}

