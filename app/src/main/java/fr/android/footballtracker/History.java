package fr.android.footballtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class History extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        handler = new Handler();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuCreate) {
                handler.post(()-> {
                    Intent intent = new Intent(History.this, MatchCreation.class);
                    startActivity(intent);
                });
            } else if (id == R.id.menuDetails) {
                handler.post(()-> {
                    Intent intent = new Intent(History.this, GameDetailsActivity.class);
                    startActivity(intent);
                });
            }
            return true;
        });
    }
}