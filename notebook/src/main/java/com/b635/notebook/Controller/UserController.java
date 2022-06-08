package com.b635.notebook.Controller;


import com.b635.notebook.Bean.User;
import com.b635.notebook.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        return userService.selectUserName(user);
    }
}
