package com.tec.datos1.tron.linkedLists;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
@JsonIdentityInfo(generator = com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator.class,property="@id")
public class GridNode {
	
	private boolean hasMoto;
	private boolean hasTrail;
	private boolean hasSomething;
	public GridNode next;
	public GridNode previous;
	public GridNode upper;
	public GridNode lower;
	private int data;
	public int x;
	public int y;
	
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
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public void setHasSomething(boolean hasSomething) {
		this.hasSomething = hasSomething;
	}
	public boolean hasMoto() {
		return hasMoto;
	}
	public void setHasMoto(boolean hasMoto) {
		this.hasMoto = hasMoto;
	}
	public boolean hasTrail() {
		return hasTrail;
	}
	public void setHasTrail(boolean hasTrail) {
		this.hasTrail = hasTrail;
	}
	public boolean hasSomething() {
		return hasSomething;
	}
	public void setHasBlink1(boolean hasSomething) {
		this.hasSomething = hasSomething;
	}

	public GridNode getNext() {
		return next;
	}
	public void setNext(GridNode next) {
		this.next = next;
	}
	public GridNode getPrevious() {
		return previous;
	}
	public void setPrevious(GridNode previous) {
		this.previous = previous;
	}
	public GridNode getUpper() {
		return upper;
	}
	public void setUpper(GridNode upper) {
		this.upper = upper;
	}
	public GridNode getLower() {
		return lower;
	}
	public void setLower(GridNode lower) {
		this.lower = lower;
	}
	
	public GridNode getCardinal(String dir){
		switch(dir){
		case "up": return this.getUpper();
		case "down": return this.getLower();
		case "left": return this.getPrevious();
		case "right": return this.getNext();
		default: return null;
		}	
	}
	
	public static GridNode getRandomNode(GridNode node, int max){
		int i = allRandom.getRandomInt(max);
		int j = allRandom.getRandomInt(max);
		int j1 = 0;
		int i1 = 0;
		GridNode current = node;
		while(j1 < j && current.lower != null){
			while(i1 < i && current.next != null){
				current = current.next;
				i1++;
			}
			current = current.lower;
			j1++;
		}
		return current;
	}
	
	public static GridNode getCordenateNode(GridNode node, int x, int y){
		int j = 0;
		int i = 0;
		GridNode current = node;
		while(j < y && current.lower != null){
			while(i < x && current.next != null){
				current = current.next;
				i++;
			}
			current = current.lower;
			j++;
		}
		return current;
	}
}

