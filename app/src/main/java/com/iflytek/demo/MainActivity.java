package com.iflytek.demo;


import static android.os.SystemClock.sleep;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;

    private TextView txtText;

    static boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtText = (TextView) findViewById(R.id.txtText);
        ImageButton btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(v -> {

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

            try {
                startActivityForResult(intent, RESULT_SPEECH);
                txtText.setText("");
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(), "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
            }

//            new Thread(() -> {
//                sleep(1000);
//                MediaManager mediaManager = new MediaManager(getApplicationContext(), R.raw.sound);
//            }).start();
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SPEECH) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                txtText.setText(text.get(0));
            }
        }
    }
}