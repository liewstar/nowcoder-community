package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer() {
        Properties properties=new Properties();
        //图片宽度
        properties.setProperty("kaptcha.image.width","100");
        //图片高度
        properties.setProperty("kaptcha.image.height","40");
        //字号大小
        properties.setProperty("kaptcha.textproducer.font.size","32");
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        //字符范围
        properties.setProperty("kaptcha.textproducer.char.String","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        //验证码长度
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //干扰类
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");//干扰

        DefaultKaptcha kaptcha=new DefaultKaptcha();
        Config config=new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
