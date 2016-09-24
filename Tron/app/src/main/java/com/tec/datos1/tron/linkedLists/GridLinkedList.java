package com.tec.datos1.tron.linkedLists;

public class GridLinkedList {
	private int size = 0;
	private GridNode head;
	private GridNode tail;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public GridNode getHead() {
		return head;
	}

	public void setHead(GridNode head) {
		this.head = head;
	}

	public GridNode getTail() {
		return tail;
	}

	public void setTail(GridNode tail) {
		this.tail = tail;
	}

	public void addFirst(GridNode node){
		if (this.head == null){
			this.tail = node;
			this.head = node;
			this.size++;
		}
		else{
			(this.head).previous = node;
			node.setNext(this.head);
			this.head = node;
			this.size++;
		}
	}	

	public void addFirst(int data){
		GridNode newNode = new GridNode();
		newNode.setData(data);
		addFirst(newNode);
	}
	
	public GridNode deleteFirst(){
		if(this.size == 1){
			GridNode toReturn = this.head;
			this.head = null;
			this.tail = null;
			this.size--;
			return toReturn;
		}
		if (this.head != null){
			GridNode current = this.head;
			((this.head).next).previous = this.head.previous;
			this.head = this.head.next;
			this.size--;
			return current;
		}
		else{
			return null;
		}
	}
	
	public void addLast(GridNode node){
		if (this.head == null){
			addFirst(node);
		}
		else{
			GridNode current = this.head;
			while (current.next != null){
				current = current.next;
			}
			current.next = node;
			node.previous = current;
			this.tail = node;
			this.size++;
		}
	}
	
	public void addLast(int data){
		GridNode newNode = new GridNode();
		newNode.setData(data);
		addLast(newNode);
	}

	public GridNode deleteLast(){
		if (this.head == null){
			return null;
		}
		else if(this.head.next == null){
			GridNode current = this.head;
			this.head = null;
			this.tail = null;
			this.size--;
			return current;
		}
		else{
			GridNode current = this.head;
			while ((current.next).next != null){
				current = current.next;
			}
			GridNode toReturn = current.next;
			(current.next).previous = null;
			current.next = null;
			this.tail = current;
			this.size--;
			return toReturn;
		}
	}
	
	public void addByIndex(GridNode node, int index){
		index = (index > this.size) ? this.size: index;
		if (index == 0){
			addFirst(node);
		}
		else if (index == this.size){
			addLast(node);
		}
		else{
			int i = 0;
			GridNode current = this.head;
			while (i != index-1){
				current = current.next;
				i++;
			}
			node.setNext(current.next);
			current.next = node;
			this.size++;
		}
	}
	
	public void addByIndex(int data, int index){
		GridNode newNode = new GridNode();
		newNode.setData(data);
		addByIndex(newNode, index);
	}
	
	public void deleteByIndex(int index){
		index = (index > this.size) ? this.size: index;
		if (index == 0){
			deleteFirst();
		}
		else if (index == this.size){
			deleteLast();
		}
		else{
			int i = 0;
			GridNode current = this.head;
			while (i != index-1){
				current = current.next;
				i++;
			}
			current.next = (current.next).next;
			(current.next).previous = current;
			this.size--;
		}
	}

	public void seeListLeftToRight(){
		if (this.head != null){
			GridNode current = this.head;
			while(current.next != null){
				System.out.println(current.getData());
				current = current.next;
			}
			System.out.println(current.getData());
		}
		else{
			throw new IllegalArgumentException("La cagaste");
		}
	}
	
	public void seeListRightToLeft(){
		if (this.tail != null){
			GridNode current = this.tail;
			while(current.previous != null){
				System.out.println(current.getData());
				current = current.previous;
			}
			System.out.println(current.getData());
		}
		else{
			throw new IllegalArgumentException("Rip in piece");
		}
	}
	
}
