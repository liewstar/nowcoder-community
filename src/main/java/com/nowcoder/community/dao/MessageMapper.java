package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    // 查询私信会话列表数据,针对每个会话，只显示最新的一条私信
    List<Message> selectConversations(int userId, int offset, int limit);

    // 查询私信列表总行数(用户的会话数量),为了分页
    int selectConversationCount(int userId);

    // 查询私信详情(某个会话包含的私信列表)
    List<Message> selectLetters(String conversationId, int offset, int limit);

    //查询私信详情消息总行数，为了分页
    int selectLetterCount(String conversationId);

    //查询未读消息记录数（2种  某个人的和总体的）
    /**
     *
     * @param userId
     * @param conversationId 动态拼 实现两种业务
     * @return
     */
    int selectLetterUnreadCount(int userId, String conversationId);
}
