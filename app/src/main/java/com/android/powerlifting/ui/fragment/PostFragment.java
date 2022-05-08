package com.android.powerlifting.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.powerlifting.R;
import com.android.powerlifting.adapters.PostsAdapter;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    private ArrayList<Post> postsList;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        postsList = new ArrayList<Post>();

        String caption = "to create a single activity with multiple fragments in the application or you can create multiple activities as well.\n" +
                "\n" +
                "But the major problem with the fragment transaction was its complex structured application. Android Jetpack Navigation controller provides you with several classes to deal with the fragment transaction\n" +
                "\n" +
                "Hassle-free fragment transaction by using Navigation Artitecture component\n" +
                "\n" +
                "Principle of Navigation\n" +
                "Fixed Start Destination\n" +
                "Navigation state is represented as a stack of destinations\n" +
                "Up and Back are identical within your app’s task\n" +
                "The Up button never exits your app\n" +
                "Deep linking simulates manual navigation\n" +
                "Advantage of using Navigation Controller\n" +
                "Automatic handling of fragment transactions\n" +
                "Correctly handling up and back by default\n" +
                "Default behaviors for animations and transitions\n" +
                "Deep linking as a first-class operation\n" +
                "Implementing navigation UI patterns (like navigation drawers and bottom navigation) with a small amount of code\n" +
                "Type safety arguments when passing information while navigating";

        Member user = new Member("hello", "e43234234234", 213.12f, "sfwefsdf", 12, "Lcochdf");
        postsList.add(new Post(caption, "sdfsdfsdf", user));
        postsList.add(new Post(caption, "sdfsdfsdf", user));
        postsList.add(new Post(caption, "sdfsdfsdf", user));
        postsList.add(new Post(caption, "sdfsdfsdf", user));
        postsList.add(new Post(caption, "sdfsdfsdf", user));
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        setUpRecyclerView(view);
        return view;
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.postRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        postsAdapter = new PostsAdapter(postsList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postsAdapter);
    }
}
