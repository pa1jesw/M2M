package com.pawanjeswani.mm.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface rightSwipeApi {
    @GET("/match_user.php")
    Call<String > rightSwiped(
            @Query("user_id") int user_id,
            @Query("right_swiped_user_id") int right_swiped_user_id
    );
}
