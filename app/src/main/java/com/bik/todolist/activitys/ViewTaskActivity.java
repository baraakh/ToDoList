package com.bik.todolist.activitys;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.R;
import com.bik.todolist.databinding.ActivityViewTaskBinding;

public class ViewTaskActivity extends AppCompatActivity {

    private ActivityViewTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_toolbar, menu);
        return true;
    }
}