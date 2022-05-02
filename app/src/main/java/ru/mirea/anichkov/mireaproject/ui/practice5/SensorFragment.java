package ru.mirea.anichkov.mireaproject.ui.practice5;

import static android.app.Activity.RESULT_OK;

import static ru.mirea.anichkov.mireaproject.MainActivity.isWorkRecording;
import static ru.mirea.anichkov.mireaproject.MainActivity.isWorkCamera;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.mirea.anichkov.mireaproject.R;


import ru.mirea.anichkov.mireaproject.databinding.FragmentSensorBinding;

public class SensorFragment extends Fragment implements SensorEventListener {

    private SensorViewModel mViewModel;
    private FragmentSensorBinding binding;

    private TextView coordinateX, coordinateY, coordinateZ;
    private TextView nameOfAudioFile;
    private Button btnPhoto, btnRecord, btnPlay, btnEnd, btnStopRecord;
    private ImageView photoView;
    private ProgressBar progressBar;
    private SensorManager sensorManager;
    private Sensor sensor;
    private Uri imageURI;
    final String TAG = "My run";
    private static final int CAMERA_REQUEST = 0;
    private String photoCurrentPath;
    private MediaPlayer player;
    private MediaRecorder mediaRecorder;
    private int progress;
    private File photoFile, audioFile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SensorViewModel pr5ViewModel = new ViewModelProvider(this).get(SensorViewModel.class);
        binding = FragmentSensorBinding.inflate(inflater, container, false);
        View inflatedView = binding.getRoot();

        initializationOfView(inflatedView);


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btnPlay.setEnabled(false);
        btnStopRecord.setEnabled(false);
        btnEnd.setEnabled(false);
        Handler handler = new Handler();



        View.OnClickListener btnTakeImage = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager()) != null && isWorkCamera){
                        photoFile = null;
                    try{
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String auth = getContext().getPackageName() +".fileprovider";
                    Log.d(TAG, auth);
                    imageURI = FileProvider.getUriForFile(getContext(), auth, photoFile);


                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageURI);


                    startActivityForResult(intent,0);
                }

            }
        };
        View.OnClickListener btnStartRecording = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


                btnRecord.setEnabled(false);
                btnStopRecord.setEnabled(true);
                btnPlay.setEnabled(false);
                btnEnd.setEnabled(false);



                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

                    if (audioFile == null) {
                        audioFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "sosiska.3gp");
                    }
                    mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
                    try {
                        mediaRecorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaRecorder.start();

                    Toast.makeText(getActivity(), "Recording started!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        View.OnClickListener btnStopRecording = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnStopRecord.setEnabled(false);
                btnPlay.setEnabled(true);
                btnRecord.setEnabled(true);
                btnEnd.setEnabled(false);

                if (mediaRecorder != null) {
                    Log.d(TAG, "stopRecording");
                    mediaRecorder.stop();
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    Toast.makeText(getActivity(), "You are not recording right now!",
                            Toast.LENGTH_SHORT).show();
                }

                ContentValues values = new ContentValues(4);
                long current = System.currentTimeMillis();

                values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
                values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
                values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gp");
                values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());

                ContentResolver contentResolver = getActivity().getContentResolver();
                Log.d(TAG, "audioFile: " + audioFile.canRead());

                Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                Uri newUri = contentResolver.insert(baseUri, values);

                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
                Toast.makeText(getActivity(), "done",
                        Toast.LENGTH_SHORT).show();
            }
        };
        View.OnClickListener btnPlayFile = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player = new MediaPlayer();
                btnEnd.setEnabled(true);
                btnRecord.setEnabled(false);

                FileInputStream stream = null;
                try {
                    stream = new FileInputStream(audioFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                nameOfAudioFile.setText(audioFile.getName());
                try {
                    player.setDataSource(stream.getFD());
                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, String.valueOf(player.getDuration()));
                int duration = player.getDuration();
                progress = player.getCurrentPosition();
                progressBar.setMax(duration);
                progressBar.setProgress(progress);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "thread start");
                        while(!player.isPlaying()){
                            Log.d(TAG, "thread sleep");
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        while(duration >= player.getCurrentPosition() && player.isPlaying()){
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
        View.OnClickListener btnEndPlayingFile = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.setEnabled(true);
                btnEnd.setEnabled(false);
                btnRecord.setEnabled(true);
                btnStopRecord.setEnabled(false);
                player.stop();
                progressBar.setProgress(0);
            }
        };

        btnPhoto.setOnClickListener(btnTakeImage);
        btnRecord.setOnClickListener(btnStartRecording);
        btnStopRecord.setOnClickListener(btnStopRecording);
        btnPlay.setOnClickListener(btnPlayFile);
        btnEnd.setOnClickListener(btnEndPlayingFile);


        return inflatedView;
    }

    private void galleryAddPic() {
        try {
            OutputStream fOut = null;
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(photoCurrentPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), f.getAbsolutePath(), f.getName(), f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializationOfView(View inflatedView){
     coordinateX = inflatedView.findViewById(R.id.coordinateX);
     coordinateY = inflatedView.findViewById(R.id.coordinateY);
     coordinateZ = inflatedView.findViewById(R.id.coordinateZ);

     nameOfAudioFile = inflatedView.findViewById(R.id.nameOfAudioFile);

     btnPhoto = inflatedView.findViewById(R.id.btnPhoto);
     btnPlay = inflatedView.findViewById(R.id.btnPlay);
     btnRecord = inflatedView.findViewById(R.id.btnRecording);
     btnEnd = inflatedView.findViewById(R.id.btnEnd);
     btnStopRecord = inflatedView.findViewById(R.id.btnStopRecording);

     photoView = inflatedView.findViewById(R.id.photoView);

     progressBar = inflatedView.findViewById(R.id.progressBar2);

     nameOfAudioFile = inflatedView.findViewById(R.id.nameOfAudioFile);
    }

    public File createImageFile() throws IOException {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String name = "Image #" + time + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(name, ".jpg", storageDirectory);
        photoCurrentPath = "file:" + file.getAbsolutePath();
        return  file;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            photoView.setImageURI(imageURI);
            galleryAddPic();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            coordinateX.setText(String.valueOf(valueAzimuth));
            coordinateY.setText(String.valueOf(valuePitch));
            coordinateZ.setText(String.valueOf(valueRoll));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}