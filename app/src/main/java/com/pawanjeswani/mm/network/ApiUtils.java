package com.pawanjeswani.mm.network;

import retrofit2.Retrofit;

public class ApiUtils {
    private ApiUtils() {

    }

    public static final String Base_url = "http://gotechservices.in/meet2munch/";

    public static insertuserapi getResponseUser(){
        return RerofitInstance.getClient(Base_url).create(insertuserapi.class);
    }

    public static apiinter getRestList(){
        return RerofitInstance.getClient(Base_url).create(apiinter.class);
    }
    public static user_details_if getUserDet(){
        return RerofitInstance.getClient(Base_url).create(user_details_if.class);
    }
    public static getusersapi getnearusers(){
        return RerofitInstance.getClient(Base_url).create(getusersapi.class);
    }
    public static api_match getmatchres(){
        return RerofitInstance.getClient(Base_url).create(api_match.class);
    }

}
