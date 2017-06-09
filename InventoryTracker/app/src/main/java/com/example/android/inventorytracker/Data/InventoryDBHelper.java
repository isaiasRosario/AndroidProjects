package com.example.android.inventorytracker.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by irosaro on 9/11/16.
 */
public class InventoryDBHelper extends SQLiteOpenHelper {

    //this will be the name of the database file
    private static final String DATABASE_NAME = "inventory.db";

    //database version
    private static final int DATABASE_VERSION = 1;


    public InventoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //database is created for the first time
        //SQL statement string for inventory table
        String CREATE_INVENTORY_TABLE = "CREATE TABLE " + InventoryContract.InventoryItems.TABLE_NAME + " ("
                + InventoryContract.InventoryItems._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryContract.InventoryItems.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + InventoryContract.InventoryItems.COLUMN_ITEM_PRICE + " TEXT NOT NULL, "
                + InventoryContract.InventoryItems.COLUMN_ITEM_DESC + " TEXT NOT NULL, "
                + InventoryContract.InventoryItems.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL, "
                + InventoryContract.InventoryItems.COLUMN_ITEM_IMAGE + " INTEGER NOT NULL, "
                + InventoryContract.InventoryItems.COLUMN_ITEM_STOCK + " INTEGER DEFAULT 1);";

        //execute SQl to create table
        db.execSQL(CREATE_INVENTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //delete database table method
    public void deleteDatabase(Context c) {
        c.deleteDatabase(DATABASE_NAME);

        Log.v("DB_HELPER_DELETE_DATA", "-------> DATABASE WAS DELETED");
    }
}