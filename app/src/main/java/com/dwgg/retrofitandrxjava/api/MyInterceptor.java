package com.dwgg.retrofitandrxjava.api;

import com.dwgg.retrofitandrxjava.utils.Tools;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义拦截
 * <p>
 *     自定义header:App-Version
 * <p/>
 * Created by 喵叔catuncle
 * 2016/05/20 20:14
 */
public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().header("App-Version", Tools.getVersionName()).build();
        return chain.proceed(request);
    }
}
