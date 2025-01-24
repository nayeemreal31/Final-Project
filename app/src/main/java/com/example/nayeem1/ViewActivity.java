package com.example.nayeem1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        listViewProducts = findViewById(R.id.list_view_products);
        
        databaseHelper = new DatabaseHelper(this);

        displayProducts();


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed products
        displayProducts();
    }

    private void displayProducts() {
        Cursor cursor = databaseHelper.getAllHotels();
        HotelAdapter adapter = new HotelAdapter(this, cursor, 0);
        listViewProducts.setAdapter(adapter);
    }
}