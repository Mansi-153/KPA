package com.example.kamdhenupashuahar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kamdhenupashuahar.Fragments.DetailsOfUdhaar;
import com.example.kamdhenupashuahar.Fragments.home;
import com.example.kamdhenupashuahar.Fragments.purchasedetail;
import com.example.kamdhenupashuahar.Fragments.udhaar;
import com.example.kamdhenupashuahar.Fragments.updatepricelist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


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
                    case R.id.updatepricelist:
                        frag = new updatepricelist();
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
                    case R.id.purchasedetail:
                        read();
                        frag = new purchasedetail();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;
                    case R.id.checkUdhaar:
                      add();
                        frag = new DetailsOfUdhaar();
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


   public void add(){
        Map<String, Object> user = new HashMap<>();
        user.put("Date", "26/06/20");
        user.put("Quantity", 100);
        user.put("Price", 1915);
        user.put("Total", 1000);
        user.put("Type", true);
       // Add a new document with a generated ID
        db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document("TABLE1").update("ArraySales", FieldValue.arrayUnion(user));
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
    }
}
