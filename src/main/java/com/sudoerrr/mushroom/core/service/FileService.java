package com.sudoerrr.mushroom.core.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {

    public String uploadFile(MultipartFile file);

    public List<String> getFiles();

    public String getFileUrl(String fileName);

    public boolean downloadFile(String fileName, HttpServletResponse response);

    public MultipartFile downloadFile(String fileName) throws IOException;

    public Map<String, String> getPictureImf(String fileName) ;
}
