package ru.mirea.anichkov.mireaproject.ui.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Character.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();
}
