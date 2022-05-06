package com.android.powerlifting.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.powerlifting.R;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Member;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        EditText name = findViewById(R.id.nameUser);
        EditText location = findViewById(R.id.locationUser);



        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Member user = new Member(name.getText().toString(), "797", 7.33f, "asas", 132424, location.getText().toString());
                Database memberDatabase = new Database();
                memberDatabase.addMembers(user);
               }
        });
    }
}