package com.sudoerrr.mushroom.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sudoerrr.mushroom.core.pojo.Marker;
import com.sudoerrr.mushroom.core.pojo.MarkerDto;
import com.sudoerrr.mushroom.core.wrapper.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/marker")
public class MarkerController extends BaseController{

    @RequestMapping("/list")
    public ResultWrapper list(Model model) {
        List<Marker> MarkerList = markerService.getBaseMapper().selectList(new QueryWrapper<>());
        model.addAttribute("MarkerList",MarkerList);
        model.addAttribute("urlFlag","index");
        return ResultWrapper.success(MarkerList);
    }

    /**
     *
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResultWrapper proImage(@Validated @RequestBody MarkerDto dto) {
        Marker marker = new Marker();
        marker.setName(dto.getName());
        marker.setImg(dto.getImageName());
        marker.setDescription(dto.getDescription());
        marker.setDetailAddress(dto.getDetailAddress());
        if(dto.getLatitude() != null && dto.getLongitude() != null) {
            marker.setLatitude(dto.getLatitude());
            marker.setLongitude(dto.getLongitude());
        } else {
            Map<String, String> map = fileService.getPictureImf(dto.getImageName());
            if(map.get("latitude") != null && map.get("longitude") != null) {
                marker.setLatitude(Double.parseDouble(map.get("latitude")));
                marker.setLongitude(Double.parseDouble(map.get("longitude")));
            } else return ResultWrapper.fail(412,"location undefined");
        }
        markerService.save(marker);
        log.info("save marker name:" + marker.getName());
        return ResultWrapper.success(marker.getName());
    }
}
