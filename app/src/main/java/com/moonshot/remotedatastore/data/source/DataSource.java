package com.moonshot.remotedatastore.data.source;

import android.support.annotation.NonNull;

import com.moonshot.remotedatastore.data.models.Comment;
import com.moonshot.remotedatastore.data.models.IRemoteModel;
import com.moonshot.remotedatastore.data.models.RemoteModelCollection;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by armando_contreras on 4/21/17.
 */

public interface DataSource {

    Observable<RemoteModelCollection<Comment>> loadItems();

    void saveComment(@NonNull Comment comment);
}
