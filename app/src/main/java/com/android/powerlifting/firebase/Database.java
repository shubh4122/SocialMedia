package com.android.powerlifting.firebase;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.powerlifting.adapters.MembersAdapter;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Database {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference postsDB;
    private DatabaseReference membersDB;
    private ChildEventListener membersChildEventListener;


    public Database() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        postsDB = firebaseDatabase.getReference().child("Posts");
        membersDB = firebaseDatabase.getReference().child("Members");
    }

    //See is it okay to initialize membersDB and postsDB here.
    //Or must be done in CONSTRUCTOR?
    public void addMembers(Member member) {
        membersDB.push().setValue(member);
    }

    public void addPosts(Post post) {
        postsDB.push().setValue(post);
    }

    public void readMembers(ArrayList<Member> membersList, MembersAdapter membersAdapter, ProgressBar memberLoaderBar){

        if(membersChildEventListener == null) {

            membersChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Member member = snapshot.getValue(Member.class);
                    membersList.add(0, member);
                    membersAdapter.notifyItemInserted(0);
                    memberLoaderBar.setVisibility(View.GONE);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };
            membersDB.addChildEventListener(membersChildEventListener);
        }
    }


}

