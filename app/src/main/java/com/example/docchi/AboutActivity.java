package com.example.docchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.parse.ParseUser;

public class AboutActivity extends AppCompatActivity {

  WebView wvAbout;
  String url = "https://github.com/DocchiAndroid/Docchi/blob/main/README.md";
  ActionBar actionBar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_about);
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("Docchi");
    setSupportActionBar(toolbar);
    wvAbout = findViewById(R.id.wvAbout);
    wvAbout.loadUrl(url);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_top, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.About:
        Intent intent1 = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent1);
        return true;
      case R.id.Help:
        Intent intent2 = new Intent(getApplicationContext(), HelpActivity.class);
        startActivity(intent2);
        return true;
      case R.id.Settings:
        Intent intent3 = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent3);
        return true;
      case R.id.Logout:
        logout();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }

  }

  private void logout() {
    ParseUser.logOut();
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
    finish();


  }
}