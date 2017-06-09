package com.example.android.inventorytracker.Data;

import android.provider.BaseColumns;

/**
 * Created by irosaro on 9/11/16.
 */
public class InventoryContract {

    //create private contract class
    private InventoryContract() {
    }

    //constant values for the database, tracks each inventory item
    public static final class InventoryItems implements BaseColumns {

        //table name for the inventory
        public final static String TABLE_NAME = "inventory";

        //id for each item entry type  INTEGER
        public static final String _ID = BaseColumns._ID;

        //name of item  TEXT
        public final static String COLUMN_ITEM_NAME = "name";

        //name of item  TEXT
        public final static String COLUMN_ITEM_PRICE = "price";

        //item description TEXT
        public final static String COLUMN_ITEM_DESC = "description";

        //item quantity  INTEGER
        public final static String COLUMN_ITEM_QUANTITY = "quantity";

        //item image resource  INTEGER
        public final static String COLUMN_ITEM_IMAGE = "image";

        //item in or out of stock  INTEGER, possible values OUT_STOCK, IN_STOCK
        public final static String COLUMN_ITEM_STOCK = "stock";

        //possible values for habit type
        public static final int OUT_STOCK = 0;
        public static final int IN_STOCK = 1;
    }
}
