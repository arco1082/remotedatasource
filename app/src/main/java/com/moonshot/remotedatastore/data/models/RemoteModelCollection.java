package com.moonshot.remotedatastore.data.models;

import android.util.Log;

import java.util.List;

/**
 * Created by armando_contreras on 4/21/17.
 */

public class RemoteModelCollection<T extends IRemoteModel> {
    private List<T> mItems;


    public RemoteModelCollection(List<T> items) {
        this.mItems = items;
    }

    public List<T> getItems() {
        return mItems;
    }

    public boolean isEmpty() {
        if (mItems == null) {
            return true;
        }

        return mItems.size() == 0;
    }

}
