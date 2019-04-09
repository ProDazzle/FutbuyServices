package com.fgc.futbuy.cache;

public interface Cache<K,V> {
	
	public void put(K k, V v);
	
	public V get(K k);
	
	public void remove (K k);
	
	public void clear();
}
