package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AlarmReceiver extends BroadcastReceiver {
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAweSCcec:APA91bEs7EQvkkZhKZGjWut5Pe-xR3ydTFCgebYCicwGXvTf3399Jg9VXP_wD4vSlFyfx2PwAJ4J9O__sZ8nCFD0Rgf_6Tvqz41FJftqQURcGeVnos_odAQI5PSxtXcETAuN4FZ0kaBB";
    final private String contentType = "application/json";
    private FirebaseAuth mAuth ;
    private DatabaseReference mDatabase ;
    private String NOTIFICATION_TITLE , NOTIFICATION_MESSAGE  ,TOPIC;
    private String date;
    @Override
    public void onReceive(final Context context, Intent intent) {
        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("event");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("date").getValue().toString().equals(date)){
                        TOPIC = "/topics/news"; //topic must match with what the receiver subscribed to
                        NOTIFICATION_TITLE = ds.child("event_name").getValue().toString();
                        NOTIFICATION_MESSAGE = "Today event :" + NOTIFICATION_TITLE + "will be held at " + ds.child("time").getValue().toString();
                        JSONObject notification = new JSONObject();
                        JSONObject notifcationBody = new JSONObject();
                        try {
                            notifcationBody.put("title", NOTIFICATION_TITLE);
                            notifcationBody.put("message", NOTIFICATION_MESSAGE);

                            notification.put("to", TOPIC);
                            notification.put("data", notifcationBody);
                        } catch (JSONException e) {
                            Log.e("WOW", "onCreate: " + e.getMessage() );
                        }
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.i("WOW", "onResponse: " + response.toString());

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("WOW", "onErrorResponse: Didn't work");
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
                        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
