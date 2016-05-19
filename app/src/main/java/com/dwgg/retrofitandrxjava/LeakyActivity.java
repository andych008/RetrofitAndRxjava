package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dwgg.retrofitandrxjava.databinding.ActivityLeakyBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
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
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Timber.d("aLong = %s", aLong);
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
