package com.dwgg.retrofitandrxjava.api.utils;

import android.util.Log;

import com.dwgg.retrofitandrxjava.BuildConfig;
import com.dwgg.retrofitandrxjava.api.interceptor.BasicAuthInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Basic Authentication(只为演示)
 * Created by 喵叔catuncle
 * 2016/05/05 19:32
 */
public class BasicAuthServiceGenerator {
    public static <S> S createService(Class<S> serviceClass, String username, String password) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BuildConfig.API_BASE_URI)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());

        //拦截器实现Basic Authentication
        if (username != null && password != null) {
            httpClient.addInterceptor(new BasicAuthInterceptor(username, password));
        }

        //只有debug才打日志-----通过Interceptor实现网络日志打印，并且这里用了一个第三方库(不过这个库也是square的)
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("HttpLogging", message);
                }
            };
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addNetworkInterceptor(logging)
                    .addNetworkInterceptor(new StethoInterceptor());
        }


        OkHttpClient client = httpClient.build();

        Retrofit retrofit = builder.client(client).build();

        return retrofit.create(serviceClass);
    }
}
