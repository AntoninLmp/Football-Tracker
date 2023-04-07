package fr.android.footballtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

        historyButton.setOnClickListener(view -> handler.post(() ->{
            Intent intent = new Intent(HomeActivity.this, History.class);
            startActivity(intent);
        }));
        newGameButton.setOnClickListener(view -> handler.post(() ->{
            Intent intent = new Intent(HomeActivity.this, MatchCreation.class);
            startActivity(intent);
        }));
    }


}