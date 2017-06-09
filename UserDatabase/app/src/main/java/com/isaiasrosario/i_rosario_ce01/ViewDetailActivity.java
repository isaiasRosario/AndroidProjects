// Isaias Rosario
// MDF3 - 1604
// ViewDetailActivity Class

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
import android.widget.TextView;

public class ViewDetailActivity extends AppCompatActivity {

    public static final String ACTION_UPDATE_LIST = "com.fullsail.android.ACTION_UPDATE_LIST";

    BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        FragmentManager fragManager = getFragmentManager();
        fragManager.beginTransaction().replace(R.id.viewFrame, new ViewDetailFragment()).commit();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item id
        int id = item.getItemId();

        // When delete item selected it will delete user
        if (id == R.id.menu_item_delete) {

            TextView first = (TextView)findViewById(R.id.firstTextView);
            TextView last = (TextView)findViewById(R.id.lastTextView);
            TextView age = (TextView)findViewById(R.id.ageTextView);

            Intent intent = new Intent(UserDataBroadcast.ACTION_DELETE_DATA);
            intent.putExtra(UserDataBroadcast.EXTRA_FIRST_NAME, first.getText().toString());
            intent.putExtra(UserDataBroadcast.EXTRA_LAST_NAME, last.getText().toString());
            intent.putExtra(UserDataBroadcast.EXTRA_AGE, Integer.parseInt(age.getText().toString()));

            sendBroadcast(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Detail View placeholder Fragment
    public static class ViewDetailFragment extends Fragment {

        TextView first,last,age;

        public ViewDetailFragment(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            super.onCreateView(inflater, container, savedInstanceState);

            // View set up
            View v = inflater.inflate(R.layout.fragment_view, container, false);

            first = (TextView)v.findViewById(R.id.firstTextView);
            last = (TextView)v.findViewById(R.id.lastTextView);
            age = (TextView)v.findViewById(R.id.ageTextView);

            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            Intent intent = getActivity().getIntent();
            if (intent.getAction().equals(UserListFragment.ACTION_VIEW_DATA)){
                first.setText(intent.getStringExtra(UserListFragment.EXTRA_FIRST_NAME));
                last.setText(intent.getStringExtra(UserListFragment.EXTRA_LAST_NAME));
                age.setText(intent.getStringExtra(UserListFragment.EXTRA_AGE));
            }else {
                System.out.println("Wrong Action");
            }
        }
    }
}
