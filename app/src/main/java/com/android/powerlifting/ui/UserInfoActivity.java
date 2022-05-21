package com.android.powerlifting.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.powerlifting.R;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Member;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class UserInfoActivity extends AppCompatActivity {


    EditText nameUser , phoneUser , weightUser , ageUser , locationUser;
    Button submit_btn;
    private String s_name, s_phone, s_location;
    private Float s_weight;
    private Integer s_age;
    private Uri mImgUri;

    ImageView profile_img;
    ImageButton btn_upload_img;
    private ActivityResultLauncher<String> ImagePicker;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        SharedPreferences getSharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);

        profile_img = findViewById(R.id.profile_Img);
        btn_upload_img = findViewById(R.id.btnUpload_img);
        nameUser = findViewById(R.id.nameUser);
        phoneUser = findViewById(R.id.phoneUser);
        weightUser = findViewById(R.id.weightUser);
        ageUser = findViewById(R.id.ageUser);
        locationUser = findViewById(R.id.locationUser);
        submit_btn = findViewById(R.id.submit);

        mStorageRef = FirebaseStorage.getInstance().getReference("Profile_Pics");

        ImagePicker = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        profile_img.setImageURI(result);
                        mImgUri = result;
                        //this uri is of image. Push it to storage and get its link
                        //in DB. Then display it.
                    }
                });

            btn_upload_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Here just launch above photoPicker
                    ImagePicker.launch("image/*");
                }
        });


        phoneUser.setEnabled(false);
        s_phone = getSharedPreferences.getString("phone_num", "Number not found!");
        phoneUser.setText("+91 " + s_phone);

        submit_btn.setEnabled(false);
        enable_submit_btn();


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(UserInfoActivity.this, "Submitting Data...", Toast.LENGTH_SHORT).show();
                }
                else{
                    SubmitData();
                }
            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver CR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    private void SubmitData(){
        if(mImgUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImgUri));

            mUploadTask = fileReference.putFile(mImgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UserInfoActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();

                            Member user = new Member(s_name, s_phone, 56
                                    , taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(), 45 , s_location);
                            Database memberDatabase = new Database();
                            memberDatabase.addMembers(user);

                            resumeActivity();

                            Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        }
                    });
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
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