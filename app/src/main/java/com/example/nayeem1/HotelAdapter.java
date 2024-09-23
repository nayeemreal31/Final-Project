package com.example.nayeem1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotelAdapter extends CursorAdapter {


    public HotelAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_hotel, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.text_view_hotel_name);
        TextView typesTextView=view.findViewById(R.id.text_view_room_types);
        TextView personTextView=view.findViewById(R.id.text_view_person);
        TextView priceTextView = view.findViewById(R.id.text_view_price);

        ImageView productImageView = view.findViewById(R.id.image_view_product);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_HOTEL_NAME));
        String types = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ROOM_TYPES));
       String person = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PERSON));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRICE));

        byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_IMAGE_URI));

        // Set text and image
        nameTextView.setText(name);
        typesTextView.setText(types);
        personTextView.setText(person);
        priceTextView.setText(String.valueOf(price));

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        productImageView.setImageBitmap(bitmap);




    }



}