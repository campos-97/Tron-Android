package com.tec.datos1.tron.gui;


import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class SwipeListener{
    public Context context;

    protected float			x;
    protected float			y;
    protected float			xDiff;
    protected float			yDiff;

    public SwipeListener(Context context, SurfaceHolder holder) {
        this.context = context;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            xDiff = event.getX() - x;
            yDiff = event.getY() - y;
            x = event.getX();
            y = event.getY();

            int g = gratistDiff(xDiff, yDiff);
            if (g == 0) {
                if (xDiff > 1 ) {
                    Log.d("hola", String.valueOf(xDiff));
                }
                if (xDiff < -1) {
                    Log.d("hola", String.valueOf(xDiff));
                }
            } else if (g == 1) {
                if (yDiff < -1) {
                    Log.d("hola", String.valueOf(xDiff));
                }
                if (yDiff > 1) {
                    Log.d("hola", String.valueOf(xDiff));
                }
            }
        }
        return true;
    }
    protected int gratistDiff(float x, float y) {
        if (x < 0) x = -x;
        if (y < 0) y = -y;

        if (x > y) return 0;
        if (y > x) return 1;
        return -1;
    }
}
