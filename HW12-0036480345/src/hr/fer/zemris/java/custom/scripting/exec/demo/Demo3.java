package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The Class Demo3.
 *
 * @author Marko Čupić
 * @version 1.0
 */
public class Demo3 {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("brojPoziva.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "4");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
				cookies);
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("\nVrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));

	}

	/**
	 * Read from disk.
	 *
	 * @param path
	 *            the path
	 * @return the string
	 */
	private static String readFromDisk(String path) {
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("File not found!");
			System.exit(-1);
		}
		return docBody;
	}

}
