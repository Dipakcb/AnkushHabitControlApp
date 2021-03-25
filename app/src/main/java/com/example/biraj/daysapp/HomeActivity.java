package com.example.biraj.daysapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ImageButton addgoalbtn;
    ImageButton historyBtn;
    ListView listView;
    MyDatabaseHelper myDB;
    List<GoalSet> list;
    ArrayAdapter<GoalSet> arrayAdapter;
    private Typeface mTypeface;

    ImageView file;
    TextView uName,noData;
    Switch notiSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addgoalbtn = findViewById(R.id.add_btn);
        historyBtn = findViewById(R.id.history_btn);
        uName = findViewById(R.id.g_name);
        noData = findViewById(R.id.noData);
        file = findViewById(R.id.file);
        notiSwitch = findViewById(R.id.switch1);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        uName.setText("Hi, " + name);

        listView = findViewById(R.id.goal_listView);
        myDB = new MyDatabaseHelper(this);




        mTypeface = Typeface.createFromAsset(getAssets(), "font/montserrat_regular.ttf");
        list = myDB.getlistContents();
        arrayAdapter = new ArrayAdapter<GoalSet>(this, android.R.layout.simple_list_item_1, list){

            public View getView ( int position, View convertView, ViewGroup parent){
                // Cast the list view each item as text view
                TextView item = (TextView) super.getView(position, convertView, parent);

                // Set the typeface/font for the current item
                item.setTypeface(mTypeface);
                return item;
            }
        };
        listView.setAdapter(arrayAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        notiSwitch.setChecked(sharedPreferences.getBoolean("value",true));

       if (arrayAdapter.getCount() == 0)
       {
           file.setVisibility(View.VISIBLE);
           noData.setVisibility(View.VISIBLE);
           notiSwitch.setClickable(false);
           notiSwitch.setChecked(false);
       }else {

           notiSwitch.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (notiSwitch.isChecked()){

                       SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                       editor.putBoolean("value",true);
                       editor.apply();
                       notiSwitch.setChecked(true);
                       registerAlarm();
                       registerAlarm2();
                       Toast.makeText(getApplicationContext(),"Daily Reminder On",Toast.LENGTH_SHORT).show();

                   }
                   else {

                       SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                       editor.putBoolean("value",false);
                       editor.apply();
                       notiSwitch.setChecked(false);
                        deleteAlarm();
                        deleteAlarm2();
                       Toast.makeText(getApplicationContext(),"Daily Reminder Off",Toast.LENGTH_SHORT).show();
                   }
               }
           });


       }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoalSet goalSet = list.get(position);

                Intent intent = new Intent(HomeActivity.this, CounterActivity.class);
                intent.putExtra("GOALSET", goalSet);
                startActivity(intent);


            }
        });




        addgoalbtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, AddgoalActivity.class);
                startActivity(intent);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CompletedGoal.class);
                startActivity(intent);

            }
        });


    }

    private void registerAlarm(){
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(HomeActivity.this,Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY,6);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    private void registerAlarm2(){
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(HomeActivity.this,Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }


    private void deleteAlarm(){
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(HomeActivity.this,Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        manager.cancel(pendingIntent);

    }

    private void deleteAlarm2(){
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(HomeActivity.this,Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        manager.cancel(pendingIntent);

    }
}





