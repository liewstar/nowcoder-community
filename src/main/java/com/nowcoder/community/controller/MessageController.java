package com.nowcoder.community.controller;

import com.nowcoder.community.entity.LoginUser;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.MessageService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.utils.JsonResult;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/letter/list")
    public JsonResult<List<Map<String, Object>>> getLetterList(Page page) {
        // 会话列表
        List<Message> conversationList = messageService.selectConversationsByPage(page);
        List<Map<String, Object>> conversations = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        if(conversationList != null) {
            for(Message message : conversationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("conversation", message);
                map.put("unreadCount", messageService.selectLetterUnRead(message.getConversationId()));
                int target = userId == message.getFromId() ? message.getToId() : message.getFromId();
                map.put("target",userService.findUserById(userId));
                conversations.add(map);
            }
        }
        return JsonResult.ok(conversations);
    }

    @GetMapping("/letter/detail/{conversationId}")
    public JsonResult<List<Map<String, Object>>> getLetterDetail(@PathVariable("conversationId") String conversationId, Page page) {
        List<Message> letterList = messageService.selectLettersByPage(conversationId, page);
        List<Map<String, Object>> letters = new ArrayList<>();
        if (letterList != null) {
            for (Message message : letterList) {
                Map<String, Object> map = new HashMap<>();
                map.put("letter", message);
                map.put("fromUser", userService.findUserById(message.getFromId()));
                letters.add(map);
            }
        }
        return JsonResult.ok(letters);
    }
}
