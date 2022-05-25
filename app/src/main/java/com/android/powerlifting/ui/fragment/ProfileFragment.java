package com.android.powerlifting.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    ImageView profileImg;
    TextView tvName , tvLocation , tvAge, tvWeight;
    String imgUrl , name , location , weight, age;
    SharedPreferences getSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
        profileImg = view.findViewById(R.id.profileImg);
        tvName = view.findViewById(R.id.tvUserName);
        tvLocation = view.findViewById(R.id.tvUserLocation);
        tvWeight = view.findViewById(R.id.tvUserWeight);
        tvAge = view.findViewById(R.id.tvUserAge);
//
        getSharedPreferences = this.getActivity().getSharedPreferences("logindata", Context.MODE_PRIVATE);

        imgUrl = getSharedPreferences.getString("imageUrl", "profileImg not found!");
        name = getSharedPreferences.getString("name", "name not found!");
        location = getSharedPreferences.getString("location", "location not found!");
        weight = getSharedPreferences.getString("weight", "weight not found!");
        age = getSharedPreferences.getString("age", "age not found!");

        Picasso.get().load(imgUrl).into(profileImg);
        tvName.setText(name);
        tvLocation.setText(location);
        tvWeight.setText(weight);
        tvAge.setText(age);

        return view;
    }
}
