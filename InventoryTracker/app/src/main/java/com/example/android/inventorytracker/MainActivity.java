package com.example.android.inventorytracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.inventorytracker.Data.DBCall;
import com.example.android.inventorytracker.Data.InventoryContract;
import com.example.android.inventorytracker.Data.InventoryDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    InventoryDBHelper mHelper;
    SQLiteDatabase db;
    TextView emptyView;
    ListView listView;
    InventoryListAdapter adapter;
    ArrayList<InventoryItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up and create database and helper
        mHelper = new InventoryDBHelper(this);

        emptyView = (TextView) findViewById(R.id.emptyView);
        listView = (ListView) findViewById(R.id.listView);

        updateUI();

        //floating button opens add view when pressed to add new item
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addIntent = new Intent(MainActivity.this, InventoryAddView.class);
                    startActivityForResult(addIntent, 1);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_dummy_data:
                //load dummy item
                loadDummyItem();
                break;
            case R.id.action_delete_all:
                //if list view not empty then call delete all items
                if (emptyView.getVisibility() != View.VISIBLE) {
                    deleteAllItems();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateUI() {
        items = DBCall.readInventory(db, mHelper);

        adapter = new InventoryListAdapter(this, items);

        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);

        //set onclick listener for each list item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // current item that was clicked on
                InventoryItem currentItem = adapter.getItem(position);

                // get current tem id
                int id = currentItem.getItemId();

                // intent to view item detail
                Intent detailViewIntent = new Intent(MainActivity.this, InventoryDetailView.class);
                detailViewIntent.putExtra("id", id);
                // launch new activity calls item detail view
                startActivityForResult(detailViewIntent, 1);
            }
        });
    }

    //loads a single dummy item to the list
    private void loadDummyItem() {

        //check if item exist in db
        boolean exist = DBCall.alreadyExist(db, mHelper, "Android Head");

        if (exist) {
            Snackbar.make(getWindow().getDecorView().findViewById(R.id.listView),
                    "Item already exist please add a different one", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            DBCall.createItem(db, mHelper,
                    "Android Head",//name TEXT
                    "$199.99",//price TEXT
                    "Green looking Android head with 2 eyes!",//description TEXT
                    5,//quantity INTEGER
                    R.mipmap.ic_launcher,//image INTEGER
                    InventoryContract.InventoryItems.IN_STOCK);//in stock or out stock INTEGER

            updateUI();
        }
    }

    //delete all items from table method
    private void deleteAllItems() {
        new AlertDialog.Builder(this)
                .setTitle("Delete All Items?")
                .setMessage("Do you really want to delete all items?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //delete all items ok button
                        DBCall.deleteInventory(db, mHelper);
                        updateUI();
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


    //check for result code inorder to update ui
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check request code
        if (requestCode == 1) {
            // make sure result is ok
            if (resultCode == RESULT_OK) {
                updateUI();
            }
        }
    }
}