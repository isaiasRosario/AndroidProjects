// Isaias Rosario
// MDF3 - 1604
// MainActivity Class

package com.isaiasrosario.i_rosario_ce01;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDatabase = new UserDatabase(this);

        FragmentManager fragManager = getFragmentManager();
        fragManager.beginTransaction().replace(R.id.listFrame, new UserListFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When add item selected go to AddActivity
        if (id == R.id.menu_item_add) {
            // Intent to open up Add activity screen
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
