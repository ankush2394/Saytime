package com.example.admin.alarm;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ankush on 4/9/2016.
 */
public class MyBroadcastReceiver extends Service implements TextToSpeech.OnInitListener {
    public TextToSpeech tts;
    String str;
    public static Boolean run=false;
    int t6;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        run = false;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        Toast.makeText(this,"service stopped",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //Log.d("kl","ji");
        if(run==false)
        {
            run =true;
            }
        tts = new TextToSpeech(this, this);
        t6 =intent.getExtras().getInt("Time");
        str= String.valueOf(t6);

        speakOut();



    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }

                speakOut();

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut()  {
        Calendar c = Calendar.getInstance();

        String Hr24=String.valueOf(c.get(Calendar.HOUR_OF_DAY)%12);
        String Min=String.valueOf(c.get(Calendar.MINUTE));

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh::mm::ss");
        //Date date = new Date();
        //String str1= simpleDateFormat.format(date);
        // Log.d("yep","go");
        tts.speak(str+" minutes gone."+"Current time is "+Hr24 +"past"+Min+"minutes", TextToSpeech.QUEUE_FLUSH, null);

    }

    public Boolean tell()
    {
        if(run==false)
     return false;
        else return true;

    }
}



