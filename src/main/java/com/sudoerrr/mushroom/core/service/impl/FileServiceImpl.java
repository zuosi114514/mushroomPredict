package com.sudoerrr.mushroom.core.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;
import com.sudoerrr.mushroom.core.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${my-config.file-path}")
    private String DOWNLOAD_DIRECTORY;

    public String uploadFile(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        filename = new Date().getTime() + filename.substring(filename.lastIndexOf("."));
        File uploadDir = new File(DOWNLOAD_DIRECTORY);
        if(!uploadDir.exists()) uploadDir.mkdir();
        File file = new File(DOWNLOAD_DIRECTORY + "/"  + filename);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("save file error,{}", e.getMessage());
            return "";
        }
        return getFileUrl(filename);
    }

    public List<String> getFiles() {
        List<String> fileUrls = new ArrayList<>();

        File file = new File(DOWNLOAD_DIRECTORY);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    fileUrls.add(getFileUrl(file1.getName()));
                }
            }
        }
        return fileUrls;
    }

    public String getFileUrl(String fileName) {
            String fileUrl = DOWNLOAD_DIRECTORY + fileName;
            log.info("fileUrl:" + fileUrl);
            return fileUrl;
    }

    public boolean downloadFile(String fileName, HttpServletResponse response) {
        File file = new File(DOWNLOAD_DIRECTORY + fileName);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                response.setHeader("Content-Disposition", "attachment;filename="+ fileName
                        +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
                ServletOutputStream outputStream = response.getOutputStream();
                FileCopyUtils.copy(fileInputStream, outputStream);
                return true;
            } catch (IOException e) {
                log.error("download file error: {}", e.getMessage());
                return false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.info("fileNotFound");
        return false;
    }

    public MultipartFile downloadFile(String imageName) throws IOException {
        String filePath = DOWNLOAD_DIRECTORY + imageName;

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + imageName);
        }

        Path path = Paths.get(filePath);
        String contentType = Files.probeContentType(path);
        byte[] fileContent = Files.readAllBytes(path);

        return new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(fileContent);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                FileCopyUtils.copy(fileContent, dest);
            }
        };
    }


    public Map<String, String> getPictureImf(String fileName) {
        Map<String, String> res = new HashMap<String, String>();

        InputStream inputStream = null;
        try {
            MultipartFile file = this.downloadFile(fileName);
            inputStream = file.getInputStream();
            // 读取图片文件的元数据
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream, file.getSize());
            // 获取GPS信息的目录
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String tagName = tag.getTagName();  //标签名
//                    log.error("标签名： " + tagName);
                    String desc = tag.getDescription(); //标签信息
                    if (tagName.equals("Image Height")) {
                        res.put("height",desc);
                    } else if (tagName.equals("Image Width")) {
                        res.put("width",desc);
                    } else if (tagName.equals("Date/Time Original")) {
                        res.put("captureTime",desc);
                    } else if (tagName.equals("GPS Latitude")) {
//                        res.put("纬度:" , desc);
                        res.put("latitude" , pointToLatlong(desc));
                    } else if (tagName.equals("GPS Longitude")) {
//                        res.put("经度:" , desc);
                        res.put("longitude" , pointToLatlong(desc));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}

            }
        }
        return res;
    }

    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }



}
