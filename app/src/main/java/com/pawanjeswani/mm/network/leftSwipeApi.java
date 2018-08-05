package com.pawanjeswani.mm.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface leftSwipeApi {
    @GET("left_swipe.php")
    Call<String > leftSwiped(
            @Query("user_id") int user_id,
            @Query("left_swiped_user_id") int left_swiped_user_id
    );
}
