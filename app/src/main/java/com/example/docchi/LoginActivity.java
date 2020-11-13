package com.example.docchi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private EditText etEmail;
    private EditText etUsername;
    private Button btnLogin;
    private Button btnRegister;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        etEmail = findViewById(R.id.etLoginEmail);
        etUsername = findViewById(R.id.etLoginUsername);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                loginUser(email, username);
            }
        });

      btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
          startActivity(intent);
        }
      });
    }

    private void loginUser(String email, String username) {
        if(email.isEmpty() || username.isEmpty()){
            Toast.makeText(LoginActivity.this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialogProgressBar();
        ParseUser.logInInBackground(email, username, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                alertDialog.dismiss();
                if (e != null) {
                    Toast.makeText(LoginActivity.this, "Error Logging in, Try Again.", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void showDialogProgressBar() {

        dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setTitle("");
        final View v = getLayoutInflater().inflate(R.layout.progress_login, null);
        dialogBuilder.setView(v);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}