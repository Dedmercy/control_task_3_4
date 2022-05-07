package ru.mirea.anichkov.mireaproject.ui.history;

import static ru.mirea.anichkov.mireaproject.MainActivity.isWorkRecording;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.FragmentMakeHistoryBinding;

public class MakeHistoryFragment extends Fragment {

    private FragmentMakeHistoryBinding binding;

    private EditText editTextName;
    private Button btnAddCharacter;
    public static final String fileName = "save";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMakeHistoryBinding.inflate(inflater,container,false);
        View inflatedView = binding.getRoot();

        btnAddCharacter = inflatedView.findViewById(R.id.btnAddCharacter);

        editTextName = inflatedView.findViewById(R.id.EditTextName);

        View.OnClickListener AddCharacterOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWorkRecording == false){
                    return;
                }
                String name = editTextName.getText().toString();
                FileOutputStream outputStream;
                try {
                    outputStream = getActivity().openFileOutput(fileName + ".txt", Context.MODE_PRIVATE);
                    outputStream.write(name.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                NavHostFragment.findNavController(MakeHistoryFragment.this)
                        .navigate(R.id.action_nav_make_history_to_nav_history);
            }
        };

        btnAddCharacter.setOnClickListener(AddCharacterOnClick);


        return  inflatedView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}