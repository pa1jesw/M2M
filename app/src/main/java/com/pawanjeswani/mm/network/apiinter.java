package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.restLispojo;
import com.pawanjeswani.mm.model.restlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiinter  {

    @GET("nearby_restaurants.php")
    Call<restLispojo> get_list(
            @Query("lat") double lat,
            @Query("lon") double lon
            );
}
