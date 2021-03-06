package com.example.docchi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.Parse;

import java.lang.reflect.Method;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    ActionBar actionBar;
    TextView btnSignUp;
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etUsername;
    EditText etPassword;
    TextView btnSignUpToLogin;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUpToLogin = findViewById(R.id.btnSignUpToLogin);



        //For back to login page from the signup page
        btnSignUpToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

            }
        });



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = etFirstName.getText().toString();
                String lastname = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();



                //Creating a new user account is the process of constructing a ParseUser object and calling signUpInBackground:
                if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }


                ParseUser user = new ParseUser();
                user.put("firstname", etFirstName.getText().toString());
                user.put("lastname", etLastName.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e.getMessage()  == null) {
                            Log.d("SignUpActivity", "successfully registered");



                        } else {
                            //Sign up didn't succeed. Look at the ParseException
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            etUsername.setText("");
                            etPassword.setText("");


                        }

                    }
                });
            }

            private void userSignup(String firstname, String lastname, String username, String email, String password) {
                Log.i(TAG, "create account" + firstname + lastname + username + email + password);

//                      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);

            }


        });
    }
}






//  }