package com.example.kamdhenupashuahar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kamdhenupashuahar.Fragments.AddingSalesRecord;
import com.example.kamdhenupashuahar.Fragments.PurchaseAddition;
import com.example.kamdhenupashuahar.Fragments.Summary;
import com.example.kamdhenupashuahar.Fragments.ViewRecord;
import com.example.kamdhenupashuahar.Fragments.ViewStock;
import com.example.kamdhenupashuahar.Fragments.home;
import com.example.kamdhenupashuahar.Fragments.udhaar;
import com.example.kamdhenupashuahar.Fragments.updatepricelist;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity  {
    transient DrawerLayout drawerLayout;
    Toolbar toolbar;
    FirebaseFirestore db;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBar;
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        try {
            dte();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        frag = new home();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place, frag);
        ft.commit();

        Toast.makeText(MainActivity.this, "Welcome Back !!!", Toast.LENGTH_SHORT).show();
        setToolbar();
        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.home:
                        Log.d("SessionId4Status" , "guvsuv");
                        frag = new home();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();

                        break;
                    case R.id.summary:
                        Log.d("SessionId4Status" , "guvsuv");
                        frag = new Summary();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();

                        break;
                    case R.id.updatepricelist:
                        frag = new updatepricelist();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;
                    case R.id.purchaseaddition:
                        frag = new PurchaseAddition();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;
                    case R.id.addsalesrecord:
                        frag = new AddingSalesRecord();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;
                    case R.id.udhaar:
                        frag = new udhaar();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;

                    case R.id.checkUdhaar:
                        frag = new ViewRecord();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;
                    case R.id.viewStock:
                        frag = new ViewStock();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;

                }


                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            });

    }
    //for setting the customized toolbar
    private void setToolbar(){
        drawerLayout=findViewById(R.id.drawer);
        toolbar=(Toolbar)findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        actionBar = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBar);
        actionBar.syncState();

    }

private void dte() throws ParseException {
    final String sdate = "2012-01-01";
    final SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
    final Date date = df.parse( sdate ); // conversion from String
    final java.util.Calendar cal = GregorianCalendar.getInstance();
    cal.setTime( date );
    cal.add( GregorianCalendar.DAY_OF_MONTH, -5 ); // date manipulation
    Log.d("DATE", "result: " + df.format( cal.getTime() ) ); // conversion to String
}
    /*public void add(){
        Map<String, Object> user = new HashMap<>();
        user.put("Date", "26/06/20");
        user.put("Quantity", 200);
        user.put("Price", 1915);
        user.put("Total", 1000);
        user.put("Type", true);
       // Add a new document with a generated ID
        db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document("TABLE1").update("ArraySales", FieldValue.arrayUnion(user));
        //collection("Database").document("irytBOPTVitXVRh5vB51").
    }
    public void read(){
        DocumentReference docRef =db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document("TABLE1");
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
    }*/
}

//https://firestore.googleapis.com/v1beta1/projects/kamdhenupashuahar-20637/databases/(default)/documents/Database/irytBOPTVitXVRh5vB51


