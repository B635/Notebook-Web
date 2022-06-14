package com.b635.notebook.Service.impl;

import com.b635.notebook.Mapper.UserMapper;
import com.b635.notebook.Model.entity.User;
import com.b635.notebook.Service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Authentication getAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 匿名用户 即为 未登录
        // if ("anonymousUser".equals(auth.getName()))
        return (auth instanceof AnonymousAuthenticationToken)
                ? null
                : auth;
    }


}

