package com.moonshot.remotedatastore.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.SparseArray;

/**
 * Created by armando_contreras on 4/21/17.
 */

public class DataProvider extends ContentProvider {
    // Indicates that the incoming query is for a picture URL
    public static final int COMMENT_QUERY = 1;

    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    // Constants for building SQLite tables during initialization
    private static final String TEXT_TYPE = "TEXT";
    private static final String PRIMARY_KEY_TYPE = "INTEGER PRIMARY KEY";
    private static final String INTEGER_TYPE = "INTEGER";

    // Identifies log statements issued by this component
    public static final String LOG_TAG = "DataProvider";

    // Defines an helper object for the backing database
    private SQLiteOpenHelper mHelper;

    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

    // Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeTypes;


    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {


        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */
        sMimeTypes = new SparseArray<String>();

        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code
        sUriMatcher.addURI(
                DataProviderContract.AUTHORITY,
                DataProviderContract.COMMENT_PATH,
                COMMENT_QUERY);


    }

    // Closes the SQLite database helper class, to avoid memory leaks
    public void close() {
        mHelper.close();
    }

    /**
     * Initializes the content provider. Notice that this method simply creates a
     * the SQLiteOpenHelper instance and returns. You should do most of the initialization of a
     * content provider in its static initialization block or in SQLiteDatabase.onCreate().
     */
    @Override
    public boolean onCreate() {

        // Creates a new database helper object
        mHelper = new CommentsDbHelper(getContext());

        return true;
    }
    /**
     * Returns the result of querying the chosen table.
     * @see android.content.ContentProvider#query(Uri, String[], String, String[], String)
     * @param uri The content URI of the table
     * @param projection The names of the columns to return in the cursor
     * @param selection The selection clause for the query
     * @param selectionArgs An array of Strings containing search criteria
     * @param sortOrder A clause defining the order in which the retrieved rows should be sorted
     * @return The query results, as a {@link android.database.Cursor} of rows and columns
     */
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        SQLiteDatabase db = mHelper.getReadableDatabase();
        // Decodes the content URI and maps it to a code
        switch (sUriMatcher.match(uri)) {

            // If the query is for a picture URL
            case COMMENT_QUERY:
                // Does the query against a read-only version of the database
                Cursor returnCursor = db.query(
                        DataProviderContract.COMMENT_TABLE_NAME,
                        projection,
                        null, null, null, null, null);

                // Sets the ContentResolver to watch this content URI for data changes
                returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
                return returnCursor;
            case INVALID_URI:

                throw new IllegalArgumentException("Query -- Invalid URI:" + uri);
        }

        return null;
    }

    /**
     * Returns the mimeType associated with the Uri (query).
     * @see android.content.ContentProvider#getType(Uri)
     * @param uri the content URI to be checked
     * @return the corresponding MIMEtype
     */
    @Override
    public String getType(Uri uri) {

        return sMimeTypes.get(sUriMatcher.match(uri));
    }
    /**
     *
     * Insert a single row into a table
     * @see android.content.ContentProvider#insert(Uri, ContentValues)
     * @param uri the content URI of the table
     * @param values a {@link android.content.ContentValues} object containing the row to insert
     * @return the content URI of the new row
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Decode the URI to choose which action to take
        switch (sUriMatcher.match(uri)) {


            case COMMENT_QUERY:

                throw new IllegalArgumentException("Insert: Invalid URI" + uri);
        }

        return null;
    }
    /**
     * Implements bulk row insertion using
     * {@link SQLiteDatabase#insert(String, String, ContentValues) SQLiteDatabase.insert()}
     * and SQLite transactions. The method also notifies the current
     * {@link android.content.ContentResolver} that the {@link android.content.ContentProvider} has
     * been changed.
     * @see android.content.ContentProvider#bulkInsert(Uri, ContentValues[])
     * @param uri The content URI for the insertion
     * @param insertValuesArray A {@link android.content.ContentValues} array containing the row to
     * insert
     * @return The number of rows inserted.
     */
    @Override
    public int bulkInsert(Uri uri, ContentValues[] insertValuesArray) {

        // Decodes the content URI and choose which insert to use
        switch (sUriMatcher.match(uri)) {

            // picture URLs table
            case COMMENT_QUERY:

                // Gets a writeable database instance if one is not already cached
                SQLiteDatabase localSQLiteDatabase = mHelper.getWritableDatabase();

                /*
                 * Begins a transaction in "exclusive" mode. No other mutations can occur on the
                 * db until this transaction finishes.
                 */
                localSQLiteDatabase.beginTransaction();

                // Deletes all the existing rows in the table
                localSQLiteDatabase.delete(DataProviderContract.COMMENT_TABLE_NAME, null, null);

                // Gets the size of the bulk insert
                int numRecords = insertValuesArray.length;

                // Inserts each ContentValues entry in the array as a row in the database
                for (int i = 0; i < numRecords; i++) {
                    localSQLiteDatabase.insert(DataProviderContract.COMMENT_TABLE_NAME, null, insertValuesArray[i]);
                }

                // Reports that the transaction was successful and should not be backed out.
                localSQLiteDatabase.setTransactionSuccessful();

                // Ends the transaction and closes the current db instances
                localSQLiteDatabase.endTransaction();
                localSQLiteDatabase.close();

                /*
                 * Notifies the current ContentResolver that the data associated with "uri" has
                 * changed.
                 */

                getContext().getContentResolver().notifyChange(uri, null);

                // The semantics of bulkInsert is to return the number of rows inserted.
                return numRecords;
            case INVALID_URI:

                // An invalid URI was passed. Throw an exception
                throw new IllegalArgumentException("Bulk insert -- Invalid URI:" + uri);

        }

        return -1;

    }
    /**
     * Returns an UnsupportedOperationException if delete is called
     * @see android.content.ContentProvider#delete(Uri, String, String[])
     * @param uri The content URI
     * @param selection The SQL WHERE string. Use "?" to mark places that should be substituted by
     * values in selectionArgs.
     * @param selectionArgs An array of values that are mapped to each "?" in selection. If no "?"
     * are used, set this to NULL.
     *
     * @return the number of rows deleted
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Delete -- unsupported operation " + uri);
    }

    /**
     * Updates one or more rows in a table.
     * @see android.content.ContentProvider#update(Uri, ContentValues, String, String[])
     * @param uri The content URI for the table
     * @param values The values to use to update the row or rows. You only need to specify column
     * names for the columns you want to change. To clear the contents of a column, specify the
     * column name and NULL for its value.
     * @param selection An SQL WHERE clause (without the WHERE keyword) specifying the rows to
     * update. Use "?" to mark places that should be substituted by values in selectionArgs.
     * @param selectionArgs An array of values that are mapped in order to each "?" in selection.
     * If no "?" are used, set this to NULL.
     *
     * @return int The number of rows updated.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        // Decodes the content URI and choose which insert to use
        switch (sUriMatcher.match(uri)) {

            case COMMENT_QUERY:

                throw new IllegalArgumentException("Update: Invalid URI: " + uri);
        }

        return -1;
    }
}