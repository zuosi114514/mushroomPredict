package com.sudoerrr.mushroom.core.controller;

import com.sudoerrr.mushroom.core.pojo.Mushroom;
import com.sudoerrr.mushroom.core.wrapper.ResultWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import ai.onnxruntime.*;
import ai.onnxruntime.OrtSession.SessionOptions;
import ai.onnxruntime.OrtEnvironment;
import javax.annotation.Nullable;
import static com.sudoerrr.mushroom.core.utils.FileUtil.readJsonFile;
import static com.sudoerrr.mushroom.core.utils.ImageUtils.processImage;


@Slf4j
@RestController
public class OnnxController extends BaseController{

    private OrtEnvironment env;
    private OrtSession session;
    private Map<String, Object> classes;


    public OnnxController() {
        try {
            String modelPath = "C:\\Users\\SHIT\\Desktop\\mushroom_vit.onnx";
            env = OrtEnvironment.getEnvironment();
            session = env.createSession(modelPath, new SessionOptions());
            classes = readJsonFile("C:\\Users\\SHIT\\Desktop\\class_indices.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param imageFile
     * @param fileName
     * @return
     */
    @GetMapping("/predict")
    public ResultWrapper predict(@RequestParam("imageFile") @Nullable MultipartFile imageFile, @RequestParam("imageName") @Nullable String fileName) {
        if(imageFile == null && (fileName == null || Objects.equals(fileName, ""))) {
            return ResultWrapper.fail(414, "params must not be null");
        }
        String inputName = session.getInputNames().iterator().next();
//        String outputName = session.getOutputNames().iterator().next();
        try {
            float[][][][] inputData;
            if(imageFile == null) {
                MultipartFile multipartFile = fileService.downloadFile(fileName);
                inputData = processImage(multipartFile);
            } else {
                inputData = processImage(imageFile);
            }
            // 使用ONNX Runtime创建输入张量
            OnnxTensor tensor = OnnxTensor.createTensor(env, inputData);
            // 推理
            OrtSession.Result result = session.run(Collections.singletonMap(inputName, tensor));
            float[][] predict = (float[][]) result.get(0).getValue();

            float[][] prediction = softmax(predict);
            int maxIndex = 0;
            int predictedClassId = 0;
            float maxProb = prediction[0][0];
            for (int i = 1; i < prediction[0].length; i++) {
                if (prediction[0][i] > maxProb) {
                    predictedClassId = i;
                    maxProb = prediction[0][i];
                }
            }
            String predictedClass = null;
            ////////////////////////////////////////////////////////////////
            predictedClass = (String) classes.get(String.valueOf(predictedClassId));

            Mushroom mushroom =  mushroomService.GetOneNyName(predictedClass);
            if(predictedClass == null || mushroom == null || maxProb < 0.2)
                return ResultWrapper.fail(410,"mushroom not found", predictedClass);
            log.info(predictedClass.toString());
            log.info(String.valueOf(maxProb));
            return ResultWrapper.success(mushroom);
        } catch (IOException | OrtException e) {
            throw new RuntimeException(e);
        }
    }

    private float[][] softmax(float[][] matrix) {
        float[][] result = new float[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            float sum = 0.0f;
            result[i] = new float[matrix[i].length];
            // 计算所有元素的指数和
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = (float) Math.exp(matrix[i][j]);
                sum += result[i][j];
            }
            // 对每个元素进行归一化
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = result[i][j] / sum;
            }
        }
        return result;
    }
}
