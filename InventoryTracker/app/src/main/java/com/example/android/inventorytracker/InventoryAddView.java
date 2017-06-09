package com.example.android.inventorytracker;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.android.inventorytracker.Data.DBCall;
import com.example.android.inventorytracker.Data.InventoryContract;
import com.example.android.inventorytracker.Data.InventoryDBHelper;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by irosaro on 9/12/16.
 */
public class InventoryAddView extends AppCompatActivity implements View.OnClickListener {

    //global variables
    ImageView image;
    int imageId;
    Spinner spinner;
    String name;
    EditText price;
    EditText description;
    EditText quantity;
    Switch stock;
    SQLiteDatabase db;
    InventoryDBHelper mHelper;

    //array of image resources for the item selection
    private static Integer[] images = {R.drawable.cereal,
            R.drawable.cleaner, R.drawable.lotion, R.drawable.shampoo,
            R.drawable.soap, R.drawable.spray, R.drawable.toiletpaper,
            R.drawable.windowcleaner, R.drawable.wipes};

    //array of prices that will be randomly selected
    private static String[] prices = {
            "$0.99", "$1.99", "$2.99", "$3.99", "$4.99",
            "$5.99", "$6.99", "$7.99", "$8.99", "$9.99",
            "$10.99", "$11.99", "$12.99", "$13.99", "$14.99",
            "$15.99", "$16.99", "$17.99", "$18.99", "$19.99"};

    //array of description tied to the item images
    private static String[] desc = {
            "Yummy Cereal for breakfast", "Mean Green all purpose Cleaner", "Baby Lotion for babies",
            "Shampoo for the babies", "Wash the dishes with Soap", "Fashion style with Hair Spray",
            "Clean up number 2 Paper", "Clean the windows with Window Cleaner", "Baby Wipes for babies"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mHelper = new InventoryDBHelper(this);

        image = (ImageView) findViewById(R.id.add_image);
        spinner = (Spinner) findViewById(R.id.add_spinner);
        price = (EditText) findViewById(R.id.add_price);
        description = (EditText) findViewById(R.id.add_desc);
        quantity = (EditText) findViewById(R.id.add_quantity);
        stock = (Switch) findViewById(R.id.in_stock_switch);

        if (stock != null) {
            stock.setChecked(true);
        }

        //Setup spinner and simple spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.inventory_item_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //set item details after spinner selection
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Picasso.with(view.getContext()).load(images[position]).into(image);
                imageId = images[position];
                name = spinner.getSelectedItem().toString();
                Random rand = new Random();
                price.setText(prices[rand.nextInt(prices.length)]);
                description.setText(desc[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        int inStock;

        //check to see if switch is toggles for stock
        if (stock.isChecked()) {
            inStock = InventoryContract.InventoryItems.IN_STOCK;
        } else {
            inStock = InventoryContract.InventoryItems.OUT_STOCK;
        }

        String priceString = price.getText().toString();
        String descString = description.getText().toString();
        String quanString = quantity.getText().toString();

        // call db to see if iem exist
        boolean exist = DBCall.alreadyExist(db, mHelper, name);

        //if it does not exist show snack bar else add new item
        if (exist) {
            Snackbar.make(v, "Item already exist please a different one", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            //check if values are empty for new item entry
            if (priceString.isEmpty()
                    || descString.isEmpty()
                    || quantity.getText().toString().isEmpty()) {

                Snackbar.make(v, "Please fill out all fields", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {

                //create new item and update db
                DBCall.createItem(db, mHelper, name, priceString,
                        descString, Integer.parseInt(quanString), imageId, inStock);
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}