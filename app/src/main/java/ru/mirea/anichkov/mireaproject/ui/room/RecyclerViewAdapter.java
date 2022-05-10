package ru.mirea.anichkov.mireaproject.ui.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.FragmentRoomBinding;
import ru.mirea.anichkov.mireaproject.ui.room.holder.CharacterHolder;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<CharacterHolder> characters;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, ArrayList<CharacterHolder> characters){
        this.inflater = LayoutInflater.from(context);
        this.characters = characters;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_room_list_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.characterHolder = characters.get(position);
        holder.idTextView.setText(characters.get(position).getId());
        holder.nameTextView.setText(characters.get(position).getName());
        holder.rankTextView.setText(characters.get(position).getRank());
        holder.powerTextView.setText(characters.get(position).getPower());

    }

    @Override
    public int getItemCount() {
        return characters.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView idTextView ,nameTextView, powerTextView, rankTextView;
        public CharacterHolder characterHolder;

        public ViewHolder(View view) {
            super(view);
            idTextView = view.findViewById(R.id.IdTextView);
            nameTextView = view.findViewById(R.id.NameTextView);
            powerTextView = view.findViewById(R.id.PowerTextView);
            rankTextView = view.findViewById(R.id.RankTextView);
        }
    }



}


