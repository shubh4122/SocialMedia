package com.android.powerlifting.models;

public class Member {
    private String name;
    private String phone;
    private float weight;
    private String profilePhotoUrl;
    private int age;
    private String location; // State

    public Member(String name, String phone, float weight, String profilePhotoUrl,int age, String location) {
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

    public void setWeight(float weight) {
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

    public float getWeight() {
        return weight;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }
}
