package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.userpojoRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface updateUserApi {

    @GET("update_user_details.php.php")
    Call<userpojoRes> updateUser(
            @Query("user_id") int user_id,
            @Query("name") String name,
            @Query("age") int age,
            @Query("email") String email,
            @Query("fb_id") String fb_id,
            @Query("gender") int gender,
            @Query("profile_url") String profile_url,
            @Query("work") String work,
            @Query("description ") String description,
            @Query("food_type") int food_type ,
            @Query("interested_restaurants") String interested_restaurants
    );
}
