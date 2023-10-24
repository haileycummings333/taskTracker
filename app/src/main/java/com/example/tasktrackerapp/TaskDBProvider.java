package com.example.tasktrackerapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class TaskDBProvider extends ContentProvider {
    public static final String DBNAME = "TASKSDB";
    public static final String COLUMN1_NAME = "Description";
    public static final String COLUMN2_NAME = "Owner";
    public static final String TABLE_NAME = "Tasks";
    public static final String AUTHORITY = "com.example.tasktrackerapp";
    public static final Uri contentURI = Uri.parse("content://" + AUTHORITY + "/" + DBNAME);
    private static final String CREATE_DB_QUERY = "CREATE TABLE" + TABLE_NAME + //SQL query
            "(_ID INTEGER PRIMARY KEY" +
            COLUMN1_NAME + "TEXT,"
            +
            COLUMN2_NAME + "TEXT)";

    protected  static final class MainDatabaseHelper extends SQLiteOpenHelper{
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_DB_QUERY);
        }
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

        }
    };

    private MainDatabaseHelper SQLHelper;

    public TaskDBProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return SQLHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        String a = values.getAsString();
//        if(values.getAsString(COLUMN1_NAME).length() > 0){
//
//        }
        long id = SQLHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        //authority/db/table/number
        return Uri.withAppendedPath(contentURI , "" + id);
    }

    @Override
    public boolean onCreate() {
        SQLHelper = new MainDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return SQLHelper.getReadableDatabase().query(TABLE_NAME,projection,selection,selectionArgs,null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}