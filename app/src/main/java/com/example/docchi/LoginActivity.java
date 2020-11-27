package com.example.docchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private EditText etUsername;
    private EditText etPassword;
    private TextView btnLogin;
    private TextView btnSignup;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if logged in, go to main activity
        checkLogin();

        //hide actionbar
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //find views
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUpLoginAct);

        //Login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        //Signup button click listener
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkLogin() {
        if (ParseUser.getCurrentUser() != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void loginUser(String username, String password) {

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username or Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        showDialogProgressBar(); //show the progress dialog
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                alertDialog.dismiss(); //login success/fail dismiss progress dialog
                if (e != null) {
                    //Login failed
                    Toast.makeText(LoginActivity.this, "Error Logging in, Try Again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Login success, start main activity
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void showDialogProgressBar() {

        dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        dialogBuilder.setTitle("");
        final View v = getLayoutInflater().inflate(R.layout.progress_dialog, null);
        dialogBuilder.setView(v);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}