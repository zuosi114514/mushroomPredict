package com.sudoerrr.mushroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//        (exclude= {DataSourceAutoConfiguration.class})
//@ComponentScan(basePackages = "com.identify.mushroom")
//@EnableAutoConfiguration
//@MapperScan("com.sudoerrr.mushroom.core.dao")
public class MushroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MushroomApplication.class, args);
    }

}
