package fr.android.footballtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.atomic.AtomicReference;

public class HomeActivity extends AppCompatActivity {

    Button historyButton, newGameButton;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        historyButton = findViewById(R.id.history_button);
        newGameButton = findViewById(R.id.new_game_button);
        handler = new Handler();

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(() ->{
                    Intent intent = new Intent(HomeActivity.this, History.class);
                    startActivity(intent);
                });
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.post(() ->{
                    Intent intent = new Intent(HomeActivity.this, MatchCreation.class);
                    startActivity(intent);
                });
            }
        });
    }


}