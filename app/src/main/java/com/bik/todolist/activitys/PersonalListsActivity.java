package com.bik.todolist.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.Adapter.TaskAdapter;
import com.bik.todolist.R;
import com.bik.todolist.databinding.ActivityPersonalListsBinding;
import com.bik.todolist.model.TaskModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalListsActivity extends AppCompatActivity implements TaskAdapter.OnCheckBoxListener {

    private ActivityPersonalListsBinding binding;
    private List<TaskModel> taskModels;
    private DatabaseReference databaseReference;
    private String uid;
    private String listName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        uid = getSharedPreferences(Constant.UID, MODE_PRIVATE).getString(Constant.UID, null);

        Intent intent = getIntent();
        listName = intent.getStringExtra(Constant.LIST_NAME);

        String title = listName + " " + Constant.LIST;
        binding.tvLists.setText(title);

        taskModels = new ArrayList<>();
        TaskAdapter taskAdapter = new TaskAdapter(taskModels, this);
        binding.rvTasks.setAdapter(taskAdapter);

        databaseReference.child(Constant.USER).child(uid).child(listName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TaskModel taskModel = dataSnapshot.getValue(TaskModel.class);
                    taskModels.add(taskModel);
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.etAddTask.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                String name = binding.etAddTask.getText().toString();
                if (!name.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    binding.etAddTask.getText().clear();
                    createTask(listName, name);
                    return true;
                }
            }
            return false;
        });
    }

    private void createTask(String listName, String taskName) {
        if (uid == null) return;
        databaseReference.child(Constant.USER).child(uid).child(listName).child(taskName).setValue(new TaskModel(taskName, false, ServerValue.TIMESTAMP, null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.personal_menu, menu);
        return true;
    }

    @Override
    public boolean onCheck(String name, boolean b) {
        databaseReference.child(Constant.USER).child(uid).child(listName).child(name).setValue(new TaskModel(name, b, ServerValue.TIMESTAMP, null));
        return b;
    }
}
