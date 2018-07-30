package com.pawanjeswani.mm.network;

import retrofit2.Retrofit;

public class ApiUtils {
    private ApiUtils() {

    }

    public static final String Base_url = "https://nearby-restaurant.000webhostapp.com/";
    public static insertuserapi getResponseUser(){
        return RerofitInstance.getClient(Base_url).create(insertuserapi.class);
    }


}
