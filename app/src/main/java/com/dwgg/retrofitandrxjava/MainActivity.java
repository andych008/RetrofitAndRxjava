package com.dwgg.retrofitandrxjava;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dwgg.retrofitandrxjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //简单用一下data binding, 实在不想findViewById
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setListener(this);
    }

    public void onClick2Get(View view) {
        startActivity(new Intent(this, GetActivity.class));
    }
}
