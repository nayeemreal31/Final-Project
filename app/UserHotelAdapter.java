package com.example.nayeem1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserHotelAdapter extends BaseAdapter {

    private Context context;
    private List<Hotel> hotelList;

    // Constructor
    public UserHotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // Reusing convertView for better performance
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Bind data to views
        Hotel hotel = hotelList.get(position);

        Bitmap image = BitmapFactory.decodeByteArray(hotel.getImage(), 0, hotel.getImage().length);
        viewHolder.ivHotelImage.setImageBitmap(image);
        viewHolder.tvHotelName.setText(hotel.getName());
        viewHolder.tvRoomType.setText(hotel.getRoomType());
        viewHolder.tvPersonCount.setText("Persons: " + hotel.getPersonCount());
        viewHolder.tvRoomPrice.setText("Price: " + hotel.getPrice());

        return convertView;
    }

    // ViewHolder Pattern for better performance
    private static class ViewHolder {
        ImageView ivHotelImage;
        TextView tvHotelName;
        TextView tvRoomType;
        TextView tvPersonCount;
        TextView tvRoomPrice;

        ViewHolder(View view) {
            ivHotelImage = view.findViewById(R.id.iv_hotel_image);
            tvHotelName = view.findViewById(R.id.tv_hotel_name);
            tvRoomType = view.findViewById(R.id.tv_room_type);
            tvPersonCount = view.findViewById(R.id.tv_person_count);
            tvRoomPrice = view.findViewById(R.id.tv_room_price);
        }
    }
}
