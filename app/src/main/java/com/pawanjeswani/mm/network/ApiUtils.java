package com.pawanjeswani.mm.network;

import retrofit2.Retrofit;

public class ApiUtils {
    private ApiUtils() {

    }

    public static final String Base_url = "http://gotechservices.in/meet2munch/";

    public static insertuserapi getResponseUser(){
        return RerofitInstance.getClient(Base_url).create(insertuserapi.class);
    }
    public static updateUserApi updateUser(){
        return RerofitInstance.getClient(Base_url).create(updateUserApi.class);
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
    public static rightSwipeApi getRightA(){
        return RerofitInstance.getClient(Base_url).create(rightSwipeApi.class);
    }
    public static leftSwipeApi getLeftA(){
        return RerofitInstance.getClient(Base_url).create(leftSwipeApi.class);
    }
    public static getUserFb getUserFb(){
        return RerofitInstance.getClient(Base_url).create(getUserFb.class);
    }

}
