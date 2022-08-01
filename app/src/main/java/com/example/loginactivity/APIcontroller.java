package com.example.loginactivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIcontroller {

    private  static final String url ="https://jsonplaceholder.typicode.com";   //your base url

    private  static APIcontroller apIcontroller ;

    private static Retrofit retrofit ;


    public APIcontroller() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  static   synchronized APIcontroller getInstance() {
        if(apIcontroller==null)
            apIcontroller= new APIcontroller();
        return apIcontroller ;
    }

    APIset getAPI(){
        return retrofit.create(APIset.class);
    }
}
