package com.bik.todolist.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.Adapter.ListsAdapter;
import com.bik.todolist.model.ListsModel;
import com.bik.todolist.databinding.ActivityListsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListsActivity extends AppCompatActivity implements ListsAdapter.OnCardviewClickListener {

    private ActivityListsBinding binding;
    private List<ListsModel> listsModels;
    private DatabaseReference databaseReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        uid = getSharedPreferences(Constant.UID, MODE_PRIVATE).getString(Constant.UID, null);

        listsModels = new ArrayList<>();
        ListsAdapter listAdapter = new ListsAdapter(listsModels, this);
        binding.rvLists.setAdapter(listAdapter);

        databaseReference.child(Constant.USER).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listsModels.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    listsModels.add(new ListsModel(dataSnapshot.getKey(), dataSnapshot.getChildrenCount()));
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.etAddList.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                String name = binding.etAddList.getText().toString();
                if (!name.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    binding.etAddList.getText().clear();
                    createList(name);
                    return true;
                }
            }
            return false;
        });

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
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(Constant.SEARCH_KEY, name);
        startActivity(intent);
    }

    private void createList(String name) {
        Intent intent = new Intent(this, PersonalListsActivity.class);
        intent.putExtra(Constant.LIST_NAME, name);
        startActivity(intent);
    }

    @Override
    public void onCardClick(ListsModel listsModel) {
        Intent intent = new Intent(this, PersonalListsActivity.class);
        intent.putExtra(Constant.LIST_NAME, listsModel.getListName());
        startActivity(intent);
    }
}