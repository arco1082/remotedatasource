package com.moonshot.remotedatastore.data.source.local;

/**
 * Created by armando_contreras on 4/21/17.
 */


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class DataProviderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DataProviderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class CommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "comment";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_COMMENT = "commentText";
    }

    /**
     * EMPLOYEE table content URI
     */

    /**
     * Picture table primary key column name
     */
    public static final String ROW_ID = BaseColumns._ID;

    /**
     * EMPLOYEE table name
     */
    public static final String COMMENT_TABLE_NAME = "CommentData";
    public static final String COMMENT_PATH = "comment";
    public static final Uri COMMENT_TABLE_CONTENTURI = Uri.parse("content://" + DataProviderContract.AUTHORITY + "/" + COMMENT_PATH);

    // Employee Database table
    public static final String COLUMN_NAME_ENTRY_ID = "uniquid";
    public static final String COLUMN_NAME_COMMENT = "comment";

    // The URI scheme used for content URIs
    public static final String SCHEME = "content";

    // The provider's authority
    public static final String AUTHORITY = "com.moonshot.remotedatastore";

    /**
     * The DataProvider content URI
     */
    public static final Uri CONTENT_URI = Uri.parse(SCHEME + "://" + AUTHORITY);

    /**
     *  The MIME type for a content URI that would return multiple rows
     *  <P>Type: TEXT</P>
     */
    public static final String MIME_TYPE_ROWS =
            "vnd.android.cursor.dir/vnd.com.moonshot.remotedatastore";

    /**
     * The MIME type for a content URI that would return a single row
     *  <P>Type: TEXT</P>
     *
     */
    public static final String MIME_TYPE_SINGLE_ROW =
            "vnd.android.cursor.item/com.moonshot.remotedatastore";


    // The content provider database name
    public static final String DATABASE_NAME = "CommentDB";

    // The starting version of the database
    public static final int DATABASE_VERSION = 1;
}
