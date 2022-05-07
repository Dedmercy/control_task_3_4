package ru.mirea.anichkov.mireaproject.ui.home;

import static ru.mirea.anichkov.mireaproject.ui.settings.SettingsFragment.getSavedText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        if (getSavedText() != null){
            mText.setValue(getSavedText());
        }
        else {
            mText.setValue("This is home fragment");
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}