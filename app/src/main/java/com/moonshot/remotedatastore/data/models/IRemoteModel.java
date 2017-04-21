package com.moonshot.remotedatastore.data.models;

import org.json.JSONObject;

/**
 * Created by armando_contreras on 4/21/17.
 */

public interface IRemoteModel {
    String serialize();
    JSONObject buildJsonObject();
}
