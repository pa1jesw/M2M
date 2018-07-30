package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.response_user_id;
import com.pawanjeswani.mm.model.userpojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface insertuserapi {

    @FormUrlEncoded
    @POST("facebook_details_insert.php")
    Call<response_user_id> insertUser(
                    @Field("name") String name,
                    @Field("age") String age,
                    @Field("email") String email,
                    @Field("gender") String gender,
                    @Field("profile_url") String profile_url,
                    @Field("work") String work,
                    @Field("description ") String description,
                    @Field("food_type") String food_type ,
                    @Field("interested_restaurants") String interested_restaurants
            );
}
