package com.nowcoder.community.service;

import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;

import java.util.List;

public interface MessageService {

    List<Message> selectConversationsByPage(Page page);

    List<Message> selectLettersByPage(String conversationId, Page page);

    int selectLetterUnRead(String conversationId);

}
