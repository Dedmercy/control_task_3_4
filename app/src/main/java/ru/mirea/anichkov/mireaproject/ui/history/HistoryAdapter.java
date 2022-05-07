package ru.mirea.anichkov.mireaproject.ui.history;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.mirea.anichkov.mireaproject.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Character> states;

    public HistoryAdapter(Context context, List<Character> states){
        this.inflater = LayoutInflater.from(context);
        this.states = states;
    }
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Character character = states.get(position);
        holder.nameView.setText(character.getName());
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
        }
    }
}
