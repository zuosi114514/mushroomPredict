package com.sudoerrr.mushroom.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sudoerrr.mushroom.core.dao.MarkerMapper;
import com.sudoerrr.mushroom.core.pojo.Marker;
import com.sudoerrr.mushroom.core.service.MarkerService;
import org.springframework.stereotype.Service;

@Service
public class MarkerServiceImpl extends ServiceImpl<MarkerMapper, Marker> implements MarkerService {
}
