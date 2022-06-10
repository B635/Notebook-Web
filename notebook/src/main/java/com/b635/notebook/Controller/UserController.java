package com.b635.notebook.Controller;

import com.b635.notebook.Model.entity.User;
import com.b635.notebook.Service.impl.UserServiceimpl;
import com.b635.notebook.utils.R;
import com.b635.notebook.utils.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceimpl userService;

    @RequestMapping("/login")
    public R login(@RequestBody User user) {
        User one = userService.getOne(
                new QueryWrapper<User>()
                        .eq("username", user.getUserName())
                        .eq("password", user.getPassWord()));
        if(one!=null){
            return RUtils.result("登录成功");
        }else{
            return RUtils.result("用户名或密码错误");
        }
    }

    @RequestMapping("/register")
    public R register(@RequestBody User user) {
        userService.save(user);
        return RUtils.result("注册成功");
    }

    @RequestMapping("/isExist")
    public R isExist(@RequestBody User user) {
        User one = userService.getOne(new QueryWrapper<User>().eq("username", user.getUserName()));
        if(one!=null){
            return RUtils.result("用户名已存在");
        }else{
            return RUtils.result("用户名不存在");
        }
    }

}
