package com.sudoerrr.mushroom;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.google.gson.Gson;

public class JsonTest {
    public static Map<String, Object> readJsonFile(String fileName) {
        Gson gson = new Gson();
        String json = "";
        try {
            File file = new File(fileName);
            Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
            int ch = 0;
            StringBuffer buffer = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                buffer.append((char) ch);
            }
            reader.close();
            json = buffer.toString();
            return gson.fromJson(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        System.out.println(readJsonFile("C:\\Users\\SHIT\\Desktop\\output_file.json").get("9"));
    }

}

