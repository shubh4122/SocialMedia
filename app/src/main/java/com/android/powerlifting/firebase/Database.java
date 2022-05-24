package com.android.powerlifting.firebase;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.powerlifting.adapters.PostsAdapter;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Database {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference postsDatabaseReference;
    private DatabaseReference membersDatabaseReference;
    private ChildEventListener postsChildEventListener;

    public Database() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        postsDatabaseReference = firebaseDatabase.getReference().child("Posts");
        membersDatabaseReference = firebaseDatabase.getReference().child("Members");
    }

    //See is it okay to initialize membersDB and postsDB here.
    //Or must be done in CONSTRUCTOR?
    public void addMembers(Member member) {
        membersDatabaseReference.push().setValue(member);
    }

    public void addPosts(Post post) {
        postsDatabaseReference.push().setValue(post);
    }

    public void readMembers() {}

    public void readPosts(ArrayList<Post> postList, PostsAdapter postsAdapter, ProgressBar postLoaderBar){

        if(postsChildEventListener == null) {

            postsChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Post post = snapshot.getValue(Post.class);
                    //Adding the changed item to last of the arraylist!!
                    postList.add(0, post);
                    //notifies item inserted at given position
                    postsAdapter.notifyItemInserted(0);

                    postLoaderBar.setVisibility(View.GONE);
                    //TODO: Done for scrolling to Position given in Params. NOTE
//                recyclerView.scrollToPosition(postList.size() - 1);
                }

                //TODO: For now i only added new post listener. Also add changed/Edit listener
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

            postsDatabaseReference.addChildEventListener(postsChildEventListener);
        }
    }

    public void editPost() {

    }

    public void deletePost() {
//        Query query = postsDatabaseReference.orderByChild("")
    }
}
