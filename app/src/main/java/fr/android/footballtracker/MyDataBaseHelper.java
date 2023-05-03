package fr.android.footballtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import android.os.Handler;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private final Handler handler;
    private final Context context;
    private static final String DATABASE_NAME = "FootballTracker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MATCH = "Matches";
    private static final String DB_URL = "jdbc:mysql://10.0.2.2:3306/footballtracker";
    private static final String USER = "root";
    private static final String PASS = "root";
    private Cursor myCursor;


    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        handler = new Handler();
    }


    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String queryCreateMatchesTable = "CREATE TABLE "+ TABLE_MATCH + " ( " +
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
                "team2_corners INTEGER DEFAULT 0," +
                "location TEXT NOT NULL);";
        db.execSQL(queryCreateMatchesTable);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int i, final int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH + ";");
    }

    public void addMatchToSqliteDb(final String team1_name,
                          final int team1_score,
                          final float team1_possession,
                          final int team1_num_shots,
                          final int team1_num_shots_on_target,
                          final int team1_num_passes,
                          final int team1_cards,
                          final int team1_outOfGame,
                          final int team1_fault,
                          final int team1_corners,
                          final String team2_name,
                          final int team2_score,
                          final float team2_possession,
                          final int team2_num_shots,
                          final int team2_num_shots_on_target,
                          final int team2_num_passes,
                          final int team2_cards,
                          final int team2_outOfGame,
                          final int team2_fault,
                          final int team2_corners,
                          final String location){
        new Thread(() -> {
            final SQLiteDatabase db = this.getWritableDatabase();

            final ContentValues contentValues = new ContentValues();
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
            contentValues.put("location", location);

            final long result = db.insert(TABLE_MATCH, null, contentValues);
            if (result == -1){
                handler.post(() -> Toast.makeText(context, "Failed to insert data", Toast.LENGTH_SHORT).show());
            }else{
                handler.post(() -> Toast.makeText(context, "Data successfully insert", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }


    public void addMatchToExternalDb(final String team1_name,
                                     final int team1_score,
                                     final float team1_possession,
                                     final int team1_num_shots,
                                     final int team1_num_shots_on_target,
                                     final int team1_num_passes,
                                     final int team1_cards,
                                     final int team1_outOfGame,
                                     final int team1_fault,
                                     final int team1_corners,
                                     final String team2_name,
                                     final int team2_score,
                                     final float team2_possession,
                                     final int team2_num_shots,
                                     final int team2_num_shots_on_target,
                                     final int team2_num_passes,
                                     final int team2_cards,
                                     final int team2_outOfGame,
                                     final int team2_fault,
                                     final int team2_corners) {
        new Thread(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                final Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

                final String query = "INSERT INTO matchs (team1_name, team1_score, team1_possession, team1_num_shots, team1_num_shots_on_target, team1_num_passes, team1_cards, team1_outOfGame, team1_fault, team1_corners, team2_name, team2_score, team2_possession, team2_num_shots, team2_num_shots_on_target, team2_num_passes, team2_cards, team2_outOfGame, team2_fault, team2_corners) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                final PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, team1_name);
                preparedStatement.setInt(2, team1_score);
                preparedStatement.setFloat(3, team1_possession);
                preparedStatement.setInt(4, team1_num_shots);
                preparedStatement.setInt(5, team1_num_shots_on_target);
                preparedStatement.setInt(6, team1_num_passes);
                preparedStatement.setInt(7, team1_cards);
                preparedStatement.setInt(8, team1_outOfGame);
                preparedStatement.setInt(9, team1_fault);
                preparedStatement.setInt(10, team1_corners);
                preparedStatement.setString(11, team2_name);
                preparedStatement.setInt(12, team2_score);
                preparedStatement.setFloat(13, team2_possession);
                preparedStatement.setInt(14, team2_num_shots);
                preparedStatement.setInt(15, team2_num_shots_on_target);
                preparedStatement.setInt(16, team2_num_passes);
                preparedStatement.setInt(17, team2_cards);
                preparedStatement.setInt(18, team2_outOfGame);
                preparedStatement.setInt(19, team2_fault);
                preparedStatement.setInt(20, team2_corners);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    /* GET DATA FUNCTION */
    public Cursor readAllMatch(){
        final String query = "SELECT * FROM "+ TABLE_MATCH;
        return readOnSqliteDb(query);
    }

    public Cursor readMatchId(final String id){
        final String query = "SELECT * FROM "+ TABLE_MATCH + " WHERE ID = "+ id;
        return readOnSqliteDb(query);
    }

    private Cursor readOnSqliteDb(final String query) {
        // Make async ??
        final SQLiteDatabase db = this.getReadableDatabase();
        if (db != null){
            return db.rawQuery(query, null);
        }
        return null;
    }
}
