package com.example.android.inventorytracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.inventorytracker.Data.DBCall;
import com.example.android.inventorytracker.Data.InventoryDBHelper;
import com.squareup.picasso.Picasso;

/**
 * Created by irosaro on 9/11/16.
 */
public class InventoryDetailView extends AppCompatActivity implements View.OnClickListener {

    //global variables
    InventoryDBHelper mHelper;
    SQLiteDatabase db;
    TextView name, quantity, stock;
    EditText quanEditText;
    LinearLayout modLayout, buttonLayout;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mHelper = new InventoryDBHelper(this);
        buttonLayout = (LinearLayout) findViewById(R.id.button_layout);
        modLayout = (LinearLayout) findViewById(R.id.mod_layout);
        quanEditText = (EditText) findViewById(R.id.quantity_editText);

        //read item id passed using intent
        id = getIntent().getIntExtra("id", 0);

        //read item from db using id
        InventoryItem item = DBCall.readItem(db, mHelper, id);

        //set returned values to detail view components
        name = (TextView) findViewById(R.id.detail_name);
        ImageView image = (ImageView) findViewById(R.id.detail_image);
        TextView price = (TextView) findViewById(R.id.detail_price);
        TextView description = (TextView) findViewById(R.id.detail_desc);
        quantity = (TextView) findViewById(R.id.detail_quantity);
        stock = (TextView) findViewById(R.id.detail_stock);

        //check if item id not null
        if (item != null) {
            assert name != null;
            name.setText(item.getItemName());
            Picasso.with(this).load(item.getItemImage()).into(image);
            assert price != null;
            price.setText(item.getItemPrice());
            assert description != null;
            description.setText(item.getItemDescription());
            assert quantity != null;
            quantity.setText(String.valueOf(item.getItemQuantity()));
            assert stock != null;
            if (item.getItemQuantity() == 0) {
                stock.setText(R.string.out_stock);
            } else {
                stock.setText(R.string.in_stock);
            }
        }

        Log.v("DETAIL_ACTIVITY_READ", "----> ROW ID " + item + " READ");
    }

    @Override
    public void onClick(View v) {

        int buttonId = v.getId();

        switch (buttonId) {
            case R.id.modify_button:
                //modify quantity button
                modifyQuantity();
                break;
            case R.id.minus_button:
                //modify minus quantity button
                if (Integer.parseInt(quanEditText.getText().toString()) <= 0) {
                    return;
                } else {
                    int i = Integer.parseInt(quanEditText.getText().toString()) - 1;
                    quanEditText.setText(String.valueOf(i));
                }
                break;
            case R.id.add_button:
                //modify add quantity button
                int i = Integer.parseInt(quanEditText.getText().toString()) + 1;
                quanEditText.setText(String.valueOf(i));
                break;
            case R.id.save_button:
                //save quantity button
                saveQuantity();
                break;
            case R.id.order_button:
                //order more button
                orderEmail();
                break;
            case R.id.delete_button:
                //deleteAlert before delete
                deleteAlert();
                break;
        }
    }

    //open modify quantity layout
    private void modifyQuantity() {
        buttonLayout.setVisibility(View.GONE);
        modLayout.setVisibility(View.VISIBLE);
        quanEditText.setText(quantity.getText().toString());
    }

    //save modified quantity
    private void saveQuantity() {
        if (quanEditText != null) {
            DBCall.updateItem(db, mHelper, Integer.parseInt(quanEditText.getText().toString()), id);
            quantity.setText(quanEditText.getText().toString());
            if (Integer.parseInt(quantity.getText().toString()) == 0) {
                stock.setText(R.string.out_stock);
            } else {
                stock.setText(R.string.in_stock);
            }
            buttonLayout.setVisibility(View.VISIBLE);
            modLayout.setVisibility(View.GONE);
            setResult(RESULT_OK);
            finish();
        }
    }

    //send order more using email method
    private void orderEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"order@moreitems.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order More " + name.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, "I would like to order more " + name.getText().toString());

        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    //deleteAlert before delete item
    private void deleteAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Delete This Item?")
                .setMessage("Do you really want to delete this item?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //delete item ok button
                        DBCall.deleteItem(db, mHelper, id);
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do not delete cancel button
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}