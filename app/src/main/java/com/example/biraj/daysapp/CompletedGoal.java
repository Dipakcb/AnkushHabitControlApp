package com.example.biraj.daysapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CompletedGoal extends AppCompatActivity {

    ImageButton backBtn;
    ListView listView2;
    MyDatabaseHelper myDB;
    List<GoalSetCompleted> list;
    ArrayAdapter<GoalSetCompleted> arrayAdapter;
    private Typeface mTypeface;

    ImageView file;
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_goal);

        noData = findViewById(R.id.noData);
        file = findViewById(R.id.file);
        listView2 = findViewById(R.id.goal_listView2);
        backBtn = findViewById(R.id.back_btn);

        myDB = new MyDatabaseHelper(this);
        mTypeface = Typeface.createFromAsset(getAssets(),"font/montserrat_regular.ttf");
        list = myDB.getlistContents2();
        arrayAdapter = new ArrayAdapter<GoalSetCompleted>(this, android.R.layout.simple_list_item_1,list) {

            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position, convertView, parent);

                // Set the typeface/font for the current item
                item.setTypeface(mTypeface);
                return item;
            }
        };

        listView2.setAdapter(arrayAdapter);

        if (arrayAdapter.getCount() == 0)
        {
            file.setVisibility(View.VISIBLE);
            noData.setVisibility(View.VISIBLE);
        }

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoalSetCompleted gs = list.get(position);
                Intent intent = new Intent(CompletedGoal.this,GoalCompleted.class);
                intent.putExtra("GOALSETCOMPLETED",gs);
                startActivity(intent);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompletedGoal.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
