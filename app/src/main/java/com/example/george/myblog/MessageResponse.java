package com.example.george.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by george on 31/10/17.
 */

public class MessageResponse {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
