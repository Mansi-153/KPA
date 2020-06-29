package com.example.kamdhenupashuahar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;

public class MainActivity extends AppCompatActivity  {
    transient DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        navigationView=findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.home:


                        break;
                    case R.id.sales:

                        break;
                    case R.id.purchases:

                        break;
                    case R.id.bank1:

                        break;
                    case R.id.cash:

                        break;
                    case R.id.customer:

                        break;
                    case R.id.supplier:

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
