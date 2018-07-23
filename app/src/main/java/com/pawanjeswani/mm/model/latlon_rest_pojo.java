package com.pawanjeswani.mm.model;

import com.google.gson.annotations.SerializedName;

public class latlon_rest_pojo {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
