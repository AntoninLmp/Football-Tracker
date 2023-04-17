package fr.android.footballtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GameDetailsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Handler handler;
    TextView name1, score, statPossession1, statShot1, statShotOnTarget1, statPasses1, statCards1, statOut1, statFault1, statCorner1;
    TextView name2, statPossession2, statShot2, statShotOnTarget2, statPasses2, statCards2, statOut2, statFault2, statCorner2;
    TextView statShotSuccess1, statShotSuccess2, statFaultTransfCard1, statFaultTransfCard2;
    MyDataBaseHelper myDB;
    private String idMatch;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        score = findViewById(R.id.scoreMatch);

        // First Team
        name1 = findViewById(R.id.name1);
        statPossession1 = findViewById(R.id.statPossession1);
        statShot1 = findViewById(R.id.statShots1);
        statShotOnTarget1 = findViewById(R.id.statShotOnTarget1);
        statPasses1 = findViewById(R.id.statPasses1);
        statCards1 = findViewById(R.id.statCards1);
        statOut1 = findViewById(R.id.statOut1);
        statFault1 = findViewById(R.id.statFault1);
        statCorner1 = findViewById(R.id.statCorner1);
        statShotSuccess1 = findViewById(R.id.statShotSuccess1);
        statFaultTransfCard1 = findViewById(R.id.statFaultTransfCard1);

        // Second Team
        name2 = findViewById(R.id.name2);
        statPossession2 = findViewById(R.id.statPossession2);
        statShot2 = findViewById(R.id.statShots2);
        statShotOnTarget2 = findViewById(R.id.statShotOnTarget2);
        statPasses2 = findViewById(R.id.statPasses2);
        statCards2 = findViewById(R.id.statCards2);
        statOut2 = findViewById(R.id.statOut2);
        statFault2 = findViewById(R.id.statFault2);
        statCorner2 = findViewById(R.id.statCorner2);
        statShotSuccess2 = findViewById(R.id.statShotSuccess2);
        statFaultTransfCard2 = findViewById(R.id.statFaultTransfCard2);


        handler = new Handler();

        myDB = new MyDataBaseHelper(GameDetailsActivity.this);

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
        getIntentData();
    }

    void getIntentData(){
        if(getIntent().hasExtra("id")){
            idMatch = getIntent().getStringExtra("id");
            storeDataInArray(idMatch);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void storeDataInArray (String id){
        // Get all row store in our table matchs
        Cursor cursor = myDB.readMatchId(id);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            // We get juste the important informations
            while(cursor.moveToNext()){
                score.setText(cursor.getString(2) + " - " +cursor.getString(12));

                name1.setText(cursor.getString(1));
                statPossession1.setText(cursor.getString(3));
                statShot1.setText(cursor.getString(4));
                statShotOnTarget1.setText(cursor.getString(5));
                statPasses1.setText(cursor.getString(6));
                statCards1.setText(cursor.getString(7));
                statOut1.setText(cursor.getString(8));
                statFault1.setText(cursor.getString(9));
                statCorner1.setText(cursor.getString(10));

                name2.setText(cursor.getString(11));
                statPossession2.setText(cursor.getString(13));
                statShot2.setText(cursor.getString(14));
                statShotOnTarget2.setText(cursor.getString(15));
                statPasses2.setText(cursor.getString(16));
                statCards2.setText(cursor.getString(17));
                statOut2.setText(cursor.getString(18));
                statFault2.setText(cursor.getString(19));
                statCorner2.setText(cursor.getString(20));

                //statistics

                statShotSuccess1.setText(String.valueOf(Integer.parseInt(statShotOnTarget1.getText().toString()) * 100 /  Integer.parseInt(statShot1.getText().toString())) + "%");
                statShotSuccess2.setText(String.valueOf(Integer.parseInt(statShotOnTarget2.getText().toString()) * 100 /  Integer.parseInt(statShot2.getText().toString())) + "%");

                statFaultTransfCard1.setText(String.valueOf(Integer.parseInt(statCards1.getText().toString()) * 100 /  Integer.parseInt(statFault1.getText().toString())) + "%");
                statFaultTransfCard2.setText(String.valueOf(Integer.parseInt(statCards2.getText().toString()) * 100 /  Integer.parseInt(statFault2.getText().toString())) + "%");
            }
        }
    }
}