package com.tec.datos1.tron.client;

import android.util.Log;

import com.tec.datos1.tron.linkedLists.GridNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client{

    private Socket socket;
    public static final  int serverPort = 9898;
    public static final String serverIp = "192.168.1.22";
    private static final String name = "Andres";

    BufferedReader in;
    PrintWriter out;
    GridNode principalNode;

    public void run() {
        try {
            socket = new Socket(serverIp, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.exit(1);
        }

    }
}
