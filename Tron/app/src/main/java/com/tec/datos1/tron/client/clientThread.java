package com.tec.datos1.tron.client;

import android.util.Log;

import com.tec.datos1.tron.gui.GL_Renderer;
import com.tec.datos1.tron.gui.GameMngr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by josea on 10/6/2016.
 */
public class clientThread extends Thread {

    public static int port;
    public static String serverAddress;
    public static String name;
    private boolean readyFlag = true;

    public static GL_Renderer renderer;
    public GameMngr gameMngr;

    BufferedReader in;
    static PrintWriter out;

    public void run() {

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

    public void addTrail(String id, int increment){
        renderer.addTrail(id, increment);
    }

    public void updatePowerUps(String id){

        gameMngr.addPowerUps(id);
        //gameMngr.powerUps.push(id);
    }

    public void usePowerUp(String id){
        NetMessage delivery = new NetMessage(null, null, 0, 0, "");
        delivery.setKind("power");
        delivery.setInfo(id);
        try {
            out.println(MessageSerial.getJsonStringFromMessage(delivery));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateShield(String id){
        renderer.updateShield(id);
    }
}
