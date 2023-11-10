package com.sudoerrr.mushroom.core.controller;

import com.sudoerrr.mushroom.core.service.FileService;
import com.sudoerrr.mushroom.core.wrapper.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping()
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     *
     * @param response
     * @param fileName
     * @return
     */
    @GetMapping("/download")
    public ResultWrapper downloadFile(HttpServletResponse response, @RequestParam("imageName") String fileName) {
        boolean res = fileService.downloadFile(fileName, response);
        return ResultWrapper.success(res);
    }

    @GetMapping("/lists")
    public ResultWrapper list() {
        return ResultWrapper.success(fileService.getFiles());
    }

    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResultWrapper upload(@RequestParam("fileName") MultipartFile file) {
        log.info("save file name {}", file.getOriginalFilename());
        String filePath = fileService.uploadFile(file);
        return ResultWrapper.success(filePath);
    }
}
