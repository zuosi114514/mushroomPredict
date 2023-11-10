package com.sudoerrr.mushroom.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sudoerrr.mushroom.core.pojo.Mushroom;

import javax.annotation.Nullable;
import java.util.List;


public interface MushroomService extends IService<Mushroom> {
    public List<Mushroom> selectMushroomByPage(Integer pageNo, Integer pageSize, @Nullable String chineseName);

    public Mushroom GetOneNyName(String name);
}
