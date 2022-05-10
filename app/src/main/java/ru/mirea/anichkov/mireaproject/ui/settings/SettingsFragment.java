package ru.mirea.anichkov.mireaproject.ui.settings;

import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.mirea.anichkov.mireaproject.LoginScreen;
import ru.mirea.anichkov.mireaproject.MainActivity;
import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.SettingsFragmentBinding;

public class SettingsFragment extends Fragment {


    private SettingsFragmentBinding binding;
    private SettingsViewModel mViewModel;
    static final String SAVED_TEXT = "saved_text";
    public static SharedPreferences sharedSettings;

    private EditText savedText;
    private Button btnAdd, btnSignOut;



    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(inflater,container, false);
        View inflatedView = binding.getRoot();

        savedText = inflatedView.findViewById(R.id.SavedTextEditText);
        btnAdd = inflatedView.findViewById(R.id.btnAddPrefereces);
        btnSignOut = inflatedView.findViewById(R.id.btnSignOut);
        sharedSettings = getActivity().getPreferences(MODE_PRIVATE);



        View.OnClickListener AddPreferencesOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedSettings.edit();
                editor.putString(SAVED_TEXT, savedText.getText().toString());
                editor.apply();

                Toast.makeText(getActivity(), "Text saved", Toast.LENGTH_SHORT).show();
            }
        };

        View.OnClickListener SignOutOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
                MainActivity.loginScreen.signOut(getView());
            }
        };

        btnAdd.setOnClickListener(AddPreferencesOnClick);
        btnSignOut.setOnClickListener(SignOutOnClick);

        return inflatedView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public static String getSavedText(){
        if (sharedSettings != null) {
            String text = sharedSettings.getString(SAVED_TEXT, "Empty");
            return text;
        }
        else {
            return null;
        }
    }
}