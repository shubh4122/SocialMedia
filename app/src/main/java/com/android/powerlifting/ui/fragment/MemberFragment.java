package com.android.powerlifting.ui.fragment;

import android.os.Bundle;
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
import com.android.powerlifting.adapters.MembersAdapter;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.ui.MembersViewModel;

import java.util.ArrayList;

public class MemberFragment extends Fragment {

    private ArrayList<Member> membersList;
    private RecyclerView recyclerView;
    private MembersAdapter membersAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MembersViewModel membersViewModel;
    private ProgressBar memberLoaderBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        membersList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        memberLoaderBar = view.findViewById(R.id.memberLoaderBar);
        setRecyclerView(view);

        membersViewModel = new ViewModelProvider(this).get(MembersViewModel.class);
        membersViewModel.readMembers(membersList, membersAdapter, memberLoaderBar);
        return view;
    }

    private void setRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.memberRecyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        membersAdapter = new MembersAdapter(membersList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(membersAdapter);
    }
}
