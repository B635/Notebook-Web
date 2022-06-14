package com.b635.notebook.Controller;

import com.b635.notebook.Model.entity.User;
import com.b635.notebook.Service.impl.UserServiceImpl;
import com.b635.notebook.utils.R;
import com.b635.notebook.utils.RUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @ResponseBody
    @PostMapping("/login")
    public R login(@RequestBody User user) {
       User result = userService.getUser(user.getUsername());
       int r = 0;
       if (Objects.equals(result.getPassword(), user.getPassword())) {
           r = 1;
       } else {
           r = -1;
       }
        return RUtils.commonFailOrNot(r, "登陆");
    }
}
