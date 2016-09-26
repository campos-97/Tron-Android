package com.tec.datos1.tron.gui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import com.tec.datos1.tron.R;

public class GameView extends SurfaceView {

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    public Context context;
    private Board gameBoard;
    private Game game;
    private long lastClick;

    protected float			x;
    protected float			y;
    protected float			xDiff;
    protected float			yDiff;

    public GameView(Context context) {
        super(context);
        this.context = context;
        this.gameLoopThread = new GameLoopThread(this);
        this.game = new Game(this, 20,context);              // The game will be 8x8 with 10 bombs
        this.game.start();
        holder = getHolder();

        holder.addCallback(new SurfaceHolder.Callback() {

            /*
             * If the game is closed, the view needs to be gracefully destroyed. This
             * means that the rendering thread must be stopped so it doesn't chew through
             * the device battery or throw errors while the game is closed
             *
             * @param    SurfaceHolder holder
             * @return   void
             */
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = false;
                gameLoopThread.setRunning(false);
                while(retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            /*
             * As soon as the drawable surface is created, activate the thread that will
             * be in charge of all rendering
             *
             * @param    SurfaceHolder holder
             * @return   void
             */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            /*
             * Not used in our code, but required to prevent errors.  Just detects changes in the
             * SurfaceView
             *
             * @param    SurfaceHolder holder
             * @param    int format
             * @param    int width
             * @param    int height
             * @return   void
             */
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

        });
    }

    /*
     * All sprite components to be drawn in the game.  This includes anything from HUD
     * components to board cells.  If it inherits the Sprite class, it should probably be
     * in this method
     *
     * @param    Canvas canvas
     * @return   void
     */
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        this.game.draw(canvas);
        this.game.gameBoard.draw(canvas);
    }


    /*
     * Detect click events on the canvas.  Time restrictions are placed on the click to prevent
     * like a million clicks from being registered if it is held too long.  In other words, it
     * is to prevent accidental clicks
     *
     * @param    MotionEvent event
     * @return   boolean clicked
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(System.currentTimeMillis() - lastClick > 500) {
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()) {
                this.game.registerTouch(event);
            }
            x = event.getX();
            y = event.getY();
        }else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            xDiff = event.getX() - x;
            yDiff = event.getY() - y;
            x = event.getX();
            y = event.getY();

            int g = gratistDiff(xDiff, yDiff);
            if (g == 0) {
                if (xDiff > 1 ) {
                    this.game.registeredSwipe("right");
                    Log.d("move", "right");
                }
                if (xDiff < -1) {
                    this.game.registeredSwipe("left");
                    Log.d("move", "left");
                }
            } else if (g == 1) {
                if (yDiff < -1) {
                    this.game.registeredSwipe("up");
                    Log.d("move", "up");
                }
                if (yDiff > 1) {
                    this.game.registeredSwipe("down");
                    Log.d("move", "down");
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