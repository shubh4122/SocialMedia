package com.android.powerlifting.models;

import java.io.Serializable;

public class Member implements Serializable {
    private String name;
    private String phone;
    private String weight;
    private String profilePhotoUrl;
    private String age;
    private String location; // State


    public Member(){}

    public Member(String name, String phone, String weight, String profilePhotoUrl, String age, String location) {
        this.name = name;
        this.phone = phone;
        this.weight = weight;
        this.profilePhotoUrl = profilePhotoUrl;
        this.age = age;
        this.location = location;
    }

    //For admin Panel
    public Member(String name, String profilePhotoUrl, String location) {
        this.name = name;
        this.profilePhotoUrl = profilePhotoUrl;
        this.location = location;
    }

    //setters are used cause these fields are changeable!
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWeight() {
        return weight;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public String getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }
}
