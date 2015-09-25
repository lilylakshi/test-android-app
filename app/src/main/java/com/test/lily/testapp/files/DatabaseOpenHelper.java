package com.test.lily.testapp.files;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.test.lily.testapp.FileHandlingActivity;

/**
 * Created by Lily on 29/06/2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String ITEM_ID = "itemId";
    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_PRICE = "itemPrice";
    public static final String ITEM_TABLE = "ItemTable";
    private static final String CREATE_QUERY = "CREATE TABLE " + ITEM_TABLE + " (" +
            ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ITEM_NAME + " TEXT," +
            ITEM_PRICE + " NUMERIC " +
            ")";
    private static final String DROP_QUERY = "DROP TABLE IF EXISTS " + ITEM_TABLE;

    public DatabaseOpenHelper(Context context) {
        super(context, "mydatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_QUERY);
        onCreate(db);
    }
}
