package com.android.powerlifting.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.powerlifting.R;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        getSupportActionBar().hide();

        ImageView imageView = findViewById(R.id.fullImage);
        Picasso.get().load(getIntent().getStringExtra("uri")).into(imageView);

//        Toast.makeText(this, getIntent().getStringExtra("uri"), Toast.LENGTH_SHORT).show();
    }
}