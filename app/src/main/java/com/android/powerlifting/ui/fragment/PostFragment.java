package com.android.powerlifting.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.powerlifting.R;
import com.android.powerlifting.adapters.PostsAdapter;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;
import com.android.powerlifting.ui.PostViewModel;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    private ArrayList<Post> postsList;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private PostViewModel postViewModel;
    private ProgressBar postLoaderBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        postsList = new ArrayList<Post>();
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        postLoaderBar = view.findViewById(R.id.postLoaderBar);
        setUpRecyclerView(view);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.readPosts(postsList, postsAdapter, postLoaderBar);
        return view;
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.postRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        postsAdapter = new PostsAdapter(postsList, getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postsAdapter);
    }
}
