package com.example.george.myblog;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    EditText username,password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        //Initialize the EditTexts
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        //Define the buttons on authentication page
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFormValid()){
                    //perform sign in
                    performSignIn();
                }

            }
        });
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFormValid()){
                    //perform registration
                    performRegistration();
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
    }

    //Check whether the provided username/password is valid.
    private Boolean isFormValid(){
        if(username.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Username cannot be left empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if(password.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Password cannot be left empty", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void performSignIn() {
        showProgressDialog(true);
        ApiManager.getApiInterface().login(new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        showProgressDialog(false);
                        if (response.isSuccessful()){
                            showAlert("welcome",response.body().getMessage());
                        }
                        else{
                            try{
                                String errorMessage = response.errorBody().string();
                                try{
                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage,ErrorResponse.class);
                                    showAlert("Sign in failed",errorResponse.getError());
                                }
                                catch(JsonSyntaxException jsonException){
                                    jsonException.printStackTrace();
                                    showAlert("SignIn Failed","Something went wrong");

                                }
                            }
                            catch(IOException e){
                                e.printStackTrace();
                                showAlert("SignIn Failed","Something went wrong");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        showProgressDialog(false);
                        showAlert("Sign In Failed","Something went wrong");

                    }
                });
    }

    private void performRegistration() {
        showProgressDialog(true);
        ApiManager.getApiInterface().registration(new AuthenticationRequest(username.getText().toString().trim(), password.getText().toString().trim()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        showProgressDialog(false);
                        if (response.isSuccessful()){
                            showAlert("welcome",response.body().getMessage());
                        }
                        else{
                            try{
                                String errorMessage = response.errorBody().string();
                                try{
                                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage,ErrorResponse.class);
                                    showAlert("Registration failed",errorResponse.getError());
                                }
                                catch(JsonSyntaxException jsonException){
                                    jsonException.printStackTrace();
                                    showAlert("Registration Failed","Something went wrong");

                                }
                            }
                            catch(IOException e){
                                e.printStackTrace();
                                showAlert("Registration Failed","Something went wrong");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        showProgressDialog(false);
                        showAlert("Registration Failed","Something went wrong");

                    }
                });
    }

    private void showProgressDialog(Boolean shouldShould) {
        if (shouldShould){
            progressDialog.show();
        }
        else {
            progressDialog.dismiss();
        }

    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    //For when you use Asynch tasks later on
    class SignInTask extends AsyncTask<String, Void, Boolean> {

        String mockUsername = "test";
        String mockPassword = "password";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgressDialog(false);

            if (aBoolean) {
                showAlert("Welcome","You have successfully signed in");
            }
            else {
                showAlert("Sorry","username/password is incorrect");
            }
        }


        @Override
        protected Boolean doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            return username.contentEquals(mockUsername) && password.contentEquals(mockPassword);
        }
    }
}
