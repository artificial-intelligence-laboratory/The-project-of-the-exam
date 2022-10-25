package com.gcc.fns.utils;

import com.gcc.fns.common.utils.SendMailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author xiaozhi
 * @description 测试邮箱发送工具类
 * @create 2022-10-2022/10/24 19:27
 */
@SpringBootTest
public class SendMailUtilTest {

    @Resource
    private SendMailUtil sendMailUtil;

    @Test
    public void test(){
        sendMailUtil.sendCodeToMail("1596971466@qq.com", "123456");
    }
}
