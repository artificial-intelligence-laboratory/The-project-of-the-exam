package com.gcc.fns.mapper;

import com.gcc.fns.pojo.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description
 * @create 2022-10-2022/10/24 20:07
 */
@SpringBootTest
public class AppUserMapperTest {

    @Resource
    private AppUserMapper appUserMapper;

    @Test
    public void test(){
        AppUser appUser = appUserMapper.selectById(1L);
        System.out.println(appUser);
    }
}
