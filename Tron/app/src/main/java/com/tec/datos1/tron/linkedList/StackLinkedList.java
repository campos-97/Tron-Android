package com.tec.datos1.tron.linkedList;


public class StackLinkedList<T> extends DoubleLinkedList<T>{
	
	/**
	 * 
	 * @return node head of the list
	 */
	
	public Node<T> pop(){
		return deleteFirst();
	}
	
	/**
	 * 
	 * @param node to push in the list
	 */
	
	public void push(Node<T> node){
		addFirst(node);
	}

	public void push(T data){
		Node<T> node = new Node<>();
		node.setData(data);
		addFirst(node);
	}
	
}

