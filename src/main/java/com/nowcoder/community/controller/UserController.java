package com.nowcoder.community.controller;

import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/uploadImg")
    public JsonResult<String> uploadImg(MultipartFile img) {
        return null;
    }

    @PostMapping("/changePassword")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword) {
        userService.changePassword(oldPassword,newPassword);
        return JsonResult.ok(null);
    }

    @PostMapping("/test")
    public String test() {
        return "1234";
    }
}
