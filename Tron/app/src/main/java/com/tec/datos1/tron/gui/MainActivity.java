package com.tec.datos1.tron.gui;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.widget.LinearLayout;

import com.tec.datos1.tron.R;

public class MainActivity extends Activity {
    private GLSurfaceView glView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new GameView(this));

        setContentView(R.layout.main);
        glView = new MyGLSurfaceView(this);
        LinearLayout openGLLayout = (LinearLayout)findViewById(R.id.openGLLayout);
        openGLLayout.addView(glView);

    }

}

