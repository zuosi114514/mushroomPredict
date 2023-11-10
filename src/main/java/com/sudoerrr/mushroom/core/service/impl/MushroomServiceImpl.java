package com.sudoerrr.mushroom.core.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudoerrr.mushroom.core.dao.MushroomMapper;
import com.sudoerrr.mushroom.core.pojo.Mushroom;
import com.sudoerrr.mushroom.core.service.MushroomService;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class MushroomServiceImpl extends ServiceImpl<MushroomMapper, Mushroom> implements MushroomService {
    @Override
    public List<Mushroom> selectMushroomByPage(Integer pageNo, Integer pageSize, @Nullable String chineseName) {

        IPage<Mushroom> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Mushroom> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("name");
        wrapper.like(StrUtil.isNotBlank(chineseName), "chinese_name", chineseName);
        IPage<Mushroom> list = this.page(page, wrapper);
        return list.getRecords();
    }

    @Override
    public Mushroom GetOneNyName(String name) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return this.getOne(queryWrapper);
    }
}
