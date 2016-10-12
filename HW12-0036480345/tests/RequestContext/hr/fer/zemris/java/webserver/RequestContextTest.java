package hr.fer.zemris.java.webserver;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Tests the behaviour of the classes in this package.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class RequestContextTest {

	/** The os. */
	private ByteArrayOutputStream os;

	/** The is. */
	private ByteArrayInputStream is;

	/** The rc. */
	private RequestContext rc;

	/**
	 * Initialize.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Before
	public void initialize() throws IOException {
		os = new ByteArrayOutputStream();
		rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding("UTF-8");
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("test");
	}

	/**
	 * Test header1.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testHeader1() throws IOException {
		rc.addRCCookie(new RCCookie("ime", "javko", "127.0.0.1", "/", 3600));
		rc.addRCCookie(new RCCookie("mjesto", "javograd", null, "/", null));
		rc.write("Čevapčići i Šiščevapčići.");
		is = new ByteArrayInputStream(((ByteArrayOutputStream) rc.getOutputStream()).toByteArray());
		Scanner sc = new Scanner(is);
		String header = "";
		while (sc.hasNextLine()) {
			header += sc.nextLine() + "\r\n";
		}
		sc.close();
		String string = "HTTP/1.1 205 test\r\n" +
				"Content-Type: text/plain; charset=UTF-8\r\n" +
				"Set-Cookie: ime=\"javko\"; Domain=127.0.0.1; Path=/; Max-Age=3600\r\n" +
				"Set-Cookie: mjesto=\"javograd\"; Path=/\r\n" +
				"\r\n" +
				"Čevapčići i Šiščevapčići.\r\n";
		assertEquals(string, header);
	}

	/**
	 * Test header2.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testHeader2() throws IOException {
		rc.write("Čevapčići i Šiščevapčići.");
		is = new ByteArrayInputStream(((ByteArrayOutputStream) rc.getOutputStream()).toByteArray());
		Scanner sc = new Scanner(is);
		String header = "";
		while (sc.hasNextLine()) {
			header += sc.nextLine() + "\r\n";
		}
		sc.close();
		String string = "HTTP/1.1 205 test\r\n" +
				"Content-Type: text/plain; charset=UTF-8\r\n" +
				"\r\n" +
				"Čevapčići i Šiščevapčići.\r\n";
		assertEquals(string, header);
	}

	/**
	 * Test header3.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testHeader3() throws IOException {
		rc.addRCCookie(new RCCookie("ime", "javko", null, "/", 3600));
		rc.addRCCookie(new RCCookie("mjesto", "javograd", null, null, null));
		rc.write("Čevapčići i Šiščevapčići.");
		is = new ByteArrayInputStream(((ByteArrayOutputStream) rc.getOutputStream()).toByteArray());
		Scanner sc = new Scanner(is);
		String header = "";
		while (sc.hasNextLine()) {
			header += sc.nextLine() + "\r\n";
		}
		sc.close();
		String string = "HTTP/1.1 205 test\r\n" +
				"Content-Type: text/plain; charset=UTF-8\r\n" +
				"Set-Cookie: ime=\"javko\"; Path=/; Max-Age=3600\r\n" +
				"Set-Cookie: mjesto=\"javograd\"\r\n" +
				"\r\n" +
				"Čevapčići i Šiščevapčići.\r\n";
		assertEquals(string, header);
	}

	/**
	 * Test header4.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testHeader4() throws IOException {
		rc.addRCCookie(new RCCookie("ime", "javko", null, null, 3600));
		rc.addRCCookie(new RCCookie("mjesto", "javograd", "127.0.0.1", "/path", null));
		rc.addRCCookie(new RCCookie("ispit", "opjj", null, "/", null));
		rc.write("Čevapčići i Šiščevapčići.");
		is = new ByteArrayInputStream(((ByteArrayOutputStream) rc.getOutputStream()).toByteArray());
		Scanner sc = new Scanner(is);
		String header = "";
		while (sc.hasNextLine()) {
			header += sc.nextLine() + "\r\n";
		}
		sc.close();
		String string = "HTTP/1.1 205 test\r\n" +
				"Content-Type: text/plain; charset=UTF-8\r\n" +
				"Set-Cookie: ime=\"javko\"; Max-Age=3600\r\n" +
				"Set-Cookie: mjesto=\"javograd\"; Domain=127.0.0.1; Path=/path\r\n" +
				"Set-Cookie: ispit=\"opjj\"; Path=/\r\n" +
				"\r\n" +
				"Čevapčići i Šiščevapčići.\r\n";
		assertEquals(string, header);
	}

	/**
	 * Finish.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@After
	public void finish() throws IOException {
		os.close();
	}

}