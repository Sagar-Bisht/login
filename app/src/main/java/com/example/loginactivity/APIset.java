package com.example.loginactivity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIset {

    @FormUrlEncoded

    @POST("login/")   //your .php file

    Call<ResponseModel> verifyUser(

      @Field("email") String email ,

      @Field("password") String password

    );
}
