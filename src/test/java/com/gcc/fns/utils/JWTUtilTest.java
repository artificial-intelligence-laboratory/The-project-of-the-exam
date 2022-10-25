package com.gcc.fns.utils;

import com.gcc.fns.common.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description 测试jwt工具类
 * @create 2022-10-2022/10/24 21:34
 */
@SpringBootTest
public class JWTUtilTest {

    @Resource
    private JWTUtil jwtUtil;

    @Test
    public void test() throws InterruptedException {
        String token = jwtUtil.createToken(1L, "xiaozhi");
        System.out.println(token);
        Thread.sleep(1000);
        String token2 = jwtUtil.createToken(1L, "xiaozhi");
        System.out.println(token2);
    }
}
