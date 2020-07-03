package com.example.kamdhenupashuahar.Fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class Summary extends Fragment {
    FirebaseFirestore db;
    View root;
    String today,toyear,tomonth,froday,fryear,frmonth;
    TextView fr,to,totalsalequant,totalpurchasequant,totalamntsale,totalamntpurchase;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener1;
    public ArrayList NoOfEmp = new ArrayList();
    public ArrayList year = new ArrayList();
    public ArrayList NoOfEmp1 = new ArrayList();
    public ArrayList NoOfEmp2 = new ArrayList();
    public ArrayList year2 = new ArrayList();
    public ArrayList temparray=new ArrayList();
    String tempdate;
    int k=0;
    int m=0;
    int t=0;
    long ky;
    float tqsale,tqpurchase,tasale,tapurchase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_summary, container, false);
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
        Initialization();
        //Date picker At WORK
        setNormalPicker(inflater,container);
        setCharts();
        tempdate=toyear+"-"+tomonth+"-"+today;
        String kmp=toyear+tomonth+today;
        ky=Long.parseLong(kmp);
        Log.d("iiiii", String.valueOf(tempdate));
        try {
            NoOfEmp2.clear();
            year2.clear();
            setTrendsChart(tempdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
            public void onDateSet(DatePicker datePicker, int yeari, int month, int dayi) {

                froday=String.valueOf(dayi);
                frmonth=String.valueOf(month+1);
                fryear=String.valueOf(yeari);
                if(froday.length()==1){
                    froday="0"+froday;
                }
                if(frmonth.length()==1){
                    frmonth="0"+frmonth;
                }
                fr.setText(froday+"/"+frmonth+"/"+fryear);
                setCharts();

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
            public void onDateSet(DatePicker datePicker, int yeari, int month, int dayi) {
                today=String.valueOf(dayi);
                tomonth=String.valueOf(month+1);
                toyear=String.valueOf(yeari);
                if(today.length()==1){
                    today="0"+today;
                }
                if(tomonth.length()==1){
                    tomonth="0"+tomonth;
                }
                to.setText(today+"/"+tomonth+"/"+toyear);
                setCharts();

            }
        };


    }
    private void Initialization(){
        db = FirebaseFirestore.getInstance();
        //Setting Date

        fr=root.findViewById(R.id.textView8);
        fr.setText(froday+"/"+frmonth+"/"+fryear);
        to=root.findViewById(R.id.textView9);
        to.setText(today+"/"+tomonth+"/"+toyear);
        totalsalequant=root.findViewById(R.id.tvh);
        totalpurchasequant=root.findViewById(R.id.textVie);
        totalamntsale=root.findViewById(R.id.textView7);
        totalamntpurchase=root.findViewById(R.id.textView24);
        // populating the editTexts and button and firestore
        temparray.add("TABLE1");
        temparray.add("Arhar");
        temparray.add("Masoor");
        temparray.add("Kutti");
        temparray.add("Chokar");
        temparray.add("Alsi Khari");
        temparray.add("Sarso Khali");


    }

private  void setTrendsChart(String datestart) throws ParseException {
      //  NoOfEmp2.clear();
      //  year2.clear();
    final String sdate = datestart;
    final SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
    final Date date = df.parse( sdate ); // conversion from String
    final java.util.Calendar cal = GregorianCalendar.getInstance();
    cal.setTime( date );
    switch (t) {
        case 0:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -9 ); // date manipulation
            String yy2=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass=Long.parseLong(yy2);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) +String.valueOf(datetopass)); // conversion to String
            readtrend(datetopass,t);
          //  year2.add("D10");
            break;
        case 1:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -8 ); // date manipulation
            String yy3=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass1=Long.parseLong(yy3);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass1,t);
         //   year2.add("D9");
            break;
        case 2:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -7 ); // date manipulation
            String yy4=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass2=Long.parseLong(yy4);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass2,t);
         //   year2.add("D8");
            break;
        case 3:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -6 ); // date manipulation
            String yy5=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass3=Long.parseLong(yy5);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass3,t);
          //  year2.add("D7");
            break;
        case 4:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -5 ); // date manipulation
            String yy6=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass4=Long.parseLong(yy6);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass4,t);
         //   year2.add("D6");
            break;
        case 5:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -4 ); // date manipulation
            String yy7=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass5=Long.parseLong(yy7);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass5,t);
          //  year2.add("D5");
            break;
        case 6:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -3 ); // date manipulation
            String yy8=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass6=Long.parseLong(yy8);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass6,t);
          //  year2.add("D4");
            break;
        case 7:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -2 ); // date manipulation
            String yy9=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass7=Long.parseLong(yy9);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass7,t);
          //  year2.add("D3");
            break;
        case 8:
            cal.add( GregorianCalendar.DAY_OF_MONTH, -1 ); // date manipulation
            String yy10=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass8=Long.parseLong(yy10);
            Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
            readtrend(datetopass8,t);
          //  year2.add("D2");
            break;
        case 9:
           /* cal.add( GregorianCalendar.DAY_OF_MONTH, 0 ); // date manipulation
            String yy11=df.format( cal.getTime() ).substring(0,4)+df.format( cal.getTime() ).substring(5,7)+df.format( cal.getTime() ).substring(8,10);
            long datetopass9=Long.parseLong(yy11);

            */
            Log.d("DATElllllll", "result: " + String.valueOf(ky)); // conversion to String
            readtrend(ky,t);
         //   year2.add("D1");
            break;
    }
}
private void setCharts(){
    NoOfEmp.clear();
    NoOfEmp1.clear();
    year.clear();
    tqsale=0;
    tasale=0;
    tqpurchase=0;
    tapurchase=0;
    setsaleswalachart();
    setpurchasewalachart();
}
   private void setsaleswalachart()
   {   switch (k) {
       case 0:
       read("TABLE1", k,"true",1);
       year.add("Bhoosa");
       break;
       case 1:
       read("Arhar", k,"true",1);
       year.add("Arhar");
       break;
       case 2:
       read("Masoor", k,"true",1);
       year.add("Masoor");
       break;
       case 3:
       read("Kutti", k,"true",1);
       year.add("Kutti");
       break;
       case 4:
       read("Chokar", k,"true",1);
       year.add("Chokar");
       break;
       case 5:
       read("Alsi Khari", k,"true",1);
       year.add("Alsi Khari");
       break;
       case 6:
       read("Sarso Khali", k,"true",1);
       year.add("Sarso Khali");
       break;
   }
   }
    private void setpurchasewalachart()
    {   switch (k) {
        case 0:
            read("TABLE1", m,"false",0);
            break;
        case 1:
            read("Arhar", m,"false",0);
            break;
        case 2:
            read("Masoor", m,"false",0);
            break;
        case 3:
            read("Kutti", m,"false",0);
            break;
        case 4:
            read("Chokar", m,"false",0);
            break;
        case 5:
            read("Alsi Khari", m,"false",0);
            break;
        case 6:
            read("Sarso Khali", m,"false",0);
            break;
    }
    }

    public void read(final String Type, final int i, final String typi, final int func){
        final float[] data = {0,0};
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
                            if((datetocheck>=frdate&&datetocheck<=todate)&&(type.equals(typi))){
                                data[0] +=Float.parseFloat(String.valueOf(map.get(i).get("Quantity")));
                                data[1] +=Float.parseFloat(String.valueOf(map.get(i).get("Price")));
                                Log.d("yesitis", String.valueOf(data[0]));

                            }
                        }
                        if(func==1)
                        { kushagra(data[0],data[1],i);}
                        else if (func==0){kushagra1(data[0],data[1],i);}

                    } else {
                        Log.d("", "No such document");
                    }
                } else {
                    Log.d("", "get failed with ", task.getException());
                }
            }
        });

    }


    public void readtrend(final long currentdate, final int i) throws ParseException {
        final float[] amnt = {0};
        Log.d("yesitis888","kk");
        int e;
        //yahan se loop lagega for categories wise
        for (e=0;e<7;e++) {
            final float[] data1 = {0, 0};
            Log.d("tessssss",temparray.get(e).toString());

            DocumentReference docRef = db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document(temparray.get(e).toString());
            final int finalE = e;
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("yesitis4444444", "cahala hai");

                            ArrayList<Map<String, Object>> map = new ArrayList<>();
                            map = (ArrayList<Map<String, Object>>) document.get("ArraySales");
                            for (int i = 0; i < map.size(); i++) {
                                Log.d("yesitis4444444", "cahala hai11");
                                long datetocheck = Long.parseLong(String.valueOf(map.get(i).get("Date")));
                                Log.d("yesitis4444444", "cahala hai222");
                                String type = String.valueOf(map.get(i).get("Type"));
                                Log.d("yesitis4444444", "cahala hai333----"+datetocheck+"-"+currentdate);
                                if (datetocheck==currentdate && type.equals("true")) {
                                    Log.d("yesitis4444444", "cahala hai444");

                                    data1[0] += Float.parseFloat(String.valueOf(map.get(i).get("Quantity")));
                                    data1[1] += Float.parseFloat(String.valueOf(map.get(i).get("Price")));
                                    amnt[0] += data1[0] * data1[1];
                                    Log.d("yesitis4444444", String.valueOf(data1[0])+"---"+String.valueOf(map.get(i).get("Quantity")));

                                }
                                Log.d("yesitiskkkk", String.valueOf(amnt[0]));

                            }
                            Log.d("yesitiskkkk11", String.valueOf(amnt[0]));
                            if(finalE==6){      Log.d("yesitiskkkk22", String.valueOf(amnt[0]));


                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            try {
                                                kushagra2(amnt[0],i);
                                            } catch (ParseException ex) {
                                                ex.printStackTrace();
                                            }

                                        }
                                    }, 300);
                                  //  kushagra2(amnt[0],i);

                            }
                        } else {
                            Log.d("", "No such document");
                        }
                    } else {
                        Log.d("", "get failed with ", task.getException());
                    }
                }
            });

        }
        Log.d("yesitis666", String.valueOf(e));


    }



    private void  kushagra(float quant,float price,int i)
    {   Log.d("yesitis2222", String.valueOf(quant)+i);
      //  float a=(float) quant;
       // test.add(i,a);
        NoOfEmp.add(new BarEntry(quant, i));
        tqsale+=quant;
        tasale+=quant*price;
        k++;
        if(k<=6)
        { setsaleswalachart();}
        else if (k==7){BarChart chart = root.findViewById(R.id.barchartk);

            BarDataSet bardataset = new BarDataSet(NoOfEmp, "Item Wise Sales");
            chart.animateY(2000);
            BarData data = new BarData(year, bardataset);
            bardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
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
            });
            totalamntsale.setText(String.valueOf(tasale)+" Rs.");
            totalsalequant.setText(String.valueOf(tqsale)+" sacks");
            k=0;
        }
    }
    private void  kushagra1(float quant,float price,int i)
    {   Log.d("yesitis2222", String.valueOf(quant)+i);
        NoOfEmp1.add(new BarEntry(quant, i));
        tqpurchase+=quant;
        tapurchase+=quant*price;
        m++;
        if(m<=6)
        { setpurchasewalachart();}
        else if (m==7){BarChart chart = root.findViewById(R.id.barchart);
            BarDataSet bardataset = new BarDataSet(NoOfEmp1, "Item Wise Sales");
            chart.animateY(2000);
            BarData data = new BarData(year, bardataset);
            bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
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
            });
            totalamntpurchase.setText(String.valueOf(tapurchase)+" Rs.");
            totalpurchasequant.setText(String.valueOf(tqpurchase)+" sacks");
            m=0;
        }
    }


    private void  kushagra2(float amountofday,int i) throws ParseException {
        Log.d("yesitis777", String.valueOf(amountofday)+"--"+String.valueOf(t));
        NoOfEmp2.add(new BarEntry(amountofday, i));
        Log.d("yesitis777", String.valueOf(NoOfEmp2));
        t++;
        if(t<=9)
        { setTrendsChart(tempdate);}
        else if (t==10){
           // BarChart chart = root.findViewById(R.id.barchart3);
            year2.add("D10");
            year2.add("D9");
            year2.add("D8");
            year2.add("D7");
            year2.add("D6");
            year2.add("D5");
            year2.add("D4");
            year2.add("D3");
            year2.add("D2");
            year2.add("D1");
            Log.d("uu",year2.toString());

            setthegraphofcustomerwisesales(NoOfEmp2,year2,R.id.barchart3);
            t=0;
        }
    }
    private void setthegraphofcustomerwisesales(ArrayList NoOfEmp1, final ArrayList year1, Integer id){

        LineChart mChart = (LineChart) root.findViewById(id);
        mChart.setDrawGridBackground(false);
        LineDataSet set1;
        // create a dataset and give it a type
        set1 = new LineDataSet(NoOfEmp1, "SALES RECORDS OF PREVIOUS 10 DAYS");
        set1.setFillAlpha(110);
        //  set1.setFillColor(Color.BLUE);
        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.rgb(2,136,209));
        set1.setCircleColor(Color.rgb(2,136,209));
        set1.setLineWidth(1f);
        set1.setCircleRadius(4f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        // create a data object with the datasets
        LineData data = new LineData(year1, dataSets);
        // set data
        mChart.setData(data);
        //data set ho gya hai dono axis par ab yahan tak
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.CIRCLE);
        // no description text
        if(id==R.id.barchart) {
            mChart.setDescription("Chart Of SALES Customer Wise: ");
        }
        else if (id==R.id.barchartk)
        {
            mChart.setDescription("Monthly total sales in INR: ");
        }
        mChart.setNoDataTextDescription("No records for the selected date to show.");
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);
        mChart.setVisibleXRangeMaximum(8f);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);
        mChart.getAxisRight().setEnabled(false);
        mChart.getViewPortHandler().setMaximumScaleY(2f);
        mChart.getViewPortHandler().setMaximumScaleX(2f);
        mChart.animateY(0);
//        mChart.animateX(2000, Easing.EasingOption.EaseOutBounce);
        //  dont forget to refresh the drawing
        mChart.invalidate();
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(getActivity(),
                        year1.get(e.getXIndex()) + " = " + e.getVal() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

}




