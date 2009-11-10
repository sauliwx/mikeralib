package mikera.util.emptyobjects;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import mikera.persistent.*;

public final class NullMap<K,V> extends PersistentMap<K, V> {
	private static final long serialVersionUID = 1717634837542733926L;

	
	@SuppressWarnings("unchecked")
	public static final NullMap INSTANCE=new NullMap();
	
	private NullMap() {
		
	}

	public void clear() {
	}

	public boolean containsKey(Object key) {
		return false;
	}

	public boolean containsValue(Object value) {
		return false;
	}

	@SuppressWarnings("unchecked")
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return (Set<java.util.Map.Entry<K, V>>) NullSet.INSTANCE;
	}

	public boolean isEmpty() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		return (Set<K>) NullSet.INSTANCE;
	}

	public V put(K key, V value) {
		throw new UnsupportedOperationException();
	}



	public int size() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public PersistentCollection<V> values() {
		return (PersistentCollection<V>) NullSet.INSTANCE;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	public V get(Object key) {
		return null;
	}

	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}
	
	public NullMap<K,V> clone() {
		return this;
	}


}