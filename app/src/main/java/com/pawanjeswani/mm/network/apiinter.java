package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.latlon_rest_pojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiinter  {

    @FormUrlEncoded
    @POST("/nearby_restaurants.php")
    Call<latlon_rest_pojo> get_list(
            @Field("lat") double lat,
            @Field("lon") double lon,
            Callback<latlon_rest_pojo> callback
            );
}
