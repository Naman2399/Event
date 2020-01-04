package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        // User is already signed in then we can use this
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {

        }

        // Activity initiated after clicking the submit butto
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mob_num = mob.getText().toString();
                if (mob_num.isEmpty() || mob_num.length() < 10 ) {
                    mob.setError("Please enter Mobile number");
                    mob.requestFocus();
                    return;
                }else {
                    if(!mob_num.contains("+91")) {
                        mob.setText("+91" + mob.getText().toString());
                    }

                    Intent i = new Intent(LoginActivity.this , VerifyMob.class);
                    startActivity(i);
                }

            }
        });
    }





}
