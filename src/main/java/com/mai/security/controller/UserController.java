package com.mai.security.controller;

import com.mai.security.common.Result;
import com.mai.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/userList")
    public Result getUserList() {
        return Result.success(userService.list());
    }


}
