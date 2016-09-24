package com.tec.datos1.tron.client;

import android.os.AsyncTask;
import android.util.Log;

import com.tec.datos1.tron.linkedLists.GridNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTask extends AsyncTask<Void, Integer, Void> {

    private Socket socket;
    public static final  int port = 9898;
    public static final String serverAddress = "192.168.1.22";
    private static final String name = "Andres";
    private boolean readyFlag;

    BufferedReader in;
    PrintWriter out;
    GridNode principalNode;

    @Override
    protected Void doInBackground(Void... voids) {
        try{
            Socket socket = new Socket(serverAddress, port);
            readyFlag = true;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        while (readyFlag) {
            try {
                String line = in.readLine();
                if (line == null) {
                    continue;
                } else if (line.startsWith("name")) {
                    out.println(this.name);
                }
            }catch (IOException e){
                    System.exit(1);
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("hola", "onProgressUpdate: ");
    }

}