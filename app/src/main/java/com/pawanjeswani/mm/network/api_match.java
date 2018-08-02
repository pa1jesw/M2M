package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.restLispojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface api_match {

    @GET("match_user.php")
    Call<String > get_match_res(
            @Query("user_id") int user_id,
            @Query("right_swiped_user_id") int right_swiped_user_id
    );
}
