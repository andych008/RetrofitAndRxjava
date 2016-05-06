package com.dwgg.retrofitandrxjava.utils;

import android.widget.EditText;

/**
 * View相关的工具类
 * Created by 喵叔catuncle
 * 2016/05/05 22:26
 */
public class ViewUtil {

    public static String getEditText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
