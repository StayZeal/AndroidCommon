package cn.stayzeal.andr.common.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class JsonUtil {
    private static final String TAG = "JsonUtil";

    public static String parseToJson(List<String> datalist) {
        String result = null;
        result = new Gson().toJson(datalist);
        MyLog.i(TAG, "Json : " + result);
        return result;
    }

    public static <T> String parseToJson(T data) {
        String result = null;
        result = new Gson().toJson(data);
        MyLog.i(TAG, "Json : " + result);
        return result;
    }

    public static Reader getFileReader(final Context context, final String fileName) throws Exception {

        InputStream inputStream = null;
        BufferedReader in;
        try {
            String fileNames[] = context.getAssets().list("");

            MyLog.i(TAG,fileNames.toString());
            inputStream = context.getAssets().open(fileName);
            in = new BufferedReader(
                    new InputStreamReader(inputStream));


        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return in;
    }

    public static <T> T getJson(final Context context, final String fileName) throws Exception {
        InputStream inputStream = null;
        T t = null;
        try {
            inputStream = context.getAssets().open(fileName);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(inputStream));
            t = new Gson().fromJson(in, new TypeToken<T>() {
            }.getType());

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return t;
    }

}
