package com.example.android.inventorytracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.inventorytracker.Data.DBCall;
import com.example.android.inventorytracker.Data.InventoryDBHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by irosaro on 9/11/16.
 */
public class InventoryListAdapter extends ArrayAdapter<InventoryItem> {

    SQLiteDatabase db;

    //custom inventory list adapter constructor
    public InventoryListAdapter(Activity context, ArrayList<InventoryItem> items) {
        super(context, 0, items);
    }

    // Components for the list item set up using getView customer adapter
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final InventoryItem item = getItem(position);

        //view reference
        View v = convertView;
        //view holder reference
        final ViewHolder holder;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater.from(getContext()));
            v = inflater.inflate(R.layout.inventory_item, null);
            // cache the holder
            holder = new ViewHolder();
            holder.nameText = (TextView) v.findViewById(R.id.item_name);
            holder.priceText = (TextView) v.findViewById(R.id.item_price);
            holder.quanText = (TextView) v.findViewById(R.id.item_quantity);
            holder.itemImage = (ImageView) v.findViewById(R.id.item_image);
            holder.saleButton = (Button) v.findViewById(R.id.sale_button);

            if (item.getItemQuantity() >= 1) {
                holder.saleButton.setText(R.string.sale);
                holder.saleButton.setEnabled(true);
            } else {
                holder.saleButton.setText(R.string.out_stock);
                holder.saleButton.setEnabled(false);
            }
            // associate  holder with view
            v.setTag(holder);
        } else {
            // if the view already exists, then get existing holder
            holder = (ViewHolder) v.getTag();
            if (item.getItemQuantity() >= 1) {
                holder.saleButton.setText(R.string.sale);
                holder.saleButton.setEnabled(true);
            } else {
                holder.saleButton.setText(R.string.out_stock);
                holder.saleButton.setEnabled(false);
            }
        }

        //set inventory item values to view holder
        holder.nameText.setText(item.getItemName());
        holder.priceText.setText(item.getItemPrice());
        holder.quanText.setText(String.valueOf(item.getItemQuantity()));
        Picasso.with(getContext()).load(item.getItemImage()).into(holder.itemImage);

        holder.saleButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (Objects.equals(holder.quanText.getText().toString(), "1")) {
                    holder.saleButton.setText(R.string.out_stock);
                    holder.saleButton.setEnabled(false);
                    update(v);
                } else {
                    update(v);
                }
            }

            public void update(View v) {

                InventoryDBHelper mHelper;

                mHelper = new InventoryDBHelper(v.getContext());

                String count = holder.quanText.getText().toString();

                DBCall.updateItem(db, mHelper, Integer.parseInt(count) - 1, item.getItemId());

                InventoryItem updateItem = DBCall.readItem(db, mHelper, item.getItemId());

                holder.quanText.setText(String.valueOf(updateItem.getItemQuantity()));
            }
        });

        //return view holder view
        return v;
    }

    //ViewHolder class
    static class ViewHolder {
        TextView nameText;
        TextView priceText;
        TextView quanText;
        ImageView itemImage;
        Button saleButton;
    }
}