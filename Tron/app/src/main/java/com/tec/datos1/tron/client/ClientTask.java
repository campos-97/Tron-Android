package com.tec.datos1.tron.client;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.Socket;

public class ClientTask extends AsyncTask<Void, Integer, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket("192.168.1.22", 8080);
            InputStream is = socket.getInputStream();

            byte[] buffer = new byte[25];
            int read = is.read(buffer);
            while(read != -1){
                publishProgress(read);
                read = is.read(buffer);
            }

            is.close();
            socket.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("hola", "onProgressUpdate: ");
    }

}