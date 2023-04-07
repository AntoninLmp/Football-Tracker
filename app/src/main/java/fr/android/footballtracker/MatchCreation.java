package fr.android.footballtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MatchCreation extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_creation);
        bottomNavigationView = findViewById(R.id.bottomNavMenu);

        handler = new Handler();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                 if (id == R.id.menuDetails) {
                    handler.post(()-> {
                        Intent intent = new Intent(MatchCreation.this, GameDetailsActivity.class);
                        startActivity(intent);
                    });
                }else if (id == R.id.menuHistory) {
                    handler.post(()-> {
                        Intent intent = new Intent(MatchCreation.this, GameDetailsActivity.class);
                        startActivity(intent);
                    });
                }
                return true;
            }
        });
    }
}