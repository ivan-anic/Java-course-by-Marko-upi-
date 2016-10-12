package hr.fer.zemris.java.webserver;

/**
 * Represents a demonstration of the functionalities of this package. <br>
 * Classes demonstrated:
 * <ul>
 * <li>{@linkplain SmartHttpServer}</li>
 * </ul>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class DemoSmartHttpServer {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments, not used here
	 */
	public static void main(String[] args) {
		SmartHttpServer s = new SmartHttpServer("./src/hr/fer/zemris/java/webserver/properties/server.properties");
		s.start();
	}
}
