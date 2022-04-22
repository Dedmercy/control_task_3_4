package ru.mirea.anichkov.mireaproject.ui.music;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MusicViewModel extends ViewModel {

    private int position = 0;
    private final MutableLiveData<Integer> mCorrectPosition;

    public MusicViewModel() {
        mCorrectPosition = new MutableLiveData<>();
        //mCorrectPosition.setValue(position);
    }

    public MutableLiveData<Integer> getText() {
        return mCorrectPosition;
    }

    public void takePosition(int p) { position = p; }
    public int returnPosition(){ return  position;}
}
