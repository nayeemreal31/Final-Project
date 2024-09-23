package com.example.nayeem1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        EditText etUsername=findViewById(R.id.et_register_username);
        EditText etEmail=findViewById(R.id.et_register_email);
        EditText etPassword=findViewById(R.id.et_register_password);
        EditText etConfirmPassword=findViewById(R.id.et_register_conf_password);
        EditText etPhone=findViewById(R.id.et_register_phone);


        Button btnLogin=findViewById(R.id.btn_signup_login);
        Button btnRegister=findViewById(R.id.btn_signup_register);

        btnRegister.setOnClickListener(v -> //using lammda expressions
        {

/*Toast.makeText(RegisterActivity.this,"Register Button Clicked",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);*/

                 String username=etUsername.getText().toString();
                 String email=etEmail.getText().toString();
                 String password=etPassword.getText().toString();
                 String confirmpassword=etConfirmPassword.getText().toString();
                 String phone=etPhone.getText().toString();
                 if(password.equals(confirmpassword) && !password.isEmpty() && !username.isEmpty() && !phone.isEmpty()){

Toast.makeText(RegisterActivity.this,"Your registration is processing",Toast.LENGTH_SHORT).show();
                     DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
                     boolean isInserted = dbHelper.insertUser(username, email, password, phone);
                     if (isInserted) {
                         Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                         startActivity(intent);
            }
                  else {
                     Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                 }}

                 else
                 {

                     Toast.makeText(RegisterActivity.this,"your username or password or phone number is invalid",Toast.LENGTH_SHORT).show();
                 }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        }

}
