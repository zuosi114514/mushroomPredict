package com.sudoerrr.mushroom.core.pojo;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Data
public class MarkerDto {
    @NotNull
    @NotBlank(message = "marker name must not be blank")
    private String name;
    @NotNull
    @NotNull(message = "marker img name must not be blank")
    private String imageName;
    private Double longitude;
    private Double latitude;
    private String detailAddress;
    private String description;

}
