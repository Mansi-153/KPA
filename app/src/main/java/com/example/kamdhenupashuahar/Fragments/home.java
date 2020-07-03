package com.example.kamdhenupashuahar.Fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kamdhenupashuahar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import androidx.annotation.NonNull;


public class home extends Fragment {
    View root;
    String today, toyear, tomonth;
    TextView date, Bqty,Bprice,Cqty,Cprice,Mqty,Mprice,Aqty,Aprice,AKqty,AKprice,SKqty,SKprice,Kqty,Kprice;;
    ImageView fr;
    FirebaseFirestore db;
    int k = 0;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false);
        Initialization();
        //Date picker At WORK
        setNormalPicker(inflater, container);
        setsaleswalachart();
        return root;
    }

    private void Initialization() {
        //Setting Date
        Calendar cal = Calendar.getInstance();
        today = String.valueOf(cal.get(Calendar.DATE));
        if (today.length() == 1) {
            today = "0" + today;
        }
        toyear = String.valueOf(cal.get(Calendar.YEAR));
        tomonth = String.valueOf(1 + (cal.get(Calendar.MONTH)));
        if (tomonth.length() == 1) {
            tomonth = "0" + tomonth;
        }
        date = root.findViewById(R.id.textView8);
        date.setText(today + "/" + tomonth + "/" + toyear);
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
        db = FirebaseFirestore.getInstance();

    }

    private void setNormalPicker(LayoutInflater inflater, ViewGroup container) {

        fr = root.findViewById(R.id.month_picker1);

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pichlafrom1 = date.getText().toString();

                Integer yy2 = Integer.parseInt(pichlafrom1.substring(6, 10));
                Integer mm2 = Integer.parseInt(pichlafrom1.substring(3, 5));
                Integer dd2 = Integer.parseInt(pichlafrom1.substring(0, 2));
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, onDateSetListener, yy2, (mm2 - 1), dd2);
                datePickerDialog.show();

            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayi) {
                today = String.valueOf(dayi);
                tomonth = String.valueOf(month + 1);
                toyear = String.valueOf(year);
                if (today.length() == 1) {
                    today = "0" + today;
                }
                if (tomonth.length() == 1) {
                    tomonth = "0" + tomonth;
                }
                date.setText(today + "/" + tomonth + "/" + toyear);
                k=0;
                setsaleswalachart();

            }
        };

    }


    private void setsaleswalachart() {
        long dat = Long.valueOf(toyear + tomonth + today);
        switch (k) {
            case 0:
                read("TABLE1", k, "true", dat);
                break;
            case 1:
                read("Arhar", k, "true", dat);
                break;
            case 2:
                read("Masoor", k, "true", dat);
                break;
            case 3:
                read("Kutti", k, "true", dat);
                break;
            case 4:
                read("Chokar", k, "true", dat);
                break;
            case 5:
                read("Alsi Khari", k, "true", dat);
                break;
            case 6:
                read("Sarso Khali", k, "true", dat);
                break;
        }

    }

    public void read(final String Type, final int i, final String typi, final long dat) {
        final float[] data = {0, 0};
        DocumentReference docRef = db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document(Type);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<Map<String, Object>> map = new ArrayList<>();
                        map = (ArrayList<Map<String, Object>>) document.get("ArraySales");
                        long todate = Long.parseLong(toyear + tomonth + today);
                        for (int i = 0; i < map.size(); i++) {
                            long datetocheck = Long.parseLong(String.valueOf(map.get(i).get("Date")));
                            String type = String.valueOf(map.get(i).get("Type"));
                            if ((datetocheck == dat) && (type.equals(typi))) {
                                data[0] += Float.parseFloat(String.valueOf(map.get(i).get("Quantity")));
                                data[1] += Float.parseFloat(String.valueOf(map.get(i).get("Total")));
                                Log.d("yesitis", String.valueOf(data[0]));

                            }
                        }
                        kushagra(data[0], data[1], i);


                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });

    }


    private void kushagra(float quant, float tot, int i) {
        Log.d("yesitis2222", String.valueOf(quant) + i);


        if (k <= 6) {
            switch (k)
            {
                case 0: Bprice.setText(String.valueOf(tot));
                       Bqty.setText(String.valueOf(quant));
                       break;
                case 1: Cprice.setText(String.valueOf(tot));
                    Cqty.setText(String.valueOf(quant));
                    break;
                case 2: Aprice.setText(String.valueOf(tot));
                    Aqty.setText(String.valueOf(quant));
                    break;
                case 3: Mprice.setText(String.valueOf(tot));
                    Mqty.setText(String.valueOf(quant));
                    break;
                case 4: Kprice.setText(String.valueOf(tot));
                    Kqty.setText(String.valueOf(quant));
                    break;
                case 5: AKprice.setText(String.valueOf(tot));
                    AKqty.setText(String.valueOf(quant));
                    break;
                case 6: SKprice.setText(String.valueOf(tot));
                    SKqty.setText(String.valueOf(quant));
                    break;

            }
            k++;
            setsaleswalachart();
        }


    }
}