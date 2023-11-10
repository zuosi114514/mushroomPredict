package com.sudoerrr.mushroom.core.utils;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Normalize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.translate.*;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDArray;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageTransformer implements Translator<BufferedImage, Classifications> {

    private List<Transform> transforms;
    private List<String> classes;

    public ImageTransformer(List<String> classes) {
        this.classes = classes;
        // 定义转换列表
        this.transforms = Arrays.asList(
                new ToTensor(),
                new Normalize(new float[]{0.485f, 0.456f, 0.406f}, new float[]{0.229f, 0.224f, 0.225f})
        );
    }


    @Override
    public Classifications processOutput(TranslatorContext ctx, NDList list) {
        // 在这里处理输出，例如，提取最可能的类别
        NDArray probabilities = list.singletonOrThrow();
        return new Classifications(classes, probabilities);
    }

    @Override
    public Batchifier getBatchifier() {
        // 返回null表示不使用批处理
        return null;
    }

    @Override
    public NDList processInput(TranslatorContext ctx, BufferedImage bufferedImage) throws Exception {
        Image input = ImageFactory.getInstance().fromImage(bufferedImage);
        NDArray array = input.toNDArray(ctx.getNDManager(), Image.Flag.COLOR);
        for (Transform transform : transforms) {
            array = transform.transform(array);
        }
        return new NDList(array);
    }
}
