package fr.android.footballtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MatchCreation extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_creation);
        bottomNavigationView = findViewById(R.id.bottomNavMenu);

        handler = new Handler();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuHistory) {
                handler.post(()-> {
                    Intent intent = new Intent(MatchCreation.this, History.class);
                    startActivity(intent);
                });
            }
            return true;
        });
    }
}