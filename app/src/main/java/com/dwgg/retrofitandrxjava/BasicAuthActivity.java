package com.dwgg.retrofitandrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dwgg.retrofitandrxjava.databinding.ActivityBasicAuthBinding;
import com.dwgg.retrofitandrxjava.listener.IBasicAuthListener;

import timber.log.Timber;

public class BasicAuthActivity extends AppCompatActivity {


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

    }
}
