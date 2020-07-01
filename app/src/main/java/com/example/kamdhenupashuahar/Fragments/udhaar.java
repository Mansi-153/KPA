package com.example.kamdhenupashuahar.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kamdhenupashuahar.R;
import com.example.kamdhenupashuahar.util.SessionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;


public class udhaar extends Fragment {
EditText bQ,bP,cQ,cP,aQ,aP,mP,mQ,kP,kQ,akQ,akP,skP,skQ,tt,bal;
SessionActivity sessionActivity;
FirebaseFirestore db;
Button button,up;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionActivity = new SessionActivity(getActivity());
        db = FirebaseFirestore.getInstance();
        View root = inflater.inflate(R.layout.fragment_udhaar, container, false);
        bQ  = root.findViewById(R.id.bhusaQ);
        bP  = root.findViewById(R.id.bhusaP);
        cQ  = root.findViewById(R.id.chokarQ);
        cP = root.findViewById(R.id.chokarP);
        aQ  = root.findViewById(R.id.arharQ);
        aP = root.findViewById(R.id.arharP);
        mQ = root.findViewById(R.id.masoorQ);
        mP  = root.findViewById(R.id.masoorP);
        kP  = root.findViewById(R.id.kuttiP);
        kQ    = root.findViewById(R.id.kuttiQ);
        akP = root.findViewById(R.id.alsiP);
        akQ  = root.findViewById(R.id.alsiQ);
        skP = root.findViewById(R.id.sarsoP);
        skQ = root.findViewById(R.id.sarsoQ);
        tt = root.findViewById(R.id.totalVal);
        bal = root.findViewById(R.id.bal);

        button = root.findViewById(R.id.button);
        up = root.findViewById(R.id.up);

        bP.setText(String.valueOf(String.valueOf(sessionActivity.getBPrice())));cP.setText(String.valueOf(sessionActivity.getCPrice()));aP.setText(String.valueOf(sessionActivity.getAPrice()));mP.setText(String.valueOf(sessionActivity.getMPrice()));
        kP.setText(String.valueOf(sessionActivity.getKPrice()));akP.setText(String.valueOf(sessionActivity.getAkPrice()));skP.setText(String.valueOf(sessionActivity.getSPrice()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        final SearchView searchView =(SearchView) root.findViewById(R.id.searchView);
        searchView.setQueryHint("Search Customer here");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String charText = newText.toLowerCase(Locale.getDefault());
                if(charText.length()>5)
                read(charText);
                return false;
            }
        });

        return root;
    }
    public void read(String name){
        DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Udhaar").document(name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("mansiiiiiiiiiiiiiiiiiii", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }
    public void add(){
        Integer[] arr = new Integer[14];
        arr[0]=Integer.parseInt(String.valueOf(bP.getText()));
        arr[2]=Integer.parseInt(String.valueOf(bP.getText()));
        arr[3]=Integer.parseInt(String.valueOf(cP.getText()));
        arr[4]=Integer.parseInt(String.valueOf(cQ.getText()));
        arr[1]=Integer.parseInt(String.valueOf(aP.getText()));
        arr[5]=Integer.parseInt(String.valueOf(aQ.getText()));
        arr[6]=Integer.parseInt(String.valueOf(mP.getText()));
        arr[7]=Integer.parseInt(String.valueOf(mQ.getText()));
        arr[8]=Integer.parseInt(String.valueOf(kP.getText()));
        arr[9]=Integer.parseInt(String.valueOf(kQ.getText()));
        arr[10]=Integer.parseInt(String.valueOf(akP.getText()));
        arr[11]=Integer.parseInt(String.valueOf(akQ.getText()));
        arr[12]=Integer.parseInt(String.valueOf(skP.getText()));
        arr[13]=Integer.parseInt(String.valueOf(skQ.getText()));
        /*HashMap[] arr = new HashMap[1];//creating an array of size one..just for sake of example
        HashMap<String, Object> arrMap = new HashMap<String, Object>();
        if(!bQ.getText().equals("0")){
            arrMap.put("BhoosaPrice",Integer.parseInt(String.valueOf(bP.getText())));
            arrMap.put("BhoosaQty",Integer.parseInt(String.valueOf(bQ.getText())));
            arr[0]=arrMap;
        }if(!cQ.getText().equals("0")){
            arrMap.clear();
            arrMap.put("ChokarPrice",Integer.parseInt(String.valueOf(cP.getText())));
            arrMap.put("ChokarQty",Integer.parseInt(String.valueOf(cQ.getText())));
            arr[c++]=arrMap;
        }if(!aQ.getText().equals("0")){
            arrMap.clear();
            arrMap.put("ArharPrice",Integer.parseInt(String.valueOf(aP.getText())));
            arrMap.put("ArharQty",Integer.parseInt(String.valueOf(aQ.getText())));
            arr[c++]=arrMap;
        }if(!mQ.getText().equals("0")){
            arrMap.clear();
            arrMap.put("MasoorPrice",Integer.parseInt(String.valueOf(mP.getText())));
            arrMap.put("MasoorQty",Integer.parseInt(String.valueOf(mQ.getText())));
            arr[c++]=arrMap;
        }if(!kQ.getText().equals("0")){
            arrMap.clear();
            arrMap.put("KuttiPrice",Integer.parseInt(String.valueOf(kP.getText())));
            arrMap.put("KuttiQty",Integer.parseInt(String.valueOf(kQ.getText())));
            arr[c++]=arrMap;
        }if(!akQ.getText().equals("0")){
            arrMap.clear();
            arrMap.put("AlsiPrice",Integer.parseInt(String.valueOf(akP.getText())));
            arrMap.put("AlsiQty",Integer.parseInt(String.valueOf(akQ.getText())));
            arr[c++]=arrMap;
        }if(!skQ.getText().equals("0")){
            arrMap.clear();
            arrMap.put("SarsoPrice",Integer.parseInt(String.valueOf(skP.getText())));
            arrMap.put("SarsoQty",Integer.parseInt(String.valueOf(skQ.getText())));
            arr[c++]=arrMap;
        }*/
        int t =  Integer.parseInt(String.valueOf(tt.getText()));
        int p =  Integer.parseInt(String.valueOf(bal.getText()));
        Map<String, Object> user = new HashMap<>();
        user.put("Date", "26/06/20");
        user.put("ItemInfo", Arrays.asList(arr));
        user.put("TotalAmount", t);
        user.put("TotalPaid", p);

        // Add a new document with a generated ID
        db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Udhaar").document("kushagra").update("Array", FieldValue.arrayUnion(user));
    }
}