package com.pawanjeswani.mm.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class restLispojo {

        @SerializedName("nearby_restaurants")
        @Expose
        private List<restlist> nearbyRestaurants = null;

        public List<restlist> getNearbyRestaurants() {
            return nearbyRestaurants;
        }

        public void setNearbyRestaurants(List<restlist> nearbyRestaurants) {
            this.nearbyRestaurants = nearbyRestaurants;
        }

    }

