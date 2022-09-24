package com.atguigu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {

    @Test
    public void  test1(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean b = bCryptPasswordEncoder.matches("123456", "$2a$10$pK6CFWA.UWlDQyX4oR4gx.nVmafK9OjevKIu2VX0cDaJUQ8gpYhz.");
        System.out.println("b = " + b);
    }
}
