package com.pawanjeswani.mm.model;

public class userpojo {

    String Uname,UAge,Uemail,UpicUrl,Uwork,UDesc,Ulat,Ulon;
    int UGender,UFoodPr;
    int[] URests;

    public userpojo(String uname, String UAge, String uemail, int UGender, String upicUrl, String uwork, String UDesc, int UFoodPr, String ulat, String ulon, int[] URests) {
        Uname = uname;
        this.UAge = UAge;
        Uemail = uemail;
        UpicUrl = upicUrl;
        Uwork = uwork;
        this.UDesc = UDesc;
        Ulat = ulat;
        Ulon = ulon;
        this.UGender = UGender;
        this.UFoodPr = UFoodPr;
        this.URests = URests;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUAge() {
        return UAge;
    }

    public void setUAge(String UAge) {
        this.UAge = UAge;
    }

    public String getUemail() {
        return Uemail;
    }

    public void setUemail(String uemail) {
        Uemail = uemail;
    }

    public String getUpicUrl() {
        return UpicUrl;
    }

    public void setUpicUrl(String upicUrl) {
        UpicUrl = upicUrl;
    }

    public String getUwork() {
        return Uwork;
    }

    public void setUwork(String uwork) {
        Uwork = uwork;
    }

    public String getUDesc() {
        return UDesc;
    }

    public void setUDesc(String UDesc) {
        this.UDesc = UDesc;
    }

    public String getUlat() {
        return Ulat;
    }

    public void setUlat(String ulat) {
        Ulat = ulat;
    }

    public String getUlon() {
        return Ulon;
    }

    public void setUlon(String ulon) {
        Ulon = ulon;
    }

    public int getUGender() {
        return UGender;
    }

    public void setUGender(int UGender) {
        this.UGender = UGender;
    }

    public int getUFoodPr() {
        return UFoodPr;
    }

    public void setUFoodPr(int UFoodPr) {
        this.UFoodPr = UFoodPr;
    }

    public int[] getURests() {
        return URests;
    }

    public void setURests(int[] URests) {
        this.URests = URests;
    }
}
