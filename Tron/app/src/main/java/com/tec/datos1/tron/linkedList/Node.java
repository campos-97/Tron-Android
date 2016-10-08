package com.tec.datos1.tron.linkedList;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator.class,property="@id")

/**
 * Class that is used as the node for the LinkedLists
 * @author Roberto
 *
 */
public class Node<T> {
	
	T data;
	Node<T> next;
	Node<T> previous;
	String name;
	
	/**
	 * Returns the data of the node
	 * @return data of the node
	 */
	
	public T getData() {
		return data;
	}
	
	/**
	 * Sets the data of the node
	 * @param data to be set
	 */
	
	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * Returns the next of the node
	 * @return next node
	 */
	
	public Node<T> getNext() {
		return next;
	}
	
	/**
	 * Sets the next node of a node
	 * @param next to be set
	 */
	
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	/**
	 * Returns the previous of the node
	 * @return previous node
	 */
	
	public Node<T> getPrevious() {
		return previous;
	}
	
	/**
	 * Sets the previous node of a node
	 * @param previous to be set
	 */
	
	public void setPrevious(Node<T> previous) {
		this.previous = previous;
	}

}