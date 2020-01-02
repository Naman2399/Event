package com.example.myapplication.ui.slideshow;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private TextView c;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        c = root.findViewById(R.id.content);
        c.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        return root;
    }
}