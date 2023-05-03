package fr.android.footballtracker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MatchCreation extends AppCompatActivity implements LocationListener {
    private Handler handler;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int LOCATION_REQUEST_CODE = 2;
    private LocationManager locationManager;
    private String location;
    private String currentPhotoPath;
    private EditText team1_Name, team1_score, team1_possession, team1_shot, team1_shotTarget, team1_passes, team1_card, team1_out, team1_fault, team1_corner;
    private EditText team2_Name, team2_score, team2_possession, team2_shot, team2_shotTarget, team2_passes, team2_card, team2_out, team2_fault, team2_corner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_creation);
        handler = new Handler();

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavMenu);
        final ImageButton buttonPhoto = findViewById(R.id.buttonPhoto);

        // First Team
        team1_Name = findViewById(R.id.NameTeam1);
        team1_score = findViewById(R.id.score1);
        team1_possession = findViewById(R.id.possession1);
        team1_shot = findViewById(R.id.shot1);
        team1_shotTarget = findViewById(R.id.shotTarget1);
        team1_passes = findViewById(R.id.passes1);
        team1_card = findViewById(R.id.card1);
        team1_out = findViewById(R.id.outOfGame1);
        team1_fault = findViewById(R.id.fault1);
        team1_corner = findViewById(R.id.corner1);
        // Second Team
        team2_Name = findViewById(R.id.NameTeam2);
        team2_score = findViewById(R.id.score2);
        team2_possession = findViewById(R.id.possession2);
        team2_shot = findViewById(R.id.shot2);
        team2_shotTarget = findViewById(R.id.shotTarget2);
        team2_passes = findViewById(R.id.passes2);
        team2_card = findViewById(R.id.card2);
        team2_out = findViewById(R.id.outOfGame2);
        team2_fault = findViewById(R.id.fault2);
        team2_corner = findViewById(R.id.corner2);
        // Saving button
        final Button buttonSave = findViewById(R.id.buttonSave);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menuHistory) {
                new Thread(() -> handler.post(() -> {
                    Intent intent = new Intent(this, History.class);
                    startActivity(intent);
                })).start();

            }
            return true;
        });
        buttonPhoto.setOnClickListener(view -> askPermissions());


        buttonSave.setOnClickListener(view -> {
            final MyDataBaseHelper myDB = new MyDataBaseHelper(MatchCreation.this);
            myDB.addMatchToSqliteDb(team1_Name.getText().toString(),
                    Integer.parseInt(team1_score.getText().toString()),
                    Float.parseFloat(team1_possession.getText().toString()),
                    Integer.parseInt(team1_shot.getText().toString()),
                    Integer.parseInt(team1_shotTarget.getText().toString()),
                    Integer.parseInt(team1_passes.getText().toString()),
                    Integer.parseInt(team1_card.getText().toString()),
                    Integer.parseInt(team1_out.getText().toString()),
                    Integer.parseInt(team1_fault.getText().toString()),
                    Integer.parseInt(team1_corner.getText().toString()),
                    team2_Name.getText().toString(),
                    Integer.parseInt(team2_score.getText().toString()),
                    Float.parseFloat(team2_possession.getText().toString()),
                    Integer.parseInt(team2_shot.getText().toString()),
                    Integer.parseInt(team2_shotTarget.getText().toString()),
                    Integer.parseInt(team2_passes.getText().toString()),
                    Integer.parseInt(team2_card.getText().toString()),
                    Integer.parseInt(team2_out.getText().toString()),
                    Integer.parseInt(team2_fault.getText().toString()),
                    Integer.parseInt(team2_corner.getText().toString()),
                    location);

            myDB.addMatchToExternalDb(team1_Name.getText().toString(),
                    Integer.parseInt(team1_score.getText().toString()),
                    Float.parseFloat(team1_possession.getText().toString()),
                    Integer.parseInt(team1_shot.getText().toString()),
                    Integer.parseInt(team1_shotTarget.getText().toString()),
                    Integer.parseInt(team1_passes.getText().toString()),
                    Integer.parseInt(team1_card.getText().toString()),
                    Integer.parseInt(team1_out.getText().toString()),
                    Integer.parseInt(team1_fault.getText().toString()),
                    Integer.parseInt(team1_corner.getText().toString()),
                    team2_Name.getText().toString(),
                    Integer.parseInt(team2_score.getText().toString()),
                    Float.parseFloat(team2_possession.getText().toString()),
                    Integer.parseInt(team2_shot.getText().toString()),
                    Integer.parseInt(team2_shotTarget.getText().toString()),
                    Integer.parseInt(team2_passes.getText().toString()),
                    Integer.parseInt(team2_card.getText().toString()),
                    Integer.parseInt(team2_out.getText().toString()),
                    Integer.parseInt(team2_fault.getText().toString()),
                    Integer.parseInt(team2_corner.getText().toString()));
        });

        // 1- Request access to the location service
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 4- Unregister from the service when the activity becomes invisible
        locationManager.removeUpdates(this);
    }

    private void askPermissions() {
        // Check if the permission has been already been given
        final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, CAMERA_REQUEST_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                // It's means the user doesn't give the permission to use it
                Toast.makeText(this, "Permission is required to use the camera", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                // 2- Register to receive the locations events
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000L, 10F, this);
            }
        }
    }

    // This function is used to use the image to do something with
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            new File(currentPhotoPath);

            // to do
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
        new Thread(() -> handler.post( () -> {
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
                    // Create a Uri to store the picture
                    Uri photoURI = FileProvider.getUriForFile(MatchCreation.this,"com.fr.android.footballtracker.android.fileprovider", photoFile);
                    // We send to the intent the Uri where to save the file
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                }
            }
        })).start();

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // 3- Received a new location from the GPS
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();

        final Geocoder geocoder = new Geocoder(this);
        new Thread(() -> {
            try {
                final List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null) {
                    this.location = addresses.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}