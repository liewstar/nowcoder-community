package com.nowcoder.community.controller;

import com.nowcoder.community.service.UploadService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadImg")
    public JsonResult<String> uploadImg(@RequestParam("file") MultipartFile img) {
        System.out.println(img.toString());
        String url = uploadService.uploadImg(img);
        return JsonResult.ok(url);
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
