package ru.mirea.anichkov.mireaproject.ui.music;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.FragmentMusicBinding;
import ru.mirea.anichkov.mireaproject.ui.browser.Browser;
import ru.mirea.anichkov.mireaproject.ui.calculator.CalculatorViewModel;

public class MusicFragment extends Fragment {

    private FragmentMusicBinding binding;
    private Button btnStart, btnPause, btnRestart;
    private ProgressBar progressBar;
    private MediaPlayer player;
    private boolean statusPlaying = false;
    Handler handler;
    final String TAG = "My run";
    int progress;
    private static final String KEY_COUNT = "COUNT";
    MusicViewModel musicViewModel;
    private ProgressBar progressBar2 = null;
    private boolean statusRestart = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMusicBinding.inflate(inflater, container, false);
        View inflatedView = binding.getRoot();

        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        btnStart = inflatedView.findViewById(R.id.buttonStart);
        btnRestart = inflatedView.findViewById(R.id.buttonRestart);
        btnPause = inflatedView.findViewById(R.id.buttonPause);

        progressBar = inflatedView.findViewById(R.id.progressBar);

        player = MediaPlayer.create(getActivity(), R.raw.gunna);

        int audioDuraction = player.getDuration();
        progressBar.setMax(audioDuraction);
        handler = new Handler();
        progress = 0;

        if (player.getCurrentPosition() != 0) {
            player.seekTo(savedInstanceState.getInt(KEY_COUNT, 0));
        }
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            }
        });

        View.OnClickListener btnStartAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!player.isPlaying()) {
                    if (!statusRestart && progressBar.getProgress() != 0) {
                        player.seekTo(progressBar.getProgress());
                        player.start();
                    }
                    else {
                        player.start();
                        statusRestart = true;
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "start");
                        while (!player.isPlaying()){
                            try {
                                Log.d(TAG, "sleep");
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        while (player.getCurrentPosition() <= audioDuraction && player.isPlaying()){
                            Log.d(TAG, "while");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progress = player.getCurrentPosition();

                                    Log.d(TAG, String.valueOf(progress));
                                    progressBar.setProgress(progress);
                                }
                            });
                        }
                    }
                }).start();
            }
        };

        View.OnClickListener btnRestartAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.pause();
                    player.seekTo(0);
                    statusPlaying = false;
                }
            }
        };

        View.OnClickListener btnPauseAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()){
                    player.pause();
                    statusPlaying = false;
                }
            }
        };



        btnStart.setOnClickListener(btnStartAction);
        btnRestart.setOnClickListener(btnRestartAction);
        btnPause.setOnClickListener(btnPauseAction);

        progressBar2 = binding.progressBar;
        musicViewModel.getText().observe(getViewLifecycleOwner(), progressBar2 ::setProgress);

        return inflatedView;
    }

    @Override
    public void onDestroyView() {
        if (player.isPlaying()){
            int lastPos = player.getCurrentPosition();
            Log.d(TAG, "last pos" + lastPos);
            musicViewModel.takePosition(lastPos);
            player.stop();
        }
        super.onDestroyView();
        binding = null;
    }
}