package com.example.nayeem1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RoomBooking extends AppCompatActivity {

    private TextView tvCheckInDate, tvCheckOutDate;
    private EditText etRoomCount, etGuestCount, etSearchHotel;
    private Button btnSearch;

    private Calendar checkInCalendar, checkOutCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booking);

        // Initialize Views
        tvCheckInDate = findViewById(R.id.tv_check_in_date);
        tvCheckOutDate = findViewById(R.id.tv_check_out_date);
        etRoomCount = findViewById(R.id.et_room_count);
        etGuestCount = findViewById(R.id.et_guest_count);
        etSearchHotel = findViewById(R.id.et_search_hotel);
        btnSearch = findViewById(R.id.btn_search);

        // Initialize Calendars
        checkInCalendar = Calendar.getInstance();
        checkOutCalendar = Calendar.getInstance();

        // Set Default Dates
        tvCheckInDate.setText("Select Date");
        tvCheckOutDate.setText("Select Date");

        // Date Picker for Check-in Date
        tvCheckInDate.setOnClickListener(v -> showDatePickerDialog(checkInCalendar, tvCheckInDate));

        // Date Picker for Check-out Date
        tvCheckOutDate.setOnClickListener(v -> showDatePickerDialog(checkOutCalendar, tvCheckOutDate));

        // Search Button Click Listener
        btnSearch.setOnClickListener(v -> handleSearchButtonClick());
    }

    // Method to Show DatePickerDialog
    private void showDatePickerDialog(Calendar calendar, TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    textView.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    // Method to Handle Search Button Click
    private void handleSearchButtonClick() {
        String searchHotel = etSearchHotel.getText().toString().trim();
        String checkInDate = tvCheckInDate.getText().toString();
        String checkOutDate = tvCheckOutDate.getText().toString();
        String roomCount = etRoomCount.getText().toString().trim();
        String guestCount = etGuestCount.getText().toString().trim();

        // Validate Input
        if (searchHotel.isEmpty()) {
            Toast.makeText(this, "Please enter a hotel name or city to search", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkInDate.equals("Select Date")) {
            Toast.makeText(this, "Please select a check-in date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkOutDate.equals("Select Date")) {
            Toast.makeText(this, "Please select a check-out date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (roomCount.isEmpty() || Integer.parseInt(roomCount) <= 0) {
            Toast.makeText(this, "Please enter a valid number of rooms", Toast.LENGTH_SHORT).show();
            return;
        }

        if (guestCount.isEmpty() || Integer.parseInt(guestCount) <= 0) {
            Toast.makeText(this, "Please enter a valid number of guests", Toast.LENGTH_SHORT).show();
            return;
        }

        // Navigate to UserHomeActivity with Search Data
        Intent intent = new Intent(this, UserHomeActivity.class);
        intent.putExtra("searchHotel", searchHotel);
        intent.putExtra("checkInDate", checkInDate);
        intent.putExtra("checkOutDate", checkOutDate);
        intent.putExtra("roomCount", roomCount);
        intent.putExtra("guestCount", guestCount);
        startActivity(intent);
    }
}
