package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.latlon_rest_pojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiinter  {
    String BASE_URL =  "https://nearby-restaurant.000webhostapp.com/nearby_restaurants.php";

    @GET("rests")
    Call<List<latlon_rest_pojo>> getrests();
}
