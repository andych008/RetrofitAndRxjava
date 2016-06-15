package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dwgg.retrofitandrxjava.api.GitHubService;
import com.dwgg.retrofitandrxjava.api.MySubscriber;
import com.dwgg.retrofitandrxjava.api.entity.GitHubUser;
import com.dwgg.retrofitandrxjava.api.utils.BasicAuthServiceGenerator;
import com.dwgg.retrofitandrxjava.databinding.ActivityBasicAuthBinding;
import com.dwgg.retrofitandrxjava.listener.IBasicAuthListener;
import com.dwgg.retrofitandrxjava.utils.RxUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BasicAuthActivity extends RxAppCompatActivity {


    private ActivityBasicAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_basic_auth);
        binding.setListener(new IBasicAuthListener() {
            @Override
            public void onClickBtn1(View view) {
                Timber.d("click btn 1");
                demo1();
            }

            @Override
            public void onClickBtn2(View view) {
                Timber.d("click btn 2");
            }
        });
    }

    private void demo1(){

        // FIXME: use your owen username and password
        BasicAuthServiceGenerator.createService(GitHubService.class, "JakeWharton", "xxxxxx")
                .testBasicAuth()
                .compose(this.<GitHubUser>bindToLifecycle())
                .compose(RxUtils.<GitHubUser>validateGitHubResponse(this))
                .subscribeOn(Schedulers.io())
                .subscribe(new MySubscriber<GitHubUser>() {
                    @Override
                    public void onNext(GitHubUser gitHubUser) {
                        Timber.i(gitHubUser.toString());
                    }
                });
    }
}
