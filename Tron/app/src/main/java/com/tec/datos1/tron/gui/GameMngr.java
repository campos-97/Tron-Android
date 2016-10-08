package com.tec.datos1.tron.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.tec.datos1.tron.R;
import com.tec.datos1.tron.client.ClientTask;
import com.tec.datos1.tron.client.clientThread;
import com.tec.datos1.tron.linkedList.Node;
import com.tec.datos1.tron.linkedList.StackLinkedList;

/**
 * @author Andres Campos
 */
public class GameMngr {


    private Context context;

    private ClientTask task;
    GL_Renderer renderer;

    public StackLinkedList<String> powerUps = new StackLinkedList<>();
    public ImageView firstPowerUp;
    public ImageView secondPowerUp;
    public ImageView thirdPowerUp;

    private clientThread client;
    public int fuel = 100;

    /**
     * The constructor starts the input menu to for the user.
     * @param context
     */
    public GameMngr(Context context) {
        this.context = context;
        task = new ClientTask();
        createDialog(context);
        //this.renderer = renderer;

        client = new clientThread();
        client.gameMngr = this;

    }

    /**
     * Takes the orientation of the swipe and send it to the ClientTask.
     * @param orientation
     */
    public void registeredSwipe(String orientation){
        client.changeOrientation(orientation);
        Log.d("swipe", "hice un swipe "+orientation);
    }

    /**
     * This is the input menu.
     * @param context
     */
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
                        client.name = userName.getText().toString();
                        client.serverAddress = ip.getText().toString();
                        String p = userPort.getText().toString();
                        client.port = Integer.valueOf(p);
                        client.port = Integer.valueOf(p);
                        client.renderer = renderer;
                        //task.execute();
                        //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        client.start();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(1);
                    }
                });
        builder.show();
    }

    public void addPowerUps(String id){
        powerUps.push(id);
        //updateHUD();
    }

    public void hudEvents(String event){
        switch (event){
            case "zoom":
                if(renderer.zoom == 1){
                    renderer.zoom = 2;
                }else if(renderer.zoom == 2){
                    renderer.zoom = 1;
                }
                break;
            case "powerUp":
                String powerUp = powerUps.pop().getData().toLowerCase();
                client.usePowerUp(powerUp);
                updateHUD();
                break;
            case "switch":
                Node<String> temp = powerUps.pop();
                powerUps.addLast(temp);
                updateHUD();
                break;
        }
    }

    private void updateHUD() {
        for (int i = 0; i < 3; i++) {
            ImageView view = thirdPowerUp;
            if(i == 0){
                view = firstPowerUp;
            }else if(i == 1){
                view = secondPowerUp;
            }else if(i == 2){
                view = thirdPowerUp;
            }
            if (powerUps.getSize()>i) {
                Log.d("HUD", String.valueOf(powerUps.getSize()));
                if(powerUps.getByIndex(i).getData().startsWith("shield")){
                    view.setBackgroundResource(R.drawable.escudo);
                }else if(powerUps.getByIndex(i).getData().startsWith("speed")){
                    view.setBackgroundResource(R.drawable.rayo);
                }
            }
        }
    }
}
