package com.dwgg.retrofitandrxjava.api.interceptor;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截器实现Basic Authentication
 * <p>
 * Created by 喵叔catuncle
 * 2016/05/20 20:14
 */
public class BasicAuthInterceptor implements Interceptor {
    private String basic;

    public BasicAuthInterceptor(String username, String password) {
        String credentials = username + ":" + password;
        this.basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", basic)
                .method(original.method(), original.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
