package com.example.nayeem1;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Button btnInsertHotel=findViewById(R.id.btn_insert_hotel);

        Button btnView =findViewById(R.id.btn_view_update_delete_hotel);

        btnInsertHotel.setOnClickListener(v ->{
            Intent intent=new Intent(AdminHomeActivity.this,InsertHotelActivity.class);
            startActivity(intent);

        });

        btnView.setOnClickListener(v ->{
            Intent intent=new Intent(AdminHomeActivity.this,ViewActivity.class);
            startActivity(intent);

        });
    }
}