package com.sudoerrr.mushroom.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sudoerrr.mushroom.core.pojo.User;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface UserMapper extends BaseMapper<User> {
}
