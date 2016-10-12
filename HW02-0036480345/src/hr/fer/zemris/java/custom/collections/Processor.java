package hr.fer.zemris.java.custom.collections;

/**
 * A class which purpose is to be instantiated and overridden so that it can
 * serve as a processor which will process all elements from a collection. Here
 * it is implemented so it is just a template.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Processor {
	/**
	 * The empty method implementation which will be overwritten so that it
	 * processes the given value in a certain way.
	 * 
	 * @param value
	 *            - object which will be processed in a defined way
	 */
	public void process(Object value) {
		return;
	}
}
