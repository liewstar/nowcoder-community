package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;

public interface RegisterService {


    void register(User user);

    int activation(int userId, String code);
}
