package hr.fer.zemris.java.hw11.jnotepadapp.Actions;

/**
 * Enumeration of the case commands used by the
 * {@linkplain AbstractActionChangeCase} class
 * 
 * <ul>
 * <li>{@link #TO_UPPER}</li>
 * <li>{@link #TO_LOWER}</li>
 * <li>{@link #INVERT}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public enum CaseOperation {
	/** Indicates that the characters will be converted to uppercase. */
	TO_UPPER,
	/** Indicates that the characters will be converted to lowercase. */
	TO_LOWER,
	/** Indicates that the character case will be inverted. */
	INVERT;
}
