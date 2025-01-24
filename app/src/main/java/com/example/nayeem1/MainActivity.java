package com.example.nayeem1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    // Admin credentials
    private static final String ADMIN_EMAIL = "nayeemreal31@gmail.com";
    private static final String ADMIN_PASSWORD = "nayeem833731";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);

        // Handle Register Button Click
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Handle Login Button Click
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Validate input fields
            if (email.isEmpty()) {
                etEmail.setError("Email cannot be empty");
                etEmail.requestFocus();
            } else if (password.isEmpty()) {
                etPassword.setError("Password cannot be empty");
                etPassword.requestFocus();
            } else {
                loginWithFirebase(email, password);
            }
        });
    }

    // Firebase Login Method
    private void loginWithFirebase(String email, String password) {
        // Show ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    // Hide ProgressBar
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        // Get the currently signed-in user
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                            // Admin Login
                            Toast.makeText(MainActivity.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (user != null && user.isEmailVerified()) {
                            // Regular user login
                            Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, RoomBooking.class);
                            startActivity(intent);
                            finish();
                        } else if (user != null) {
                            // Email not verified
                            mAuth.signOut(); // Log out the user
                            Toast.makeText(MainActivity.this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Login failed
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Login failed!";
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
