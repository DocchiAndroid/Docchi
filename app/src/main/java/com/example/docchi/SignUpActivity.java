package com.example.docchi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {

  ActionBar actionBar;
  TextView tvSignUp;
  EditText etFirstName;
  EditText etLastName;
  EditText etEmail;
  EditText etUsername;
  EditText etPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    actionBar = getSupportActionBar();
    actionBar.hide();

    etFirstName = findViewById(R.id.etFirstName);
    etLastName = findViewById(R.id.etLastName);
    etEmail = findViewById(R.id.etEmail);
    etUsername = findViewById(R.id.etUsername);
    etPassword = findViewById(R.id.etPassword);
    tvSignUp = findViewById(R.id.btnSignUp);

    tvSignUp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        userSignUp(firstName,lastName,email,username,password);


      }
    });
  }

  private void userSignUp(String firstName, String lastName, String email, String username, String password) {

    // store these values to parse


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

  }
}