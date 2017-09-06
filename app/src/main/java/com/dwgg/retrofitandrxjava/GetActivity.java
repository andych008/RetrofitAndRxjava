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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class GetActivity extends AppCompatActivity implements ClickListener {

    private ActivityGetBinding binding;
    private GetVM vmData;
    Disposable disposable;

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

        disposable = ServiceGenerator.createService(GitHubService.class)
                .getUser(ViewUtil.getEditText(binding.username))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Tools.toast(GetActivity.this, "loading");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GitHubUser>() {
                    @Override
                    public void accept(GitHubUser gitHubUser) throws Exception {
                        vmData.setText(gitHubUser.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Tools.toast(GetActivity.this, throwable.getMessage());
                    }
                });

    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}
