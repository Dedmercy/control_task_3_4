package ru.mirea.anichkov.mireaproject.ui.browser;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.mirea.anichkov.mireaproject.R;
import ru.mirea.anichkov.mireaproject.databinding.FragmentBrowserBinding;
import ru.mirea.anichkov.mireaproject.databinding.FragmentCalculatorBinding;


public class Browser extends Fragment {

    private @NonNull FragmentBrowserBinding binding;

    private WebView myWebView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBrowserBinding.inflate(inflater, container, false);
        View inflatedView = binding.getRoot();

        myWebView = inflatedView.findViewById(R.id.WebView);

        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://google.com");

        return inflatedView;
    }
    private class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}