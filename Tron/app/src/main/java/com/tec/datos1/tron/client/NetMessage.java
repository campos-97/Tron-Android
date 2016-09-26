package com.tec.datos1.tron.client;

public class NetMessage {
	
	private String kind;
	private String info;
	private int x;
	private int y;
	private String color;
	
	
	
	public NetMessage(String kind, String info, int x, int y, String color) {
		this.kind = kind;
		this.info = info;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public NetMessage(){
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public synchronized String getInfo() {
		return info;
	}

	public synchronized void setInfo(String info) {
		this.info = info;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
}
