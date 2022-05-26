package com.android.powerlifting.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.powerlifting.R;
import com.android.powerlifting.adapters.PostsAdapter;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.models.Post;
import com.android.powerlifting.ui.fragment.PostFragment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AddNewPostActivity extends AppCompatActivity {

    private EditText captionEditText;
    private ImageView postImage;
    private Button addImageBtn, createPostBtn;
    private ProgressBar progressBar;
    private String caption;
    private PostViewModel postViewModel;
    private ActivityResultLauncher<String> postPhotoPicker;
    Uri downloadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        captionEditText = findViewById(R.id.postEditText);
        postImage = findViewById(R.id.postImage);
        addImageBtn = findViewById(R.id.addPhotoInPost);
        createPostBtn = findViewById(R.id.makePost);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        progressBar = findViewById(R.id.addingImage);
        progressBar.setVisibility(View.GONE);

        //Used for Edit Post
        if (getIntent().getBooleanExtra("edit",false)) {
            captionEditText.setText(getIntent().getStringExtra("caption"));
            String intentUri = getIntent().getStringExtra("image");
            Picasso.get().load(intentUri).into(postImage);
        }


        //This is new method to pick content from Storage.
        //Alternate of startActivityForResult(DEPRECATED)
        postPhotoPicker = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                            postImage.setImageURI(result);
                            progressBar.setVisibility(View.VISIBLE);
                            createPostBtn.setEnabled(false);
                            //this uri is of image. Push it to storage and get its link
                            //in DB. Then display it.


                        //See this must not be done now, Must do only when button create post clicked

                        if (result != null) {
                            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("Post_Pics");
                            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                                    + "." + getFileExtension(result));

                            UploadTask mUploadTask = fileReference.putFile(result);
                            Task<Uri> urlTask = mUploadTask.continueWithTask(task -> {
                                if (!task.isSuccessful()) {
                                    throw Objects.requireNonNull(task.getException());
                                }
                                return fileReference.getDownloadUrl();
                            }).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    downloadUri = task.getResult();
//                                    Toast.makeText(AddNewPostActivity.this, downloadUri.toString(), Toast.LENGTH_SHORT).show();

                                    progressBar.setVisibility(View.GONE);
                                    createPostBtn.setEnabled(true);
                                }
                            });
                        }
                        else {
                            Toast.makeText(AddNewPostActivity.this, "No file selected", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);
                            createPostBtn.setEnabled(true);
                        }
                    }

//
                });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Here just launch above photoPicker
                postPhotoPicker.launch("image/*");
            }
        });

        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caption = captionEditText.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);
                String name = sharedPreferences.getString("name", "defName");
//                String imageUrl = sharedPreferences.getString("imageUrl", "https://getodk-a3b1.kxcdn.com/uploads/default/d97aaae47866522c9404527cc25f6d6b11a0328c");
                String location = sharedPreferences.getString("location", "defLocation");

//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                FirebaseUser user = auth.getCurrentUser();
//
//                String name = user.getDisplayName();
                String imageUrl = sharedPreferences.getString("imageUrl", null);
                Member postCreator = new Member(name, imageUrl, location);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd MMM, yyyy");
                String currentDateTime = sdf.format(new Date());

                String postImageUri = downloadUri == null ? null : downloadUri.toString();
                Post post = post = new Post(caption, postImageUri, postCreator, currentDateTime, postCreator.getLocation());
//                Toast.makeText(AddNewPostActivity.this, postImageUri, Toast.LENGTH_SHORT).show();
                if (getIntent().getBooleanExtra("edit",false)) {
                    postViewModel.editPost(getIntent().getStringExtra("uid"), caption, postImageUri);
                }
                else
                    postViewModel.addPosts(post);
                startActivity(new Intent(AddNewPostActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver CR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }
}