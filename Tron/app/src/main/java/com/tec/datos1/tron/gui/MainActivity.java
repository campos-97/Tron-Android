package com.tec.datos1.tron.gui;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tec.datos1.tron.R;

import java.io.InputStream;

public class MainActivity extends Activity {


    private Button button_escudo;
    private Button button_velocidad;
    private Button button_zoom;
    private String prev = "zoomIn";



    private GLSurfaceView glView;
    private GameMngr game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new GameMngr(this);
        setContentView(R.layout.main);
        glView = new MyGLSurfaceView(this,game);
        LinearLayout openGLLayout = (LinearLayout) findViewById(R.id.openGLLayout);
        openGLLayout.addView(glView);


        game.firstPowerUp = (ImageView) findViewById(R.id.FirstPowerUp);
        game.secondPowerUp = (ImageView) findViewById(R.id.SecondPowerUp);
        game.thirdPowerUp = (ImageView) findViewById(R.id.ThirdPowerUp);


        /**
         * Button Switch powerUps creation.
         */
        button_escudo = (Button) findViewById(R.id.button1);
        button_escudo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(game.powerUps.getHead() != null){
                    Log.d("hud", "Switch");
                    game.hudEvents("switch");
                }
            }
        });

        /**
         * Button use powerUp  creation
         */
        button_velocidad = (Button) findViewById(R.id.button2);
        button_velocidad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(game.powerUps.getHead() != null){
                    Log.d("hud", "Choose");
                    game.hudEvents("powerUp");
                }
            }
        });

        /**
         * Button screen zoom creation.
         */
        button_zoom = (Button) findViewById(R.id.button6);
        button_zoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                game.hudEvents("zoom");
                if(prev.startsWith("zoomIn")){
                    button_zoom.setBackgroundResource(R.drawable.zoom_out);
                    prev = "zoomOut";
                }else{
                    button_zoom.setBackgroundResource(R.drawable.zoom_in);
                    prev = "zoomIn";
                }

            }
        });
    }
}

