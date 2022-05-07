package ru.mirea.anichkov.mireaproject.ui.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HistoryViewModel extends ViewModel {
    private ArrayList<Character> characters;
    private final MutableLiveData<HistoryAdapter> mText;

    public HistoryViewModel(){
        mText = new MutableLiveData<>();
//        mText.setValue(text);
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    public MutableLiveData<HistoryAdapter> getCharacters() {
        return mText;
    }
}