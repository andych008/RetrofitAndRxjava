package com.dwgg.retrofitandrxjava.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.dwgg.retrofitandrxjava.R;

import rx.Observable;
import rx.Subscriber;

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

    public static Observable<Boolean> confirmOk(final Activity activity, final String text) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                showConfirmOk(activity, text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public static Observable<Boolean> confirm(final Activity activity, final String text) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                showConfirm(activity, text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subscriber.onNext(false);
                        subscriber.onCompleted();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private static void showConfirmOk(Activity activity, String text, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(text)
                .setPositiveButton(R.string.ok, okListener)
                .create()
                .show();
    }

    private static void showConfirm(Activity activity, String text, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(activity)
                .setMessage(text)
                .setPositiveButton(R.string.ok, okListener)
                .setNegativeButton(R.string.cancel, cancelListener)
                .create()
                .show();
    }

    private static void requireInited() {
        if (app == null) {
            throw new IllegalStateException("Tools instance NOT init !");
        }
    }
}
