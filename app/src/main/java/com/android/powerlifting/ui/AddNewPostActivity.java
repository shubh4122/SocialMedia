package com.android.powerlifting.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.powerlifting.R;
import com.android.powerlifting.adapters.PostsAdapter;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;
import com.android.powerlifting.ui.fragment.PostFragment;

public class AddNewPostActivity extends AppCompatActivity {

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

                Post post = new Post(caption, "PHOTO TO BE UPLOADED", postCreator, "09:30", "Kanpur, UP");
                postViewModel.addPosts(post);
                startActivity(new Intent(AddNewPostActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}