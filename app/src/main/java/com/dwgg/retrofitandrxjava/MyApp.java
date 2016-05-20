package com.dwgg.retrofitandrxjava;

import android.app.Application;

import com.dwgg.retrofitandrxjava.utils.Tools;
import com.facebook.stetho.Stetho;

import timber.log.Timber;

/**
 * Created by 喵叔catuncle
 * 2016/05/11 14:45
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Tools.setContext(this);
        Stetho.initializeWithDefaults(this);
    }
}
