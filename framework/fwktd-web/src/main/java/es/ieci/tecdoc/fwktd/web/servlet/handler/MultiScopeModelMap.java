package es.ieci.tecdoc.fwktd.web.servlet.handler;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.CompositeMap;
import org.apache.commons.collections.map.CompositeMap.MapMutator;

/**
 * Inspirado por GrailsFlashScope
 * 
 * Implementa a flash scoped map. Los scopes se cambian usando {@link #next()}.
 * Los elementos se mantienen durante el scope actual y el siguiente, pero no
 * más.
 */
public class MultiScopeModelMap extends CompositeMap implements Serializable,
		MapMutator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3881882252012513508L;

	@SuppressWarnings("unchecked")
	public MultiScopeModelMap(int num) {
		super();
		setMutator(this);
		for (int i = 0; i < num; ++i) {
			addComposited(new HashMap());
		}
	}

	/** Shadows composite map. */
	@SuppressWarnings("unchecked")
	private final LinkedList<Map> maps = new LinkedList<Map>();

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void addComposited(Map map)
			throws IllegalArgumentException {
		super.addComposited(map);
		this.maps.addLast(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Map removeComposited(Map map) {
		Map removed = super.removeComposited(map);
		this.maps.remove(map);
		return removed;
	}

	/**
	 * Crea un nuevo scope. Todos los elementos añadidos en la sesión son
	 * eliminados. Todos los elementos añadidos en el scope anterior son todavía
	 * recuperables y eliminables.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void next() {
		removeComposited(this.maps.getFirst());
		addComposited(new HashMap());
	}

	@SuppressWarnings("unchecked")
	public Object put(CompositeMap map, Map[] composited, Object key,
			Object value) {
		if (composited.length < 1) {
			throw new UnsupportedOperationException(
					"No composites to add elements to");
		}
		Object result = map.get(key);
		if (result != null) {
			map.remove(key);
		}
		composited[composited.length - 1].put(key, value);
		return result;
	}

	@SuppressWarnings("unchecked")
	public void putAll(CompositeMap map, Map[] composited, Map mapToAdd) {
		for (Entry entry : (Set<Entry>) mapToAdd.entrySet()) {
			put(map, composited, entry.getKey(), entry.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	public void resolveCollision(CompositeMap composite, Map existing,
			Map added, Collection intersect) {
		existing.keySet().removeAll(intersect);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		return new HashMap(this).toString();
	}

}