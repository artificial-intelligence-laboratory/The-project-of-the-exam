package com.gcc.fns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcc.fns.model.entity.SessionList;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author snail
 * @create 2022-10-30  17:16
 */
public interface SessionListMapper extends BaseMapper<SessionList> {

    //根据id查询会话列表
    SessionList selectByPrimaryKey(Long id);

    //已建立会话的用户
    Long selectIdByUser(@Param("fromId") Long fromId, @Param("toUserId") Long toUserId);

    //查询可建立会话的用户
    List<Long> selectUserIdByUserId(Long id);

    //查询已建立会话的用户
    List<SessionList> selectByUserId(Long id);

    //新建会话
    int insert(SessionList sessionList);

    void addUnReadCount(@Param("userId") Long userId, @Param("toUserId") Long toUserId, String lastContent, Date lastTime);

    //删除会话
    int deleteByPrimaryKey(Long id);

    void updateUnReadCount(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

}
