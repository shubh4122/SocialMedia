package com.android.powerlifting.models;

public class Member {
    private String name;
    private String phone;
    private float weight;
    private String profilePhotoUrl;

    public Member(String name, String phone, float weight, String profilePhotoUrl) {
        this.name = name;
        this.phone = phone;
        this.weight = weight;
        this.profilePhotoUrl = profilePhotoUrl;
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
}
