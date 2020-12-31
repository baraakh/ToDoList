package com.bik.todolist.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.Adapter.ResultAdapter;
import com.bik.todolist.databinding.ActivityMeetBinding;
import com.bik.todolist.model.ListsModel;
import com.bik.todolist.model.ResultModel;
import com.bik.todolist.model.TaskModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements ResultAdapter.OnCheckBoxListener {

    private ActivityMeetBinding binding;
    private List<ResultModel> resultModels;
    private DatabaseReference databaseReference;
    private String uid;
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        uid = getSharedPreferences(Constant.UID, MODE_PRIVATE).getString(Constant.UID, null);

        Intent intent = getIntent();
        String searchKey = intent.getStringExtra(Constant.SEARCH_KEY);

        resultModels = new ArrayList<>();
        resultAdapter = new ResultAdapter(resultModels, this);
        binding.rvResults.setAdapter(resultAdapter);

        searchLists(searchKey);

        binding.etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String name = binding.etSearch.getText().toString();
                if (!name.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    binding.etSearch.getText().clear();
                    searchLists(name);
                    return true;
                }
            }
            return false;
        });

    }

    private void searchLists(String name) {
        databaseReference.child(Constant.USER).child(uid).get().addOnCompleteListener(task -> {
            DataSnapshot dataSnapshot = task.getResult();
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                for (DataSnapshot d : data.getChildren()) {
                    if (d.getKey().contains(name)) {
                        ListsModel listsModel = new ListsModel(data.getKey(), data.getChildrenCount());
                        TaskModel taskModel = d.getValue(TaskModel.class);
                        resultModels.add(new ResultModel(taskModel, listsModel));
                        resultAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    @Override
    public boolean onCheck(String name, boolean b, String listName) {
        databaseReference.child(Constant.USER).child(uid).child(listName).child(name).setValue(new TaskModel(name, b, ServerValue.TIMESTAMP, null));
        return b;
    }
}