package com.example.docchi;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

  WebView wvAbout;
  String url = "https://github.com/DocchiAndroid/Docchi/blob/main/README.md";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    wvAbout = findViewById(R.id.wvAbout);
    wvAbout.loadUrl(url);
  }
}