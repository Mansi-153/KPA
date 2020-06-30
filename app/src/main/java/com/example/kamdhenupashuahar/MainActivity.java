package com.example.kamdhenupashuahar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kamdhenupashuahar.Fragments.DetailsOfUdhaar;
import com.example.kamdhenupashuahar.Fragments.home;
import com.example.kamdhenupashuahar.Fragments.purchasedetail;
import com.example.kamdhenupashuahar.Fragments.udhaar;
import com.example.kamdhenupashuahar.Fragments.updatepricelist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;


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
                        frag = new purchasedetail();
                        fm = getFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_place, frag);
                        ft.commit();
                        break;
                    case R.id.checkUdhaar:
//                        add();
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
        user.put("Price", 1815);
        user.put("Total", 1000);
        user.put("Type", true);

        ArrayList<Map<String, Object>> ArraySales = new ArrayList<Map<String, Object>>();
        ArraySales.add(user);
// Add a new document with a generated ID
        db.collection("Database").document("irytBOPTVitXVRh5vB51").collection("SalesPurchase").document("TABLE1")
                .set(ArraySales, SetOptions.merge());
    }
}
