package ru.mirea.anichkov.mireaproject.ui.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Character {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public int power;
    public String rank;

}
