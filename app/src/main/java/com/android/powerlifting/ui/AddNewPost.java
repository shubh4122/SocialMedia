package com.android.powerlifting.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.powerlifting.R;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;

public class AddNewPost extends AppCompatActivity {

    private EditText captionEditText;
    private ImageView postImage;
    private Button addImageBtn, createPostBtn;
    private String caption;
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        captionEditText = findViewById(R.id.postEditText);
        postImage = findViewById(R.id.postImage);
        addImageBtn = findViewById(R.id.addPhotoInPost);
        createPostBtn = findViewById(R.id.makePost);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);


        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Open system to upload image
            }
        });

        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caption = captionEditText.getText().toString();

                Member postCreator = new Member("postCreatorName",
                                                "PROFILE PIC",
                                                "Lucknow, UP");
                Post post = new Post(caption, "PHOTO TO BE UPLOADED", postCreator);
                postViewModel.addPosts(post);
            }
        });
    }
}