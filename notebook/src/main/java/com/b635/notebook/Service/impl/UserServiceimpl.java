package com.b635.notebook.Service.impl;

import com.b635.notebook.Mapper.UserMapper;
import com.b635.notebook.Model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
@Service
public class UserServiceimpl extends ServiceImpl<UserMapper,User>implements IService<User> {


//    @Autowired
//    private UserMapper userMapper;
//
//    @Override
//    public void register(User user) {
//        String userName=user.getUserName();
//        String password=user.getPassword();
//        userMapper.insert(user);
//
//    }
//    @Override
//    public User login(User user) {
//        String userName=user.getUserName();
//        String password=user.getPassword();
//        User user1=userMapper.selectOne(new QueryWrapper<User>().eq("userName",userName)
//                .eq("passWord",password));
//        return user1;
//    }


}

