package fr.android.footballtracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.logging.Handler;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> Team1, Team2, Score, id;

    CustomAdapter(Context context, ArrayList<String> Team1,ArrayList<String> Team2, ArrayList<String> Score, ArrayList<String> id){
        this.context = context;
        this.Team1 = Team1;
        this.Team2 = Team2;
        this.Score = Score;
        this.id = id;
    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a view from XML layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row_history, parent, false);
        return new MyViewHolder(view);
    }

    // Remplace value inside a view (call by the layer manager)
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.Team1.setText(String.valueOf(Team1.get(position)));
        holder.Team2.setText(String.valueOf(Team2.get(position)));
        holder.Score.setText(String.valueOf(Score.get(position)));
        holder.mainLayout.setOnClickListener(view -> {
            Intent details = new Intent(context, GameDetailsActivity.class);
            details.putExtra("id", String.valueOf(id.get(position)));
            context.startActivity(details);
        });
    }

    // Returns the number of elements in the list
    @Override
    public int getItemCount() {
        return Team1.size();
    }

    // Internal class that represents the views of the list
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Team1, Team2, Score;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Team1 = itemView.findViewById(R.id.Team1);
            Team2 = itemView.findViewById(R.id.Team2);
            Score = itemView.findViewById(R.id.Score);
            mainLayout = itemView.findViewById(R.id.recyclerViewXML);
        }
    }
}
