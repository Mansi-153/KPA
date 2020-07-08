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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kamdhenupashuahar.R;
import com.example.kamdhenupashuahar.util.SessionActivity;
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

public class AddingSalesRecord extends Fragment implements AdapterView.OnItemSelectedListener {
    View root;
    FirebaseFirestore db;
    ImageView fr;
    Button add;
    TextView date;
    EditText Bqty,Bprice,Cqty,Cprice,Mqty,Mprice,Aqty,Aprice,AKqty,AKprice,SKqty,SKprice,Kqty,Kprice;
    String today,toyear,tomonth;
    SessionActivity sessionActivity;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    ArrayList<String> mylist = new ArrayList<String>();
    private Spinner spinner;
    String chokartype;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_adding_sales_record, container, false);
        sessionActivity = new SessionActivity(getActivity());
        Initialization();
        //spinner at work
        //Making spinner work
        spinner = root.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, mylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //Date picker At WORK
        setNormalPicker(inflater,container);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                work();

            }
        });
        return root;
    }

   private void work()
   {
     if(!(Bqty.getText().toString().isEmpty()&&Bprice.getText().toString().isEmpty()))
     { String type="TABLE1";
       Add(Bqty.getText().toString(),Bprice.getText().toString(),type);
     }

       if(!(Aqty.getText().toString().isEmpty()&&Aprice.getText().toString().isEmpty()))
       { String type="Arhar";
           Add(Aqty.getText().toString(),Aprice.getText().toString(),type);
       }

       if(!(Mqty.getText().toString().isEmpty()&&Mprice.getText().toString().isEmpty()))
       { String type="Masoor";
           Add(Mqty.getText().toString(),Mprice.getText().toString(),type);
       }

       if(!(AKqty.getText().toString().isEmpty()&&AKprice.getText().toString().isEmpty()))
       { String type="Alsi Khari";
           Add(AKqty.getText().toString(),AKprice.getText().toString(),type);
       }

       if(!(SKqty.getText().toString().isEmpty()&&SKprice.getText().toString().isEmpty()))
       { String type="Sarso Khali";
           Add(SKqty.getText().toString(),SKprice.getText().toString(),type);
       }

       if(!(Cqty.getText().toString().isEmpty()&&Cprice.getText().toString().isEmpty()))
       { String type="Chokar";
           Add(Cqty.getText().toString(),Cprice.getText().toString(),type);
       }

       if(!(Kqty.getText().toString().isEmpty()&&Kprice.getText().toString().isEmpty()))
       { String type="Kutti";
           Add(Kqty.getText().toString(),Kprice.getText().toString(),type);
       }


   }

    private void Add(String qy, String pri, final String Type)
    {
        Map<String, Object> user = new HashMap<>();
        user.put("Date",toyear+tomonth+today);
        user.put("Quantity",Float.parseFloat(qy));
        user.put("Price",Float.parseFloat(pri));
        user.put("Total",Float.parseFloat(qy)*Float.parseFloat(pri));
        user.put("Type",true);
        if(Type.equals("Chokar")){
            user.put("TypeChokar",chokartype);
        }
        // Add a new document with a generated ID
        db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document(Type).update("ArraySales", FieldValue.arrayUnion(user));
        //now Add data to update Stock
//for this firstly read data from the stock then add the quantity from it and update it with write command.
        final Double[] stock = new Double[1];
        final String str=qy;
        DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //  Double stock;

                        //check for chokar type if there is chokar
                        if(Type.equals("Chokar")){
                            switch (chokartype){
                                case "Type 1": stock[0] = document.getDouble("Chokar1");
                                    stock[0] = stock[0] - Double.parseDouble(str);
                                    db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock")
                                            .update("Chokar1", stock[0]);
                                break;
                                case "Type 2": stock[0] = document.getDouble("Chokar2");
                                    stock[0] = stock[0] - Double.parseDouble(str);
                                    db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock")
                                            .update("Chokar2", stock[0]);
                                    break;
                                case "Type 3": stock[0] = document.getDouble("Chokar3");
                                    stock[0] = stock[0] - Double.parseDouble(str);
                                    db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock")
                                            .update("Chokar3", stock[0]);
                                    break;
                            }
                        }
                        else {
                            if(Type.equals("TABLE1")){
                                stock[0] =document.getDouble("Bhoosa");
                                stock[0] = stock[0] - Double.parseDouble(str);
                                db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock")
                                        .update("Bhoosa", stock[0]);
                            }else {
                                stock[0] = document.getDouble(Type);
                                stock[0] = stock[0] - Double.parseDouble(str);
                                db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock")
                                        .update(Type, stock[0]);
                            }
                        }
                    } else {
                        Log.d("", "No such document");
                    }
                    //add the intent and toast
                    Toast.makeText(getActivity(),"Added Successfully In Records !!",Toast.LENGTH_SHORT).show();
                    FragmentManager fm;
                    FragmentTransaction ft;
                    Fragment frag;
                    frag = new AddingSalesRecord();
                    fm = getFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_place, frag);
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    Toast.makeText(getActivity(),"get failed with!"+task.getException(),Toast.LENGTH_SHORT).show();
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
    private void Initialization(){
        //Setting Date
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
        // populating the editTexts and button and firestore
        Bprice=root.findViewById(R.id.textView9);
        Bqty=root.findViewById(R.id.textView7);
        Cprice=root.findViewById(R.id.textView91);
        Cqty=root.findViewById(R.id.textView71);
        Aprice=root.findViewById(R.id.textView912);
        Aqty=root.findViewById(R.id.textView712);
        Mprice=root.findViewById(R.id.textView9123);
        Mqty=root.findViewById(R.id.textView7123);
        Kprice=root.findViewById(R.id.textView91234);
        Kqty=root.findViewById(R.id.textView71234);
        SKprice=root.findViewById(R.id.textView912345);
        SKqty=root.findViewById(R.id.textView712345);
        AKprice=root.findViewById(R.id.textView9123456);
        AKqty=root.findViewById(R.id.textView7123456);
        add=root.findViewById(R.id.add);
        mylist.add("Type 1");mylist.add("Type 2");mylist.add("Type 3");
        db = FirebaseFirestore.getInstance();

        /*Bprice.setText(String.valueOf(sessionActivity.getBPrice()));
        Cprice.setText(String.valueOf(sessionActivity.getCPrice()));
        Aprice.setText(String.valueOf(sessionActivity.getAPrice()));
        Mprice.setText(String.valueOf(sessionActivity.getMPrice()));
        Kprice.setText(String.valueOf(sessionActivity.getKPrice()));
        AKprice.setText(String.valueOf(sessionActivity.getAkPrice()));
        SKprice.setText(String.valueOf(sessionActivity.getSPrice()));*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chokartype=mylist.get(position);
        //Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
