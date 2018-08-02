package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.listNearbyUsers;
import com.pawanjeswani.mm.model.userpojoRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface getusersapi {
    @GET("find_nearby_users.php")
    Call<List<userpojoRes>> getNearUSers(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("user_id") int id
    );
}
