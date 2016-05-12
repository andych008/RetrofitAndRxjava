package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.dwgg.retrofitandrxjava.databinding.ActivityLeakyBinding;
import com.dwgg.retrofitandrxjava.utils.Tools;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class LeakyActivity extends RxAppCompatActivity {
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(Tools.defaultTag(LeakyActivity.class));

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

    public void onClick(View view) {
        //开启线程
        new MyThread().start();
    }


    public class MyThread extends Thread{

        @Override
        public void run() {
            super.run();
            //模拟耗时操作
            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
