package com.mai.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mai.security.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}