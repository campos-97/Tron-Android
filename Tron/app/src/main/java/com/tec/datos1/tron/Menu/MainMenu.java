package com.tec.datos1.tron.Menu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tec.datos1.tron.R;
import com.tec.datos1.tron.gui.MainActivity;

public class MainMenu extends AppCompatActivity {

    private Button button3;
    private Button button2;
    private Button button1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent button2 = new Intent(MainMenu.this, acerca.class);
                startActivity(button2);
            }
        });

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent button1 = new Intent(MainMenu.this, MainActivity.class);
                startActivity(button1);
            }
        });

        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.pista1);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();
        mediaPlayer.stop();

        //MusicPlayer musica = new MusicPlayer(this);
        //musica.execute();
    }
}
