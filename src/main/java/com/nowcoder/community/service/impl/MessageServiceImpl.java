package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.entity.LoginUser;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.MessageService;
import com.nowcoder.community.utils.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public List<Message> selectConversationsByPage(Page page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        int rows = messageMapper.selectConversationCount(userId);
        page.setLimit(5);
        page.setRows(rows);
        List<Message> messages = messageMapper.selectConversations(userId, page.getOffset(), page.getLimit());
        return messages;
    }

    @Override
    public List<Message> selectLettersByPage(String conversationId, Page page) {
        int rows = messageMapper.selectLetterCount(conversationId);
        page.setLimit(5);
        page.setRows(rows);
        List<Message> messages = messageMapper.selectLetters(conversationId, page.getOffset(), page.getLimit());
        return messages;
    }

    @Override
    public int selectLetterUnRead(String conversationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUser().getId();
        int count = messageMapper.selectLetterUnreadCount(userId, conversationId);
        return count;
    }
}
