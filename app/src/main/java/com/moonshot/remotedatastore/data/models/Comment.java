package com.moonshot.remotedatastore.data.models;

import org.json.JSONObject;

/**
 * Created by armando_contreras on 4/21/17.
 */

public class Comment implements IRemoteModel{

    private int mId;
    private String mUniqueId;
    private String mComment;


    public Comment(int uId, String comment, String uniqueId) {
        this.mId = uId;
        this.mComment = comment;
        this.mUniqueId = uniqueId;
    }

    public String getUniqueId() {
        return mUniqueId;
    }

    public String getComment() {
        return mComment;
    }

    @Override
    public String serialize() {
        return null;
    }

    @Override
    public JSONObject buildJsonObject() {
        return null;
    }
}
