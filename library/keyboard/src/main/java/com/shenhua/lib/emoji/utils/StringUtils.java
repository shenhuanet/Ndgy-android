package com.shenhua.lib.emoji.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shenhua on 4/27/2017.
 * Email shenhuanet@126.com
 */
public class StringUtils {

    /**
     * 读取asset文件夹下的json文件
     *
     * @param context  c
     * @param fileName name
     * @return string
     */
    public static String getJsonDataFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String json = new String(buffer, "utf-8");
            stringBuilder = stringBuilder.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
