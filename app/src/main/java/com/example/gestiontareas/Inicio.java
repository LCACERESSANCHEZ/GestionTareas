package com.example.gestiontareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class Inicio extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //ocultamos el ActionBar
        getSupportActionBar().hide();
        videoView = (VideoView) findViewById(R.id.vv_Cargando);

        //codigo para ejecutar Splash screen, luego cargamos el MainActivity
        String path = "android.resource://" + getPackageName() + "/" + R.raw.vcomsatel;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplication(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}