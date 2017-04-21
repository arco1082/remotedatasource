package com.moonshot.remotedatastore.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.moonshot.remotedatastore.data.models.Comment;
import com.moonshot.remotedatastore.data.models.RemoteModelCollection;
import com.moonshot.remotedatastore.data.source.DataSource;
import com.moonshot.remotedatastore.rx.BaseSchedulerProvider;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by armando_contreras on 4/21/17.
 */

@Singleton
public class LocalDataSource implements DataSource {
    private static String TAG = com.moonshot.remotedatastore.data.source.remote.RemoteDataSource.class.getSimpleName();

    private Context mContext;

    public LocalDataSource(Context context) {
        mContext  = context;
        CommentsDbHelper dbHelper = new CommentsDbHelper(context);
    }

    @Override
    public Observable<RemoteModelCollection<Comment>> loadItems() {
        Log.d(TAG, "getFollowerList");
        ArrayList<Comment> items = new ArrayList<>();
        items.add(new Comment(1, "unique id 1", "Test Comment 1"));
        items.add(new Comment(2, "unique id 1","Test Comment 1"));
        items.add(new Comment(3,"unique id 1", "Test Comment 1"));
        items.add(new Comment(4,"unique id 1", "Test Comment 1"));
        items.add(new Comment(5, "unique id 1","Test Comment 1"));

        RemoteModelCollection<Comment> collection = new RemoteModelCollection<Comment>(items);
        Observable<RemoteModelCollection<Comment>> obs = Observable.just(collection);
        return obs;
    }

    @Override
    public void saveComment(@NonNull Comment comment) {
        checkNotNull(comment);
        ContentValues values = new ContentValues();
        values.put(DataProviderContract.CommentEntry.COLUMN_NAME_ENTRY_ID, comment.getUniqueId());
        values.put(DataProviderContract.CommentEntry.COLUMN_NAME_COMMENT, comment.getComment());
        //mDatabaseHelper.insert(DataProviderContract.CommentEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);

        //Uri aid = mContext.getContentResolver().insert(DataProviderContract.COMMENT_PATH, values);

    }


}