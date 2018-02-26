package com.ambulance.Model;

/**
 * Created by sumit on 25-Jan-18.
 */

public class Result {

    public String messageId;

    public Result() {
    }

    public Result(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
