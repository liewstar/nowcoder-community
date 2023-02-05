package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginUser;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        //获取token,解析获取token
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if(!passwordEncoder.matches(oldPassword,loginUser.getUser().getPassword())) {
            throw new RuntimeException("原密码输入有误，请修正后重试");
        }
        //获取userId
        int userId = loginUser.getUser().getId();
        userMapper.updatePassword(userId,passwordEncoder.encode(newPassword));
    }
}
