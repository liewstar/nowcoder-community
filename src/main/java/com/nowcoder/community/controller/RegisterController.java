package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.HttpCodeEnum;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.CommunityConstant;
import com.nowcoder.community.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController implements CommunityConstant {
    @Autowired
    private UserService userService;

    //注册账号
    @PostMapping("/register")
    public JsonResult<String> register(User user) {
        userService.register(user);
        return JsonResult.ok("我们已经向您的邮箱发送激活链接了，请尽快前去激活");
    }

    //激活账号
    @GetMapping("/activation/{userId}/{code}")
    public JsonResult<String> activation(@PathVariable("userId") int userId,@PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        if(result == ACTIVATION_SUCCESS) {
            return JsonResult.ok(HttpCodeEnum.SUCCESS.getMsg());
        }else if(result == ACTIVATION_REPEAT) {
            return JsonResult.fail(HttpCodeEnum.ACTIVATION_REPEAT.getCode(), HttpCodeEnum.ACTIVATION_REPEAT.getMsg());
        }else {
            return JsonResult.fail(HttpCodeEnum.ACTIVATION_FAILURE.getCode(), HttpCodeEnum.ACTIVATION_FAILURE.getMsg());
        }
    }
}
