package com.android.powerlifting.ui.fragment;

import android.content.Intent;
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
import com.android.powerlifting.ui.AddNewPostActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminFragment extends Fragment {

    private ArrayList<Member> adminList;
    private RecyclerView recyclerView;
    private PostsAdapter postsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addPostBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_admin, container, false);

        addPostBtn = v.findViewById(R.id.addPost);

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddNewPostActivity.class));
            }
        });


        adminList = new ArrayList<Member>();

        Member member = new Member("SEC_ADMIN_NAME", "Fasdfse", "Mumbai, Maharashtra");
        adminList.add(member);
        return v;
    }

    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.postRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
//        postsAdapter = new PostsAdapter(adminList); MemberAdapter use

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postsAdapter);
    }
}
