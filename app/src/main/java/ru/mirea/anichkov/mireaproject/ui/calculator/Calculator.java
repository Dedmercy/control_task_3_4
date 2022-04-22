package ru.mirea.anichkov.mireaproject.ui.calculator;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.FragmentCalculatorBinding;


public class Calculator extends Fragment {

    private @NonNull FragmentCalculatorBinding binding;
    private TextView resultTextView;
    private Button btnAddNumber1, btnAddNumber2, btnAddNumber3,btnAddNumber4,btnAddNumber5, btnAddNumber6,
            btnAddNumber7, btnAddNumber8, btnAddNumber9, btnAddNumber0, btnAddDot, btnDelete, btnReset, btnPercent,
            btnPlus, btnMinus, btnDivide, btnMultiplied, btnEquals;

    private String resultText;
    private static final String KEY_COUNT = "COUNT";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CalculatorViewModel calculatorViewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);

//        View inflatedView  = inflater.inflate(R.layout.fragment_calculator, container, false);
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View inflatedView = binding.getRoot();

        resultTextView = inflatedView.findViewById(R.id.ResultTextView);

        if (savedInstanceState != null) {
            resultText = savedInstanceState.getString(KEY_COUNT, "");
            resultTextView.setText(resultText);
        }

        initializeButtons(inflatedView);


        View.OnClickListener btnAddNumber1Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "1";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber2Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "2";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber3Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "3";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber4Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "4";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber5Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "5";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber6Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "6";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber7Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "7";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber8Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "8";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber9Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "9";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnAddNumber0Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString() + "0";
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnDeleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString();
                resultText = resultText.substring(0, resultText.length() - 1);
                resultTextView.setText(resultText);
            }
        };
        View.OnClickListener btnResetListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultTextView.setText("");
            }
        };
        View.OnClickListener btnPercentListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString();
                if (resultText.length() != 0){
                    if ((count('/') == 0) && (count('*') == 0) &&
                            (count('-') == 0) && (count('+') == 0)) {
                        float result = Float.parseFloat(resultText);
                        result /= 100;
                        resultText = String.valueOf(result);
                        resultTextView.setText(resultText);
                    }
                }
            }
        };
        View.OnClickListener btnPlusListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString();
                if (resultText.length() != 0) {
                    if ((count('/') == 0) && (count('*') == 0) &&
                            (count('-') == 0) && (count('+') == 0)) {
                        resultText = resultTextView.getText().toString() + "+";
                        resultTextView.setText(resultText);
                    }
                    if((resultText.substring(resultText.length() - 1).equals("/")) ||
                            (resultText.substring(resultText.length() - 1).equals("-")) ||
                            (resultText.substring(resultText.length() - 1).equals("*"))){
                        resultText = resultText.substring(0,resultText.length() - 1) + "+";
                        resultTextView.setText(resultText);
                    }
                }
            }
        };
        View.OnClickListener btnMinusListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString();
                if (resultText.length() != 0) {
                    if ((count('/') == 0) && (count('*') == 0) &&
                            (count('-') == 0) && (count('+') == 0)) {
                        resultText = resultTextView.getText().toString() + "-";
                        resultTextView.setText(resultText);
                    }
                    if((resultText.substring(resultText.length() - 1).equals("/")) ||
                            (resultText.substring(resultText.length() - 1).equals("+")) ||
                            (resultText.substring(resultText.length() - 1).equals("*"))){
                        resultText = resultText.substring(0,resultText.length() - 1) + "-";
                        resultTextView.setText(resultText);
                    }
                }
            }

        };
        View.OnClickListener btnDivideListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString();
                if (resultText.length() != 0) {
                    if ((count('/') == 0) && (count('*') == 0) &&
                            (count('-') == 0) && (count('+') == 0)) {
                        resultText = resultTextView.getText().toString() + "/";
                        resultTextView.setText(resultText);
                    }
                    if((resultText.substring(resultText.length() - 1).equals("-")) ||
                            (resultText.substring(resultText.length() - 1).equals("+")) ||
                            (resultText.substring(resultText.length() - 1).equals("*"))){
                        resultText = resultText.substring(0,resultText.length() - 1) + "/";
                        resultTextView.setText(resultText);
                    }
                }
            }
        };
        View.OnClickListener btnMultipliedListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString();
                if (resultText.length() != 0) {
                    if ((count('/') == 0) && (count('*') == 0) &&
                            (count('-') == 0) && (count('+') == 0)) {
                        resultText = resultTextView.getText().toString() + "*";
                        resultTextView.setText(resultText);
                    }
                    if((resultText.substring(resultText.length() - 1).equals("/")) ||
                            (resultText.substring(resultText.length() - 1).equals("+")) ||
                            (resultText.substring(resultText.length() - 1).equals("-"))){
                        resultText = resultText.substring(0,resultText.length() - 1) + "*";
                        resultTextView.setText(resultText);
                    }
                }
            }
        };
        View.OnClickListener btnEqualsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText = resultTextView.getText().toString();
                if (resultText.length() != 0) {
                    if ((count('/') == 1) || (count('*') == 1) ||
                            (count('-') == 1) || (count('+') == 1)){
                        if((!resultText.substring(resultText.length() - 1).equals("/")) &&
                                (!resultText.substring(resultText.length() - 1).equals("+")) &&
                                (!resultText.substring(resultText.length() - 1).equals("-")) &&
                                (!resultText.substring(resultText.length() - 1).equals("*"))){
                            int index = 0;
                            String mathSign = "";
                            for (char ch : resultText.toCharArray()) {
                                if (!Character.isDigit(ch)) {
                                    index = resultText.indexOf(ch);
                                    mathSign = String.valueOf(ch);
                                }
                            }
                            calculate(index, mathSign);
                            resultTextView.setText(resultText);

                        }
                    }
                }
            }
        };

        btnAddNumber1.setOnClickListener(btnAddNumber1Listener);
        btnAddNumber2.setOnClickListener(btnAddNumber2Listener);
        btnAddNumber3.setOnClickListener(btnAddNumber3Listener);
        btnAddNumber4.setOnClickListener(btnAddNumber4Listener);
        btnAddNumber5.setOnClickListener(btnAddNumber5Listener);
        btnAddNumber6.setOnClickListener(btnAddNumber6Listener);
        btnAddNumber7.setOnClickListener(btnAddNumber7Listener);
        btnAddNumber8.setOnClickListener(btnAddNumber8Listener);
        btnAddNumber9.setOnClickListener(btnAddNumber9Listener);
        btnAddNumber0.setOnClickListener(btnAddNumber0Listener);
        btnDelete.setOnClickListener(btnDeleteListener);
        btnPercent.setOnClickListener(btnPercentListener);
        btnReset.setOnClickListener(btnResetListener);
        btnMinus.setOnClickListener(btnMinusListener);
        btnPlus.setOnClickListener(btnPlusListener);
        btnDivide.setOnClickListener(btnDivideListener);
        btnMultiplied.setOnClickListener(btnMultipliedListener);
        btnEquals.setOnClickListener(btnEqualsListener);

        calculatorViewModel.takeText(resultText);
        final TextView textView = binding.ResultTextView;
        calculatorViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return inflatedView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_COUNT, resultText);
    }


    private void initializeButtons(View inflatedView){
        btnAddNumber1 = inflatedView.findViewById(R.id.btnAddNumber1);
        btnAddNumber2 = inflatedView.findViewById(R.id.btnAddNumber2);
        btnAddNumber3 = inflatedView.findViewById(R.id.btnAddNumber3);
        btnAddNumber4 = inflatedView.findViewById(R.id.btnAddNumber4);
        btnAddNumber5 = inflatedView.findViewById(R.id.btnAddNumber5);
        btnAddNumber6 = inflatedView.findViewById(R.id.btnAddNumber6);
        btnAddNumber7 = inflatedView.findViewById(R.id.btnAddNumber7);
        btnAddNumber8 = inflatedView.findViewById(R.id.btnAddNumber8);
        btnAddNumber9 = inflatedView.findViewById(R.id.btnAddNumber9);
        btnAddNumber0 = inflatedView.findViewById(R.id.btnAddNumber0);
        btnDelete = inflatedView.findViewById(R.id.btnDelete);
        btnReset = inflatedView.findViewById(R.id.btnReset);
        btnPercent = inflatedView.findViewById(R.id.btnPersent);
        btnPlus = inflatedView.findViewById(R.id.btnPlus);
        btnMinus = inflatedView.findViewById(R.id.btnMinus);
        btnDivide = inflatedView.findViewById(R.id.btnDivide);
        btnMultiplied = inflatedView.findViewById(R.id.btnMultiplied);
        btnEquals = inflatedView.findViewById(R.id.btnEquals);
    }
    private void calculate(int index, String mathSign) {
        float num1 = Float.parseFloat(resultText.substring(0, index));
        float num2 = Float.parseFloat(resultText.substring(index + 1));

        switch (mathSign) {
            case ("+"): {
                resultText = String.valueOf(num1 + num2);
                break;
            }
            case ("-"): {
                resultText = String.valueOf(num1 - num2);
                break;
            }
            case ("/"): {
                resultText = String.valueOf(num1 / num2);
                break;
            }
            case ("*"): {
                resultText = String.valueOf(num1 * num2);
                break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int count(char mathSign){
        int result = 0;
        result = (int) resultText.chars().filter(x->x==mathSign).count();
        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public String getResultText(){
        return resultText;
    }
}