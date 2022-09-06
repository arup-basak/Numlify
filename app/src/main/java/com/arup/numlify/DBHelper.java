package com.arup.numlify;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    String TABLE_NAME = "HISTORY";
    private final String NUMBER = "NUMBER";
    private final String ANSWER = "ANSWER";
    private final String TIME = "TIME";

    public DBHelper(Context context) {
        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + NUMBER + " TEXT,"
                + ANSWER + " TEXT,"
                + TIME + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Recycle")
    private boolean exists(String val) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + NUMBER + "=" + val;
        Cursor cursor = db.rawQuery(query, new String[0]);
        return cursor.getCount() != 0;
    }

    private void update(String val) {

    }

    public void insert(String val, String ans){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NUMBER, val);
        cv.put(ANSWER, ans);
        cv.put(TIME, getCurrentTime());
        db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + TIME + " ASC";
        return db.rawQuery(query, new String[0]);
    }

    @SuppressLint("Recycle")
    public void remove(String val) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + NUMBER + "=" + val;
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(query, new String[0]);
    }

    private String getCurrentTime() {
        return String.valueOf((int) (Math.random() * 1000));
    }
}
