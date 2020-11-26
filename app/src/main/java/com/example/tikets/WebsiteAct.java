package com.example.tikets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class WebsiteAct extends AppCompatActivity {

    WebView webview;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        webview = findViewById(R.id.webview);



    }
}