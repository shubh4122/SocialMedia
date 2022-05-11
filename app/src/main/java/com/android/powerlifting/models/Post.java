package com.android.powerlifting.models;

public class Post {

    private String caption;
    private String photoUrl;
    private String timeOfPost;
    private String locationOfPostGenerator;
    private Member user; // Who posted the post. Admin or Secretaries

//    This no argument constructor is necessary to make.
//    OtherWise it poses this error:
//    FATAL EXCEPTION:
//    firebaseDatabaseException: Class models.Post does not define a no-argument constructor.
    public Post(){}

    public Post(String caption, String photoUrl, Member user, String time, String location) {
        this.caption = caption;
        this.photoUrl = photoUrl;
        this.user = user;
        this.timeOfPost = time;
        this.locationOfPostGenerator = location;
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

    public String getTimeOfPost() {
        return timeOfPost;
    }

    public String getLocationOfPostGenerator() {
        return locationOfPostGenerator;
    }
}
