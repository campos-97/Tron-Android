package com.tec.datos1.tron.client;

/**
 * @author Roberto Gutierrez
 */
public class NetMessage {
	
	private String kind;
	private String info;
	private int x;
	private int y;
	private String color;


	/**
	 * Constructor.
	 * @param kind
	 * @param info
	 * @param x
	 * @param y
     * @param color
     */
	public NetMessage(String kind, String info, int x, int y, String color) {
		this.kind = kind;
		this.info = info;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public NetMessage(){
		
	}

	/**
	 * Returns x position.
	 * @return
     */
	public int getX() {
		return x;
	}

	/**
	 * Sets x position.
	 * @param x
     */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * returns y position
	 * @return
     */
	public int getY() {
		return y;
	}

	/**
	 *
	 * @param y
     */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns color.
	 * @return
     */
	public String getColor() {
		return color;
	}

	/**
	 * Set color.
	 * @param color
     */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Returns info.
	 * @return
     */
	public synchronized String getInfo() {
		return info;
	}

	/**
	 * Sets info.
	 * @param info
     */
	public synchronized void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Returns Kind
	 * @return
     */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets kind
	 * @param kind
     */
	public void setKind(String kind) {
		this.kind = kind;
	}
	
}
