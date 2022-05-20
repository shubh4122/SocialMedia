package com.android.powerlifting.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.powerlifting.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignIn extends AppCompatActivity {

    Button get_otp;
    EditText mobile_no;
    ProgressBar pb;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        get_otp =findViewById(R.id.get_otp);
        mobile_no =findViewById(R.id.mobileno);

        pb=findViewById(R.id.pb1);
        pb.setVisibility(View.INVISIBLE);

        mAuth= FirebaseAuth.getInstance();

        get_otp.setOnClickListener(view -> {
            if(mobile_no.getText().toString().trim().isEmpty())
                Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            else if(mobile_no.getText().toString().trim().length()!=10)
                Toast.makeText(this, "Enter a Valid Mobile Number", Toast.LENGTH_SHORT).show();
            else
                Send_OTP();
        });
        if (mVerificationId == null && savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VERIFICATION_ID,mVerificationId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationId = savedInstanceState.getString(KEY_VERIFICATION_ID);
    }

    // method to get otp via message
    private void Send_OTP() {
        pb.setVisibility(View.VISIBLE);
        get_otp.setVisibility(View.INVISIBLE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                pb.setVisibility(View.GONE);
                get_otp.setVisibility(View.VISIBLE);
                mVerificationId=verificationId;
                Toast.makeText(SignIn.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignIn.this, OTP_Verification.class);
                intent.putExtra("mobileno", mobile_no.getText().toString().trim());
                intent.putExtra("verificationID", verificationId);
                startActivity(intent);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pb.setVisibility(View.GONE);
                get_otp.setVisibility(View.VISIBLE);
                Toast.makeText(SignIn.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+ mobile_no.getText().toString().trim())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}