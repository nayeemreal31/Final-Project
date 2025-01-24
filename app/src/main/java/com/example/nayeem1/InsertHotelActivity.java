package com.example.nayeem1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertHotelActivity extends AppCompatActivity {

    private EditText hotelNameEditText, roomTypesEditText, personEditText, roomPriceEditText;
    private ImageView selectedImageView;
    private Button selectImageButton, insertHotelButton;
    private DatabaseHelper databaseHelper;
    private byte[] imageByteArray = null;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_hotel);

        // Initialize views
        hotelNameEditText = findViewById(R.id.et_insert_hotel_name);
        roomTypesEditText = findViewById(R.id.et_insert_room_types);
        personEditText = findViewById(R.id.et_insert_person);
        roomPriceEditText = findViewById(R.id.et_insert_price);
        selectedImageView = findViewById(R.id.iv_selected_image);
        selectImageButton = findViewById(R.id.btn_select_image);
        insertHotelButton = findViewById(R.id.btn_insert_hotel);

        databaseHelper = new DatabaseHelper(this);

        // Image picker setup
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageView.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap); // Convert bitmap to byte array
                } catch (IOException e) {
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        // Button click listeners
        selectImageButton.setOnClickListener(view -> pickImageFromGallery());
        insertHotelButton.setOnClickListener(view -> insertHotel());
    }

    // Open the gallery to pick an image
    private void pickImageFromGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }

    // Convert a Bitmap to a byte array
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // Insert hotel details into the database
    private void insertHotel() {
        // Get user input
        String name = hotelNameEditText.getText().toString().trim();
        String types = roomTypesEditText.getText().toString().trim();
        String personText = personEditText.getText().toString().trim();
        String priceText = roomPriceEditText.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty() || types.isEmpty() || personText.isEmpty() || priceText.isEmpty() || imageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse numeric fields
        int person;
        double price;
        try {
            person = Integer.parseInt(personText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numeric values for person and price", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert data into the database
        boolean isInserted = databaseHelper.insertHotel(name, types, person, price, imageByteArray);
        if (isInserted) {
            Toast.makeText(this, "Hotel inserted successfully", Toast.LENGTH_SHORT).show();
            clearFields(); // Clear input fields after insertion
        } else {
            Toast.makeText(this, "Failed to insert hotel", Toast.LENGTH_SHORT).show();
        }
    }

    // Clear input fields after successful insertion
    private void clearFields() {
        hotelNameEditText.setText("");
        roomTypesEditText.setText("");
        personEditText.setText("");
        roomPriceEditText.setText("");
        selectedImageView.setImageResource(R.drawable.upload_icon); // Set to a placeholder image
        imageByteArray = null;
    }
}
