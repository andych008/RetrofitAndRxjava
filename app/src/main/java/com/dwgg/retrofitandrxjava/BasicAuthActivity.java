package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.dwgg.retrofitandrxjava.api.GitHubService;
import com.dwgg.retrofitandrxjava.api.MySubscriber;
import com.dwgg.retrofitandrxjava.api.entity.GitHubUser;
import com.dwgg.retrofitandrxjava.api.utils.BasicAuthServiceGenerator;
import com.dwgg.retrofitandrxjava.databinding.ActivityBasicAuthBinding;
import com.dwgg.retrofitandrxjava.listener.IBasicAuthListener;
import com.dwgg.retrofitandrxjava.utils.RxUtils;
import com.dwgg.retrofitandrxjava.utils.ViewUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * <p>
 * See <a href="https://developer.github.com/v3/auth/#basic-authentication">github basic-authentication</a> for detailed
 * <p>
 * See <a href="http://stackoverflow.com/questions/3406534/password-hint-font-in-android">password-hint-font-in-android</a>
 */
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
        });

        binding.password.setTypeface(Typeface.DEFAULT);
        binding.password.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void demo1() {

        BasicAuthServiceGenerator.createService(GitHubService.class, ViewUtil.getEditText(binding.username), ViewUtil.getEditText(binding.password))
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
