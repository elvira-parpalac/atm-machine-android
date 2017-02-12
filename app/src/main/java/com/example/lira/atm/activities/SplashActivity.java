package com.example.lira.atm.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.lira.atm.R;
import com.example.lira.atm.utils.Serialization;
import com.example.lira.atm.models.Account;


public class SplashActivity extends Activity {

    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView textView = (TextView) findViewById(R.id.text1);

        // set custom font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/android.ttf");
        textView.setTypeface(typeface);

        // get the serialized object from the sdcard and caste it into the Person class.
        Account serializedObject = (Account) Serialization.loadSerializedObject("/sdcard/save_object.bin");
        Account.setInstance(serializedObject);

        int SPLASH_TIME_OUT = 1500;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), OptionActivity.class));
                finish();
            }
        };

        handler.postDelayed(runnable, SPLASH_TIME_OUT);

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}
