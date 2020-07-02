package com.example.kamdhenupashuahar.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamdhenupashuahar.R;
import com.example.kamdhenupashuahar.util.SessionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class udhaar extends Fragment {
    ImageView fr;
    TextView date;
    String today,toyear,tomonth;
    CardView card;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
EditText bQ,bP,cQ,cP,aQ,aP,mP,mQ,kP,kQ,akQ,akP,skP,skQ,tt,bal,name1;
SessionActivity sessionActivity;
FirebaseFirestore db;
Button button,up;
ListView listView;
ArrayList<String> arrayList1 = new ArrayList<>();
View root;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionActivity = new SessionActivity(getActivity());
        db = FirebaseFirestore.getInstance();
         root = inflater.inflate(R.layout.fragment_udhaar, container, false);
        //Getting present date
        Calendar cal = Calendar.getInstance();
        today=String.valueOf(cal.get(Calendar.DATE));
        if(today.length()==1){
            today="0"+today;
        }
        toyear = String.valueOf(cal.get(Calendar.YEAR));
        tomonth =String.valueOf(1+(cal.get(Calendar.MONTH)));
        if(tomonth.length()==1){
            tomonth="0"+tomonth;
        }
        date=root.findViewById(R.id.textView8);
        date.setText(today+"/"+tomonth+"/"+toyear);
        //Date picker At WORK
        setNormalPicker(inflater,container);
        listView = root.findViewById(R.id.listView);
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, arrayList1);
        listView.setAdapter(arrayAdapter);
        card = root.findViewById(R.id.card);
        bQ  = root.findViewById(R.id.bhusaQ);
        bP  = root.findViewById(R.id.bhusaP);
        cQ  = root.findViewById(R.id.chokarQ);
        cP = root.findViewById(R.id.chokarP);
        aQ  = root.findViewById(R.id.masoorQ);
        aP = root.findViewById(R.id.masoorP);
        mQ = root.findViewById(R.id.masoor);
        mP  = root.findViewById(R.id.arharP);
        kP  = root.findViewById(R.id.kuttiP);
        kQ    = root.findViewById(R.id.kuttiQ);
        akP = root.findViewById(R.id.alsiP);
        akQ  = root.findViewById(R.id.alsiQ);
        skP = root.findViewById(R.id.sarsoP);
        skQ = root.findViewById(R.id.sarsoQ);
        tt = root.findViewById(R.id.totalVal);
        bal = root.findViewById(R.id.bal);
        name1 = root.findViewById(R.id.nameC);

        button = root.findViewById(R.id.button);
        up = root.findViewById(R.id.up);

        bP.setText(String.valueOf(String.valueOf(sessionActivity.getBPrice())));cP.setText(String.valueOf(sessionActivity.getCPrice()));aP.setText(String.valueOf(sessionActivity.getAPrice()));mP.setText(String.valueOf(sessionActivity.getMPrice()));
        kP.setText(String.valueOf(sessionActivity.getKPrice()));akP.setText(String.valueOf(sessionActivity.getAkPrice()));skP.setText(String.valueOf(sessionActivity.getSPrice()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setVisibility(View.VISIBLE);
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add(String.valueOf(name1.getText()).toLowerCase(Locale.getDefault()));
                Toast.makeText(getActivity(),"Data Succesfully Added", Toast.LENGTH_SHORT).show();
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
                arrayList1.clear();
                final List<String> list = new ArrayList<>();
                final String charText = newText.toLowerCase(Locale.getDefault());
                db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Udhaar").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           List<String> list = new ArrayList<>();
                           list.clear();
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               list.add(document.getId());
                           }for(int i=0;i<list.size();i++){
                               if(list.get(i).contains(charText)){
                                   arrayList1.add(list.get(i));
                               }
                           }
                           Log.d("mansiiiiii",arrayList1.toString());
                           listView.setAdapter(arrayAdapter);

                       } else {
                           Log.d("mansiiii", "Error getting documents: ", task.getException());
                       }
                   }
                });
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) listView.getItemAtPosition(position);
                arrayList1.clear();
                card.setVisibility(View.VISIBLE);
                name1.setText(clickedItem);
            }
        });
        return root;
    }
    public void add(final String s){
        Integer[] arr = new Integer[14];
        arr[0]=Integer.parseInt(String.valueOf(bP.getText()));
        arr[1]=Integer.parseInt(String.valueOf(bQ.getText()));
        arr[2]=Integer.parseInt(String.valueOf(cP.getText()));
        arr[3]=Integer.parseInt(String.valueOf(cQ.getText()));
        arr[4]=Integer.parseInt(String.valueOf(aP.getText()));
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
        }*/
        final int t = arr[0]*arr[1]+arr[2]*arr[3]+arr[4]*arr[5]+arr[6]*arr[7]+arr[8]*arr[9]+arr[10]*arr[11]+arr[12]*arr[13];
        tt.setText(String.valueOf(t));
        final int p =  Integer.parseInt(String.valueOf(bal.getText()));
        int b = t-p;
        final Map<String, Object> user = new HashMap<>();
        user.put("Date", toyear+tomonth+today);
        user.put("ItemInfo", Arrays.asList(arr));
        user.put("TotalAmount", t);
        user.put("TotalPaid", p);
        final Map<String, Object> user2 = new HashMap<>();
        user2.put("Array", Arrays.asList(user));
        user2.put("TotalBal", b);

        final DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Udhaar").document(s);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        docRef.update("Array", FieldValue.arrayUnion(user));
                        double p1 = document.getDouble("TotalBal");
                        docRef.update("TotalBal", p1+t-p);
                    } else {
                        docRef.set(user2);
                    }
                    Fragment frag = new udhaar();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_place, frag);
                    ft.commit();
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
        // Add a new document with a generated ID
        }
    private void setNormalPicker(LayoutInflater inflater, ViewGroup container) {

        fr=root.findViewById(R.id.month_picker1);

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pichlafrom1=date.getText().toString();

                Integer yy2=Integer.parseInt(pichlafrom1.substring(6,10));
                Integer mm2=Integer.parseInt(pichlafrom1.substring(3,5));
                Integer dd2=Integer.parseInt(pichlafrom1.substring(0,2));
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,onDateSetListener,yy2,(mm2-1),dd2);
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayi) {
                today=String.valueOf(dayi);
                tomonth=String.valueOf(month+1);
                toyear=String.valueOf(year);
                if(today.length()==1){
                    today="0"+today;
                }
                if(tomonth.length()==1){
                    tomonth="0"+tomonth;
                }
                date.setText(today+"/"+tomonth+"/"+toyear);

            }
        };

    }

    /*   public void read(final String name){
        DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Udhaar").document(name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        card.setVisibility(View.VISIBLE);
                        name1.setText(name);
                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });
    }*/

}