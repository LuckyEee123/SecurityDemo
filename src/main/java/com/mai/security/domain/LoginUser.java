package com.mai.security.domain;

import cn.hutool.core.collection.ListUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mai.security.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
// 防止序列化异常
@JsonIgnoreProperties({"enabled","accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities", "permsList"})
public class LoginUser implements UserDetails {

    private User user;

    // 权限信息
    private List<String> perms;

    public LoginUser(User user, List<String> perms) {
        this.user = user;
        this.perms = perms;
    }


    private List<SimpleGrantedAuthority> permsList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permsList != null) {
            return permsList;
        }
        // 把perms中的String类型的权限信息封装成SimpleGrantedAuthority对象
        permsList = new ArrayList<>();
        for (String perm : perms) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(perm);
            permsList.add(authority);
        }

//permsList = perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return permsList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
