package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.CustomSwipeAdapter;
import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ViewPager viewPager ;
    CustomSwipeAdapter customSwipeAdapter ;
    private Timer timer;
    private int current_position=0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        viewPager = root.findViewById(R.id.view_pager);
//        customSwipeAdapter = new CustomSwipeAdapter(getActivity());
//        viewPager.setAdapter(customSwipeAdapter);
//        createSlideShow();
        return root;
    }

    public void createSlideShow(){
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                current_position=current_position%5;
                viewPager.setCurrentItem(current_position++,true);

            }
        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },250,2500);
    }

}