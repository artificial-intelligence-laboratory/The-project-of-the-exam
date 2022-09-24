package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.UserFollowDao;
import com.atguigu.entity.UserFollow;
import com.atguigu.service.DictService;
import com.atguigu.service.UserFollowService;
import com.atguigu.vo.UserFollowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Reference
    private DictService dictService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long id, Long houseId) {
        //新建一个userFollow对象
        UserFollow userFollow = new UserFollow();
        //把userId和houseId设置进去
        userFollow.setUserId(id);
        userFollow.setHouseId(houseId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        //调用userFollowedDao查询用户是否关注该房源
       Integer count= userFollowDao.getCountByUserIdAndHouseId(userId,houseId);
        //判断数量来判断是否关注该房源
        if (count>0){
            return true;
        }else {
            return false;
        }
    }

    //分页查询我关注的房源
    @Override
    public PageInfo<UserFollowVo> findMyFollowPageList(Integer pageNum, Integer pageSize, Long userId) {
        //开启分页
        PageHelper.startPage(pageNum,pageSize);
        //调用dao分页查询
        Page<UserFollowVo> page=userFollowDao.findMyFollowPageList(userId);
        //遍历page
        for (UserFollowVo userFollowVo : page) {
            //获取户型
            String houseTypeName = dictService.getNameById(userFollowVo.getHouseTypeId());
            //获取楼层
            String floorName = dictService.getNameById(userFollowVo.getFloorId());
            //获取朝向
            String DirectionName = dictService.getNameById(userFollowVo.getDirectionId());
            //赋值给userFollowVo对象
            userFollowVo.setHouseTypeName(houseTypeName);
            userFollowVo.setFloorName(floorName);
            userFollowVo.setDirectionName(DirectionName);
        }
        return new PageInfo<>(page,5);
    }

    @Override
    public void canselFollow(Long id) {
        userFollowDao.delete(id);
    }
}
