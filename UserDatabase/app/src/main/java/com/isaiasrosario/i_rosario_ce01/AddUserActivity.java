// Isaias Rosario
// MDF3 - 1604
// AddUserActivity Class

package com.isaiasrosario.i_rosario_ce01;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddUserActivity extends AppCompatActivity {
    public static final String ACTION_UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";

    BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        FragmentManager fragManager = getFragmentManager();
        fragManager.beginTransaction().replace(R.id.addFrame, new AddUserFragment()).commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        mReceiver = new UpdateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_LIST);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    private class UpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When save item selected it will save user
        if (id == R.id.menu_item_save) {

            // Initiate edit text view set up
            EditText first = (EditText)findViewById(R.id.firstEditText);
            EditText last = (EditText)findViewById(R.id.lastEditText);
            EditText age = (EditText)findViewById(R.id.ageEditText);

            if (first.getText().toString().isEmpty()){
                System.out.println("No data saved");
            }else {
                // Send broadcast to save new user data
                Intent intent = new Intent(UserDataBroadcast.ACTION_SAVE_DATA);
                intent.putExtra(UserDataBroadcast.EXTRA_FIRST_NAME, first.getText().toString());
                intent.putExtra(UserDataBroadcast.EXTRA_LAST_NAME, last.getText().toString());
                intent.putExtra(UserDataBroadcast.EXTRA_AGE, Integer.parseInt(age.getText().toString()));

                sendBroadcast(intent);

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //AddUserFragment placeholder Fragment
    public static class AddUserFragment extends Fragment {

        public AddUserFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            super.onCreateView(inflater, container, savedInstanceState);
            // View set up
            View v = inflater.inflate(R.layout.fragment_add, container, false);
            return v;
        }
    }
}
