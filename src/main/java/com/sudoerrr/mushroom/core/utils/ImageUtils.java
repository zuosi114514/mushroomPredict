package com.sudoerrr.mushroom.core.utils;

import com.sudoerrr.mushroom.core.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.sudoerrr.mushroom.core.utils.Constant.DOUBLE_IMAGE_SIZE;
import static com.sudoerrr.mushroom.core.utils.Constant.IMAGE_SIZE;

public class ImageUtils {

    @Autowired
    FileService fileService;

    public static float[][][][] processImage(MultipartFile img) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(img.getBytes());
        BufferedImage image = ImageIO.read(bis);

        int width = IMAGE_SIZE;
        int height = IMAGE_SIZE;
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        float[][][][] inputTensor = new float[1][3][height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = resizedImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int lg = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                inputTensor[0][0][y][x] = (float) ((float) r / DOUBLE_IMAGE_SIZE);
                inputTensor[0][1][y][x] = (float) ((float) lg / DOUBLE_IMAGE_SIZE);
                inputTensor[0][2][y][x] = (float) ((float) b / DOUBLE_IMAGE_SIZE);
            }
        }

        // 标准化图像
        double meanR = 0.485D;
        double meanG = 0.456D;
        double meanB = 0.406D;
        double stdR = 0.229D;
        double stdG = 0.224D;
        double stdB = 0.225D;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                inputTensor[0][0][y][x] = (float) ((inputTensor[0][0][y][x] - meanR) / stdR);
                inputTensor[0][1][y][x] = (float) ((inputTensor[0][1][y][x] - meanG) / stdG);
                inputTensor[0][2][y][x] = (float) ((inputTensor[0][2][y][x] - meanB) / stdB);
            }
        }
        return inputTensor;
    }

}

