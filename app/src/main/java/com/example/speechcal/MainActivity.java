package com.example.speechcal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton i;
    EditText e;
    TextView t,t2,t3;

    SpeechRecognizer speechRecognizer;
    TextToSpeech tts;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e = findViewById(R.id.edittext);
        i = findViewById(R.id.image);
        t = findViewById(R.id.textView6);
        t2= findViewById(R.id.textView7);
        t3= findViewById(R.id.textView5);




        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(1.0f);
                    tts.speak(t3.getText().toString(), TextToSpeech.QUEUE_ADD,null);
                }
            }
        });

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count==0)
                {
                    i.setImageDrawable(getDrawable(R.drawable.ic_mic));

                    //start listening
                    speechRecognizer.startListening(speechRecognizerIntent);
                    count = 1;

                }
            }
        });


        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                e.setText(data.get(0));
                qresult(e);

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

    }

    public void qresult(EditText e){

        String r =  e.getText().toString();
        if("4".equals(r))
        {
            Toast.makeText(this, "Right answer", Toast.LENGTH_SHORT).show();
            t.setText("Answer : 4 is Right answer");
            t2.setText("Great!!" );
            i.setImageDrawable(getDrawable(R.drawable.ic_mic_off));
            speechRecognizer.stopListening();
            count = 0;

        }
        else {
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
            t.setText("Answer :  Wrong answer");
            t2.setText("try again" );
            i.setImageDrawable(getDrawable(R.drawable.ic_mic_off));
            speechRecognizer.stopListening();
            count = 0;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}