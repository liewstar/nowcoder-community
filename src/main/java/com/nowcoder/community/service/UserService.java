package com.nowcoder.community.service;

import com.nowcoder.community.entity.User;

public interface UserService {
    User findUserById(int id);

    void changePassword(String oldPassword,String newPassword);
}
