package com.tec.datos1.tron.Menu;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.tec.datos1.tron.R;
import com.tec.datos1.tron.gui.MainActivity;
import static com.tec.datos1.tron.R.layout.activity_main_menu;

public class MainMenu extends AppCompatActivity {

    private Button button3;
    private Button button2;
    private Button button1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main_menu);

        final MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this,R.raw.electro);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();


        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent button2 = new Intent(MainMenu.this, acerca.class);
                mediaPlayer.stop();
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

    }
}
