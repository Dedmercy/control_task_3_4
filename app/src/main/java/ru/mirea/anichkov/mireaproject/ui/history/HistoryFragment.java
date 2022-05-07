package ru.mirea.anichkov.mireaproject.ui.history;

import static ru.mirea.anichkov.mireaproject.ui.history.MakeHistoryFragment.fileName;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.HistoryFragmentBinding;
import ru.mirea.anichkov.mireaproject.ui.home.HomeViewModel;

public class HistoryFragment extends Fragment {

    private HistoryViewModel mViewModel;
    private FloatingActionButton btnAddHistory;
    private RecyclerView recyclerView;
    private ArrayList<Character> states = new ArrayList<Character>();
    private ArrayList<String> names = new ArrayList<String>();
    private String TAG = "history fragment";
    private String KEY = "history";
    private JSONHelper jsonHelper = new JSONHelper();
    private String result = null;


    private HistoryFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = HistoryFragmentBinding.inflate(inflater, container, false);
        View inflatedView = binding.getRoot();

        btnAddHistory = (FloatingActionButton) inflatedView.findViewById(R.id.floatingActionButton);
        recyclerView = inflatedView.findViewById(R.id.MyRecyclerView);

        String result = openSaveFile();


        if (jsonHelper.importFromJSON(getActivity()) != null) {
            names = jsonHelper.importFromJSON(getActivity());
            for (String s : names) {
                Log.d("names", s);
                states.add(new Character(s));
            }
        }

        View.OnClickListener AddHistoryOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HistoryFragment.this)
                        .navigate(R.id.action_nav_history_to_nav_make_history);
            }
        };
        btnAddHistory.setOnClickListener(AddHistoryOnClick);

        if (result != null) {
            names.add(result);
            Log.d("result", result);
            states.add(new Character(result));
            result = null;
        }

        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), states);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(historyAdapter);

        return inflatedView;
    }

    @Override
    public void onStop() {
        Log.d(TAG, "стоп");
        boolean statusExport = jsonHelper.exportToJSON(getContext(), names);
        if (statusExport) {
            Toast.makeText(getActivity(), "Data export", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "death");
        super.onDestroyView();
        binding = null;
    }

    public String openSaveFile() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = getActivity().openFileInput(fileName + ".txt");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            String text = new String(bytes, "UTF-8");
            Log.d(TAG, text);
            return text;
        } catch (Exception e) {
            if (e.getClass().getName().equals("class java.io.FileNotFoundException"))
                return null;
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}