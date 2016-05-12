package com.dwgg.retrofitandrxjava.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 常用工具方法
 * Created by 喵叔catuncle
 * 2016/05/05 22:42
 */
public class Tools {

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static String defaultTag(Class clazz) {
        return clazz.getSimpleName();
    }
}
