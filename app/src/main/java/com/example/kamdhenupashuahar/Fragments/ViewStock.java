package com.example.kamdhenupashuahar.Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kamdhenupashuahar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

public class ViewStock extends Fragment {

    FirebaseFirestore db;
    double a1,a2,a3,a4,a5,a6,a7;
    EditText edit,edit2,edit3,edit4,edit5,edit6,edit7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_view_stock, container, false);
        db = FirebaseFirestore.getInstance();

        edit = root.findViewById(R.id.editBhusa);
        edit2 = root.findViewById(R.id.editChokar);
        edit3= root.findViewById(R.id.editArhar);
        edit4 = root.findViewById(R.id.editMasoor);
        edit5 = root.findViewById(R.id.editKutti);
        edit6 = root.findViewById(R.id.editAlsi);
        edit7 = root.findViewById(R.id.editSarso);

        final DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        a1 = document.getDouble("Bhoosa");
                        a2 = document.getDouble("Chokar");
                        a3 = document.getDouble("Arhar");
                        a4 = document.getDouble("Masoor");
                        a5 = document.getDouble("Kutti");
                        a6 = document.getDouble("Alsi Khari");
                        a7 = document.getDouble("Sarso Khali");
                        edit.setText(String.valueOf(a1));edit2.setText(String.valueOf(a2));edit3.setText(String.valueOf(a3));
                        edit4.setText(String.valueOf(a4));edit5.setText(String.valueOf(a5));edit6.setText(String.valueOf(a6));edit7.setText(String.valueOf(a7));


                    } else {
                        Toast.makeText(getActivity(), "No Such Field Present", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
          return root;
    }
}