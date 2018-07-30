package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.response_user_id;
import com.pawanjeswani.mm.model.userpojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface insertuserapi {


    @GET("facebook_details_insert.php")
    Call<response_user_id> insertUser(
                    @Query("name") String name,
                    @Query("age") int age,
                    @Query("email") String email,
                    @Query("gender") int gender,
                    @Query("profile_url") String profile_url,
                    @Query("work") String work,
                    @Query("description ") String description,
                    @Query("food_type") int food_type ,
                    @Query("interested_restaurants") String interested_restaurants
            );
}
