package com.kelasp1.mydrawer01;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import com.kelasp1.mydrawer01.Fragment01;
import com.kelasp1.mydrawer01.Fragment02;
import com.kelasp1.mydrawer01.Fragment03;
import com.kelasp1.mydrawer01.Fragment04;
import com.kelasp1.mydrawer01.Fragment05;
import com.kelasp1.mydrawer01.R;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Default fragment saat pertama kali dibuka
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment01())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_pertama);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_pertama) {
                selectedFragment = new Fragment01();
            } else if (itemId == R.id.nav_kedua) {
                selectedFragment = new Fragment02();
            } else if (itemId == R.id.nav_ketiga) {
                selectedFragment = new Fragment03();
            } else if (itemId == R.id.nav_keempat) {
                selectedFragment = new Fragment04();
            } else if (itemId == R.id.nav_kelima) {
                selectedFragment = new Fragment05();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                item.setChecked(true);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}