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
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class InsertHotelActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private EditText hotelNameEditText;
    private EditText roomTypesEditText;
    private EditText personEditText;
    private EditText roomPriceEditText;
    private EditText locationEditText;
    private ImageView selectedImageView;
    private Button selectImageButton;
    private Button insertHotelButton;
    private DatabaseHelper databaseHelper;
    private byte[] imageByteArray;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_hotel);

        hotelNameEditText = findViewById(R.id.et_insert_hotel_name);
        roomTypesEditText = findViewById(R.id.et_insert_room_types);
        personEditText= findViewById(R.id.et_insert_person);
        roomPriceEditText= findViewById(R.id.et_insert_price);

        selectedImageView = findViewById(R.id.iv_selected_image);
        selectImageButton = findViewById(R.id.btn_select_image);
        insertHotelButton = findViewById(R.id.btn_insert_hotel);

        databaseHelper = new DatabaseHelper(this);
//////////////////////////////////////////////
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageView.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        selectImageButton.setOnClickListener(view -> showImageSelectionDialog());

        insertHotelButton.setOnClickListener(view -> insertHotel());
    }

    private void showImageSelectionDialog() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void insertHotel() {
        String name = hotelNameEditText.getText().toString();
        String types= roomTypesEditText.getText().toString();
        int person = Integer.parseInt(personEditText.getText().toString());
        double price = Double.parseDouble(roomPriceEditText.getText().toString());





        if (name.isEmpty() || imageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseHelper.insertHotel(name,types, person, price, imageByteArray);
        Toast.makeText(this, "Product inserted successfully", Toast.LENGTH_SHORT).show();
    }
}