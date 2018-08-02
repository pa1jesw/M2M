
package com.pawanjeswani.mm.model;


public class userpojo {
    //Request send this params

    private String Id;
    private String fbId;
    private String name;
    private String age;
    private String email;
    private String gender;
    private String profileUrl;
    private String work;
    private String description;
    private String foodType;
    private String interestedRestaurants;

    public userpojo( String Id,String fbId, String name, String age, String email, String gender, String profileUrl, String work, String description, String foodType, String interestedRestaurants) {

        this.Id=Id;
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
