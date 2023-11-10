package com.sudoerrr.mushroom.core.utils;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

public class FileUtil {
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
}
