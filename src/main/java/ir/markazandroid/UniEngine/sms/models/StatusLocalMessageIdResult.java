/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.markazandroid.UniEngine.sms.models;

import com.google.gson.JsonObject;

/**
 * @author mohsen
 */
public class StatusLocalMessageIdResult extends StatusResult {

    long localId;

    public StatusLocalMessageIdResult(JsonObject json) {
        super(json);
        this.localId = json.get("localid").getAsLong();
    }

    public long getLocalId() {
        return localId;
    }
}
