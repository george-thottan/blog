package com.example.george.myblog;

import com.google.gson.annotations.SerializedName;

/**
 * Created by george on 31/10/17.
 */

public class AuthenticationRequest {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;
    public AuthenticationRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
}
