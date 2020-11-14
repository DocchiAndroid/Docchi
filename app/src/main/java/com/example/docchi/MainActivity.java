package com.example.docchi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (ParseUser.getCurrentUser() == null) {
      Intent i = new Intent(this, LoginActivity.class);
      startActivity(i);
      finish();
    }



  }
}