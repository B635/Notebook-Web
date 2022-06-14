package com.b635.notebook.Controller;

import com.b635.notebook.Model.entity.User;
import com.b635.notebook.Service.impl.UserServiceImpl;
import com.b635.notebook.utils.R;
import com.b635.notebook.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public R login(@RequestBody User user) {
       Authentication auth = userService.getAuth();
        if (Objects.isNull(auth)) {
            return RUtils.result("登陆失败");
        }
        return RUtils.result("用户 [" + auth.getName() + "] 已登录");
    }
}
