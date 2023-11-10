package com.sudoerrr.mushroom.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoerrr.mushroom.core.pojo.Mushroom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface MushroomMapper extends BaseMapper<Mushroom> {
}
