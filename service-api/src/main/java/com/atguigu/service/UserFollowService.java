package com.atguigu.service;

import com.atguigu.entity.UserFollow;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService extends BaseService<UserFollow> {
    //关注房源
    void follow(Long id, Long houseId);
    //判断是否关注房源
    Boolean isFollowed(Long id, Long id1);
    //分页查询我的关注房源
    PageInfo<UserFollowVo> findMyFollowPageList(Integer pageNum, Integer pageSize, Long userId);
    //取消关注
    void canselFollow(Long id);
}
