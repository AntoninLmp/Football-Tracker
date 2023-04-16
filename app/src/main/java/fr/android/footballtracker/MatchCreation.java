package fr.android.footballtracker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MatchCreation extends AppCompatActivity {
    private static final int PERMISSION_CAMERA_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 1;

    BottomNavigationView bottomNavigationView;
    Handler handler;
    ImageButton buttonPhoto;
    Button buttonSave;
    String currentPhotoPath;

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
        buttonPhoto.setOnClickListener(view -> handler.post(this::askPermission));
        buttonSave.setOnClickListener(view -> handler.post(Toast.makeText(MatchCreation.this, "Save", Toast.LENGTH_SHORT)::show));
    }

    private void askPermission() {
        // Check if the permission has been already been given
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_CODE);
        }else{
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                // It's means the user doesn't give the permission to use it
                handler.post(Toast.makeText(this, "Permission is required to use the camera", Toast.LENGTH_SHORT)::show);
            }
        }
    }

    // This function is used to use the image to do something with
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_CAMERA_CODE && resultCode == Activity.RESULT_OK){
            //Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            File newfile = new File(currentPhotoPath);
        }
    }

    private File createImageFile() throws IOException {
        // Create an unique image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile("JPEG_" + timeStamp + "_", ".jpg", storageDir);
        // Path for use
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        handler.post( () -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null; // Create the File where the photo should go
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,"com.fr.android.footballtracker.android.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                }
            }
        });
    }
}