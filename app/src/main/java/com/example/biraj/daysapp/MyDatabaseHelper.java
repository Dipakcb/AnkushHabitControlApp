package com.example.biraj.daysapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "DaysApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_goal";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "goal_name";
    private static final String COLUMN_STARTDATE = "goal_start";
    private static final String COLUMN_ENDDATE = "goal_end";

    private static final String TABLE_NAME2 = "goal_complete";
    private static final String COLUMN_ID2 = "_id2";
    private static final String COLUMN_NAME2 = "goal_name2";
    private static final String COLUMN_STARTDATE2 = "goal_start2";
    private static final String COLUMN_ENDDATE2 = "goal_end2";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_STARTDATE + " DATE, " +
                COLUMN_ENDDATE + " DATE );";
        db.execSQL(query);

        String query2 = " CREATE TABLE " + TABLE_NAME2 +
                "(" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME2 + " TEXT, " +
                COLUMN_STARTDATE2 + " DATE, " +
                COLUMN_ENDDATE2 + " DATE );";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }


    //Addgoal Listview1

    public boolean addGoal(String name, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_STARTDATE, start);
        cv.put(COLUMN_ENDDATE, end);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }

    public void updateRow(String newStart, String newEnd, String name, String oldStart) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_STARTDATE +
                " = '" + newStart + "' " + " , " + COLUMN_ENDDATE + " = '" + newEnd + "' WHERE " + COLUMN_NAME + " = '" + name + "'" +
                " AND " + COLUMN_STARTDATE + " = '" + oldStart + "'";
        db.execSQL(query);

    }

    public void deleteRow(String name, String start) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + name + "'" + " AND " + COLUMN_STARTDATE + " = '" + start + "' ";
        db.execSQL(query);
    }

    public List<GoalSet> getlistContents() {

        List<GoalSet> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        if (data.moveToFirst()) {
            do {
                int id = data.getInt(0);
                String name = data.getString(1);
                String start = data.getString(2);
                String end = data.getString(3);

                GoalSet gs = new GoalSet(name, start, end);
                list.add(gs);

            } while (data.moveToNext());
        }

        return list;
    }


    //ListView 2

    public boolean addGoalComplete(String name, String start, String end) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME2, name);
        cv.put(COLUMN_STARTDATE2, start);
        cv.put(COLUMN_ENDDATE2, end);

        long result = db.insert(TABLE_NAME2, null, cv);

        if (result == -1) {
            return false;
        } else {

            return true;
        }
    }

    public Cursor getData(String name , String start){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + name + "'" +
                " AND " + COLUMN_STARTDATE + " = '" + start + "'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor ;
    }


    public List<GoalSetCompleted> getlistContents2() {

        List<GoalSetCompleted> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery(" SELECT * FROM " + TABLE_NAME2, null);
        if (data.moveToFirst()) {
            do {
                int id = data.getInt(0);
                String name = data.getString(1);
                String start = data.getString(2);
                String end = data.getString(3);

                GoalSetCompleted gs = new GoalSetCompleted(name, start, end);
                list.add(gs);

            } while (data.moveToNext());
        }

        return list;
    }

}
