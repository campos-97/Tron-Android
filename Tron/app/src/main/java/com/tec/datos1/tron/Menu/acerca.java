package com.tec.datos1.tron.Menu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.tec.datos1.tron.R;

import static com.tec.datos1.tron.R.layout.activity_acerca;


public class acerca extends AppCompatActivity {

    private Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_acerca);

        final MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.pista1);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent button = new Intent(acerca.this, MainMenu.class);
                mediaPlayer.stop();
                startActivity(button);
            }
        });
    }
}
