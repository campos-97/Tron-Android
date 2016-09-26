package com.tec.datos1.tron.linkedLists;

public class PriorityQueueLinkedList{
	
	private PriorityNode head = null;
	private PriorityNode tail = null;
	private int size = 0;

	public void addByPriority(int priority){
		PriorityNode toAdd = new PriorityNode();
		toAdd.setPriority(priority);
		addByPriority(toAdd);
	}
	
	public void addByPriority(PriorityNode node){
		if (this.head == null){
			this.tail = node;
			this.head = node;
			this.size++;
		}
		else if (this.size == 1){
			if(node.priority >= this.head.priority){
				node.next = this.head;
				this.head.previous = node;
				this.head = node;
				this.size++;
			}
		}
		else if(this.tail.priority > node.priority){
			this.tail.next = node;
			node.previous = this.tail;
			this.tail = node;
			this.size++;
		}
		else{
			PriorityNode current = this.head;
			while(current != null){
				if(current.priority > node.priority){
					current = current.next;
				}
				else{
					node.next = current;
					node.previous = current.previous;
					(current.previous).next = node;
					current.previous = node;
					this.size++;
					break;
				}
			}
		}
		
	}

	public PriorityNode pop(){
		if(this.size == 1){
			PriorityNode toReturn = this.head;
			this.tail = null;
			this.head = null;
			return toReturn;
		}
		else{
			PriorityNode toReturn = this.head;
			((this.head).next).previous = null;
			this.head = this.head.next;
			return toReturn;
		}
	}
	
	public PriorityNode getHead() {
		return head;
	}

	public void setHead(PriorityNode head) {
		this.head = head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void seeListLeftToRight(){
		if (this.head != null){
			PriorityNode current = this.head;
			while(current.next != null){
				System.out.println(current.getPriority());
				System.out.println(current.getInfo());
				current = current.next;
			}
			System.out.println(current.getPriority());
			System.out.println(current.getInfo());
		}
		else{
			throw new IllegalArgumentException("La cagaste");
		}
	}
}