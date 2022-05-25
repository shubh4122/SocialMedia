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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.powerlifting.R;
import com.android.powerlifting.firebase.Database;
import com.android.powerlifting.models.Member;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class UserInfoActivity extends AppCompatActivity {

    TextView tvAgeUser, tvWeightUser;
    EditText nameUser , phoneUser , locationUser;
    NumberPicker npAge, npWeight;
    Button submit_btn;
    private String s_name, s_phone, s_age, s_weight, s_location;
    ImageView profile_img;
    ImageButton btn_upload_img;

    private Uri mImgUri;
    private ActivityResultLauncher<String> ImagePicker;
    private StorageReference mStorageRef;
    private UploadTask mUploadTask;
    Uri downloadUri;
    ProgressBar progressBar;
    Member user;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

//        SharedPreferences getSharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);
//        storeUri = "FUCK YOU!";
        sharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        profile_img = findViewById(R.id.profile_Img);
        btn_upload_img = findViewById(R.id.btnUpload_img);
        nameUser = findViewById(R.id.nameUser);
        phoneUser = findViewById(R.id.phoneUser);
        tvWeightUser = findViewById(R.id.tvWeightUser);
        tvAgeUser = findViewById(R.id.tvAgeUser);
        npAge = findViewById(R.id.numberPickerAge);
        npWeight = findViewById(R.id.numberPickerWeight);
        locationUser = findViewById(R.id.locationUser);
        submit_btn = findViewById(R.id.submit);
        progressBar = findViewById(R.id.submittingData);
        progressBar.setVisibility(View.GONE);

        phoneUser.setEnabled(false);
        s_phone = sharedPreferences.getString("phone_num", "Number not found!");
        phoneUser.setText("+91 " + s_phone);

        npAge.setMinValue(20);
        npAge.setMaxValue(40);
        tvAgeUser.setText(String.valueOf(npAge.getValue()));
        npAge.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                tvAgeUser.setText(String.valueOf(i1));
                s_age = tvAgeUser.getText().toString();
            }
        });


        npWeight.setMinValue(50);
        npWeight.setMaxValue(100);
        tvWeightUser.setText(String.valueOf(npWeight.getValue()));
        npWeight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                tvWeightUser.setText(String.valueOf(i1));
                s_weight = tvWeightUser.getText().toString();
            }
        });



        mStorageRef = FirebaseStorage.getInstance().getReference("Profile_Pics");

        ImagePicker = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        profile_img.setImageURI(result);
                        mImgUri = result;
                    }
                });

            btn_upload_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImagePicker.launch("image/*");
                }
        });

        phoneUser.setEnabled(false);
        s_phone = sharedPreferences.getString("phone_num", "Number not found!");
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
//
//        SharedPreferences sharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();

//        editor.putString("imageUrl", "NULL");
        if(mImgUri != null){

            progressBar.setVisibility(View.VISIBLE);
            submit_btn.setEnabled(false);

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImgUri));

            mUploadTask = fileReference.putFile(mImgUri);
            Task<Uri> urlTask = mUploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();

                    editor.putString("imageUrl", downloadUri.toString());
                    editor.putString("name", s_name);
                    editor.putString("location", s_location);
                    editor.putString("weight", s_weight);
                    editor.putString("age", s_age);
                    editor.apply();

                    Toast.makeText(UserInfoActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(UserInfoActivity.this, downloadUri != null ? downloadUri.toString() : "NULL", Toast.LENGTH_SHORT).show();

                    user = new Member(s_name, s_phone, s_weight, downloadUri.toString(), s_age , s_location);
                    Database memberDatabase = new Database();
                    memberDatabase.addMembers(user);

                    resumeActivity();

                    Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
//                    String uri = downloadUri.toString();
//                    intent.putExtra("profilePicUrl", downloadUri.toString());

                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
//            editor.clear().apply();
        }

//        editor.putString("name", s_name);
//        editor.putString("location", s_location);
//        editor.putString("imageUrl", user.getProfilePhotoUrl());
//        editor.putFloat("weight", s_weight);
//        editor.putInt("age", s_age);
    }

    private void enable_submit_btn() {
        nameUser.addTextChangedListener(watcher);
        locationUser.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            s_name = nameUser.getText().toString().trim();
//<<<<<<< HEAD
//            try {
//                s_weight = Float.valueOf(weightUser.getText().toString());
//                s_age = Integer.valueOf(ageUser.getText().toString());
//            } catch (NumberFormatException e) {
//                Toast.makeText(UserInfoActivity.this, "Enter appropriate num", Toast.LENGTH_SHORT).show();
//            }
//=======
//>>>>>>> 28fb12c01d9e6154e78690e164e9ff4f5496c649
            s_location = locationUser.getText().toString().trim();

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