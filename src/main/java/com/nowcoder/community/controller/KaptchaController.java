package com.nowcoder.community.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KaptchaController {

    @GetMapping("/imgCode")
    public void createCode() {

    }
}
