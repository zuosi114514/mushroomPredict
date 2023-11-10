package com.sudoerrr.mushroom.core.controller;

import com.sudoerrr.mushroom.core.pojo.Mushroom;
import com.sudoerrr.mushroom.core.wrapper.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/sudoerrr")
public class MushroomController extends BaseController{


    /**
     *
     * @param chineseName
     * @param pageNum
     * @param pageSize
     */
    @GetMapping("/list")
    public ResultWrapper list(@RequestParam(value = "chineseName", defaultValue = "") String chineseName,
                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) throws UnsupportedEncodingException {
        chineseName = URLDecoder.decode(chineseName, "UTF-8");
        List<Mushroom> mushrooms = mushroomService.selectMushroomByPage(pageNum, pageSize,chineseName);
        return ResultWrapper.success(mushrooms);
    }

}
