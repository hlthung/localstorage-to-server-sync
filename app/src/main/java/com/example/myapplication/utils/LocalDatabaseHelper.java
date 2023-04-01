package com.example.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.User;

import java.util.ArrayList;
import java.util.List;

public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "my_database";
    protected static final String TABLE_NAME = "sample_sync";
    private static final String ID_NAME = "id";
    private static final String KEY_NAME = "sample_key";
    private static final String VALUE_NAME = "sample_value";

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + VALUE_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (db != null && !db.isOpen()) {
            // if db is null, create it by calling onCreate
            onCreate(db);
        }
    }

    public void addData(String user, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user);
        values.put(VALUE_NAME, value);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<User>  getData() {
        List<User> dataList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        while(cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
            String user = cursor.getString(cursor.getColumnIndexOrThrow("sample_key"));
            String value = cursor.getString(cursor.getColumnIndexOrThrow("sample_value"));

            User data = new User(id, user, value);
            dataList.add(data);
        }

        cursor.close();
        db.close();
        return dataList;
    }

    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}