package com.example.biraj.daysapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddgoalActivity extends AppCompatActivity {

    Button submit;
    Button cancel;
    ImageButton btn_Back;
    EditText goalname;
    String currentdate;
    String enddate;

    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgoal);

        goalname=findViewById(R.id.goal_name);
        submit=findViewById(R.id.submit_button);
        cancel = findViewById(R.id.cancel_button);
        btn_Back = findViewById(R.id.back_btn);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        Calendar c =Calendar.getInstance();
        Date now = c.getTime();

        c.add(Calendar.DATE , 21 );
        Date nextdate = c.getTime();

        currentdate=formatter.format(now);
        enddate=formatter.format(nextdate);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goalname.length() != 0) {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddgoalActivity.this);
                    myDB.addGoal(goalname.getText().toString(), currentdate, enddate);
                    goalname.setText("");

                    Intent intent = new Intent(AddgoalActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(AddgoalActivity.this,"You must enter Goal Name !",Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalname.setText("");
            }
        });


        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddgoalActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
