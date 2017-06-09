// Isaias Rosario
// MDF3 - 1604
// UserListFragment Class

package com.isaiasrosario.i_rosario_ce01;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class UserListFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String ACTION_VIEW_DATA = "com.fullsail.android.ACTION_VIEW_DATA";
    public static final String EXTRA_FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String EXTRA_AGE = "com.fullsail.android.EXTRA_AGE";

    // Set up global variables
    ArrayAdapter<UserDataObject> mAdapter;
    ArrayList<UserDataObject> mUsers = new ArrayList<>();
    ListView mListView;
    UserDatabase userDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        // Set view
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        // Initiate list view layout
        mListView = (ListView)v.findViewById(R.id.user_list);

        userDatabase =  new UserDatabase(getActivity());

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listSetup();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        UserDataObject user =  (UserDataObject) mAdapter.getItem(position);

        // Intent to open up Add activity screen
        Intent intent = new Intent();
        intent.setAction(ACTION_VIEW_DATA);
        intent.putExtra(EXTRA_FIRST_NAME, user.getFirstName());
        intent.putExtra(EXTRA_LAST_NAME, user.getLastName());
        intent.putExtra(EXTRA_AGE, user.getAge());
        startActivity(intent);

    }

    public void listSetup(){
        Cursor result = userDatabase.allData();
        mUsers.clear();
        if (result.getCount() == 0){
            // show message
        }else{
            while (result.moveToNext()){
                mUsers.add(new UserDataObject(result.getString(1), result.getString(2), result.getString(3)));
            }
        }

        // Initiate new mAdapter view settings
        mAdapter = new ArrayAdapter<UserDataObject>(getActivity(),
                android.R.layout.simple_list_item_1, mUsers){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                UserDataObject user = (UserDataObject) mUsers.get(position);
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextSize(20);
                text.setText(user.getFirstName() + " " +  user.getLastName());
                return view;
            }
        };

        // Sort mAdapter data alphabetical
        mAdapter.sort(new Comparator<UserDataObject>() {
            @Override
            public int compare(UserDataObject lhs, UserDataObject rhs) {
                return lhs.getFirstName().compareTo(rhs.getFirstName());
            }
        });

        // Update list mAdapter data
        mAdapter.notifyDataSetChanged();

        // Set list view on click listener
        mListView.setOnItemClickListener(this);

        //Set list view adapter
        mListView.setAdapter(mAdapter);
    }
}
