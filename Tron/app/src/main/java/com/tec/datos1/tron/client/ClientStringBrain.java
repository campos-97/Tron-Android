package com.tec.datos1.tron.client;

import android.util.Log;

import java.io.IOException;

/**
 * @author Roberto Gutierrez
 */
public class ClientStringBrain {
	/**
	 * Manages the inputs and outputs of the server.
	 * @param message
	 * @param client
     */
	public static void think(NetMessage message, ClientTask client) {

		switch (message.getKind()){
			case "state":
				if(message.getInfo().startsWith("name")){
					client.out.println(client.name);
				}
				else if(message.getInfo().startsWith("newname")){
					client.out.println(client.name);
				}
				else if (message.getInfo().startsWith("wait")){
					//Agregado.
				}
				else if(message.getInfo().startsWith("ready")){
					Log.d("game", "READY !!!");
					try {
						client.out.println(MessageSerial.getJsonStringFromMessage(new NetMessage
								("state", "ready", 0, 0, null)));
					} catch (IOException e) {
						e.printStackTrace();
					}
					client.createMyPlayer(message.getColor());
				}
				else if(message.getInfo().startsWith("wait")){
				}
				else if(message.getInfo().startsWith("end")){
					System.exit(1);
				}else if(message.getInfo().startsWith("kill")){
					Log.d("kill", "kill!!!!!!");
					client.kill(message.getColor());
				}else if(message.getInfo().startsWith("trail")){
					client.addTrail(message.getColor(), message.getX());
				}
				break;
			case "cordenate":
				switch (message.getInfo()){
					case "head":
                        Log.d("swipe", "nueva ubicacion ");
                        client.update(message.getColor(), message.getX(), message.getY());
						break;
					case "bomb":
						client.addItem("bomb",message.getX(),message.getY());
						break;
					case "fuel":
						client.addItem("fuel",message.getX(),message.getY());
						break;
					case "speed":
						client.addItem("turbo",message.getX(),message.getY());
						break;
					case "trail":
						client.addItem("trail",message.getX(),message.getY());
						break;
					case "shield":
						client.addItem("shield",message.getX(),message.getY());
						break;
					case "bombed":
						client.removeItem("bomb",message.getX(),message.getY());
						break;
					case "fueled":
						client.removeItem("fuel",message.getX(),message.getY());
						break;
					case "trailed":
						client.removeItem("trail",message.getX(),message.getY());
						break;
					case "speeded":
						client.removeItem("turbo",message.getX(),message.getY());
						break;
					case "shielded":
						client.removeItem("shield",message.getX(),message.getY());
						break;
				}
				break;
			case "hud":
				switch (message.getInfo()){
					case "power":
						if (message.getColor().equals("shield")){

						}else if (message.getColor().equals("speed")){

						}
						break;
					case "shield":
				}
				break;

		}
	}
}
