package com.tec.datos1.tron.linkedLists;
import java.util.Random;
public class allRandom extends Random{
	public static boolean getBoolean(int percentage){
		Random randomizer = new Random();
		int num = randomizer.nextInt(100);
		if (num < percentage){
			return true;
		}
		else{
			return false;
		}
		
	}
	public static int getRandomInt(int max){
		Random randomizer = new Random();
		int num = randomizer.nextInt(max);
		return num;
	}
	public static String getRandomCardinal(){
		Random randomizer = new Random();
		int toWatch = randomizer.nextInt(3);
		switch(toWatch){
		case 0:
			return "up";
		case 1:
			return "down";
		case 2:
			return "right";
		default:
			return "left";
		}
	}
	
	private static final long serialVersionUID = 1L;

}
