package ru.mirea.anichkov.mireaproject.ui.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM Character")
    List<Character> getAll();

    @Query("SELECT * FROM Character WHERE id = :id")
    Character getById(long id);

    @Insert
    void insert(Character character);

    @Update
    void update(Character character);

    @Delete
    void delete(Character character);
}
