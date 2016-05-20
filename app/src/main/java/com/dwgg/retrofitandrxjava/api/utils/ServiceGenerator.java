package com.dwgg.retrofitandrxjava.api.utils;

import android.util.Log;

import com.dwgg.retrofitandrxjava.BuildConfig;
import com.dwgg.retrofitandrxjava.api.MyInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 生成相应的api service对象
 * Created by 喵叔catuncle
 * 2016/05/05 19:32
 */
public class ServiceGenerator {
    public static <S> S createService(Class<S> serviceClass) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BuildConfig.API_BASE_URI)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create());

        //加自定义header
        httpClient.addInterceptor(new MyInterceptor());

        //只有debug才打日志-----通过Interceptor实现网络日志打印，并且这里用了一个第三方库(不过这个库也是square的)
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //直接用Log.i打日志
                    Log.i("HttpLogging", message);

                    //这里你也可以用你喜欢的日志工具库（比如Timber）
//                    Timber.tag("HttpLogging").i(message);
                }
            };
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(logger);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addNetworkInterceptor(logging)
                    .addNetworkInterceptor(new StethoInterceptor());//Stetho方便在Chrome上查看网络日志
        }


        OkHttpClient client = httpClient.build();

        Retrofit retrofit = builder.client(client).build();

        return retrofit.create(serviceClass);
    }
}
