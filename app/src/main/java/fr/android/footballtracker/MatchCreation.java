package fr.android.footballtracker;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class MatchCreation extends AppCompatActivity {
    private static final int PERMISSION_CAMERA_CODE = 100;
    BottomNavigationView bottomNavigationView;
    Handler handler;
    ImageButton buttonPhoto;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_creation);
        handler = new Handler();

        bottomNavigationView = findViewById(R.id.bottomNavMenu);
        buttonPhoto = findViewById(R.id.buttonPhoto);
        buttonSave = findViewById(R.id.buttonSave);


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
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MatchCreation.this, "Save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void askPermission() {
        // Check if the permission has been already been given
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_CODE);
        }else{
            openCamera();
        }
    }

    private void openCamera() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else {
                // It's means the user doesn't give the permission to use it
                Toast.makeText(this, "Permission is required to use the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }
}