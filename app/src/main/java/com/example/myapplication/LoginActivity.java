package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    Button submit ;
    FirebaseAuth mAuth;
    static EditText mob ;
    static String code  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button)findViewById(R.id.submit) ;
        mAuth = FirebaseAuth.getInstance();
        mob = (EditText)findViewById(R.id.login_mob);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            Toast.makeText(LoginActivity.this , "Automatic authenticate" , Toast.LENGTH_LONG).show();

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mob_num = mob.getText().toString();
                if (mob_num.isEmpty() || mob_num.length() < 10 ) {
                    mob.setError("Please enter Mobile number");
                    mob.requestFocus();
                    return;
                }else {
                    Intent i = new Intent(LoginActivity.this , VerifyMob.class);
                    startActivity(i);
                }

            }
        });
    }





}
