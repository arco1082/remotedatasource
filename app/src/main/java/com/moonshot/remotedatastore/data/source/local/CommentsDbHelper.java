package com.moonshot.remotedatastore.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by armando_contreras on 4/21/17.
 */


public class CommentsDbHelper extends SQLiteOpenHelper {
    /**
     * Instantiates a new SQLite database using the supplied database name and version
     *
     * @param context The current context
     */
    CommentsDbHelper(Context context) {
        super(context,
                DataProviderContract.DATABASE_NAME,
                null,
                DataProviderContract.DATABASE_VERSION);
    }


    /**
     * Executes the queries to drop all of the tables from the database.
     *
     * @param db A handle to the provider's backing database.
     */
    private void dropTables(SQLiteDatabase db) {

        // If the table doesn't exist, don't throw an error
        db.execSQL("DROP TABLE IF EXISTS " + DataProviderContract.COLUMN_NAME_COMMENT);
    }

    /**
     * Does setup of the database. The system automatically invokes this method when
     * SQLiteDatabase.getWriteableDatabase() or SQLiteDatabase.getReadableDatabase() are
     * invoked and no db instance is available.
     *
     * @param db the database instance in which to create the tables.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the tables in the backing database for this provider
        CommentTable.onCreate(db);
    }

    /**
     * Handles upgrading the database from a previous version. Drops the old tables and creates
     * new ones.
     *
     * @param db The database to upgrade
     * @param version1 The old database version
     * @param version2 The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
        Log.w(CommentsDbHelper.class.getName(),
                "Upgrading database from version " + version1 + " to "
                        + version2 + ", which will destroy all the existing data");

        // Drops all the existing tables in the database
        dropTables(db);

        // Invokes the onCreate callback to build new tables
        onCreate(db);
    }
    /**
     * Handles downgrading the database from a new to a previous version. Drops the old tables
     * and creates new ones.
     * @param db The database object to downgrade
     * @param version1 The old database version
     * @param version2 The new database version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int version1, int version2) {
        Log.w(CommentsDbHelper.class.getName(),
                "Downgrading database from version " + version1 + " to "
                        + version2 + ", which will destroy all the existing data");

        // Drops all the existing tables in the database
        dropTables(db);

        // Invokes the onCreate callback to build new tables
        onCreate(db);

    }
}

