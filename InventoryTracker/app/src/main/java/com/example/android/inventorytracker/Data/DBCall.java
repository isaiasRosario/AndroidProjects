package com.example.android.inventorytracker.Data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.inventorytracker.InventoryItem;

import java.util.ArrayList;

/**
 * Created by irosaro on 9/11/16.
 */
public class DBCall {

    //create item entry method
    static public void createItem(SQLiteDatabase db, InventoryDBHelper mHelper, String name,
                                  String price, String desc, int quantity, int img, int stock) {
        db = mHelper.getWritableDatabase();

        //values to store when item entry is created
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryItems.COLUMN_ITEM_NAME, name);
        values.put(InventoryContract.InventoryItems.COLUMN_ITEM_PRICE, price);
        values.put(InventoryContract.InventoryItems.COLUMN_ITEM_DESC, desc);
        values.put(InventoryContract.InventoryItems.COLUMN_ITEM_QUANTITY, quantity);
        values.put(InventoryContract.InventoryItems.COLUMN_ITEM_IMAGE, img);
        values.put(InventoryContract.InventoryItems.COLUMN_ITEM_STOCK, stock);

        long rowId = db.insert(InventoryContract.InventoryItems.TABLE_NAME, null, values);

        Log.v("MAIN_ACTIVITY_INSERT", "----> ROW ID " + rowId + " ADDED");
    }

    //read item entry method returns inventory item
    static public InventoryItem readItem(SQLiteDatabase db, InventoryDBHelper mHelper, int id) {
        db = mHelper.getWritableDatabase();

        String itemQuery = "SELECT * FROM " + InventoryContract.InventoryItems.TABLE_NAME
                + " WHERE " + InventoryContract.InventoryItems._ID + " = " + id;

        Cursor cursor = db.rawQuery(itemQuery, null);

        InventoryItem item = null;
        if (cursor != null) {
            try {
                cursor.moveToFirst();

                int currentID = cursor.getInt(cursor.
                        getColumnIndex(InventoryContract.InventoryItems._ID));
                String itemName = cursor.getString(cursor.
                        getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_NAME));
                String itemPrice = cursor.getString(cursor.
                        getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_PRICE));
                String itemDesc = cursor.getString(cursor.
                        getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_DESC));
                int itemQuantity = cursor.getInt(cursor.
                        getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_QUANTITY));
                int itemImage = cursor.getInt(cursor.
                        getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_IMAGE));
                int itemStock = cursor.getInt(cursor.
                        getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_STOCK));

                item = new InventoryItem(currentID, itemName, itemPrice, itemDesc,
                        itemQuantity, itemImage, itemStock);
            } finally {
                cursor.close();
            }
        }

        Log.v("MAIN_ACTIVITY_ITEM", "-------> ROW " + item + " WAS READ");

        return item;
    }

    //update item entry method
    static public void updateItem(SQLiteDatabase db, InventoryDBHelper mHelper,
                                  int quantity, int id) {
        db = mHelper.getWritableDatabase();

        //content values to store in the database after update
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryItems.COLUMN_ITEM_QUANTITY, quantity);

        db.update(InventoryContract.InventoryItems.TABLE_NAME, values, "_id=" + id, null);

        Log.v("MAIN_ACTIVITY_UPDATE", "-------> ROW " + id + " WAS UPDATED");
    }

    //delete item entry method
    static public void deleteItem(SQLiteDatabase db, InventoryDBHelper mHelper, int id) {
        db = mHelper.getWritableDatabase();

        db.execSQL("DELETE FROM inventory WHERE _id = '" + id + "'");

        Log.v("MAIN_ACTIVITY_DELETE", "-------> ROW " + id + " WAS DELETED");
    }

    //check to see if item already exist in database returns boolean
    static public boolean alreadyExist(SQLiteDatabase db, InventoryDBHelper mHelper, String name){
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryItems.TABLE_NAME + " WHERE name = '" + name + "'", null);
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }

    //read all inventory method returns an array
    static public ArrayList<InventoryItem> readInventory(SQLiteDatabase db, InventoryDBHelper mHelper) {
        db = mHelper.getReadableDatabase();

        //database projection values
        String[] projection = {
                InventoryContract.InventoryItems._ID,
                InventoryContract.InventoryItems.COLUMN_ITEM_NAME,
                InventoryContract.InventoryItems.COLUMN_ITEM_PRICE,
                InventoryContract.InventoryItems.COLUMN_ITEM_DESC,
                InventoryContract.InventoryItems.COLUMN_ITEM_QUANTITY,
                InventoryContract.InventoryItems.COLUMN_ITEM_IMAGE,
                InventoryContract.InventoryItems.COLUMN_ITEM_STOCK
        };

        // cursor query to get database data
        Cursor cursor = db.query(
                InventoryContract.InventoryItems.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        ArrayList<InventoryItem> array = new ArrayList<InventoryItem>();
        //move through cursor data and add it to array list

        if (cursor != null) {
            try {

                while (cursor.moveToNext()) {

                    int currentID = cursor.getInt(cursor.
                            getColumnIndex(InventoryContract.InventoryItems._ID));
                    String itemName = cursor.getString(cursor.
                            getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_NAME));
                    String itemPrice = cursor.getString(cursor.
                            getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_PRICE));
                    String itemDesc = cursor.getString(cursor.
                            getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_DESC));
                    int itemQuantity = cursor.getInt(cursor.
                            getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_QUANTITY));
                    int itemImage = cursor.getInt(cursor.
                            getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_IMAGE));
                    int itemStock = cursor.getInt(cursor.
                            getColumnIndex(InventoryContract.InventoryItems.COLUMN_ITEM_STOCK));

                    array.add(new InventoryItem(currentID, itemName, itemPrice, itemDesc, itemQuantity, itemImage, itemStock));

                    Log.v("MAIN_ACTIVITY_READ", "---CURSOR ROW---> " +
                            currentID + " - " +
                            itemName + " - " +
                            itemPrice + " - " +
                            itemDesc + " - " +
                            itemQuantity + " - " +
                            itemImage + " - " +
                            itemStock);
                }

            } finally {
                //close cursor when done
                cursor.close();
            }
        }

        return array;
    }

    //delete all item entries method
    static public void deleteInventory(SQLiteDatabase db, InventoryDBHelper mHelper) {
        db = mHelper.getWritableDatabase();

        //delete all inventory items
        db.delete(InventoryContract.InventoryItems.TABLE_NAME, null, null);

        Log.v("MAIN_ACTIVITY_DELETE", "-------> All ROWS ARE DELETED");
    }
}