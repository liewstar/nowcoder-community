package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LoginService;
import com.nowcoder.community.service.RegisterService;
import com.nowcoder.community.utils.CommunityConstant;
import com.nowcoder.community.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@RestController
public class LoginController implements CommunityConstant {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer kaptcha;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public JsonResult<HashMap<String,Object>> login(User user) {
        if(!StringUtils.hasText(user.getUsername())) {
            throw new RuntimeException("请输入用户名");
        }
        HashMap<String, Object> data = loginService.login(user);
        return JsonResult.ok(data);
    }

    @PostMapping("/logout")
    public JsonResult<Void> logout() {
        loginService.logout();
        return JsonResult.ok(null);
    }


    //验证码
    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void kaptcha(HttpServletResponse response, HttpSession session) {
        //生成验证码
        String text = kaptcha.createText();
        //将验证码存入session
        session.setAttribute("kaptcha", text);
        //根据验证码生成验证码图片
        BufferedImage image = kaptcha.createImage(text);
        //将图片发送到浏览器，设置请求头
        response.setContentType("image/png");
        try {
            //响应输出字节流（图片）
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("验证码响应失败" + e.getMessage());
        }
    }
}
