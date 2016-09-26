package com.tec.datos1.tron.client;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.tec.datos1.tron.R;
import com.tec.datos1.tron.gui.Board;
import com.tec.datos1.tron.linkedLists.GridNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTask extends AsyncTask<Void, Integer, Void> {

    public static int port;
    public static String serverAddress;
    public static String name;
    private boolean readyFlag;

    public static Board board;

    BufferedReader in;
    static PrintWriter out;

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("server", "Client running " + this.serverAddress + this.name + this.port);
        try {
            Socket socket = new Socket(serverAddress, port);
            readyFlag = true;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            Log.d("server", "sobon");
            e.printStackTrace();
        }

        while (readyFlag) {
            Log.d("server", "Connected to Server!");
            try {
                String input = in.readLine();
                if (input == null) {
                    Log.d("server", "Line = null");
                    return null;
                } else {
                    Log.d("server", "Linea no null");
                    ClientStringBrain.think(MessageSerial.getInfoFromJsonString(input), this);
                }
            /*else if (line.startsWith("name")) {
                out.println(getName());
            }
            else if (line.startsWith("something")) {
                String command = line.substring(9);
                messageArea.append(command + "\n");
            }
            else if (line.startsWith("wait")){
                System.out.print("Fuck you");
            }*/
            } catch (Exception e) {
                Log.d("server", "Flag es false por que la cagaste!");
                readyFlag = false;
                e.printStackTrace();
            }
        }
        Log.d("server", "voy jalao");
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("hola", "onProgressUpdate: ");
    }

    public static void update(int x, int y){
        board.xCoord = x;
        board.yCoord = y;
    }

    public void changeOrientation(String orientation){
        NetMessage delivery = new NetMessage(null, null, 0, 0, "");
        delivery.setKind("direction");
        delivery.setInfo(orientation);
        try {
            out.println(MessageSerial.getJsonStringFromMessage(delivery));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}