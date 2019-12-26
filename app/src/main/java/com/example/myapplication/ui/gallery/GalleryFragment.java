package com.example.myapplication.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.Event_desc_data;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class GalleryFragment extends Fragment {

    // Firebase
    private FirebaseAuth mAuth ;
    private DatabaseReference mDatabase ;
    private RecyclerView recyclerView ;
    private Query query ;
    private String post_key , date , desc , time ;
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        // Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid  = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Planning");
        //Querry
        query = FirebaseDatabase.getInstance().getReference().child("Planning");
        //Recycler View
        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Event_desc_data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Event_desc_data, MyViewHolder>(
                Event_desc_data.class,
                R.layout.event_discription_block,
                MyViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder, final Event_desc_data event_desc_data, final int i) {
                myViewHolder.setDate(event_desc_data.getDate());
                myViewHolder.setName(event_desc_data.getDesc());
                myViewHolder.setText(event_desc_data.getTime());
            }
        };
        recyclerView.setAdapter(adapter);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDate(String date){
            TextView mDate = mView.findViewById(R.id.date);
            mDate.setText(date);
        }
        public void setName(String name){
            TextView mName = mView.findViewById(R.id.event_name);
            mName.setText(name);
        }
        public void setText(String task){
            TextView mTask = mView.findViewById(R.id.event_desc);
            mTask.setText(task);
        }

    }
}