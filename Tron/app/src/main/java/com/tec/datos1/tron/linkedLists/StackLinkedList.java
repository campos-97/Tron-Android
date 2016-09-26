package com.tec.datos1.tron.linkedLists;

public class StackLinkedList<T, U, V> extends DoubleLinkedList<T, U, V>{
	public Node<T, U, V> pop(){
		return deleteFirst();
	}
	public void push(Node<T, U, V> node){
		addFirst(node);
	}
}
