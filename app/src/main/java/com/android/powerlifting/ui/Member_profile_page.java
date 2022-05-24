package com.android.powerlifting.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.powerlifting.R;
import com.android.powerlifting.models.Member;
import com.squareup.picasso.Picasso;

public class Member_profile_page extends AppCompatActivity {

    ImageView profileImg;
    ImageButton editPic;
    TextView tvName , tvLocation , tvAge, tvWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        profileImg = findViewById(R.id.profileImg);
        editPic = findViewById(R.id.btnEditProfileImg);
        tvName = findViewById(R.id.tvUserName);
        tvLocation = findViewById(R.id.tvUserLocation);
        tvWeight = findViewById(R.id.tvUserWeight);
        tvAge = findViewById(R.id.tvUserAge);

        editPic.setVisibility(View.INVISIBLE);

        // getSerializableExtra is used because we are passing object through intent
        // and also corresponding Model class is defined as ( public class Name implements Serializable..)
        Member member = (Member) getIntent().getSerializableExtra("memberPosition");
        Picasso.get().load(member.getProfilePhotoUrl()).into(profileImg);

        tvName.setText(member.getName());
        tvLocation.setText(member.getLocation());
//        tvAge.setText(member.getAge());
//        tvWeight.setText("60");



    }
}