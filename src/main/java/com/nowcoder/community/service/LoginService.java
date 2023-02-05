package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;

import java.util.HashMap;

public interface LoginService {
    HashMap<String,Object> login(User user);
}
