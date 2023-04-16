package fr.android.footballtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList Team1, Team2, Score;

    CustomAdapter(Context context, ArrayList Team1,ArrayList Team2, ArrayList Score){
        this.context = context;
        this.Team1 = Team1;
        this.Team2 = Team2;
        this.Score = Score;
    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.Team1.setText(String.valueOf(Team1.get(position)));
        holder.Team2.setText(String.valueOf(Team2.get(position)));
        holder.Score.setText(String.valueOf(Score.get(position)));
    }

    @Override
    public int getItemCount() {
        return Team1.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Team1, Team2, Score;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Team1 = itemView.findViewById(R.id.Team1);
            Team2 = itemView.findViewById(R.id.Team2);
            Score = itemView.findViewById(R.id.Score);
        }
    }
}
