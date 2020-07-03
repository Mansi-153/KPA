package com.example.kamdhenupashuahar.Fragments;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.kamdhenupashuahar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PurchaseAddition extends Fragment implements AdapterView.OnItemSelectedListener {
    View root;
    FirebaseFirestore db;
    ArrayList<String> mylist = new ArrayList<String>();
    private Spinner spinner;
    ImageView fr;
    TextView date,qty,pr;
    String today,toyear,tomonth,type,quantity,price;
    Button add;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_purchase_addition, container, false);
        db = FirebaseFirestore.getInstance();
       //getting current day date and setting it to be default
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
        mylist.clear();
        mylist.add("Bhoosa");
        mylist.add("Chokar");
        mylist.add("Arhar");
        mylist.add("Masoor");
        mylist.add("Kutti");
        mylist.add("Sarso Khali");
        mylist.add("Alsi Khari");
        //Making spinner work
        spinner = root.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, mylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
      //populating id
        pr=root.findViewById(R.id.editTextTextPersonName2);
        qty=root.findViewById(R.id.editTextTextPersonName3);
        add=root.findViewById(R.id.button4);
        //listener of add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               update();
            }
        });



     return root;
    }


    private void update()
    {
        //take the type,price,quantity
        quantity=qty.getText().toString();
        price=pr.getText().toString();
        if(quantity.length()==0 || price.length()==0){
            Toast.makeText(getActivity(), "Field must not be empty", Toast.LENGTH_SHORT).show();
        }else{
        switch (type) {
            case "Bhoosa":
                Add(quantity, price, "TABLE1");
                break;
            case "Chokar":
                Add(quantity, price, "Chokar");
                break;
            case "Arhar":
                Add(quantity, price, "Arhar");
                break;
            case "Masoor":
                Add(quantity, price, "Masoor");
                break;
            case "Kutti":
                Add(quantity, price, "Kutti");
                break;
            case "Sarso Khali":
                Add(quantity, price, "Sarso Khali");
                break;
            case "Alsi Khari":
                Add(quantity, price, "Alsi Khari");
                break;
        }
        }

        //end mai intent karo doosre fragment par with an update successful toast
        FragmentManager fm;
        FragmentTransaction ft;
        Fragment frag;
        frag = new PurchaseAddition();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, frag);
        ft.addToBackStack(null);
        ft.commit();
    }

    //function for adding according to type
    private void Add(String qy, String pri, final String Type)
    {
        Map<String, Object> user = new HashMap<>();
        user.put("Date",toyear+tomonth+today);
        user.put("Quantity",Float.parseFloat(qy));
        user.put("Price",Float.parseFloat(pri));
        user.put("Total",Float.parseFloat(qy)*Float.parseFloat(pri));
        user.put("Type",false);
        // Add a new document with a generated ID
        db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document(Type).update("ArraySales", FieldValue.arrayUnion(user));
  //now Add data to update Stock
//for this firstly read data from the stock then add the quantity from it and update it with write command.
        final Double[] stock = new Double[1];
        stock[0]= Double.valueOf(0);
        final String str=qy;
        DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("mansiiiiiiiiiiiiiiiiiii", "DocumentSnapshot data: " + document.getData());
                      //  Double stock;
                        if(Type.equals("TABLE1")){
                            stock[0] =document.getDouble("Bhoosa");
                        }else{
                            stock[0] =document.getDouble(Type);
                        }
                       stock[0]=stock[0]+Double.parseDouble(str);
                       db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock")
                               .update(Type, stock[0]);
                    } else {
                        Log.d("", "No such document");
                    }
                    Toast.makeText(getActivity(),"Added Successfully In Records !!",Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //item type now stored as a string named type
        type=mylist.get(position);
        Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}