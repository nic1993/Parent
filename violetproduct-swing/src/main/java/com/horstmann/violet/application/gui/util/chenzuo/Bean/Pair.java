package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.io.Serializable;
import java.util.Comparator;

public class Pair<K,V> implements Comparator<Pair>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 560468465208646461L;
	private K first;
	private V second;
	
	public Pair() {
	}
	
	public Pair(K first, V second) {
		this.first = first;
		this.second = second;
	}
	public K getFirst() {
		return first;
	}
	public void setFirst(K first) {
		this.first = first;
	}
	public V getSecond() {
		return second;
	}
	public void setSecond(V second) {
		this.second = second;
	}

	public int compare(Pair o1, Pair o2) {
		if(o1.first instanceof String && o2.first instanceof String) {
			return Integer.parseInt((String) o1.first) - Integer.parseInt((String) o2.first);
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Date [first=" + first + ", second=" + second + "]";
	}
}
