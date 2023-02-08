package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/discuss")
public class DisucssPostController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public JsonResult<String> addDiscussPost(DiscussPost post) {
        discussPostService.insertDisucssPost(post);
        return JsonResult.ok("发布成功");
    }

    @GetMapping("/detail/{discussPostId}")
    public JsonResult<HashMap<String,Object>> getDiscussPost(@PathVariable("discussPostId") int discussPostId) {
        DiscussPost post = discussPostService.selectDiscussPostById(discussPostId);
        User user = userService.findUserById(post.getUserId());
        HashMap<String,Object> map=new HashMap<>();
        map.put("post",post);
        map.put("user",user);
        return JsonResult.ok(map);
    }
}
