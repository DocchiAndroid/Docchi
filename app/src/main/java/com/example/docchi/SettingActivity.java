package com.example.docchi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
}