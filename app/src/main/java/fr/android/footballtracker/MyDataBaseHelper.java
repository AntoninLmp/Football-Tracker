package fr.android.footballtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FootballTracker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MATCH = "Matchs";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTableTeam = "CREATE TABLE "+ TABLE_MATCH + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "team1_name TEXT NOT NULL," +
                "team1_score INTEGER DEFAULT 0," +
                "team1_possession REAL DEFAULT 0.0," +
                "team1_num_shots INTEGER DEFAULT 0," +
                "team1_num_shots_on_target INTEGER DEFAULT 0," +
                "team1_num_passes INTEGER DEFAULT 0," +
                "team1_cards INTEGER DEFAULT 0," +
                "team1_outOfGame INTEGER DEFAULT 0," +
                "team1_fault INTEGER DEFAULT 0," +
                "team1_corners INTEGER DEFAULT 0," +
                "team2_name TEXT NOT NULL," +
                "team2_score INTEGER DEFAULT 0," +
                "team2_possession REAL DEFAULT 0.0," +
                "team2_num_shots INTEGER DEFAULT 0," +
                "team2_num_shots_on_target INTEGER DEFAULT 0," +
                "team2_num_passes INTEGER DEFAULT 0," +
                "team2_cards INTEGER DEFAULT 0," +
                "team2_outOfGame INTEGER DEFAULT 0," +
                "team2_fault INTEGER DEFAULT 0," +
                "team2_corners INTEGER DEFAULT 0);";
        db.execSQL(queryCreateTableTeam);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH + ";");
    }

    public void addMatch (String team1_name, int team1_score, float team1_possession, int team1_num_shots, int team1_num_shots_on_target, int team1_num_passes, int team1_cards,int team1_outOfGame, int team1_fault, int team1_corners,
                         String team2_name, int team2_score, float team2_possession, int team2_num_shots, int team2_num_shots_on_target, int team2_num_passes, int team2_cards,int team2_outOfGame, int team2_fault, int team2_corners){
        SQLiteDatabase db = this.getWritableDatabase();
        // Use to store the data of our application
        ContentValues contentValues = new ContentValues();
        contentValues.put("team1_name",team1_name);
        contentValues.put("team1_score", team1_score);
        contentValues.put("team1_possession", team1_possession);
        contentValues.put("team1_num_shots", team1_num_shots);
        contentValues.put("team1_num_shots_on_target", team1_num_shots_on_target);
        contentValues.put("team1_num_passes", team1_num_passes);
        contentValues.put("team1_cards", team1_cards);
        contentValues.put("team1_outOfGame", team1_outOfGame);
        contentValues.put("team1_fault", team1_fault);
        contentValues.put("team1_corners", team1_corners);
        contentValues.put("team2_name",team2_name);
        contentValues.put("team2_score", team2_score);
        contentValues.put("team2_possession", team2_possession);
        contentValues.put("team2_num_shots", team2_num_shots);
        contentValues.put("team2_num_shots_on_target", team2_num_shots_on_target);
        contentValues.put("team2_num_passes", team2_num_passes);
        contentValues.put("team2_cards", team2_cards);
        contentValues.put("team2_outOfGame", team2_outOfGame);
        contentValues.put("team2_fault", team2_fault);
        contentValues.put("team2_corners", team2_corners);

        long result = db.insert(TABLE_MATCH, null, contentValues);
        if (result == -1){
            Toast.makeText(context, "Failed to insert data", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Data successfully insert", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllMatch(){
        String query = "SELECT * FROM "+ TABLE_MATCH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
