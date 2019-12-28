package com.example.myapplication.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.GenerateNotification;
import com.example.myapplication.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    public Button submit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_share, container, false);

        submit=(Button) root.findViewById(R.id.Idsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText one = root.findViewById(R.id.Idadmin);
                EditText two = root.findViewById(R.id.Idpassword);
                String x = one.getText().toString();
                String y = two.getText().toString();
                if (x.toLowerCase().equals("james") && y.toLowerCase().equals("007")){
                    Intent intent=new Intent(root.getContext(), GenerateNotification.class);
                    startActivity(intent);
                }
                else{
                    if (x.toLowerCase().equals("james")){
                        two.setError("Wrong PassKey");
                    }
                    if(y.toLowerCase().equals("007")){
                        one.setError("Wrong Admin ID");
                    }
                }

            }
        });
        return root;
    }
}