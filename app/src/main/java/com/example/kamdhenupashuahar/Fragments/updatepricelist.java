package com.example.kamdhenupashuahar.Fragments;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kamdhenupashuahar.R;
import com.example.kamdhenupashuahar.util.SessionActivity;

public class updatepricelist extends Fragment {
TextView Bp,Cp,Ap,Mp,Kp,Akp,Skp;
Button update;
SessionActivity sessionActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionActivity = new SessionActivity(getActivity());
        View root =  inflater.inflate(R.layout.fragment_updatepricelist, container, false);
        Bp = root.findViewById(R.id.editTextTextPersonName);
        Cp = root.findViewById(R.id.editTextTextPersonName1);
        Ap = root.findViewById(R.id.editTextTextPersonName12);
        Mp = root.findViewById(R.id.editTextTextPersonName123);
        Kp = root.findViewById(R.id.editTextTextPersonName1234);
        Akp = root.findViewById(R.id.editTextTextPersonName12345);
        Skp = root.findViewById(R.id.editTextTextPersonName123456);
        update = root.findViewById(R.id.button2);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionActivity.setRespose(Integer.parseInt(String.valueOf(Bp.getText())), Integer.parseInt(String.valueOf(Cp.getText())), Integer.parseInt(String.valueOf(Ap.getText())), Integer.parseInt(String.valueOf(Mp.getText())), Integer.parseInt(String.valueOf(Kp.getText())), Integer.parseInt(String.valueOf(Akp.getText())), Integer.parseInt(String.valueOf(Skp.getText())));
            }
        });
        return root;
    }
}