package com.android.powerlifting.ui;

import android.app.Application;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.powerlifting.adapters.MembersAdapter;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Member;

import java.util.ArrayList;

public class MembersViewModel extends AndroidViewModel {

    private Database memberDatabase;

    public MembersViewModel(@NonNull Application application) {
        super(application);
        memberDatabase = new Database();
    }

    public void readMembers(ArrayList<Member> membersList, MembersAdapter membersAdapter, ProgressBar memberLoaderBar) {
        memberDatabase.readMembers(membersList, membersAdapter, memberLoaderBar);
    }

}
