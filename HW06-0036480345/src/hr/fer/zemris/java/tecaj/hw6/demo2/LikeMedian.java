package hr.fer.zemris.java.tecaj.hw6.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A class which accepts {@code <T>} type objects, saves them into an internal
 * list, has the option to return the median element.
 *
 * @author Ivan Anić
 * @version 1.0
 * @param <T>
 *            the generic type
 */
public class LikeMedian<T> implements Iterable<T> {
	/** The list of {@code <T>} type objects contained in this object. */
	List<T> contents;

	/**
	 * Instantiates a new like median.
	 */
	public LikeMedian() {
		super();
		this.contents = new ArrayList<>();
	}

	/**
	 * Adds a new {@code <T>} value to this object.
	 *
	 * @param param
	 *            the desired parameter to be added
	 */
	public void add(T param) {
		if (param == null) {
			throw new IllegalArgumentException("Parameter can't be null!");
		}
		contents.add(param);
	}

	/**
	 * Returns a median elements contained in this object, in natural order. The
	 * median is calculated as following:<br>
	 * <ul>
	 * <li>If an even number of elements is added, a smaller from the two
	 * numbers which would usually be used to calculate median element is
	 * returned.</li>
	 * <li>If an odd number of elements is added, the number at the middle
	 * {@code index} is returned.</li>
	 * </ul>
	 * If no elements are added, an empty {@linkplain Optional} object is returned.
	 *
	 * @return the median element
	 */
	public Optional<T> get() {
		if (contents.size() == 0) {
			return Optional.empty();
		}
		contents.sort(Collections.reverseOrder());
		return (Optional<T>) Optional.of(contents.stream()
				.sorted(Collections.reverseOrder())
				.collect(Collectors.toList())
				.get(contents.size() / 2));
	}

	@Override
	public Iterator<T> iterator() {
		return contents.iterator();
	}
}
