package com.bik.todolist.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.R;
import com.bik.todolist.databinding.ActivityViewTaskBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ViewTaskActivity extends AppCompatActivity {

    private ActivityViewTaskBinding binding;
    private static final String TIME_FORMAT = "dd/MM/yyyy , h:mm a";
    private String listName;
    private String name;
    private String desc;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        name = intent.getStringExtra(Constant.TASK_NAME);
        desc = intent.getStringExtra(Constant.TASK_DESC);
        time = intent.getLongExtra(Constant.TASK_TIME, 0l);
        listName = intent.getStringExtra(Constant.LIST_NAME);
        binding.tvTaskNameView.setText(name);
        binding.tvDesc.setText(desc);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format(TIME_FORMAT, cal).toString();
        binding.time.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            String uid = getSharedPreferences(Constant.UID, MODE_PRIVATE).getString(Constant.UID, null);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> map = new HashMap<>();
            map.put(Constant.TIME_STAMP, ServerValue.TIMESTAMP);
            map.put(Constant.TASK_DESC, binding.tvDesc.getText().toString());
            databaseReference.child(Constant.USER).child(uid).child(listName).child(name).updateChildren(map);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}