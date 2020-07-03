package com.example.kamdhenupashuahar.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamdhenupashuahar.R;
import com.example.kamdhenupashuahar.util.SessionActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ViewStock extends Fragment {
   View root;
    FirebaseFirestore db;
    double a1,a2,a3,a4,a5,a6,a7;
    EditText edit,edit2,edit3,edit4,edit5,edit6,edit7;
    TextView stock;
    SessionActivity sessionActivity;
    public ArrayList NoOfEmp = new ArrayList();
    public ArrayList year = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         root =  inflater.inflate(R.layout.fragment_view_stock, container, false);
       sessionActivity  = new SessionActivity(getActivity());
        db = FirebaseFirestore.getInstance();

        edit = root.findViewById(R.id.editBhusa);
        edit2 = root.findViewById(R.id.editChokar);
        edit3= root.findViewById(R.id.editArhar);
        edit4 = root.findViewById(R.id.editMasoor);
        edit5 = root.findViewById(R.id.editKutti);
        edit6 = root.findViewById(R.id.editAlsi);
        edit7 = root.findViewById(R.id.editSarso);
        stock = root.findViewById(R.id.stockW);


        final Button button = root.findViewById(R.id.stockUpdate);

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
                        Double worth = a1*sessionActivity.getBPrice()+a2*sessionActivity.getCPrice()+a3*sessionActivity.getAPrice()+a4*sessionActivity.getMPrice()+a5*sessionActivity.getKPrice()+a6*sessionActivity.getAkPrice()+a7*sessionActivity.getSPrice();
                        stock.setText(Double.toString(worth));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                btn();
                            }
                        });
                      setchart();
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

    public void btn(){
        final DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("Stock").document("stock");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if(edit.getText()==null || edit.getText()==null || edit.getText()==null || edit.getText()==null || edit5.getText()==null || edit6.getText()==null || edit7.getText()==null)
                        docRef.update("Bhoosa", Double.parseDouble(String.valueOf(edit.getText())));
                        docRef.update("Chokar", Double.parseDouble(String.valueOf(edit2.getText())));
                        docRef.update("Arhar", Double.parseDouble(String.valueOf(edit3.getText())));
                        docRef.update("Masoor", Double.parseDouble(String.valueOf(edit4.getText())));
                        docRef.update("Kutti", Double.parseDouble(String.valueOf(edit5.getText())));
                        docRef.update("Alsi Khari", Double.parseDouble(String.valueOf(edit6.getText())));
                        docRef.update("Sarso Khali", Double.parseDouble(String.valueOf(edit7.getText())));
                        Toast.makeText(getActivity(), "Your Stock Updated", Toast.LENGTH_SHORT).show();
                        Double worth = a1*sessionActivity.getBPrice()+a2*sessionActivity.getCPrice()+a3*sessionActivity.getAPrice()+a4*sessionActivity.getMPrice()+a5*sessionActivity.getKPrice()+a6*sessionActivity.getAkPrice()+a7*sessionActivity.getSPrice();
                        stock.setText(Double.toString(worth));

                    } else {
                        Toast.makeText(getActivity(), "No Such Field Present", Toast.LENGTH_SHORT).show();
                    }Toast.makeText(getActivity(), "Data Succesfully Added", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "get failed with "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private void setchart(){
        NoOfEmp.clear();
        year.clear();
        NoOfEmp.add(new BarEntry((float) Float.parseFloat(edit.getText().toString()), 0));
        NoOfEmp.add(new BarEntry((float) Float.parseFloat(edit2.getText().toString()), 1));
        NoOfEmp.add(new BarEntry((float) Float.parseFloat(edit3.getText().toString()), 2));
        NoOfEmp.add(new BarEntry((float) Float.parseFloat(edit4.getText().toString()), 3));
        NoOfEmp.add(new BarEntry((float) Float.parseFloat(edit5.getText().toString()), 4));
        NoOfEmp.add(new BarEntry((float) Float.parseFloat(edit6.getText().toString()), 5));
        NoOfEmp.add(new BarEntry((float) Float.parseFloat(edit7.getText().toString()), 6));

        year.add("Bhoosa");
        year.add("Chokar");
        year.add("Arhar");
        year.add("Masoor");
        year.add("Kutti");
        year.add("Alsi Khari");
        year.add("Sarso Khali");
        Log.d("ppooooooooo",NoOfEmp.toString());
        Log.d("ppjjjjjjjjjjjj",year.toString());
        PieChart pieChart = root.findViewById(R.id.piechart);
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "STOCK ITEM-WISE");

        PieData data = new PieData(year, dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart.animateXY(1500, 1500);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
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
        });
    }
}