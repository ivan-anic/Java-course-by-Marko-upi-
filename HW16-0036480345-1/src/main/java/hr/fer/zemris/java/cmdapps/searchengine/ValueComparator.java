package hr.fer.zemris.java.cmdapps.searchengine;

import java.util.Comparator;
import java.util.Map;

/**
 * A nested class used for map sorting
 * 
 * @author Ivan Anić
 * @version 1.0
 */
class ValueComparator implements Comparator<String> {
	/** the map reference */
	Map<String, Double> map;

	/**
	 * Instantiates a new value comparator.
	 *
	 * @param map
	 *            the map
	 */
	public ValueComparator(Map<String, Double> map) {
		this.map = map;
	}

	@Override
	public int compare(String o1, String o2) {
		return Double.compare(map.get(o2), map.get(o1));
	}
}
