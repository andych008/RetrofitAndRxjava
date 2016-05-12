package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dwgg.retrofitandrxjava.api.GitHubService;
import com.dwgg.retrofitandrxjava.api.entity.GitHubUser;
import com.dwgg.retrofitandrxjava.api.utils.ServiceGenerator;
import com.dwgg.retrofitandrxjava.databinding.ActivityGetBinding;
import com.dwgg.retrofitandrxjava.utils.Tools;
import com.dwgg.retrofitandrxjava.utils.ViewUtil;
import com.dwgg.retrofitandrxjava.vmdata.GetVM;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class GetActivity extends RxAppCompatActivity {

    private ActivityGetBinding binding;
    private GetVM vmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(Tools.defaultTag(GetActivity.class));

        vmData = new GetVM();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_get);
        binding.setListener(this);
        binding.setData(vmData);

        if (BuildConfig.DEBUG) {
            binding.username.setText("JakeWharton");
        }
    }

    public void onClick(View view) {

        ServiceGenerator.createService(GitHubService.class)
                .getUser(ViewUtil.getEditText(binding.username))
                .compose(this.<GitHubUser>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitHubUser>() {
                    @Override
                    public void onCompleted() {

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
}
