package com.tec.datos1.tron.gui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;


/**
 * @author Andres Campos
 */
public class MyGLSurfaceView extends GLSurfaceView{
    GL_Renderer renderer;    // Custom GL Renderer
    GameMngr game;



    /**
     * Constructor, allocates the renderer
     * Sets OpenGL|ES 2.0
     * Creates a new game manager
     * @param context
     */
    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        renderer = new GL_Renderer(context);
        this.setRenderer(renderer);
        // Request focus, otherwise key/button won't react
        this.requestFocus();
        this.setFocusableInTouchMode(true);

        game =  new GameMngr(context,renderer);

    }

    /**
     * These variables area needed for the touch event
     * to detect a swipe.
     */
    private float x = 0;
    private float y = 0;
    private float dx = 0;
    private float dy = 0;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = e.getRawX();
                y = e.getRawY();
            case MotionEvent.ACTION_MOVE:

                dx = e.getRawX()-x;
                dy = e.getRawY()-y;

                int g = gratistDiff(dx, dy);
                if (g == 0) {
                    if (dx > 1 ) {
                        game.registeredSwipe("right");
                        //renderer.movex -= 0.1;
                        Log.d("move", "right");
                    }
                    if (dx < -1) {
                        game.registeredSwipe("left");

                        //renderer.movex += 0.1;
                        Log.d("move", "left");
                    }
                } else if (g == 1) {
                    if (dy < -1) {
                        game.registeredSwipe("up");
                        //renderer.movey += 0.1;
                        Log.d("move", "up");
                    }
                    if (dy > 1) {
                        game.registeredSwipe("down");
                        //renderer.movey -= 0.1;
                        Log.d("move", "down");
                    }
                }
        }
        return true;
    }

    /**
     * Helps with the swipe event.
     * @param x
     * @param y
     * @return
     */
    protected int gratistDiff(float x, float y) {
        if (x < 0) x = -x;
        if (y < 0) y = -y;

        if (x > y) return 0;
        if (y > x) return 1;
        return -1;
    }

    private float prevZoom = 1;
    public void hudEvents(String event){
        switch (event){
            case "zoom":
                if(prevZoom == 1){
                    renderer.zoom = 2;
                }else if(prevZoom == 2){
                    renderer.zoom = 1;
                }
                break;
        }
    }

}