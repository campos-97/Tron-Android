package com.tec.datos1.tron.linkedLists;

public class PriorityNode{
	
	int priority;
	int id;
	String info;
	PriorityNode next;
	PriorityNode previous;
	
	public int getData() {
		return priority;
	}
	public void setData(int data) {
		this.priority = data;
	}
	public PriorityNode getNext() {
		return next;
	}
	public void setNext(PriorityNode next) {
		this.next = next;
	}
	public PriorityNode getPrevious() {
		return previous;
	}
	public void setPrevious(PriorityNode previous) {
		this.previous = previous;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
