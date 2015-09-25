package com.test.lily.testapp.files;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lily on 30/06/2015.
 */

public class MyTableSource {

    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    private static final String[] COLUMN_NAMES = new String[]{DatabaseOpenHelper.ITEM_ID, DatabaseOpenHelper.ITEM_NAME, DatabaseOpenHelper.ITEM_PRICE};

    public  MyTableSource(Context context) {
        helper = new DatabaseOpenHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
        Log.i(DatabaseActivity.MESSAGES, "Databse connection opened");
    }

    public void close() {
        database.close();
        Log.i(DatabaseActivity.MESSAGES, "Database connection closed");
    }

    public long insertItem(String name, String price) {
        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.ITEM_NAME, name);
        values.put(DatabaseOpenHelper.ITEM_PRICE, price);
        return  database.insert(DatabaseOpenHelper.ITEM_TABLE, null, values);
    }

    public List<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<Item>();

        Cursor cursor = database.query(DatabaseOpenHelper.ITEM_TABLE, COLUMN_NAMES, null, null, null, null, null, null);

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                Item item = new Item();
                item.setId(cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.ITEM_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.ITEM_NAME)));
                item.setPrice(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.ITEM_PRICE)));
                items.add(item);
            }
        }
        return items;
    }
}
