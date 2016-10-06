package com.tec.datos1.tron.gui;

import android.content.Intent;
import android.nfc.Tag;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tec.datos1.tron.R;

public class MainActivity extends Activity {

    public int velocidad = 5;
    public int escudo = 5;
    private Button button_escudo;
    private Button button_velocidad;

    TextView text_notificacion1;
    TextView text_notificacion2;

    private GLSurfaceView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        glView = new MyGLSurfaceView(this);
        LinearLayout openGLLayout = (LinearLayout) findViewById(R.id.openGLLayout);
        openGLLayout.addView(glView);


        //CREACION DEL BOTON DEL ESCUDO
        button_escudo = (Button) findViewById(R.id.button1);
        button_escudo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (escudo>0) {
                    escudo--;
                    text_notificacion1 = (TextView) findViewById(R.id.badge1);
                    text_notificacion1.setText(String.valueOf(escudo));
                }else{
                    text_notificacion1 = (TextView) findViewById(R.id.badge1);
                    text_notificacion1.setText(String.valueOf(escudo));

                }
            }
        });

        //CREACION DEL BOTON VELOCIDAD
        button_velocidad = (Button) findViewById(R.id.button2);
        button_velocidad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (velocidad>0) {
                    velocidad--;
                    text_notificacion2 = (TextView) findViewById(R.id.badge2);
                    text_notificacion2.setText(String.valueOf(velocidad));
                }else{
                    text_notificacion2 = (TextView) findViewById(R.id.badge2);
                    text_notificacion2.setText(String.valueOf(velocidad));
                }
            }
        });
    }
}

