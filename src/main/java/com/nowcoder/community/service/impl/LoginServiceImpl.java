package com.nowcoder.community.service.impl;

import com.alibaba.fastjson2.JSON;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginUser;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LoginService;
import com.nowcoder.community.utils.JwtUtil;
import com.nowcoder.community.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public HashMap<String,Object> login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        //authenticationManager会自动调用UserDetailsService
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userId生成token
        //获取认证主体
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        int userId = loginUser.getUser().getId();
        //创建用户专属token
        String jwt = JwtUtil.createJWT(String.valueOf(userId));
        //用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token和userinfo信息封装后返回
        HashMap<String,Object> data = new HashMap<>();
        Object json = JSON.toJSON(loginUser.getUser());
        data.put("token",jwt);
        data.put("userinfo",json);
        return data;
    }

    @Override
    public void logout() {
        //获取token,解析获取token
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        System.out.println(loginUser.toString());
        //获取userId
        int userId = loginUser.getUser().getId();
        //删除缓存中的userId
        redisCache.deleteObject("login:"+userId);
    }
}
