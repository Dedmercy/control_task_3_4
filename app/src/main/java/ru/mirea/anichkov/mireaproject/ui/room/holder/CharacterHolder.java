package ru.mirea.anichkov.mireaproject.ui.room.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterHolder {

    private String name;
    private String id;
    private String rank;
    private String power;
    public CharacterHolder(String name, String id, String rank, String power) {
        this.name = name;
        this.id = id;
        this.rank = rank;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPower() {
        return power;
    }

    public String getRank() {
        return rank;
    }
}
