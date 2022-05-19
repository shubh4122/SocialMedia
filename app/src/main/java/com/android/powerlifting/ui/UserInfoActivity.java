package com.android.powerlifting.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.powerlifting.R;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Member;

public class UserInfoActivity extends AppCompatActivity {

    EditText nameUser , phoneUser , weightUser , ageUser , locationUser;
    Button submit_btn;
    private String s_name, s_phone, s_location;
    private Float s_weight;
    private Integer s_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        SharedPreferences getSharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);

        nameUser = findViewById(R.id.nameUser);
        phoneUser = findViewById(R.id.phoneUser);
        weightUser = findViewById(R.id.weightUser);
        ageUser = findViewById(R.id.ageUser);
        locationUser = findViewById(R.id.locationUser);
        submit_btn = findViewById(R.id.submit);

        phoneUser.setEnabled(false);
        s_phone = getSharedPreferences.getString("phone_num", "Number not found!");
        phoneUser.setText("+91 " + s_phone);

        submit_btn.setEnabled(false);
        enable_submit_btn();


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Member user = new Member(s_name, s_phone, 56, "asas", 45 , s_location);
                Database memberDatabase = new Database();
                memberDatabase.addMembers(user);

                resumeActivity();

                Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

               }
        });
    }


    private void enable_submit_btn() {
        nameUser.addTextChangedListener(watcher);
        weightUser.addTextChangedListener(watcher);
        ageUser.addTextChangedListener(watcher);
        locationUser.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            s_name = nameUser.getText().toString().trim();
//            s_weight = Float.valueOf(weightUser.getText().toString());
//            s_age = Integer.valueOf(ageUser.getText().toString());
            s_location = locationUser.getText().toString().trim();

            // to check alternative input type for weight and age
            // isEmpty can,t be used with int or float type value so ignoring for now
            submit_btn.setEnabled(!s_name.isEmpty() && !s_location.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    private void resumeActivity() {
        SharedPreferences sharedPreferences=getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("writtenToDB", true); // This ensures to open MainActivity only when User Info written to DB
        editor.apply();
    }

}