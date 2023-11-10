package com.sudoerrr.mushroom;

import ai.onnxruntime.OrtEnvironment;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;
import com.sudoerrr.mushroom.core.dao.MushroomMapper;
import com.sudoerrr.mushroom.core.dao.UserMapper;
import com.sudoerrr.mushroom.core.pojo.Mushroom;
import com.sudoerrr.mushroom.core.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


@Slf4j
@SpringBootTest
class MushroomApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MushroomMapper mushroomMapper;

    @Test
    public void testSelect1() {
        System.out.println(("----- selectAll method test ------"));
        List<Mushroom> userList = mushroomMapper.selectList(null);
//        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
//        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
        System.out.println("1");
    }




    @Test
    public void fetchPictureImf() throws ImageProcessingException, Exception {
        File file = new File("C:\\Users\\SHIT\\Desktop\\新建文件夹\\IMG_20231015_182032.jpg");
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String tagName = tag.getTagName();  //标签名
//                    log.error("标签名： " + tagName);
                    String desc = tag.getDescription(); //标签信息
                    if (tagName.equals("Image Height")) {
                        System.out.println("图片高度: " + desc);
                    } else if (tagName.equals("Image Width")) {
                        System.out.println("图片宽度: " + desc);
                    } else if (tagName.equals("Date/Time Original")) {
                        System.out.println("拍摄时间: " + desc);
                    } else if (tagName.equals("GPS Latitude")) {
                        System.err.println("纬度 : " + desc);
                        System.err.println("纬度(度分秒格式) : " + pointToLatlong(desc));
                    } else if (tagName.equals("GPS Longitude")) {
                        System.err.println("经度: " + desc);
                        System.err.println("经度(度分秒格式): " + pointToLatlong(desc));
                    }
                }

        }

    }

    /**
     * 经纬度格式  转换为  度分秒格式 ,如果需要的话可以调用该方法进行转换
     *
     * @param point 坐标点
     * @return
     */
    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }


}
