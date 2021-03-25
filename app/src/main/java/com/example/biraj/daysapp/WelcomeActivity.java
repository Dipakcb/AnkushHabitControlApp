package com.example.biraj.daysapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeActivity extends AppCompatActivity {

    Button submit;
    EditText userName;
    SharedPreferences preferences;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        submit = findViewById(R.id.submit_button);
        userName = findViewById(R.id.user_Name);

         preferences
                = getSharedPreferences("PREFERENCES",MODE_PRIVATE);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name",name);
                editor.commit();
                Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}
