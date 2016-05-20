package com.dwgg.retrofitandrxjava.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 常用工具方法
 * Created by 喵叔catuncle
 * 2016/05/05 22:42
 */
public class Tools {
    private static Application app;
    private static String versionName;

    public static void setContext(Application context) {
        Tools.app = context;
    }

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static String defaultTag(Class clazz) {
        return clazz.getSimpleName();
    }

    public static String getVersionName() {
        requireInited();
        if (TextUtils.isEmpty(versionName)) {
            try {
                PackageInfo pi = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
                versionName = pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                versionName = "";
            }
        }
        return versionName;
    }

    private static void requireInited() {
        if (app == null) {
            throw new IllegalStateException("Tools instance NOT init !");
        }
    }
}
