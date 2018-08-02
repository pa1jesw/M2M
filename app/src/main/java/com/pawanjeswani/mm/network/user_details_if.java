package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.restLispojo;
import com.pawanjeswani.mm.model.userpojoRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface user_details_if {

    @GET("get_user_details.php")
    Call<userpojoRes> get_details(
            @Query("user_id") int user_id
    );
}

