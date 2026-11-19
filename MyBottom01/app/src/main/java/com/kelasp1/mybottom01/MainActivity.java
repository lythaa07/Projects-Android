package com.kelasp1.mybottom01;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Default fragment saat pertama kali dibuka
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Fragment01())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_pertama);
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
            return true;
        });
    }
}