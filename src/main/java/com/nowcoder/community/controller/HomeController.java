package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.enums.HttpCodeEnum;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.RegisterService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @GetMapping("/dicussList")
    public JsonResult<List> getIndexPage(Page page) {
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");
//test git2
        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
            return JsonResult.ok(discussPosts);
        }
        return JsonResult.fail(HttpCodeEnum.NOT_EXIST.getCode(), HttpCodeEnum.NOT_EXIST.getMsg());
    }

}
