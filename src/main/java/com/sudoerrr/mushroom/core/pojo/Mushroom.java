package com.sudoerrr.mushroom.core.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("mushroom_list")
@Data
public class Mushroom {
    private Integer id;
    private String name;
    private String chineseName;
    private String poisonous;
    private String habitat;
    private String environment;
    private String description;
    private String recipe;
    private String note;
    private String img;
}
