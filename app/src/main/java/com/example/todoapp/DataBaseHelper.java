package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UCharacter;
import android.provider.BaseColumns;
import android.provider.Telephony;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {



    public static final String DATABASE_NAME = "Task.db";
    public static final String TABLE_NAME = "Task_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "JOB";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "TIME";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

         db.execSQL("CREATE TABLE " +TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, JOB TEXT, DATE TEXT, TIME TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String task, String date, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, task);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, time);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result== -1)
            return false;
        else
            return true;

    }

   public Cursor getall()
   {
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
       return res;
   }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        //Context context;
        //String DB_FULL_PATH = context.getDatabasePath(DATABASE_NAME);
        String DB_FULL_PATH ="/data/data/com.example.todoapp/databases/Task.db";
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }


}
