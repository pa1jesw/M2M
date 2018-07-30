package com.pawanjeswani.mm.network;

import retrofit2.Retrofit;

public class ApiUtils {
    private ApiUtils() {

    }

    public static final String Base_url = "http://gotechservices.in/meet2munch/ ";
    public static insertuserapi getResponseUser(){
        return RerofitInstance.getClient(Base_url).create(insertuserapi.class);
    }


}
