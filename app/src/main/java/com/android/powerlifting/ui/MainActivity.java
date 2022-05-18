package com.android.powerlifting.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.powerlifting.R;
import com.android.powerlifting.ui.fragment.AdminFragment;
import com.android.powerlifting.ui.fragment.MemberFragment;
import com.android.powerlifting.ui.fragment.PostFragment;
import com.android.powerlifting.ui.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private Fragment selectedFragment;   // General Var for selected Fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        When App loads for the first time, Home fragment must be displayed
//        If condition added, so on Screen rotation, it doesn't change the fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new PostFragment()).commit();
            selectedFragment = new PostFragment();
        }

        //FIND A WAY TO REPLACE THIS DEPRECATED THING!!

        //Navigation bar Code
        bottomNav =  findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


//        startActivity(new Intent(this, UserInfoActivity.class));
    }

    //NavBar Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {

//                        On clicking any one, initializes selectedFragment with the corresponding fragment
                        case R.id.home:
                            selectedFragment = new PostFragment();
                            setTitle("Posts");
                            break;

                        case R.id.admin:
                            selectedFragment = new AdminFragment();
                            setTitle("Admin");
                            break;

                        case R.id.members:
                            selectedFragment = new MemberFragment();
                            setTitle("Members");
                            break;

                        case R.id.profile:
                            selectedFragment = new ProfileFragment();
                            setTitle("Profile");
                            break;
                    }

//                    Now depending on whichever fragment was clicked we display that *selectedFragment*
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();

//                    True --> We want to select the clicked Item
//                    False --> We would still show the fragment but Item WONT be selected
                    return true;
                }
            };
}