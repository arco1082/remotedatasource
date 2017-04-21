package com.moonshot.remotedatastore.data.source;

import android.support.annotation.NonNull;

import com.moonshot.remotedatastore.data.models.Comment;
import com.moonshot.remotedatastore.data.models.IRemoteModel;
import com.moonshot.remotedatastore.data.models.RemoteModelCollection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by armando_contreras on 4/21/17.
 */

@Singleton
public class DataRepository implements DataSource {

    private final DataSource mDataSource;
    private final DataSource mLocalDataSource;
    @VisibleForTesting
    @Nullable
    Map<String, Comment> mCachedComments;
    @Inject
    DataRepository(@Remote DataSource remoteSource, @Local DataSource localSource) {
        mDataSource = remoteSource;
        mLocalDataSource = localSource;
    }

    @Override
    public Observable<RemoteModelCollection<Comment>> loadItems() {
        return mDataSource.loadItems();


    }

    @Override
    public void saveComment(@NonNull Comment comment) {
        checkNotNull(comment);
        mDataSource.saveComment(comment);
        mLocalDataSource.saveComment(comment);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedComments == null) {
            mCachedComments = new LinkedHashMap<>();
        }
    }


}

