package com.example.nayeem1;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteHotelActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextName;
    private TextView  textViewRoomTypes;
    private TextView textViewPerson;
    private TextView textViewPrice;

    private ImageView imageViewProduct;
    private Button buttonDelete;
    private Button buttonSelectImage;
    private Button buttonSearch;
    private TextView textViewHotelId;

    private DatabaseHelper databaseHelper;
    private byte[] productImageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_hotel);

        editTextName = findViewById(R.id.edit_text_hotel_name);
        textViewRoomTypes=findViewById(R.id.text_vi_room_types);
        textViewPerson =findViewById(R.id.text_vi_person);
        textViewPrice = findViewById(R.id.text_vi_price);

        textViewHotelId = findViewById(R.id.text_vi_hotel_id);
        imageViewProduct = findViewById(R.id.image_vi_hotel);
        buttonDelete = findViewById(R.id.button_delete);

        buttonSearch = findViewById(R.id.button_search);


        databaseHelper = new DatabaseHelper(this);

        buttonSearch.setOnClickListener(view -> searchProduct());
        buttonDelete.setOnClickListener(view -> deleteProduct());
    }

    private void searchProduct() {
        String productName = editTextName.getText().toString().trim();
        if (productName.isEmpty()) {
            Toast.makeText(this, "Please enter a hotel name to search", Toast.LENGTH_SHORT).show();
            return;
        }


        Cursor cursor = databaseHelper.getProductByName(productName);
        if (cursor != null && cursor.moveToFirst()) {
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ID));
            String types=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ROOM_TYPES));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRICE));
            int person = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PERSON));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_IMAGE_URI));

            textViewRoomTypes.setText(types);
            textViewPrice.setText(String.valueOf(price));
            textViewPerson.setText(String.valueOf(person));
            textViewHotelId.setText("Hotel ID: " + productId);

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

    private void deleteProduct() {
        String productName = editTextName.getText().toString().trim();

        databaseHelper.deleteHotel(productName);
        Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show();

    }
}