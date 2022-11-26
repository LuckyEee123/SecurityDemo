package com.mai.security.service;

import com.mai.security.common.Result;
import com.mai.security.pojo.User;

public interface LoginService {
    Result login(User user);

    Result logout();
}
