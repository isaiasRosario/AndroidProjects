// Isaias Rosario
// MDF3 - 1604
// UserDatabase Class

package com.isaiasrosario.i_rosario_ce01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="user.db";
    public static final String TABLE_NAME ="user_table";
    public static final String COLUM_1 ="ID";
    public static final String COLUM_2 ="FIRST";
    public static final String COLUM_3 ="LAST";
    public static final String COLUM_4 ="AGE";

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRST TEXT, LAST TEXT, AGE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String first, String last, Integer age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUM_2, first);
        contentValues.put(COLUM_3, last);
        contentValues.put(COLUM_4, age);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            System.out.println("not added");
            return false;
        }else {
            System.out.println("user added");
            return true;

        }
    }

    public Cursor allData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }

    public Integer deleteData(String first, String last , Integer age){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME, "FIRST=? and LAST=? and AGE=?",new String[]{first,last, String.valueOf(age)});
    }
}
