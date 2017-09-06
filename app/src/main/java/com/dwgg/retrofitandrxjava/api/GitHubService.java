package com.dwgg.retrofitandrxjava.api;

import com.dwgg.retrofitandrxjava.api.entity.GitHubUser;

import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface GitHubService {
    @GET("users/{username}")
    Observable<GitHubUser> getUser(@Path("username") String username);

    @GET("user")
    Observable<GitHubUser> testBasicAuth();
}
