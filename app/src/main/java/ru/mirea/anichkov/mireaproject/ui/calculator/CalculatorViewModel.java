package ru.mirea.anichkov.mireaproject.ui.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private String text = "1";

    public CalculatorViewModel(){
        mText = new MutableLiveData<>();
//        mText.setValue(text);

    }
    public LiveData<String> getText() {
        return mText;
    }
    public void takeText (String s){
        text = s;
    }
}
