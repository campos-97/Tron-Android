package com.tec.datos1.tron.linkedList;

/**
 * Creates DoubleLinked Lists
 * @author Roberto
 * 
 */

public class DoubleLinkedList<T>  {
	
	private int size = 0;
	private Node<T> head;
	private Node<T> tail;

	/**
	 *
	 * @return size of the list
	 */

	public int getSize() {
		return size;
	}

	/**
	 *
	 * @param size that puts to the list
	 */

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 *
	 * @return head of the list
	 */

	public Node<T> getHead() {
		return head;
	}

	/**
	 *
	 * @param head placed in the list
	 */

	public void setHead(Node<T> head) {
		this.head = head;
	}

	/**
	 *
	 * @return tail of the list
	 */

	public Node<T> getTail() {
		return tail;
	}

	/**
	 *
	 * @param tail placed in the list
	 */

	public void setTail(Node<T> tail) {
		this.tail = tail;
	}

	/**
	 * Adds a node to the first position in the list
	 * @param node introduced in the first place
	 */

	public void addFirst(Node<T> node){
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

	/**
	 * Adds a node to the first position in the list, with a data
	 * @param data of the node
	 *
	 */

	public void addFirst(T data){
		Node<T> newNode = new Node<T>();
		newNode.setData(data);
		addFirst(newNode);
	}

	/**
	 * Deletes and returns the first node of the list
	 * @return current first node of the list
	 */

	public Node<T> deleteFirst(){
		if(this.size == 1){
			Node<T> toReturn = this.head;
			this.head = null;
			this.tail = null;
			this.size--;
			return toReturn;
		}
		if (this.head != null){
			Node<T> current = this.head;
			((this.head).next).previous = this.head.previous;
			this.head = this.head.next;
			this.size--;
			return current;
		}
		else{
			return null;
		}
	}

	/**
	 * Adds a node to the end of the list
	 * @param node To add at the end of the list
	 */

	public void addLast(Node<T> node){
		node.setNext(null);
		node.setPrevious(null);
		if (this.head == null){
			addFirst(node);
		}
		else{
			Node<T> current = this.head;
			while (current.next != null){
				current = current.next;
			}
			current.next = node;
			node.previous = current;
			this.tail = node;
			this.size++;
		}
	}

	/**
	 * Adds a node to the end of the list only with the data
	 * @param data to add to the node
	 */

	public void addLast(T data){
		Node<T> newNode = new Node<>();
		newNode.setData(data);
		addLast(newNode);
	}

	/**
	 * Deletes and returns the last node of the list
	 * @return node in the end of the list
	 */

	public Node<T> deleteLast(){
		if (this.head == null){
			return null;
		}
		else if(this.head.next == null){
			Node<T> current = this.head;
			this.head = null;
			this.tail = null;
			this.size--;
			return current;
		}
		else{
			Node<T> current = this.head;
			while ((current.next).next != null){
				current = current.next;
			}
			Node<T> toReturn = current.next;
			(current.next).previous = null;
			current.next = null;
			this.tail = current;
			this.size--;
			return toReturn;
		}
	}

	/**
	 * Add a node in the list depending on the index
	 * @param node to add in the list
	 * @param index to add the node
	 */

	public synchronized void addByIndex(Node<T> node, int index){
		index = (index > this.size) ? this.size: index;
		if (index == 0){
			addFirst(node);
		}
		else if (index == this.size){
			addLast(node);
		}
		else{
			int i = 0;
			Node<T> current = this.head;
			while (i != index-1){
				current = current.next;
				i++;
			}
			node.setNext(current.next);
			current.next = node;
			this.size++;
		}
	}

	/**
	 * Add a node in the list depending on the index, only with the data
	 * @param data to add in the node
	 * @param index to add the node
	 */

	public void addByIndex(T data, int index){
		Node<T> newNode = new Node<>();
		newNode.setData(data);
		addByIndex(newNode, index);
	}

	/**
	 * Deletes a node by index
	 * @param index of the node that needs to be eliminated
	 */

	public void deleteByIndex(int index){
		index = (index > this.size) ? this.size: index;
		if (index == 0){
			deleteFirst();
		}
		else if (index == this.size-1){
			deleteLast();
		}
		else{
			int i = 0;
			Node<T> current = this.head;
			while (i != index-1){
				current = current.next;
				i++;
			}
			current.next = (current.next).next;
			(current.next).previous = current;
			this.size--;
		}
	}

	/**
	 * Shows the list from left to right
	 */

	public void seeListLeftToRight(){
		if (this.head != null){
			Node<T> current = this.head;
			while(current.next != null){
				System.out.println(current.getData());
				current = current.next;
			}
			System.out.println(current.getData());
		}
		else{
			throw new IllegalArgumentException("Mal hecho");
		}
	}

	/**
	 * Shows the list from right to left
	 */

	public void seeListRightToLeft(){
		if (this.tail != null){
			Node<T> current = this.tail;
			while(current.previous != null){
				System.out.println(current.getData());
				current = current.previous;
			}
			System.out.println(current.getData());
		}
		else{
			throw new IllegalArgumentException("Mal hecho");
		}
	}

	/**
	 * Deletes the node with the info
	 * @param data to find
	 */

	public synchronized void deleteThis(T data){
		if(this.head.getData() == data){
			if(this.size == 1){
				this.head = null;
				this.tail = null;
				this.size--;
			}
			else{
				this.head = this.head.next;
				this.size--;
			}
		}
		else if(this.tail.getData() == data){
			this.tail.previous.next = null;
			this.tail = this.tail.previous;
			this.size--;
		}
		else{
			Node<T> current = this.head;
			while(current != null){
				if(current.getData().equals(data)){
					current.previous.next = current.next;
					current.next.previous = current.previous;
					this.size--;
				}
				else{
					current = current.next;
				}
			}
		}
	}

	/**
	 * Deletes a Node from this list with a name
	 * @param name To be deleted
	 */

	public synchronized void deleteName(String name){
		if(this.head.name.equals(name)){
			if(this.size == 1){
				this.head = null;
				this.tail = null;
				this.size--;
			}
			else{
				this.head = this.head.next;
				this.size--;
			}
		}
		else if(this.tail.name.equals(name)){
			this.tail.previous.next = null;
			this.tail = this.tail.previous;
			this.size--;
		}
		else{
			Node<T> current = this.head;
			while(current != null){
				if(current.name.equals(name)){
					current.previous.next = current.next;
					current.next.previous = current.previous;
					this.size--;
				}
				else{
					current = current.next;
				}
			}
		}
	}

	/**
	 * Checks if the list has a node with some name
	 * @param name to look for
	 * @return booli boolean if it has the name
	 */

	public Boolean hasName(String name){
		boolean booli = false;
		if (this.head != null){
			Node<T> current = this.head;
			while(current != null){
				if (current.name.equals(name)){
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

	/**
	 * Returns a node with an index
	 * @param index of the node to return
	 * @return current node to return
	 */

	public Node<T> getByIndex(int index) {
		index = (index > this.size) ? this.size: index;
		if (index == 0){
			return this.head;
		}
		else if (index == this.size){
			return this.tail;
		}
		else{
			int i = 0;
			Node<T> current = this.head;
			while (i != index-1){
				current = current.next;
				i++;
			}
			return current;
		}
	}
	
}
