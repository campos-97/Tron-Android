package com.tec.datos1.tron.linkedLists;

public class Node<T, U, V> {
	
	T data;
	U data2;
	V data3;
	String name;
	String ip;
	Node<T, U, V>  next;
	Node<T, U, V>  previous;
	public void setAll(T t, U u, V v, String ip, String name){
		this.data = t;
		this.data2 = u;
		this.data3 = v;
		this.ip = ip;
		this.name = name;
	}
	
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public U getData2() {
		return data2;
	}
	public void setData2(U data2) {
		this.data2 = data2;
	}
	public V getData3() {
		return data3;
	}
	public void setData3(V data3) {
		this.data3 = data3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Node<T, U, V> getNext() {
		return next;
	}
	public void setNext(Node<T, U, V> next) {
		this.next = next;
	}
	public Node<T, U, V> getPrevious() {
		return previous;
	}
	public void setPrevious(Node<T, U, V> previous) {
		this.previous = previous;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

}