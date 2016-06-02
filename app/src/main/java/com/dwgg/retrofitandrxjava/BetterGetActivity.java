package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dwgg.retrofitandrxjava.api.GitHubService;
import com.dwgg.retrofitandrxjava.api.MySubscriber;
import com.dwgg.retrofitandrxjava.api.entity.GitHubUser;
import com.dwgg.retrofitandrxjava.api.utils.ServiceGenerator;
import com.dwgg.retrofitandrxjava.databinding.ActivityGetBinding;
import com.dwgg.retrofitandrxjava.listener.ClickListener;
import com.dwgg.retrofitandrxjava.utils.RxUtils;
import com.dwgg.retrofitandrxjava.utils.Tools;
import com.dwgg.retrofitandrxjava.utils.ViewUtil;
import com.dwgg.retrofitandrxjava.vmdata.GetVM;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BetterGetActivity extends RxAppCompatActivity implements ClickListener, RxUtils.ILoading {

    private ActivityGetBinding binding;
    private GetVM vmData;

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

        ServiceGenerator.createService(GitHubService.class)
                .getUser(ViewUtil.getEditText(binding.username))
                .compose(this.<GitHubUser>bindToLifecycle())
                .compose(RxUtils.<GitHubUser>validateGitHubResponse(this))
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<GitHubUser>showLoading(this))
                .subscribe(new MySubscriber<GitHubUser>() {
                    @Override
                    public void onNext(GitHubUser gitHubUser) {
                        vmData.setText(gitHubUser.toString());
                    }
                });

    }

    @Override
    public void showLoading(String tip) {
        Timber.d("thread of showLoading = %s", Thread.currentThread().getName());
        Tools.toast(this, tip);
    }

    @Override
    public void hideLoading() {
        Timber.d("thread of hideLoading =  %s", Thread.currentThread().getName());
        Tools.toast(this, "over.");
    }
}
