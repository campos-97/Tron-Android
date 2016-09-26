package com.tec.datos1.tron.gui;

import android.os.Bundle;
import android.app.Activity;

import com.tec.datos1.tron.client.ClientTask;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        //ClientTask task = new ClientTask();
        //task.execute();
    }

}

