// Isaias Rosario
// MDF3 - 1604
// UserDataBroadcast Class

package com.isaiasrosario.i_rosario_ce01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UserDataBroadcast extends BroadcastReceiver {

    public static final String ACTION_SAVE_DATA = "com.fullsail.android.ACTION_SAVE_DATA";
    public static final String ACTION_DELETE_DATA = "com.fullsail.android.ACTION_DELETE_DATA";

    public static final String EXTRA_FIRST_NAME = "com.fullsail.android.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.fullsail.android.EXTRA_LAST_NAME";
    public static final String EXTRA_AGE = "com.fullsail.android.EXTRA_AGE";

    UserDatabase userDatabase;

    @Override
    public void onReceive(Context context, Intent intent) {

        userDatabase = new UserDatabase(context);

        String first = intent.getStringExtra(EXTRA_FIRST_NAME);
        String last = intent.getStringExtra(EXTRA_LAST_NAME);
        Integer age = intent.getIntExtra(EXTRA_AGE,0);

        System.out.println(first+ last+ age);

        if (intent.getAction().equals(ACTION_SAVE_DATA)){

            userDatabase.addData(first,last,age);

            intent = new Intent(AddUserActivity.ACTION_UPDATE_LIST);
            context.sendBroadcast(intent);

        } else if (intent.getAction().equals(ACTION_DELETE_DATA)){

            Integer didDeleteRow = userDatabase.deleteData(first, last, age);

            if (didDeleteRow > 0){
                System.out.println("user deleted");

            }else {
                System.out.println("not deleted");

            }

            intent = new Intent(ViewDetailActivity.ACTION_UPDATE_LIST);
            context.sendBroadcast(intent);
        }
    }
}
