package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar1;
    EditText username1, password1;
    Button login;
    TextView textview;
    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getWindow().setStatusBarColor(Color.BLACK);
        toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        username1 = findViewById(R.id.username1);
        password1 = findViewById(R.id.password1);
        login = findViewById(R.id.login);
        textview = findViewById(R.id.textview);
        DB = new DBHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username1.getText().toString();
                String pass = password1.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginPage.this, "Please enter all the values.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean checkUserPass = DB.checkUsernamePassword(user,pass);

                    if(checkUserPass) {
                        Toast.makeText(LoginPage.this, "User Login Successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                    }
                    else {
                        Toast.makeText(LoginPage.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
        builder.setTitle("Alert!");
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES",(dialog, which) -> finish());
        builder.setNegativeButton("NO",(dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}