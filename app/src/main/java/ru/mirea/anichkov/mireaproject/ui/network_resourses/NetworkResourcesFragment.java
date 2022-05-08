package ru.mirea.anichkov.mireaproject.ui.network_resourses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.FragmentNetworkResourcesBinding;

public class NetworkResourcesFragment extends Fragment {

    FragmentNetworkResourcesBinding binding;
    public ArrayList<String> titleList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    public Elements title;



    private ListView listView;
    private Button btnDownloadData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNetworkResourcesBinding.inflate(inflater, container, false);
        View inflatedView = binding.getRoot();

        listView = inflatedView.findViewById(R.id.ListView);
        btnDownloadData = inflatedView.findViewById(R.id.btnDownload);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item2, R.id.textView3, titleList);

        View.OnClickListener DownloadDataOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://wttr.in/Moon";
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkinfo = null;
                if (connectivityManager != null) {
                    networkinfo = connectivityManager.getActiveNetworkInfo();
                }
                if (networkinfo != null && networkinfo.isConnected()) {
                    new DownloadPageTask().execute(url);
                } else {
                    Toast.makeText(getActivity(), "Нет интернета", Toast.LENGTH_SHORT).show();
                }

            }
        };
        btnDownloadData.setOnClickListener(DownloadDataOnClick);
        return  inflatedView;
    }
    private class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... urls) {
            Document doc;
            try{
                Log.d("Parsing", "s");
                doc = Jsoup.connect(urls[0]).get();
                title = doc.select("html");

                titleList.clear();
                for (Element titles : title) {
                    titleList.add(titles.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            listView.setAdapter(adapter);
        }
    }
}