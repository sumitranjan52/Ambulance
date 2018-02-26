package com.ambulance.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sumit on 26-Jan-18.
 */

public class FCMClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(String baseURL){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
