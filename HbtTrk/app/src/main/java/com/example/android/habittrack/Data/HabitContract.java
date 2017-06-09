package com.example.android.habittrack.Data;

import android.provider.BaseColumns;

/**
 * Created by irosaro on 9/10/16.
 */
public class HabitContract {

    //create private contract class
    private HabitContract(){}

    //constant values for the database, tracks each habit
    public static final class HabitEntry implements BaseColumns{

        //table name for the habits
        public final static String TABLE_NAME = "habits";

        //id for each habit entry type  INTEGER
        public static final String _ID = BaseColumns._ID;

        //name of habit  TEXT
        public final static String COLUMN_HABIT_NAME = "name";

        //type of habit good or bad  INTEGER, possible values GOOD_HABIT, BAD_HABIT
        public final static String COLUMN_HABIT_TYPE = "type";

        //location where the habit occurs  TEXT
        public final static String COLUMN_HABIT_LOCATION = "location";

        //counts how many times the habit happens  INTEGER
        public final static String COLUMN_HABIT_COUNTER = "counter";

        //possible values for habit type
        public static final int GOOD_HABIT = 0;
        public static final int BAD_HABIT = 1;
    }
}