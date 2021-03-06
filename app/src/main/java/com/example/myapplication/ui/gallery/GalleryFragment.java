package com.example.myapplication.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.Event_desc_data;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class GalleryFragment extends Fragment {

    // Firebase
    private FirebaseAuth mAuth ;
    private View root ;
    private DatabaseReference mDatabase ;
    private RecyclerView recyclerView ;
    private Query query ;
    private String post_key , date , desc , time ;
    private FirebaseRecyclerOptions options;
    private ProgressDialog dialog ;
    private MyViewHolder myViewHolder ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //Recycler View
        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        // Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events").child("events");
        Log.d("Data" , Objects.requireNonNull(mDatabase.getKey()));
        //Querry
        query = FirebaseDatabase.getInstance().getReference().child("events").child("events");



        return root;
    }

    @Override
    public void onStart(){
        super.onStart();

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loding ....");
        dialog.show();
        options = new FirebaseRecyclerOptions.Builder<Event_desc_data>()
                .setQuery(query , Event_desc_data.class)
                .build();
        FirebaseRecyclerAdapter<Event_desc_data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Event_desc_data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i, @NonNull Event_desc_data event_desc_data) {
                String userIDS = getRef(i).getKey();
                Log.d("DataSnap", userIDS);
                mDatabase.child(userIDS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {
                        String date = ds.child("Date").getValue().toString();
                        String event_name = ds.child("Event_Name").getValue().toString();
                        String event_desc = ds.child("Descr").getValue().toString();
                        myViewHolder.mDate.setText(date);
                        myViewHolder.mName.setText(event_name);
                        myViewHolder.mTask.setText(event_desc);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        dialog.dismiss();

                    }
                });
                dialog.dismiss();

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_discription_block, viewGroup, false);
                myViewHolder = new MyViewHolder(view);
                return myViewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;
        TextView mName , mDate , mTask ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mDate = mView.findViewById(R.id.date);
            mName = mView.findViewById(R.id.event_name);
            mTask = mView.findViewById(R.id.event_desc);
        }
        public void setDate(String date){
            TextView mDate = mView.findViewById(R.id.date);
            mDate.setText(date);
        }
        public void setEvent(String name){
            TextView mName = mView.findViewById(R.id.event_name);
            mName.setText(name);
        }
        public void setDescp(String task){
            TextView mTask = mView.findViewById(R.id.event_desc);
            mTask.setText(task);
        }



    }





}