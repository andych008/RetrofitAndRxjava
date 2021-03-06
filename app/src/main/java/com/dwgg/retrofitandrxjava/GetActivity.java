package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dwgg.retrofitandrxjava.api.GitHubService;
import com.dwgg.retrofitandrxjava.api.entity.GitHubUser;
import com.dwgg.retrofitandrxjava.api.utils.ServiceGenerator;
import com.dwgg.retrofitandrxjava.databinding.ActivityGetBinding;
import com.dwgg.retrofitandrxjava.listener.ClickListener;
import com.dwgg.retrofitandrxjava.utils.Tools;
import com.dwgg.retrofitandrxjava.utils.ViewUtil;
import com.dwgg.retrofitandrxjava.vmdata.GetVM;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class GetActivity extends AppCompatActivity implements ClickListener {

    private ActivityGetBinding binding;
    private GetVM vmData;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vmData = new GetVM();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_get);
        binding.setListener(this);
        binding.setData(vmData);

        if (BuildConfig.DEBUG) {
            binding.username.setText("JakeWharton");
        }
    }

    @Override
    public void onClick(View view) {
        vmData.clear();

        subscription = ServiceGenerator.createService(GitHubService.class)
                .getUser(ViewUtil.getEditText(binding.username))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Tools.toast(GetActivity.this, "loading");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitHubUser>() {
                    @Override
                    public void onCompleted() {
                        Tools.toast(GetActivity.this, "over.");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Tools.toast(GetActivity.this, e.getMessage());
                    }

                    @Override
                    public void onNext(GitHubUser gitHubUser) {
                        vmData.setText(gitHubUser.toString());
                    }
                });

    }

    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
