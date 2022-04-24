package com.android.powerlifting.firebase;

import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference postsDB;
    private DatabaseReference membersDB;

    public Database() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    //See is it okay to initialize membersDB and postsDB here.
    //Or must be done in CONSTRUCTOR?
    public void addMembers(Member member) {
        membersDB = firebaseDatabase.getReference().child("Members");
        membersDB.push().setValue(member);
    }

    public void addPosts(Post post) {
        postsDB = firebaseDatabase.getReference().child("Posts");
        postsDB.push().setValue(post);
    }
}
