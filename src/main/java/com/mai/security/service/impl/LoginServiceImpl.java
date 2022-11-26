package com.mai.security.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mai.security.config.SecurityConfig;
import com.mai.security.domain.LoginUser;
import com.mai.security.common.Result;
import com.mai.security.pojo.User;
import com.mai.security.exception.ServiceException;
import com.mai.security.service.LoginService;
import com.mai.security.utils.JwtUtil;
import com.mai.security.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {



    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public Result logout() {

        // 获取SecurityContextHolder中的userId
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        // 删除redis中的key
        redisUtils.deleteObject("login:" + userId);
        return Result.success("注销成功");
    }

    @Override
    public Result login(User user) {
        // AuthenticationManager authenticate进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证失败，给出提示
        if (Objects.isNull(authenticate)) {
            throw new ServiceException("登录失败！");
        }
        // 认证通过，使用userId生成一个jwt,存入Result返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        // 把完整的用户信息存入redis userId作为KEY
        redisUtils.setCacheObject("login:"+userId, loginUser);
        return Result.success("登陆成功",map);
    }
}
