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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // Regex Patterns
    private final Pattern namePattern = Pattern.compile("[A-Z]{1}[a-zA-Z .-]+");
    private final Pattern emailPattern = Pattern.compile("[a-z](?:[a-z\\d]*_?[a-z\\d]+)*(@gmail|@yahoo)\\.com");
    private final Pattern passPattern = Pattern.compile("(\\w|\\W){8,}");
    private final Pattern phonePattern = Pattern.compile("01{1}[3-9]{1}\\d{8}");

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        EditText etName = findViewById(R.id.et_register_username);
        EditText etEmail = findViewById(R.id.et_register_email);
        EditText etPassword = findViewById(R.id.et_register_password);
        EditText etConfirmPassword = findViewById(R.id.et_register_conf_password);
        EditText etPhone = findViewById(R.id.et_register_phone);
        Button btnLogin = findViewById(R.id.btn_signup_login);
        Button btnRegister = findViewById(R.id.btn_signup_register);
        progressBar = findViewById(R.id.progress_bar);

        // Register Button Click Listener
        btnRegister.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();
            String phoneNum = etPhone.getText().toString().trim();

            // Perform validation
            if (!validateInputs(name, email, password, confirmPassword, phoneNum, etName, etEmail, etPassword, etConfirmPassword, etPhone)) {
                return;
            }

            // Show ProgressBar and Register user with Firebase Authentication
            progressBar.setVisibility(View.VISIBLE);
            registerUserWithFirebase(email, password);
        });

        // Login Button Click Listener
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Validate Inputs
    private boolean validateInputs(String name, String email, String password, String confirmPassword, String phoneNum,
                                   EditText etName, EditText etEmail, EditText etPassword, EditText etConfirmPassword, EditText etPhone) {
        if (name.isEmpty()) {
            etName.setError("Name cannot be empty");
            etName.requestFocus();
            return false;
        }
        if (!namePattern.matcher(name).matches()) {
            etName.setError("Enter a valid name (Start with uppercase)");
            etName.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            return false;
        }
        if (!emailPattern.matcher(email).matches()) {
            etEmail.setError("Enter a valid Gmail or Yahoo email");
            etEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        if (!passPattern.matcher(password).matches()) {
            etPassword.setError("Password must be at least 8 characters");
            etPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (phoneNum.isEmpty()) {
            etPhone.setError("Phone number cannot be empty");
            etPhone.requestFocus();
            return false;
        }
        if (!phonePattern.matcher(phoneNum).matches()) {
            etPhone.setError("Enter a valid Bangladeshi phone number");
            etPhone.requestFocus();
            return false;
        }
        return true;
    }

    // Register User with Firebase
    private void registerUserWithFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE); // Hide ProgressBar
                    if (task.isSuccessful()) {
                        // Registration successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Send Email Verification
                            user.sendEmailVerification()
                                    .addOnCompleteListener(emailTask -> {
                                        if (emailTask.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Registration successful! Please check your email to verify your account.", Toast.LENGTH_SHORT).show();
                                            // Navigate to Login screen
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            String errorMessage = emailTask.getException() != null
                                                    ? emailTask.getException().getMessage()
                                                    : "Failed to send verification email.";
                                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        // Registration failed
                        String errorMessage = task.getException() != null
                                ? task.getException().getMessage()
                                : "Registration failed!";
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
