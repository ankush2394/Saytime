package com.example.admin.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Scanner;

public class MainActivity extends Activity  {

    Button b1, b2;
    EditText text;
    static int i;
    String si;
    TextWatcher watchl;
    SharedPreferences sharedpreferences;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    String str;
    MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        b1 = (Button) findViewById(R.id.button1);
        text = (EditText) findViewById(R.id.time);
        b2 = (Button) findViewById(R.id.button);
        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);


        /*text.addTextChangedListener(watch);/* {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                copy(s.toString());
            }
        });*/
        final Context context = this;
        //sharedPreferences = getSharedPreferences("lo", Context.MODE_PRIVATE);
        //final Scanner scanner= new Scanner(System.in);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int k = 1;


                si = text.getText().toString();

                /*
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Time", si);
                editor.commit();
                  */

                if (si.isEmpty())
                    Toast.makeText(context, "enter first to start your service ", Toast.LENGTH_SHORT).show();
                else {
                    if (myBroadcastReceiver.tell() == true)
                        Toast.makeText(context, "service already started stop to start again", Toast.LENGTH_SHORT).show();
                    else {
                        si = text.getText().toString();
                        Toast.makeText(context, "service  started see you in again in "+si+"minutes", Toast.LENGTH_SHORT).show();

                    /*
                    editor = sharedpreferences.edit();
                    editor.putString("Time", si);
                    editor.commit();
                      */

                        i = Integer.parseInt(si);
                        Intent intent = new Intent(context, MyBroadcastReceiver.class);
                        intent.putExtra("Time", i);
                        pendingIntent = PendingIntent.getService(context, 23434567, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        if (k == 1) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 60 * 1000), (i * 60 * 1000), pendingIntent);
                            k++;
                        } else {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (i * 60 * 1000), pendingIntent);
                        }
                    }
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si = text.getText().toString();

                if(myBroadcastReceiver.tell()==false)
                    Toast.makeText(context,"Start service first to stop the service.",Toast.LENGTH_SHORT).show();
                else {
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(context, "Service stopped", Toast.LENGTH_SHORT).show();
                    myBroadcastReceiver.run=false;
                }
            }
        });

    }







   /* public void copy(String str8)
    {si=new String(str8);}

    TextWatcher watch = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            copy(s.toString());
        }
    };
    */

}