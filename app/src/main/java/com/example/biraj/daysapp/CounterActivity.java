package com.example.biraj.daysapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.System.exit;
import static java.lang.System.in;

public class CounterActivity extends AppCompatActivity {


    TextView goal_Name;
    TextView goal_Startdate;
    TextView goal_Enddate;
    TextView goal_Goaldays;
    ImageView ssView;
    private Context mContext;

    MyDatabaseHelper myDB;

    ImageButton btn_Back;
    ImageButton btn_Share;
    Button btn_Delete;
    Button btn_Restart;
    private Handler handler;
    private Runnable runnable;

    Dialog dialog,dialog2,dialog3;
    String goal_Id;
    String fDate;
    String name2, startdate2, enddate2;
    GoalSet goalSet;
    View relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        goal_Name = findViewById(R.id.g_name);
        goal_Startdate = findViewById(R.id.g_start);
        goal_Enddate = findViewById(R.id.g_end);
        goal_Goaldays = findViewById(R.id.g_day);
        btn_Delete = findViewById(R.id.delete_button);
        btn_Back = findViewById(R.id.back_btn);
        btn_Restart = findViewById(R.id.restart_button);
        btn_Share = findViewById(R.id.share_btn);
        relativeLayout = findViewById(R.id.parentLayout);
        ssView = findViewById(R.id.ssView);

        final BitmapDrawable[] drawable = new BitmapDrawable[1];
        final Bitmap[] bitmap = new Bitmap[1];

        myDB = new MyDatabaseHelper(this);
        dialog = new Dialog(this);

        dialog2 = new Dialog(this);
        dialog2.setCanceledOnTouchOutside(false);

        dialog3 = new Dialog(this);
        goalSet = (GoalSet) getIntent().getExtras().getSerializable("GOALSET");

        goal_Id = goalSet.getId();
        goal_Name.setText(goalSet.getName());
        goal_Startdate.setText(goalSet.getStart());
        goal_Enddate.setText(goalSet.getEnd());

        countDownStart();

        mContext = getApplicationContext();

    // Delete custom dialog
        dialog3.setContentView(R.layout.custom_dialog);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnDel = dialog3.findViewById(R.id.del_btn);
        Button btnCancel = dialog3.findViewById(R.id.cancel_btn);

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteRow(goalSet.getName(), goalSet.getStart());
                Toast.makeText(CounterActivity.this, "Goal Deleted !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CounterActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });

    // Restart custom dialog
        dialog.setContentView(R.layout.dialog_restart);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnRestart = dialog.findViewById(R.id.restart_btn);
        Button cancelBtn = dialog.findViewById(R.id.cancel_btn);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentdate, enddate;
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
                Calendar c = Calendar.getInstance();
                Date now = c.getTime();

                c.add(Calendar.DATE, 21);
                Date nextdate = c.getTime();

                currentdate = formatter.format(now);
                enddate = formatter.format(nextdate);

                myDB.updateRow(currentdate, enddate, goalSet.getName(), goalSet.getStart());
                Toast.makeText(CounterActivity.this, "Goal Restarted !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CounterActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        //Goal complete custom dialog
        dialog2.setContentView(R.layout.dialog_finish);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnOk = dialog2.findViewById(R.id.ok_btn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            dialog2.dismiss();

            completedGoal();
            myDB.addGoalComplete(name2, startdate2, enddate2);
            myDB.deleteRow(goalSet.getName(), goalSet.getStart());
            Intent intent = new Intent(CounterActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
            }
        });


        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog3.show();

            }
        });


        btn_Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });


        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounterActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap b =Screenshot.takeScreenShotOfRootView(ssView);
                ssView.setImageBitmap(b);
                relativeLayout.setBackgroundColor(Color.parseColor("#999999"));

                share();
            }

            private void share() {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                drawable[0] = (BitmapDrawable) ssView.getDrawable();
                bitmap[0] = drawable[0].getBitmap();
                File file = new File(getExternalCacheDir()+"/"+"My Goal"+".png");
                Intent intent;
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap[0].compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                }catch (Exception e){
                    throw new RuntimeException(e);

                }
                startActivity(Intent.createChooser(intent,"share via :"));

            }
        });



    }

    private void completedGoal() {

        Cursor cursor = (Cursor) myDB.getData(goalSet.getName(), goalSet.getStart());

        while (cursor.moveToNext()){
            name2 = cursor.getString(1);
            startdate2 = cursor.getString(2);
            enddate2 = cursor.getString(3);

        }

    }


    public void countDownStart() {

        fDate = (String) goal_Enddate.getText();

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 1000);
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
                        Date futureDate = dateFormat.parse(fDate);
                        Date currentDate = new Date();
                        if (!currentDate.after(futureDate)) {
                            long diff = futureDate.getTime() - currentDate.getTime();
                            long days = diff / (24 * 60 * 60 * 1000);

                            goal_Goaldays.setText("" + String.format("%02d", days));

                            if (days == 0)
                            {
                                dialog2.show();

                            }

                        } else {
                            Toast.makeText(CounterActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(runnable, 1 * 1000);
        }

}
