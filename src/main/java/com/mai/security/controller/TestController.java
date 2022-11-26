package com.mai.security.controller;

import com.mai.security.common.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('guest')")
    public Result hello() {
        return Result.success("hello");
    }

    @RequestMapping("/kcehchalth")
    public Result healthCheck() {
        return Result.success("Server is Running");
    }

    @RequestMapping("/nobody")
    @PreAuthorize("hasAuthority('nobody')")
    public Result nobodyCanSee() {
        return Result.success();
    }

}
