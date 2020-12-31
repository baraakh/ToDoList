package com.bik.todolist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.databinding.ActivityMeetBinding;

public class MeetActivity extends AppCompatActivity {

    private ActivityMeetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}