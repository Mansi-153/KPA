package com.example.kamdhenupashuahar.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kamdhenupashuahar.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Summary extends Fragment {
    FirebaseFirestore db;
    View root;
    String today,toyear,tomonth,froday,fryear,frmonth;
    TextView fr,to;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener1;
    public ArrayList NoOfEmp = new ArrayList();
    public ArrayList test = new ArrayList();
    public ArrayList year = new ArrayList();
    int k=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_summary, container, false);
        Initialization();
        //Date picker At WORK
        setNormalPicker(inflater,container);
        setsaleswalachart();
        return root;
    }

    private void setNormalPicker(LayoutInflater inflater, ViewGroup container) {

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pichlafrom1=fr.getText().toString();

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
                froday=String.valueOf(dayi);
                frmonth=String.valueOf(month+1);
                fryear=String.valueOf(year);
                if(froday.length()==1){
                    froday="0"+froday;
                }
                if(frmonth.length()==1){
                    frmonth="0"+frmonth;
                }
                fr.setText(froday+"/"+frmonth+"/"+fryear);
                setsaleswalachart();

            }
        };


        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pichlafrom1=to.getText().toString();

                Integer yy2=Integer.parseInt(pichlafrom1.substring(6,10));
                Integer mm2=Integer.parseInt(pichlafrom1.substring(3,5));
                Integer dd2=Integer.parseInt(pichlafrom1.substring(0,2));
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,onDateSetListener1,yy2,(mm2-1),dd2);
                datePickerDialog.show();

            }
        });
        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
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
                to.setText(today+"/"+tomonth+"/"+toyear);
                setsaleswalachart();

            }
        };


    }
    private void Initialization(){
        db = FirebaseFirestore.getInstance();
        //Setting Date
        Calendar cal = Calendar.getInstance();
        today=String.valueOf(cal.get(Calendar.DATE));
        if(today.length()==1){
            today="0"+today;
        }
        froday=today;
        toyear = String.valueOf(cal.get(Calendar.YEAR));
        fryear=toyear;
        tomonth =String.valueOf(1+(cal.get(Calendar.MONTH)));
        frmonth =String.valueOf((cal.get(Calendar.MONTH)));
        if(tomonth.length()==1){
            tomonth="0"+tomonth;
            frmonth="0"+frmonth;
        }
        fr=root.findViewById(R.id.textView8);
        fr.setText(froday+"/"+frmonth+"/"+fryear);
        to=root.findViewById(R.id.textView9);
        to.setText(today+"/"+tomonth+"/"+toyear);
        // populating the editTexts and button and firestore


    }



   private void setsaleswalachart()
   {   switch (k) {
       case 0:
       read("TABLE1", k);
       year.add("Bhoosa");
       break;
       case 1:
       read("Arhar", k);
       year.add("Arhar");
       break;
       case 2:
       read("Masoor", k);
       year.add("Masoor");
       break;
       case 3:
       read("Kutti", k);
       year.add("Kutti");
       break;
       case 4:
       read("Chokar", k);
       year.add("Chokar");
       break;
       case 5:
       read("Alsi Khari", k);
       year.add("Alsi Khari");
       break;
       case 6:
       read("Sarso Khali", k);
       year.add("Sarso Khali");

       //now will have to set the graph

       break;
   }
   }



    public void read(String Type, final int i){
        final float[] data = {0};
        DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document(Type);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<Map<String,Object>> map = new ArrayList<>();
                        map = (ArrayList<Map<String, Object>>) document.get("ArraySales");
                        long frdate=Long.parseLong(fryear+frmonth+froday);
                        long todate=Long.parseLong(toyear+tomonth+today);
                        for (int i=0;i<map.size();i++){
                            long datetocheck = Long.parseLong(String.valueOf(map.get(i).get("Date")));
                            String type=String.valueOf(map.get(i).get("Type"));
                            if((datetocheck>=frdate&&datetocheck<=todate)&&(type.equals("true"))){
                                data[0] +=Float.parseFloat(String.valueOf(map.get(i).get("Quantity")));
                                Log.d("yesitis", String.valueOf(data[0]));

                            }
                        }
                     kushagra(data[0],i);

                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });

    }


    private void  kushagra(float quant,int i)
    {   Log.d("yesitis2222", String.valueOf(quant)+i);
      //  float a=(float) quant;
       // test.add(i,a);
        NoOfEmp.add(new BarEntry(quant, i));
        k++;
        if(k<=6)
        { setsaleswalachart();}
        else if (k==7){BarChart chart = root.findViewById(R.id.barchartk);

            BarDataSet bardataset = new BarDataSet(NoOfEmp, "Item Wise Sales");
            chart.animateY(2000);
            BarData data = new BarData(year, bardataset);
            bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
            chart.setData(data);
            chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    if (e == null)
                        return;

                    Toast.makeText(getActivity(),
                            year.get(e.getXIndex()) + " = " + e.getVal() , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected() {

                }
            });}
    }




}




