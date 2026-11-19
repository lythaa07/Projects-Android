package com.kelasp1.myfragmentt;

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
import com.kelasp1.myfragmentt.home.FragmentHome;
import com.kelasp1.myfragmentt.itembeli.Fragment_list_beli;
import com.kelasp1.myfragmentt.itemjual.Fragment_list_jual;
import com.kelasp1.myfragmentt.transaksijual.Fragmenttransaksi;
import com.kelasp1.myfragmentt.transaksijual.Fragmenttransaksilist;

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
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentHome())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new FragmentHome();
            } else if (itemId == R.id.nav_pertama) {
                selectedFragment = new Fragment_list_jual();
            } else if (itemId == R.id.nav_kedua) {
                selectedFragment = new Fragment_list_beli();
            } else if (itemId == R.id.nav_ketiga) {
                selectedFragment = new Fragmenttransaksilist();

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