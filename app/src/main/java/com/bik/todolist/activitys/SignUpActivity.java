package com.bik.todolist.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.R;
import com.bik.todolist.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        binding.btnSignUp.setOnClickListener(v -> {

            String name = binding.etName.getText().toString();
            String email = binding.etEmail.getText().toString();
            String pass = binding.etPass.getText().toString();

            if (name.isEmpty()) {
                binding.etName.setError(getString(R.string.fill_this_field));
                return;
            }
            if (pass.isEmpty()) {
                binding.etPass.setError(getString(R.string.fill_this_field));
                return;
            }
            if (email.isEmpty()) {
                binding.etEmail.setError(getString(R.string.fill_this_field));
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(this, "Failed to create an account!", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void updateUI(FirebaseUser user) {
        SharedPreferences.Editor editor = getSharedPreferences(Constant.UID, MODE_PRIVATE).edit();
        editor.putString(Constant.UID, user.getUid());
        editor.apply();

        SharedPreferences.Editor userStauts = getSharedPreferences(Constant.ISLOGIN, MODE_PRIVATE).edit();
        userStauts.putBoolean(Constant.ISLOGIN, true);
        userStauts.apply();

        Intent intent = new Intent(this, ListsActivity.class);
        startActivity(intent);
        finish();
    }
}