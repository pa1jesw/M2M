package com.pawanjeswani.mm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class listNearbyUsers {

    @SerializedName("")
    @Expose
    private List<userpojoRes> nearbyUsers = null;

    public List<userpojoRes> getNearbyRestaurants() {
        return nearbyUsers;
    }

    public void setNearbyRestaurants(List<userpojoRes> nearbyUsers) {
        this.nearbyUsers = nearbyUsers;
    }

}
