/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.markazandroid.UniEngine.sms.models;

import com.google.gson.JsonObject;
import ir.markazandroid.UniEngine.sms.enums.MessageStatus;

/**
 * @author mohsen
 */
public class StatusResult {

    int messageId;
    MessageStatus status;
    String statusText;

    protected StatusResult() {

    }

    public StatusResult(JsonObject json) {
        this.messageId = (json.get("messageid").getAsInt());
        this.status = MessageStatus.valueOf(json.get("status").getAsInt());
        this.statusText = json.get("statustext").getAsString();
    }

    public int getMessageId() {
        return messageId;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public String getStatusText() {
        return statusText;
    }
}
