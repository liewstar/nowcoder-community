package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    /**
     * 分页查询帖子列表
     * @param userId 用户id，动态查询，如果没有的话就查询所有帖子
     * @param offset 当前页的起始行
     * @param limit  每页显示条数
     * @return
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    /**
     * 查询帖子总条数
     * @param userId 动态查询
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);

}
