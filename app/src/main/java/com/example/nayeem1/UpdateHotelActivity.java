package com.example.nayeem1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateHotelActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextName;
    private EditText editTextRoomTypes;
    private EditText editTextPerson;
    private EditText editTextPrice;

    private ImageView imageViewProduct;
    private Button buttonUpdate;
    private Button buttonSelectImage;
    private Button buttonSearch;
    private TextView textViewHotelId;

    private DatabaseHelper databaseHelper;
    private byte[] productImageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hotel);

        editTextName = findViewById(R.id.edit_text_hotel_name);
        editTextRoomTypes = findViewById(R.id.edit_text_room_types);
        editTextPerson = findViewById(R.id.edit_text_person);
        editTextPrice = findViewById(R.id.edit_text_price);

        imageViewProduct = findViewById(R.id.image_view_product);
        buttonUpdate = findViewById(R.id.button_update);
        buttonSelectImage = findViewById(R.id.button_select_image);
        buttonSearch = findViewById(R.id.button_search);
        textViewHotelId = findViewById(R.id.text_view_hotel_id);

        databaseHelper = new DatabaseHelper(this);

        buttonSearch.setOnClickListener(view -> searchProduct());
        buttonSelectImage.setOnClickListener(view -> selectImage());
        buttonUpdate.setOnClickListener(view -> updateHotel());
    }

    private void searchProduct() {
        String productName = editTextName.getText().toString().trim();
        if (productName.isEmpty()) {
            Toast.makeText(this, "Please enter a product name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseHelper.getProductByName(productName);
        if (cursor != null && cursor.moveToFirst()) {
            int hotelId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
            String types=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ROOM_TYPES));
            String person=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PERSON));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRICE));

          //  String Location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_HOTEL_LOCATION));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_IMAGE_URI));


            editTextRoomTypes.setText(String.valueOf(types));
            editTextPerson.setText(String.valueOf(person));
            editTextPrice.setText(String.valueOf(price));


            textViewHotelId.setText("Hotel ID: " + hotelId);

            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageViewProduct.setImageBitmap(bitmap);
                productImageByteArray = image;
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Hotel not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageViewProduct.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                productImageByteArray = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateHotel() {
        String hotelName = editTextName.getText().toString().trim();
        String types = editTextRoomTypes.getText().toString().trim();
        String person = editTextPerson.getText().toString().trim();
        String Price = editTextPrice.getText().toString().trim();


        if (hotelName.isEmpty() || types.isEmpty()||person.isEmpty()|| Price.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        }

        int Person = Integer.parseInt(person);
        double price = Double.parseDouble(Price);



        String hotelIdText = textViewHotelId.getText().toString();
        int hotelId = Integer.parseInt(hotelIdText.replaceAll("\\D+", ""));

        databaseHelper.updateHotel(hotelId, hotelName,types,Person, price, productImageByteArray);
    }
}