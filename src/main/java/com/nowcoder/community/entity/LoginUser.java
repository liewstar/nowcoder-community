package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private User user;

    //返回授予用户的权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //验证密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //验证用户名
    @Override
    public String getUsername() {
        return user.getPassword();
    }

    //验证账户过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //验证账户锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //验证密码过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //验证用户是启用还是禁用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
