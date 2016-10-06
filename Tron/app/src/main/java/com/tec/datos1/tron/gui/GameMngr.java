package com.tec.datos1.tron.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tec.datos1.tron.R;
import com.tec.datos1.tron.client.ClientTask;

/**
 * @author Andres Campos
 */
public class GameMngr {


    private Context context;

    private ClientTask task;
    GL_Renderer renderer;

    /**
     * The constructor starts the input menu to for the user.
     * @param context
     */
    public GameMngr(Context context, GL_Renderer renderer) {
        this.context = context;
        task = new ClientTask();
        createDialog(context);
        this.renderer = renderer;

    }

    /**
     * Takes the orientation of the swipe and send it to the ClientTask.
     * @param orientation
     */
    public void registeredSwipe(String orientation){
        task.changeOrientation(orientation);
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
                        task.name = userName.getText().toString();
                        task.serverAddress = ip.getText().toString();
                        String p = userPort.getText().toString();
                        task.port = Integer.valueOf(p);
                        task.port = Integer.valueOf(p);
                        task.renderer = renderer;
                        task.execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //End Game
                    }
                });
        builder.show();
    }

    private float prevZoom = 1;
    public void hudEvents(String event){
        switch (event){
            case "zoom":
                if(prevZoom == 1){
                    renderer.zoom = 2;
                    prevZoom = 2;
                }else if(prevZoom == 2){
                    renderer.zoom = 1;
                    prevZoom = 1;
                }
                break;
        }
    }
}
