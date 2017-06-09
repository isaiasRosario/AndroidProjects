package com.example.android.inventorytracker;

/**
 * Created by irosaro on 9/11/16.
 */
public class InventoryItem {

    // Strings for the inventory item
    private int mItemId;

    private String mItemName;

    private String mItemPrice;

    private String mItemDescription;

    private int mItemQuantity;

    private int mItemImage;

    private int mItemStock;

    // custom constructor for adding new inventory items
    public InventoryItem(int id, String name, String price, String desc, int quantity, int img, int stock) {
        mItemId = id;
        mItemName = name;
        mItemPrice = price;
        mItemDescription = desc;
        mItemQuantity = quantity;
        mItemImage = img;
        mItemStock = stock;
    }

    // inventory item get methods
    public int getItemId() {
        return mItemId;
    }

    public String getItemName() {
        return mItemName;
    }

    public String getItemPrice() {
        return mItemPrice;
    }

    public String getItemDescription() {
        return mItemDescription;
    }

    public int getItemQuantity() {
        return mItemQuantity;
    }

    public int getItemImage() {
        return mItemImage;
    }

    public int getItemStock() {
        return mItemStock;
    }
}
