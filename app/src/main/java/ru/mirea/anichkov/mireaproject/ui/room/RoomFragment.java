package ru.mirea.anichkov.mireaproject.ui.room;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.mirea.anichkov.mireaproject.App;
import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.FragmentRoomBinding;
import ru.mirea.anichkov.mireaproject.ui.history.HistoryAdapter;
import ru.mirea.anichkov.mireaproject.ui.room.holder.CharacterHolder;


public class RoomFragment extends Fragment {

    private List<Character> bdList = new ArrayList<>();
    private List<CharacterHolder> characterHolders;
    private FragmentRoomBinding binding;
    CharacterDao characterDao;
    private RecyclerView recyclerView;
    private Button btnAddCharacter, btnDeleteCharacter;
    private EditText nameOfCharacterEditText, powerOfCharacterEditText, idEditText;
    String TAG;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View inflatedView = binding.getRoot();



        btnAddCharacter = inflatedView.findViewById(R.id.BtnAddCharacter);
        btnDeleteCharacter = inflatedView.findViewById(R.id.btnDeleteCharacter);
        nameOfCharacterEditText = inflatedView.findViewById(R.id.CharacterNameEditText);
        powerOfCharacterEditText = inflatedView.findViewById(R.id.CharacterPowerEditText);
        idEditText = inflatedView.findViewById(R.id.IdEditText);
        recyclerView = inflatedView.findViewById(R.id.RecyclerViewDatabase);


        AppDatabase db = App.getInstance().getDatabase();
        if (db.characterDao() != null)
            characterDao = db.characterDao();
        bdList = characterDao.getAll();

        View.OnClickListener AddCharacterOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TAG = "добавление";
                try {
                    String rank = "";
                    String name = nameOfCharacterEditText.getText().toString();
                    int power = Integer.parseInt(powerOfCharacterEditText.getText().toString());
                    if (power <= 1000) {
                        rank = "C";
                    } else {
                        if (power <= 5000) {
                            rank = "B";
                        } else {
                            if (power <= 25000) {
                                rank = "A";
                            } else {
                                if (power <= 100000) {
                                    rank = "S";
                                } else {
                                    if (power <= 500000) {
                                        rank = "SS";
                                    } else {
                                        rank = "SSS";
                                    }
                                }
                            }
                        }
                    }
                    Character character = new Character();
                    character.id = 0;
                    character.name = name;
                    character.power = power;
                    character.rank = rank;

                    characterDao.insert(character);
                    bdList = characterDao.getAll();
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), convert(bdList));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);

                    Log.d(TAG, "Успешно");
                } catch (NumberFormatException e) {
                    Log.d(TAG, "Ошибка");
                }

            }
        };

        View.OnClickListener DeleteCharacterOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TAG = "Удаление";
                try {
                    String id = idEditText.getText().toString();
                    characterDao.delete(characterDao.getById(Long.parseLong(id)));
                    bdList = characterDao.getAll();
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), convert(bdList));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "Успешно");

                } catch (Exception e) {
                    Log.d(TAG, "Ошибка");
                }
            }
        };


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), convert(bdList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnAddCharacter.setOnClickListener(AddCharacterOnClick);
        btnDeleteCharacter.setOnClickListener(DeleteCharacterOnClick);

        return  inflatedView;
    }


    public ArrayList<CharacterHolder> convert(List<Character> arrayListCases) {
        ArrayList<CharacterHolder> arrayListPlaceholder = new ArrayList<>();
        for(Character item: arrayListCases){
            arrayListPlaceholder.add(new CharacterHolder(String.valueOf(item.id), item.name, item.rank, String.valueOf(item.power)));
        }
        return arrayListPlaceholder;
    }
}