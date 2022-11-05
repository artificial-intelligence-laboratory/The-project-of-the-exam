package com.gcc.fns.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcc.fns.model.entity.AppUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/24 0:29
 */
public interface AppUserMapper extends BaseMapper<AppUser> {

    //查询id
    AppUser selectByPrimaryUserKey(Long id);

    //通过id查询头像
    String selectByIdAvatar(Long id);

    //查询消息列表
    List<AppUser> getCloudList(@Param("list") List<Long> list);


}
