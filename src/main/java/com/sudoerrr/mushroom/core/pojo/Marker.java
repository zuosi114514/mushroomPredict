package com.sudoerrr.mushroom.core.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("map")
@Data
public class Marker {
    private Integer id;
    private String name;
    private String img;
    private Double longitude;
    private Double latitude;
    private String detailAddress;
    private String description;
}
