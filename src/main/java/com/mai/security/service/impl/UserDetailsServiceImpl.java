package com.mai.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mai.security.domain.LoginUser;
import com.mai.security.pojo.User;
import com.mai.security.exception.ServiceException;
import com.mai.security.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户名或密码错误！");
        }
        // 查询对应的权限信息
        List<String> perms = new ArrayList<>(Arrays.asList("guest","admin","user"));

        // 把数据封装成UserDetails返回
        return new LoginUser(user, perms);
    }
}
