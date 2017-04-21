package com.moonshot.remotedatastore.data.source.local;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by armando_contreras on 4/21/17.
 */

public class CommentTable {

    public static final String[] projection = {DataProviderContract.ROW_ID, DataProviderContract.COLUMN_NAME_ENTRY_ID,
            DataProviderContract.COLUMN_NAME_COMMENT};

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + DataProviderContract.COMMENT_TABLE_NAME
            + "("
            + DataProviderContract.ROW_ID + " integer primary key autoincrement, "
            + DataProviderContract.COLUMN_NAME_ENTRY_ID + " TEXT, "
            + DataProviderContract.COLUMN_NAME_COMMENT + " TEXT"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DataProviderContract.COMMENT_TABLE_NAME);
        onCreate(database);
    }
}
