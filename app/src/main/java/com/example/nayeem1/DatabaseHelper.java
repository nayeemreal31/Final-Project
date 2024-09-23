package com.example.nayeem1;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Test_DB";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_REGISTER = "register";
    public static final String TABLE_HOTEL = "hotel";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_MOBILE = "phone";
    public static final String COL_HOTEL_NAME = "hotelName";
    public static final String COL_ROOM_TYPES = "roomTypes";
    public static final String COL_PERSON = "person";
    public static final String COL_PRICE = "roomPrice";

    public static final String COL_IMAGE_URI = "ImageUri";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        onCreate(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_REGISTER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_MOBILE + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_HOTEL + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_HOTEL_NAME + " TEXT, " +
                COL_ROOM_TYPES + " TEXT, " +
                COL_PERSON + " INTEGER, " +
                COL_PRICE + " DOUBLE, " +

                COL_IMAGE_URI + " BLOB)");


    }


    public boolean insertUser(String username, String email, String password, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_MOBILE, mobile);

        long result = db.insert(TABLE_REGISTER, null, contentValues);
        //result value, if inserted, then "row number"
        //result value, if not inserted, then -1

        return result != -1;
    }

    public boolean checkUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void insertHotel(String name, String types, int person,double price, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HOTEL_NAME, name);
        values.put( COL_ROOM_TYPES , types);
        values.put( COL_PERSON, person);
        values.put(COL_PRICE, price);

        values.put(COL_IMAGE_URI, imageByteArray);
        db.insert(TABLE_HOTEL, null, values);
        db.close();
    }
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HOTEL, null);
    }
    public Cursor getProductByName(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HOTEL + " WHERE " + COL_HOTEL_NAME + " = ?", new String[]{productName});
}
    public void updateHotel(int hotelId, String hotelName,String types,int person, double price, byte[] productImageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(COL_HOTEL_NAME, hotelName);
        values.put(COL_ROOM_TYPES, types);
        values.put(COL_PERSON, person);
        values.put(COL_PRICE, price);

        values.put(COL_IMAGE_URI, productImageByteArray);

        db.update(TABLE_HOTEL, values, COL_ID + " = ?", new String[]{String.valueOf(hotelId)});
        db.close();
    }

    public void deleteHotel(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HOTEL, COL_HOTEL_NAME + " = ?", new String[]{productName});
        db.close();
    }
}