package com.example.docchi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.parse.ParseUser;

public class SettingActivity extends AppCompatActivity {

  EditText etCurrentUsername;
  EditText etNewUsername;
  EditText etCurrentPassword;
  EditText etNewPassword;
  Button btnUpdateUsername;
  Button btnUpdatePassword;
  String TAG = "Settings";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("Docchi");
    setSupportActionBar(toolbar);

    etCurrentUsername = findViewById(R.id.etCurrentUsername);
    etNewUsername = findViewById(R.id.etNewUsername);
    etCurrentPassword = findViewById(R.id.etCurrentPassword);
    etNewPassword = findViewById(R.id.etNewPassword);

    btnUpdateUsername = findViewById(R.id.btnUpdateUsername);
    btnUpdatePassword = findViewById(R.id.btnUpdatePassword);

    ParseUser user = ParseUser.getCurrentUser();

    btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        String currentUsername = etCurrentUsername.getText().toString();
        Log.i(TAG, " username is: " + user.getUsername());
        if(currentUsername.equals(user.getUsername())){
          String newUsername = etNewUsername.getText().toString();
          user.setUsername(newUsername);
          user.saveInBackground();
          Toast.makeText(getApplicationContext(), "Username updated!", Toast.LENGTH_SHORT).show();
        }
        else {
          Toast.makeText(getApplicationContext(), "Enter correct username", Toast.LENGTH_SHORT).show();
        }
      }
    });




    btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String currentPassword = etCurrentPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        Log.i(TAG, " user password is: " + currentPassword + " " + newPassword);
        if(currentPassword.equals(newPassword)){
          Log.i(TAG, "this is user pass:" + "true");
          user.put("password", newPassword);
          user.saveInBackground();
          Toast.makeText(getApplicationContext(), "Password updated!", Toast.LENGTH_SHORT).show();
        }
        else {
          Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
        }

      }
    });
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