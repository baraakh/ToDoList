package com.bik.todolist.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.todolist.R;
import com.bik.todolist.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.tvCreateProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        });

        binding.btnLogin.setOnClickListener(v -> {

            String email = binding.etEmail.getText().toString();
            String pass = binding.etPass.getText().toString();

            if (email.isEmpty()) {
                binding.etEmail.setError(getString(R.string.fill_this_field));
                return;
            }

            if (pass.isEmpty()) {
                binding.etPass.setError(getString(R.string.fill_this_field));
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(this, R.string.erorr_failed_login, Toast.LENGTH_SHORT).show();
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