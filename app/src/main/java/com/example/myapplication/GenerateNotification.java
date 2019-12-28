package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GenerateNotification extends AppCompatActivity {
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAweSCcec:APA91bEs7EQvkkZhKZGjWut5Pe-xR3ydTFCgebYCicwGXvTf3399Jg9VXP_wD4vSlFyfx2PwAJ4J9O__sZ8nCFD0Rgf_6Tvqz41FJftqQURcGeVnos_odAQI5PSxtXcETAuN4FZ0kaBB";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    private ProgressDialog dialog;
    private FirebaseAuth mAuth ;
    private DatabaseReference mDatabase ;
    private String NOTIFICATION_TITLE , NOTIFICATION_MESSAGE  ,TOPIC;
    public String  ename  , e;
    public ArrayList<String> events;
    public AutoCompleteTextView acTextView;
    public Button generateNotification , getGenerateNotification2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_notification);
        events = new ArrayList<>() ;
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events").child("events");
        dialog = new ProgressDialog(GenerateNotification.this);
        dialog.setMessage("Loading Events ....");
        dialog.show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, events);
        acTextView = findViewById(R.id.IdSelectEvent);
        generateNotification=(Button) findViewById(R.id.gen_notif);
        getGenerateNotification2 = findViewById(R.id.gen_notif_sec);
        acTextView.setThreshold(0);
        acTextView.setAdapter(adapter);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ename = Objects.requireNonNull(ds.child("Event_Name").getValue()).toString();
                    Log.d("ename" , ename);
                    events.add(ename);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        dialog.dismiss();






        TOPIC = "/topics/news";
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(GenerateNotification.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.i("FCM Token", token);

            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        generateNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TOPIC = "/topics/news"; //topic must match with what the receiver subscribed to
                content();
                JSONObject notification = new JSONObject();
                JSONObject notifcationBody = new JSONObject();
                try {
                    notifcationBody.put("title", NOTIFICATION_TITLE);
                    notifcationBody.put("message", NOTIFICATION_MESSAGE);

                    notification.put("to", TOPIC);
                    notification.put("data", notifcationBody);
                } catch (JSONException e) {
                    Log.e(TAG, "onCreate: " + e.getMessage() );
                }
                sendNotification(notification);
            }
        });

        getGenerateNotification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText one = findViewById(R.id.sec_topic);
                EditText two = findViewById(R.id.sec_date);
                EditText three = findViewById(R.id.sec_msg);
                String x = one.getText().toString();
                String y = one.getText().toString();
                String z = one.getText().toString();
                if (x.isEmpty()){
                    one.setError("Enter required field");
                }
                if (y.isEmpty()){
                    two.setError("Enter required field");
                }
                if (z.isEmpty()){
                    three.setError("Enter required field");
                }
                else{
                    TOPIC = "/topics/news"; //topic must match with what the receiver subscribed to
                    NOTIFICATION_TITLE = x ;
                    NOTIFICATION_MESSAGE = "Event" + x + "will be held on " + y ;
                    JSONObject notification = new JSONObject();
                    JSONObject notifcationBody = new JSONObject();
                    try {
                        notifcationBody.put("title", NOTIFICATION_TITLE);
                        notifcationBody.put("message", NOTIFICATION_MESSAGE);

                        notification.put("to", TOPIC);
                        notification.put("data", notifcationBody);
                    } catch (JSONException e) {
                        Log.e(TAG, "onCreate: " + e.getMessage() );
                    }
                    sendNotification(notification);
                }
            }
        });


    }
    public void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GenerateNotification.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void content() {
        e = acTextView.getText().toString();
        Log.d("e" , e);
        if (e.length() != 0 ){
            dialog = new ProgressDialog(this);
            dialog.setMessage(" Generating Nofication");
            dialog.show();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ename = Objects.requireNonNull(ds.child("Event_Name").getValue()).toString();
                        if (ename.equals(e)){
                            NOTIFICATION_TITLE = ename ;
                            String time = ds.child("Time").getValue().toString();
                            String date = ds.child("Date").getValue().toString();
                            NOTIFICATION_MESSAGE = "Following event" + ename + "will be held on" + date + "and" + time;
                            break;
                        }
                        Log.d("ename" , ename);
                        events.add(ename);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    dialog.dismiss();
                }
            });
        }
    }

}
