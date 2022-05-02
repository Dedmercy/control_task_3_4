package ru.mirea.anichkov.mireaproject.ui.practice5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SensorViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SensorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}