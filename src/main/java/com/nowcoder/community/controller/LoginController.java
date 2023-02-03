package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.CommunityConstant;
import com.nowcoder.community.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
public class LoginController implements CommunityConstant {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer kaptcha;

    @Autowired
    private UserService userService;




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
