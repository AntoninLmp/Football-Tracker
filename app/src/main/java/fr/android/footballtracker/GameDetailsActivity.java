package fr.android.footballtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GameDetailsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Handler handler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        handler = new Handler();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuCreate) {
                handler.post(()-> {
                    Intent intent = new Intent(GameDetailsActivity.this, MatchCreation.class);
                    startActivity(intent);
                });
            } else if (id == R.id.menuHistory) {
                handler.post(()-> {
                    Intent intent = new Intent(GameDetailsActivity.this, History.class);
                    startActivity(intent);
                });
            }
            return true;
        });
    }
}