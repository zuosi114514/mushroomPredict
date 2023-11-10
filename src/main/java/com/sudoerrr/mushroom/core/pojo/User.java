package com.sudoerrr.mushroom.core.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}