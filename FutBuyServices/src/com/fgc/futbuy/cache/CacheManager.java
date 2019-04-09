package com.fgc.futbuy.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.cache.impl.CacheImpl;
import com.fgc.futbuy.dao.impl.CategoriaDAOImpl;

public class CacheManager<K,V> {
	
	private static Logger logger = LogManager.getLogger(CategoriaDAOImpl.class);
	private static Map<String, Cache> caches = null;
	
	//Singleton pouco recomendable
	//public static final CacheManager INSTANCE = new CacheManager();	

//Singleton no recomendable
//	public static final CacheManager INSTANCE;
//	static {
//		INSTANCE = new CacheManager();
//	}
	
	public static CacheManager INSTANCE = null;	
	
	public static CacheManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new CacheManager();
		}
		return INSTANCE;
	}
	
	private CacheManager() {
		caches =  new HashMap<String, Cache>();
	}
	
	public <K,V> Cache<K,V> createCache(String nombre, Class<K> keyClass, Class<V> valueClass){
		Cache<K,V> newCache = new CacheImpl<K,V>(nombre);
		caches.put(nombre, newCache);
		
		if(logger.isInfoEnabled()) {
			logger.info("Cache {} <{},{}> created.",nombre, keyClass, valueClass);
		}
		
		return newCache;
	}
	
	public <K,V> Cache<K, V> getCache(String nombre, Class<K> keyClass, Class<V> valueClass){
		
		return getCache(nombre, keyClass, valueClass, true);
		
	}
	
	public <K,V> Cache<K, V> getCache(String nombre, Class<K> keyClass, Class<V> valueClass, boolean autoCreate){
		
		Cache<K,V> cache =  (Cache<K,V>) caches.get(nombre);
		
		if(cache == null) {
			if(autoCreate) {
				//logger
				cache = createCache(nombre, keyClass, valueClass);
			}
		}
		
		return cache;
	}

}