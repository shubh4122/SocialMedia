package com.android.powerlifting.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.powerlifting.R;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.ui.MembersViewModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

//    TextView tvName;
//
//    ArrayList<Member> memberList;
//    private String phone;
//    private Integer position;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
//        tvName = view.findViewById(R.id.tvUserName);
//
//        SharedPreferences getSharedPreferences = this.getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);
//        phone = getSharedPreferences.getString("phone_num", "Number not found!");

//        memberList = new ArrayList<>();
//        Database userDB = new Database();
//        position = userDB.readMembers(memberList , phone);
//        Toast.makeText(getContext(), memberList.isEmpty()+" "+memberList.size(), Toast.LENGTH_SHORT).show();

//        Member member = memberList.get(0);
//        Log.d("abhi", member.getName());

//        for (int i = 0; i < memberList.size(); i++) {
//            Member member = memberList.get(i);
//            if(member.getPhone() == phone){
//                position = i;
//            }
//        }
//
//        Member user = memberList.get(position);
//        tvName.setText(user.getName());

        return view;
    }
}
