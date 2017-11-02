package com.example.george.myblog;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by george on 31/10/17.
 */

public interface ApiInterface {

    @POST(NetworkURL.LOGIN)
    Call<MessageResponse> login(@Body AuthenticationRequest body);

    @POST(NetworkURL.REGISTRATION)
    Call<MessageResponse> registration(@Body AuthenticationRequest body);

}
