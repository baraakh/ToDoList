package com.bik.todolist.activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.bik.todolist.databinding.ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(v -> {

            boolean status = getSharedPreferences(Constant.ISLOGIN, MODE_PRIVATE).getBoolean(Constant.ISLOGIN, false);

            if (status) {
                startActivity(new Intent(this, ListsActivity.class));
            } else {
                startActivity(new Intent(this, SignUpActivity.class));
            }
            finish();
        });

    }
}