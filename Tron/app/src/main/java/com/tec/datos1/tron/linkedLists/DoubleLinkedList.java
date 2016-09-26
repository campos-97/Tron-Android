package com.tec.datos1.tron.linkedLists;

public class DoubleLinkedList<T, U, V>  {
	
	private int size = 0;
	private Node<T, U, V>  head;
	private Node<T, U, V> tail;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Node<T, U, V>  getHead() {
		return head;
	}

	public void setHead(Node<T, U, V>  head) {
		this.head = head;
	}

	public Node<T, U, V>  getTail() {
		return tail;
	}

	public void setTail(Node<T, U, V>  tail) {
		this.tail = tail;
	}

	public void addFirst(Node<T, U, V>  node){
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
	
	public void addFirst(T data){
		Node<T, U, V>  newNode = new Node<T, U, V> ();
		newNode.setData(data);
		addFirst(newNode);
	}
	
	public Node<T, U, V>  deleteFirst(){
		if(this.size == 1){
			Node<T, U, V>  toReturn = this.head;
			this.head = null;
			this.tail = null;
			this.size--;
			return toReturn;
		}
		if (this.head != null){
			Node<T, U, V>  current = this.head;
			((this.head).next).previous = this.head.previous;
			this.head = this.head.next;
			this.size--;
			return current;
		}
		else{
			return null;
		}
	}
	
	public void addLast(Node<T, U, V>  node){
		if (this.head == null){
			addFirst(node);
		}
		else{
			Node<T, U, V>  current = this.head;
			while (current.next != null){
				current = current.next;
			}
			current.next = node;
			node.previous = current;
			this.tail = node;
			this.size++;
		}
	}
	
	public void addLast(T data){
		Node<T, U, V>  newNode = new Node<T, U, V> ();
		newNode.setData(data);
		addLast(newNode);
	}

	public Node<T, U, V>  deleteLast(){
		if (this.head == null){
			return null;
		}
		else if(this.head.next == null){
			Node<T, U, V>  current = this.head;
			this.head = null;
			this.tail = null;
			this.size--;
			return current;
		}
		else{
			Node<T, U, V>  current = this.head;
			while ((current.next).next != null){
				current = current.next;
			}
			Node<T, U, V>  toReturn = current.next;
			(current.next).previous = null;
			current.next = null;
			this.tail = current;
			this.size--;
			return toReturn;
		}
	}
	
	public void addByIndex(Node<T, U, V>  node, int index){
		index = (index > this.size) ? this.size: index;
		if (index == 0){
			addFirst(node);
		}
		else if (index == this.size){
			addLast(node);
		}
		else{
			int i = 0;
			Node<T, U, V>  current = this.head;
			while (i != index-1){
				current = current.next;
				i++;
			}
			node.setNext(current.next);
			current.next = node;
			this.size++;
		}
	}
	
	public void addByIndex(T data, int index){
		Node<T, U, V>  newNode = new Node<T, U, V> ();
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
			Node<T, U, V>  current = this.head;
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
			Node<T, U, V>  current = this.head;
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
			Node<T, U, V>  current = this.tail;
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
	
	public void deleteThis(String searchName){
		if(this.head.name== searchName){
			if(this.size == 1){
				this.head = null;
				this.tail = null;
			}
			else{
				this.head = this.head.next;
			}
		}
		else if(this.tail.name == searchName){
			this.tail.previous.next = null;
			this.tail = this.tail.previous;
		}
		else{
			Node<T, U, V>  current = this.head;
			while(current.next != null){
				if(current.next.name.equals(searchName)){
					current.next = current.next.next;
					((current.next).next).previous = current;
				}
				else{
					current = current.next;
				}
			}
		}
	}
	
	public Boolean hasName(String name){
		boolean booli = false;
		if (this.head != null){
			Node<T, U, V>  current = this.head;
			while(current != null){
				if (current.getName().equals(name)){
					booli = true;
					break;
				}
				else{
					current = current.next;
				}
			}
		}
		else{
			booli = false;
		}
		return booli;
	}
}
