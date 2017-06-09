package com.example.android.habittrack;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.habittrack.Data.HabitContract;
import com.example.android.habittrack.Data.HabitDBHelper;

public class MainActivity extends AppCompatActivity {
    HabitDBHelper mHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //set up and create database and helper
            mHelper = new HabitDBHelper(this);
            db = mHelper.getReadableDatabase();

        //crud database methods

        //call create
        createHabit();
        //call read
        readHabit();
        //call update and pass row id you want to update
        updateHabit(1);
        //call read to see update
        readHabit();
        //call delete all habits
        deleteAllHabits();
        //call delete database table
        mHelper.deleteDatabase(this);
    }

    //create habit entry method
    private void createHabit(){
        db = mHelper.getWritableDatabase();

        //values to store when habit entry is created
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, "Running");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TYPE, HabitContract.HabitEntry.GOOD_HABIT);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION, "Gym");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_COUNTER, 5);

        long rowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        Log.v("MAIN_ACTIVITY_INSERT","----> ROW ID " + rowId + " ADDED");
    }

    //create habit entry method
    private void readHabit(){
        db = mHelper.getReadableDatabase();

        //datebaase projection values
        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_TYPE,
                HabitContract.HabitEntry.COLUMN_HABIT_LOCATION,
                HabitContract.HabitEntry.COLUMN_HABIT_COUNTER
        };

        // cursor query to get database data
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try{
            //move through cursor data and display it
            while (cursor.moveToNext()){

                int currentID = cursor.getInt(cursor.
                        getColumnIndex(HabitContract.HabitEntry._ID));
                String habitName = cursor.getString(cursor.
                        getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME));
                int habitType = cursor.getInt(cursor.
                        getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_TYPE));
                String habitLocation = cursor.getString(cursor.
                        getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION));
                int habitCounter = cursor.getInt(cursor.
                        getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_COUNTER));

                Log.v("MAIN_ACTIVITY_READ","---CURSOR ROW---> " +
                        currentID + " - " +
                        habitName + " - " +
                        habitType + " - " +
                        habitLocation + " - " +
                        habitCounter);
            }

        }finally {
            //close cursor when done
            cursor.close();
        }
    }

    //update habit entry method
    private void updateHabit(int id){
        db = mHelper.getWritableDatabase();

        //content values to store in the database after update
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, "Hey it changed!");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TYPE, HabitContract.HabitEntry.BAD_HABIT);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION, "Somewhere Cool");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_COUNTER, 99);

        db.update(HabitContract.HabitEntry.TABLE_NAME, values, "_id=" + id, null);

        Log.v("MAIN_ACTIVITY_UPDATE","-------> ROW " + id + " WAS UPDATED");
    }

    //delete all habit entries method
    private void deleteAllHabits(){
        db = mHelper.getWritableDatabase();

        //delete all habit entries by deleting the entire table
        db.delete(HabitContract.HabitEntry.TABLE_NAME, null, null);

        Log.v("MAIN_ACTIVITY_DELETE","-------> All ROWS ARE DELETED");
    }
}