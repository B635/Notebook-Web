package com.b635.notebook.Controller;

import com.b635.notebook.Model.entity.User;
import com.b635.notebook.Model.vo.Result;
import com.b635.notebook.Service.impl.UserServiceimpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceimpl userService;

    @RequestMapping("/login")
    public Result login(@RequestBody User user) {
        Result result = new Result();
        User one = userService.getOne(
                new QueryWrapper<User>()
                        .eq("username", user.getUserName())
                        .eq("password", user.getPassWord()));
        if(one!=null){
            return result.ok(null,"登录成功");
        }else{
            return result.fail(null,"用户名或密码错误");
        }
    }

    @RequestMapping("/register")
    public Result register(@RequestBody User user) {
        userService.save(user);
        return Result.ok(null,"success");
    }

    @RequestMapping("/isExist")
    public Result isExist(@RequestBody User user) {
        User one = userService.getOne(new QueryWrapper<User>().eq("username", user.getUserName()));
        if(one!=null){
            return Result.fail(null,"用户名已存在");
        }else{
            return Result.ok(null,"用户名不存在");
        }
    }

}
