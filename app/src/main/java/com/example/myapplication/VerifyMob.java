package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.example.myapplication.LoginActivity.code;

public class VerifyMob extends AppCompatActivity {

    Button submit , resend_otp , change_mob ;
    EditText otp ;
    FirebaseAuth mAuth ;

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mob);

        submit = (Button) findViewById(R.id.submi);
        resend_otp = (Button)findViewById(R.id.resend_otp);
        change_mob = (Button) findViewById(R.id.change_mobile);
        mAuth = FirebaseAuth.getInstance();
        otp = (EditText)findViewById(R.id.otp_value);
        getVerificationCode();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ver_code = otp.getText().toString() ;
                if (ver_code.isEmpty()){
                    Toast.makeText(VerifyMob.this , "dsfkljsd" , Toast.LENGTH_LONG).show();
                    otp.setError("Please enter verification Code");
                    otp.requestFocus();
                }else {

                    codeVerification();
                }

            }
        });

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        change_mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void getVerificationCode(){
        String phoneNumber = LoginActivity.mob.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            if (e instanceof FirebaseAuthInvalidCredentialsException) {

                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {

            }

        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId , token);
            code = verificationId;

        }
    };
    public void codeVerification (){
        String otpcode = otp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(LoginActivity.code, otpcode);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyMob.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(VerifyMob.this  ,HomeActivity.class));

                        } else {
                            Toast.makeText(VerifyMob.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyMob.this, "You entered a wrong code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }




}
