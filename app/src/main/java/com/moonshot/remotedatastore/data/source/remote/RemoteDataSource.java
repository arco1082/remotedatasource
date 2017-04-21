package com.moonshot.remotedatastore.data.source.remote;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import com.moonshot.remotedatastore.data.models.Comment;
import com.moonshot.remotedatastore.data.models.RemoteModelCollection;
import com.moonshot.remotedatastore.data.source.DataSource;
import com.moonshot.remotedatastore.data.source.local.DataProviderContract;

import java.util.ArrayList;

import javax.inject.Singleton;

import io.reactivex.Observable;
import static com.google.common.base.Preconditions.checkNotNull;
/**
 * Created by armando_contreras on 4/21/17.
 */

@Singleton
public class RemoteDataSource implements DataSource {
    private static String TAG = RemoteDataSource.class.getSimpleName();

    public RemoteDataSource() {

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
        //TODO save to remote
    }


}

