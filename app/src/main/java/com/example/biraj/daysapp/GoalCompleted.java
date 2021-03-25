package com.example.biraj.daysapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GoalCompleted extends AppCompatActivity {

    ImageButton backBtn;
    Button okBtn;
    TextView name, start, end;
    GoalSetCompleted goalSetCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_completed);

        backBtn = findViewById(R.id.back_btn);
        okBtn = findViewById(R.id.ok_btn);
        name = findViewById(R.id.goalName);
        start = findViewById(R.id.goalStart);
        end = findViewById(R.id.goalEnd);

        goalSetCompleted = (GoalSetCompleted) getIntent().getExtras().getSerializable("GOALSETCOMPLETED");

        name.setText("Name : "+goalSetCompleted.getName());
        start.setText("Start Date : "+goalSetCompleted.getStart());
        end.setText("End Date : "+goalSetCompleted.getEnd());

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalCompleted.this,CompletedGoal.class);
                startActivity(intent);
                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalCompleted.this,CompletedGoal.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
