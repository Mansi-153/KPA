package com.example.kamdhenupashuahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.kamdhenupashuahar.Fragments.home;
import com.example.kamdhenupashuahar.Fragments.purchasedetail;
import com.example.kamdhenupashuahar.Fragments.udhaar;
import com.example.kamdhenupashuahar.Fragments.updatepricelist;
import com.google.android.material.navigation.NavigationView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity  {
    transient DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBar;
    FragmentManager fm;
    FragmentTransaction ft;
    Fragment frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
