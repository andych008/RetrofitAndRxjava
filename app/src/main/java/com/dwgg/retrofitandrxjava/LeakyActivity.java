package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dwgg.retrofitandrxjava.databinding.ActivityLeakyBinding;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class LeakyActivity extends RxAppCompatActivity {
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLeakyBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_leaky);
        binding.setListener(this);

        //模拟Activity一些其他的对象
        for(int i=0; i<10000;i++){
            list.add("Memory Leak!");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("destroy");
    }

    public void onClick1(View view) {
        // 耗时线程, 内存泄漏
        new MyThread().start();
    }

    public void onClick2(View view) {
        //这样也会内存泄漏
        Observable.interval(1000, TimeUnit.MILLISECONDS)
//                .compose(this.<Long>bindToLifecycle())//加上这个就不会泄漏了
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Timber.d("aLong = %s", aLong);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable.getMessage());
                    }
                });
    }

    public class MyThread extends Thread {
        private int i = 0;

        @Override
        public void run() {
            super.run();
            Timber.d("running... %d", i++);

            //模拟耗时操作
            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
