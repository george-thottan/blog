package com.example.george.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by george on 31/10/17.
 */

public class ErrorResponse {
    @SerializedName("error")
    String error;

    public String getError(){
        return error;
    }
}
