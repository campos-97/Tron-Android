package com.tec.datos1.tron.client;

import android.os.AsyncTask;
import android.util.Log;

import com.tec.datos1.tron.gui.GL_Renderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Andres Campos
 * @author Roberto Gutierrez
 */
public class ClientTask extends AsyncTask<Void, Integer, Void> {

    public static int port;
    public static String serverAddress;
    public static String name;
    private boolean readyFlag = true;

    public static GL_Renderer renderer;

    BufferedReader in;
    static PrintWriter out;

    /**
     * Does listens the server in the background.
     * @param voids
     * @return
     */
    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("server", "Client running " + this.serverAddress + this.name + this.port);
        try{
            Socket socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(Exception e){
            Log.d("server", "could not create socket!");
            e.printStackTrace();
        }

        while (readyFlag) {
            try{
                String input = in.readLine();
                /**if (input == null){
                    continue;
                }*/

                if (input != null) {
                    ClientStringBrain.think(MessageSerial.getInfoFromJsonString(input),this);
                }

            }
            catch(Exception e){
                readyFlag = false;
                e.printStackTrace();
            }
        }
        Log.d("server", "voy jalao");
        return null;
    }

    /**
     * Not used, but included due to the use of AsyncTask.
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
    }

    /**
     *Updates the position of the players.
     * @param id
     * @param x
     * @param y
     */
    public static void update(String id,int x, int y){
        renderer.updatePlayers(id,x,y);
    }

    /**
     * Creates the main player.
     * @param id
     */
    public static void createMyPlayer(String id){
        Log.d("game", id);
        renderer.addPlayer(id,true);
    }

    /**
     * Sends the server the new orientation of the player.
     * @param orientation
     */
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

    public void addItem(String item,int x, int y){
        renderer.addItem(item,x,y);
    }

    public void removeItem(String item, int x, int y){
        renderer.removeItem(item,x,y);
    }

    public void kill(String id){
        renderer.destroyPlayer(id);
    }

    public void addTrail(String id){
        renderer.addTrail(id);
    }
}