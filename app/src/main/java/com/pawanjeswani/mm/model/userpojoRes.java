package com.pawanjeswani.mm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userpojoRes {

    public userpojoRes(String id, String fbId, String name, String age, String email, String gender, String profileUrl, String work, String description, String foodType, String interestedRestaurants) {
        this.id = id;
        this.fbId = fbId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.profileUrl = profileUrl;
        this.work = work;
        this.description = description;
        this.foodType = foodType;
        this.interestedRestaurants = interestedRestaurants;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("work")
    @Expose
    private String work;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("food_type")
    @Expose
    private String foodType;
    @SerializedName("interested_restaurants")
    @Expose
    private String interestedRestaurants;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getInterestedRestaurants() {
        return interestedRestaurants;
    }

    public void setInterestedRestaurants(String interestedRestaurants) {
        this.interestedRestaurants = interestedRestaurants;
    }

}
