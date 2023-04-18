package fr.android.footballtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.utilities.Score;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    Handler handler;
    MyDataBaseHelper myDB;
    ArrayList<String> teamName1, teamName2, score, id;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerViewHistory);
        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        handler = new Handler();

        // Load Data from sqlite
        myDB = new MyDataBaseHelper(History.this);
        teamName1 = new ArrayList<>();
        teamName2 = new ArrayList<>();
        score = new ArrayList<>();
        id = new ArrayList<>();

        // Navigation Menu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuCreate) {
                handler.post(()-> {
                    Intent intent = new Intent(History.this, MatchCreation.class);
                    startActivity(intent);
                });
            }
            return true;
        });

        storeDataInArray();
        customAdapter = new CustomAdapter(History.this, teamName1, teamName2, score, id);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(History.this));
    }
    void storeDataInArray (){
        // Get all row store in our table matchs
        Cursor cursor = myDB.readAllMatch();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            // We get juste the important informations
            while(cursor.moveToNext()){
                id.add(cursor.getString(0));
                teamName1.add(cursor.getString(1));
                teamName2.add(cursor.getString(11));
                score.add(cursor.getString(2) + "-"+cursor.getString(12));
            }
        }
    }
}