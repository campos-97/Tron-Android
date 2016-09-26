package com.tec.datos1.tron.client;

import android.util.Log;

import com.tec.datos1.tron.gui.Board;

public class ClientStringBrain {

	public static void think(NetMessage message,ClientTask client) {
		if(message.getKind().startsWith("state")){
			if(message.getInfo().startsWith("name")){
				ClientTask.out.println(client.name);
			}
			else if(message.getInfo().startsWith("newname")){
				ClientTask.out.println(client.name);
			}
			else if(message.getInfo().startsWith("ready")){
			}
			else if(message.getInfo().startsWith("wait")){
			}
			else{
				Log.d("GAME", "GAME OVER");
			}
		}else if(message.getKind().startsWith("cordenate")){
			if(message.getColor().startsWith("head")){
                client.update(message.getX(),message.getY());

			}
		}
	}
	
}
