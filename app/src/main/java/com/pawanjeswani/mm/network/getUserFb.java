package com.pawanjeswani.mm.network;

import com.pawanjeswani.mm.model.userpojoRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface getUserFb {
    @GET("get_user_from_fb_id.php")
    Call<String > getUserDetFb(
            @Query("fb_id") String fb_id
    );
}
