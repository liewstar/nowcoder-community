package com.nowcoder.community.service;

import com.nowcoder.community.entity.Comment;

import java.util.List;

public interface CommentService {

        List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset,int limit);

        int selectCountByEntity(int entityType,int entityId);

        int insertComment(Comment comment);
}
