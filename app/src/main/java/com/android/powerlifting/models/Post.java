package com.android.powerlifting.models;

public class Post {

    private String caption;
    private String photoUrl;
    private Member user; // Who posted the post. Admin or Secretaries

    public Post(String caption, String photoUrl, Member user) {
        this.caption = caption;
        this.photoUrl = photoUrl;
        this.user = user;
    }

    //No setter method for now. Coz we don't wish to let them edit post for now

    public String getCaption() {
        return caption;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Member getUser() {
        return user;
    }
}
