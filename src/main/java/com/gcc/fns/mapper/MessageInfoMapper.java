package com.gcc.fns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcc.fns.model.entity.MessageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author snail
 * @create 2022-10-29  18:19
 */
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {

    //查询对应会话
    List<MessageInfo> selectMsgList(@Param("fromUserId") Long fromUserId,@Param("toUserId") Long toUserId);

    //添加会话
    int insert(MessageInfo msgInfo);

    //修改
    void msgRead(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

}
