package com.example.android.habittrack.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by irosaro on 9/10/16.
 */
public class HabitDBHelper extends SQLiteOpenHelper {

    //this will be the name of the database file
    private static final String DATABASE_NAME = "habits.db";

    //database version
    private static final int DATABASE_VERSION = 1;

    //constructs new HabitDBHelper class
    public HabitDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL statement string for habits table
        String CREATE_HABITS_TABLE =  "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_TYPE + " INTEGER NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_LOCATION+ " TEXT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_COUNTER + " INTEGER DEFAULT 0);";

        //execute SQl to create table
        db.execSQL(CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //delete database table method
    public void deleteDatabase(Context c){
        c.deleteDatabase(DATABASE_NAME);

        Log.v("DB_HELPER_DELETE_DATA","-------> DATABASE WAS DELETED");
    }
}