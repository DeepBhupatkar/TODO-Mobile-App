package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    EditText username, password, repassword;
    Button register;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.BLACK);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        register = findViewById(R.id.register);
        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter all the values.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pass.equals(repass)) {
                        Boolean checkUser = DB.checkUsername(user);

                        if(!checkUser) {
                            Boolean insert = DB.insertData(user,pass);

                            if (insert) {
                                Toast.makeText(MainActivity.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                            }
                            else {
                                Toast.makeText(MainActivity.this, "User Registration Failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(MainActivity.this, "User already exists! please Login.", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Passwords are not Matching.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}