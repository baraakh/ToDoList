package com.bik.todolist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.databinding.ActivityListsBinding;

public class ListsActivity extends AppCompatActivity {

    private ActivityListsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}