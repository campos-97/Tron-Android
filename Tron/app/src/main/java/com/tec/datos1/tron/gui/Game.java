package com.tec.datos1.tron.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tec.datos1.tron.R;
import com.tec.datos1.tron.client.ClientTask;

public class Game {

    private GameView gameView;
    public Board gameBoard;
    private Bitmap hudSpriteSheet;
    private int boardSize;
    private int bombCount;
    private boolean isGameOver;
    private int score;
    public HUD[] hud;

    private Context context;
    private Dialog dialog;


    private ClientTask task;

    //ClientTask  task;

    /*
     * Initialize various game components in the constructor.  Record the size of the board,
     * how many bombs must exist on it, and also prepare various HUD components.
     *
     * @param    GameView gameView
     * @param    int boardSize
     * @param    int bombCount
     */
    public Game(GameView gameView, int boardSize,Context context) {
        this.context = context;
        this.gameView = gameView;
        this.boardSize = boardSize;
        this.gameBoard = new Board(gameView, boardSize);
        this.hud = new HUD[3];
        this.hudSpriteSheet = BitmapFactory.decodeResource(this.gameView.context.getResources(), R.drawable.hud_spritesheet_md);
        this.hud[0] = new HUD(this.gameView, this.hudSpriteSheet, 0, 0);
        this.hud[1] = new HUD(this.gameView, this.hudSpriteSheet, 240, 0);
        this.hud[2] = new HUD(this.gameView, this.hudSpriteSheet, 100, 60);

        task = new ClientTask();

    }

    /*
     * Start a new game by resetting the board and clearing the game over indicator
     *
     * @param
     * @return   void
     */
    public void start() {
        createDialog(context);
        this.isGameOver = false;
        this.score = 0;
        this.gameBoard.reset();
    }

    /*
     * Game over has ocurred because we hit a bomb.  Show all bombs that were remaining and set
     * the game over indicator to true so that way further cells cannot be unlocked
     *
     * @param
     * @return   void
     */
    public void gameOver() {
        this.isGameOver = true;
        Toast.makeText(this.gameView.context, "Game over!", Toast.LENGTH_LONG).show();
    }

    /*
     * The game was completed without triggering a bomb.  Lock the game and present a victory message
     *
     * @param
     * @return   void
     */
    public void gameFinished() {
        this.isGameOver = true;
        Toast.makeText(this.gameView.context, "You've beat the game!", Toast.LENGTH_LONG).show();
    }

    /*
     * Draw the two HUD components to the screen.  The two components being a new game
     * and cheat button in a sprite sheet
     *
     * @param    Canvas canvas
     * @return   void
     */
    public void draw(Canvas canvas) {
        this.hud[0].onDraw(canvas, 0, 0);
        this.hud[1].onDraw(canvas, 0, 1);
        this.hud[2].onDraw(canvas, 0, 2);
    }

    public void registerTouch(MotionEvent event) {
        if (this.hud[0].hasCollided(event.getX(), event.getY())) {
            task.board = this.gameBoard;
            task.execute();
        }
        if (this.hud[1].hasCollided(event.getX(), event.getY())) {
            gameBoard.move();
        }
        if (this.hud[2].hasCollided(event.getX(), event.getY())) {
            gameBoard.move();
        }
    }

    public void registeredSwipe(String orientation){
        task.changeOrientation(orientation);
    }

    public void createDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_signin, null);

        // Set up the input
        final EditText userName = (EditText) dialogView
                .findViewById(R.id.username);
        final EditText userPort = (EditText) dialogView
                .findViewById(R.id.port);
        final EditText ip = (EditText) dialogView
                .findViewById(R.id.ip);
        //setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        task.name = userName.getText().toString();
                        task.serverAddress = ip.getText().toString();
                        String p = userPort.getText().toString();
                        task.port = Integer.valueOf(p);
                        task.port = Integer.valueOf(p);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //End Game
                    }
                });
        builder.show();
    }
}
