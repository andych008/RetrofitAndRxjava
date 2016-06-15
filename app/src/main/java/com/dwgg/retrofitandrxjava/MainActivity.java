package com.dwgg.retrofitandrxjava;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dwgg.retrofitandrxjava.databinding.ActivityMainBinding;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //简单用一下data binding, 实在不想findViewById
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setListener(this);
    }

    public void onClick2Leaky(View view) {
        Timber.d("click 2 leaky");
        startActivity(new Intent(this, LeakyActivity.class));
    }

    public void onClick2Get(View view) {
        Timber.d("click 2 get");
        startActivity(new Intent(this, GetActivity.class));
    }

    public void onClick2BetterGet(View view) {
        Timber.d("click 2 better get");
        startActivity(new Intent(this, BetterGetActivity.class));
    }

    public void onClick2BasicAuth(View view) {
        Timber.d("click 2 basic auth");
        startActivity(new Intent(this, BasicAuthActivity.class));
    }
}
