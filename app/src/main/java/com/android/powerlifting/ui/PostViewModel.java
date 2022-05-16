package com.android.powerlifting.ui;

import android.app.Application;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.powerlifting.adapters.PostsAdapter;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Post;

import java.util.ArrayList;

//Its good Practice to make 1 viewModel per View/Model. So here i made
//2 viewModels -- Member and Post viewModel.
public class PostViewModel extends AndroidViewModel {

    //Theres one doubt. Whats the point of using Livedata here. Firebase DB already
    //automatically looks for changes. SEE?

    private Database postDatabase;

    public PostViewModel(@NonNull Application application) {
        super(application);
        postDatabase = new Database();
    }

    public void addPosts(Post post) {
        postDatabase.addPosts(post);
    }

    public void readPosts(ArrayList<Post> postList, PostsAdapter postsAdapter, ProgressBar postLoaderBar) {
        postDatabase.readPosts(postList, postsAdapter, postLoaderBar);
    }
}
