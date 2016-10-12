package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * Enumeration of the shell statuses used by the <i>{@link MyShell}<i> class.
 * 
 * <ul>
 * <li>{@link #CONTINUE}</li>
 * <li>{@link #TERMINATE}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public enum ShellStatus {
	/** Signals that the shell should keep working. */
	CONTINUE,
	/** Signals that the shell should be terminated. */
	TERMINATE
}
