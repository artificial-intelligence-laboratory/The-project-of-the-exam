package com.gcc.fns;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gcc.fns")
public class GccFnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GccFnsApplication.class, args);
    }

}
