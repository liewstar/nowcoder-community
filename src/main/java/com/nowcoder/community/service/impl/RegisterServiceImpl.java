package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.RegisterService;
import com.nowcoder.community.utils.CommunityConstant;
import com.nowcoder.community.utils.CommunityUtil;
import com.nowcoder.community.utils.MailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class RegisterServiceImpl implements CommunityConstant, RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private MailClient mailClient;

    @Resource
    private TemplateEngine templateEngine;

    //域名
    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void register(User user) {
        if(Objects.isNull(user.getUsername()) || Objects.isNull(user.getPassword()) || Objects.isNull(user.getEmail())) {
            throw new RuntimeException("请完善注册信息");
        }
        User u = userMapper.selectByName(user.getUsername());
        User user2 = userMapper.selectByEmail(user.getEmail());
        //验证账户
        if (u != null) {
            //但是未激活
            if (u.getStatus() == 0) {
                //删除未激活的原账号
                userMapper.deleteByUserName(u.getUsername());
                //注册现有新账号
                setRegister(user);
                userMapper.insertUser(user);
                //发送注册邮件
                sendActivationMail(user);
            } else {
                throw new RuntimeException("账户已存在");
            }
        } else {
            //账号不存在，验证邮箱
            if (user2 != null) {
                throw new RuntimeException("邮箱已存在");
            } else {
                setRegister(user);
                userMapper.insertUser(user);
                sendActivationMail(user);
            }
        }
    }

    //激活账号
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if(user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        }else if(user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FAILURE;
        }
    }

    public User setRegister(User user) {
        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        return user;
    }


    // 激活邮件
    public void sendActivationMail(User user){
        Context context=new Context();
        context.setVariable("email",user.getEmail());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/"+user.getActivationCode();
        context.setVariable("url",url);
        String content=templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);
    }




}
