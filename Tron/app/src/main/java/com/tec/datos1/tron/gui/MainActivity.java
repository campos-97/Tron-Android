package com.tec.datos1.tron.gui;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {
    private GLSurfaceView glView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new GameView(this));

        glView = new MyGLSurfaceView(this);
        setContentView(glView);
    }

}

