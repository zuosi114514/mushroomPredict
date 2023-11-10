package com.sudoerrr.mushroom.core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sudoerrr.mushroom.core.service.FileService;
import com.sudoerrr.mushroom.core.service.MarkerService;
import com.sudoerrr.mushroom.core.service.MushroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {

    @Autowired
    FileService fileService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    MarkerService markerService;

    @Autowired
    MushroomService mushroomService;

    public Page getPage(){
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        int size = ServletRequestUtils.getIntParameter(request, "size", 10);

        return new Page(current, size);
    }
}
