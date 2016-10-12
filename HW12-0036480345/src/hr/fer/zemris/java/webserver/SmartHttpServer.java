package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * An implementation of a simple, yet functional web server.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class SmartHttpServer {

	/** A system path which points to the location of worker classes. */
	private final static String CLASSPATH = "hr.fer.zemris.java.webserver.workers.";
	/** A system path which points to the location of worker classes. */
	private final static String SID_LETTERS = "PYFGCRLAOEUIDHTNSQJKXBMWVZ";
	/** Holds a reference to the designated IP adress. */
	private String address;
	/** Holds a reference to the designated port. */
	private int port;
	/** Holds a reference to the number of threads used in the pool. */
	private int workerThreads;
	/** Holds a reference to the duration of a single user session. */
	private int sessionTimeout;
	/**
	 * Holds a reference to the mimetypes obtained from the given properties
	 * file.
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Holds a reference to the workers obtained from the given properties file.
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/** Holds a reference to the active sessions. */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Holds a reference to the random generator used for calculation of session
	 * ID-s.
	 */
	private Random sessionRandom = new Random();
	/** Holds a reference to the clean-up thread. */
	private CleanUpThread cleanUpThread;
	/** Holds a reference to the server thread. */
	private ServerThread serverThread;
	/** Holds a reference to the thread pool. */
	private ExecutorService threadPool;
	/** Holds a reference to the root directory. */
	private Path documentRoot;

	/**
	 * Instantiates a new smart http server.
	 *
	 * @param configFileName
	 *            the config file name
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();

		try (InputStreamReader isr = new InputStreamReader(
				new FileInputStream(new File(configFileName)),
				StandardCharsets.UTF_8)) {
			properties.load(isr);

		} catch (IOException e) {
			e.printStackTrace();
		}
		address = properties.get("server.address").toString();
		port = Integer.parseInt(properties.getProperty("server.port"));
		workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));

		String pathRoot = properties.getProperty("server.documentRoot").substring(2);
		documentRoot = Paths.get(pathRoot).toAbsolutePath();
		sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));

		processMimeProperties(properties);
		processWorkersProperties(properties);

	}

	/**
	 * Process the mime properties from the given file and puts them into
	 * {@link #mimeTypes} map
	 *
	 * @param properties
	 *            the properties
	 */
	private void processMimeProperties(Properties properties) {
		Path mimePath = Paths.get(properties.getProperty("server.mimeConfig"));
		Properties mimeProperties = new Properties();

		try {
			mimeProperties.load(Files.newInputStream(mimePath, StandardOpenOption.READ));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object o : mimeProperties.keySet()) {
			mimeTypes.put((String) o, mimeProperties.getProperty((String) o));
		}
	}

	/**
	 * Processes the workers properties from the given file and puts them into
	 * {@link #workersMap} map.
	 *
	 * @param properties
	 *            the properties
	 */
	private void processWorkersProperties(Properties properties) {
		Path workersPath = Paths.get(properties.getProperty("server.workers"));
		Properties workersProperties = new Properties();

		try {
			workersProperties.load(Files.newInputStream(workersPath, StandardOpenOption.READ));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object o : workersProperties.keySet()) {
			String path = (String) o;
			String fqcn = workersProperties.getProperty((String) o);

			workersMap.put(path, getWebWorkerByName(fqcn));

		}
	}

	/**
	 * Starts the server.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
		}

		if (cleanUpThread == null) {
			cleanUpThread = new CleanUpThread();
			cleanUpThread.setDaemon(true);
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);

		serverThread.start();
		cleanUpThread.start();

		System.out.println(String.format("Server started: %s at port %d", address, port));
		while (true) {
		}
	}

	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.terminate();
		cleanUpThread.terminate();

		threadPool.shutdown();
	}

	/**
	 * A class derived from {@linkplain Thread}. Represents a server thread
	 * which listens for connections and accepts them.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {

		/** Remembers whether the thread should be running or not. */
		private boolean running = true;

		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket(port);

				while (running) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Terminates the thread.
		 */
		public void terminate() {
			running = false;
		}
	}

	/**
	 * A class derived from {@linkplain Thread}. Represents a clean-up thread
	 * which goes through all session records and removes the expired sessions
	 * from the sessions map.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	private class CleanUpThread extends Thread {

		/** Remembers whether the thread should be running or not. */
		private boolean running = true;
		/** The time which the thread will wait for in between its runs. */
		private final static int WAIT_TIME = 1000 * 60 * 5;

		@Override
		public void run() {
			try {
				while (running) {
					Thread.sleep(WAIT_TIME);
					Set<String> keys = sessions.keySet();
					for (String k : keys) {
						if (sessions.get(k).validUntil > new Date().getTime()) {
							sessions.remove(k);
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Terminates the thread.
		 */
		public void terminate() {
			running = false;
			sessions.clear();
		}
	}

	/**
	 * A class which represents a single session entry, generated for each
	 * client encountered.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	private static class SessionMapEntry {
		/** Holds reference to the session id. */
		@SuppressWarnings("unused")
		private String sid;
		/** Holds reference to the time until which this entry is valid. */
		private long validUntil;
		@SuppressWarnings("javadoc")
		private Map<String, String> map;

		/**
		 * Instantiates a new session map entry.
		 *
		 * @param sid
		 *            the sid
		 * @param validUntil
		 *            the valid until
		 * @param map
		 *            the map
		 */
		public SessionMapEntry(String sid, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = map;
		}

	}

	/**
	 * A worker class which creates a new {@linkplain Runnable} implementation
	 * based on the given function element.
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable {
		/** Holds reference to the given {@linkplain Socket}. */
		private Socket csocket;
		/**
		 * Holds reference to the input stream received from the
		 * {@link #csocket}.
		 */
		private PushbackInputStream istream;
		/**
		 * Holds reference to the output stream received from the
		 * {@link #csocket}.
		 */
		private OutputStream ostream;
		/**
		 * Holds reference to the protocol version received from the
		 * {@link #csocket}.
		 */
		private String version;
		/**
		 * Holds reference to the method being used, received from the
		 * {@link #csocket}.
		 */
		private String method;
		/**
		 * Holds reference to the parameters sent within the request, received
		 * from the {@link #csocket}.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Holds reference to the permanent parameters sent within the request,
		 * received from the {@link #csocket}.
		 */
		private Map<String, String> permPrams = null;
		/** Holds reference to the output cookies. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/** Holds reference to the session id. */
		@SuppressWarnings("unused")
		private String SID;

		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket
		 *            the csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}

			List<String> request = readRequest();

			if (request.isEmpty()) {
				return;
			}

			System.out.println("req" + request);

			if (request.size() < 1) {
				returnStatus(400, "Invalid request!");
				return;
			}
			String firstLine = request.get(0);

			System.out.println(firstLine);

			String[] lineParams = firstLine.split(" ");
			method = lineParams[0];
			String reqPath = lineParams[1];
			version = lineParams[2];
			if (!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
				returnStatus(400, "Invalid request!");
				return;
			}

			String[] requestedPathParams = reqPath.split("\\?");

			String path = requestedPathParams[0];

			checkSession(request);

			if (requestedPathParams.length != 1) {
				String paramString = requestedPathParams[1];

				System.out.println(paramString);

				parseParameters(paramString);
			}

			Path requestedPath = Paths.get(documentRoot + path);
			File requestedFile = requestedPath.toFile();

			System.out.println("Requested file: " + requestedFile.getAbsolutePath().toString());

			if (!requestedPath.startsWith(documentRoot)) {
				returnStatus(403, "Forbidden!");
			}

			IWebWorker worker = null;
			boolean workerToBeInvoked = false;
			if (path.startsWith("/ext")) {
				workerToBeInvoked = true;
				try {
					worker = getWebWorkerByName(CLASSPATH + path.split("/")[2]);
				} catch (ArrayIndexOutOfBoundsException ex) {
					returnStatus(400, "Invalid request!");
					return;
				}
			}
			if (workersMap.containsKey(path)) {
				workerToBeInvoked = true;
				worker = workersMap.get(path);
			}
			if (workerToBeInvoked == true) {
				worker.processRequest(new RequestContext(ostream, params, null, outputCookies));
			} else {
				if (!requestedFile.exists() || !requestedFile.canRead() || !requestedFile.isFile()) {
					returnStatus(404, "Not Found!");
					return;
				}

				String name = requestedFile.getName();
				String ext = name.substring(name.lastIndexOf('.') + 1);
				String mimeType = mimeTypes.get(ext);

				if (ext.equals("smscr")) {
					processSmartScript(requestedPath, mimeType);
				} else {
					processFile(requestedPath, mimeType);
				}
			}
			closeSocket();

		}

		/**
		 * Returns the given status by initialising a new
		 * {@linkplain RequestContext} and writing a message to its output
		 * stream.
		 *
		 * @param code
		 *            the code
		 * @param msg
		 *            the msg
		 */
		private void returnStatus(int code, String msg) {
			RequestContext rc = new RequestContext(ostream, null, null, null);
			rc.setStatusCode(code);
			rc.setStatusText(msg);
			rc.write(msg);
			closeSocket();
			System.out.println("socket closed; " + code + msg);
		}

		/**
		 * Closes the {@link #csocket}.
		 */
		private void closeSocket() {
			try {
				ostream.flush();
				csocket.close();
				System.out.println("Socket closed!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Reads the request from the given input stream.
		 *
		 * @return the list
		 */
		private List<String> readRequest() {

			List<String> result = new ArrayList<String>();

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(istream,
						StandardCharsets.UTF_8));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.isEmpty()) {
						break;
					}
					result.add(line);
				}
				result.forEach(System.out::println);

			} catch (IOException e) {
			}
			return result;
		}

		/**
		 * Checks if the session is valid.
		 *
		 * @param headerLines
		 *            the header lines
		 */
		private synchronized void checkSession(List<String> headerLines) {
			String sidCandidate = null;
			Pattern p = Pattern.compile("sid=\"(.*)\"");
			Matcher m;
			long date = new Date().getTime() + sessionTimeout;
			SessionMapEntry entry;
			@SuppressWarnings("unused")
			boolean registerNew = true;

			for (String l : headerLines) {
				if (!l.startsWith("Cookie:")) {
					continue;
				}

				m = p.matcher(l);
				if (m.find()) {
					sidCandidate = m.group(1);
					entry = sessions.get(sidCandidate);
					if (entry != null && entry.validUntil < date) {
						entry.validUntil = date;
						sessions.remove(sidCandidate);
						sessions.put(sidCandidate, entry);
						break;
					} else
						sidCandidate = null;
				} else {
					registerNew = true;
				}
				registerNew = true;
			}

			if (sidCandidate == null) {
				String sid = generateSessionID();

				entry = new SessionMapEntry(
						sid,
						date,
						new ConcurrentHashMap<>());
				sessions.put(sid, entry);
				outputCookies.add(new RequestContext.RCCookie("sid", sid, address, "/", null, true));
				registerNew = false;
			} else {
				entry = sessions.get(sidCandidate);
			}
			permPrams = entry.map;
		}

		/**
		 * Generates a new randomized session id.
		 *
		 * @return the string
		 */
		private String generateSessionID() {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				builder.append(SID_LETTERS.charAt(sessionRandom.nextInt(SID_LETTERS.length())));
			}
			return builder.toString();
		}

		/**
		 * Parses the parameters.
		 *
		 * @param paramString
		 *            the param string
		 */
		private void parseParameters(String paramString) {
			Arrays.asList(paramString.split("&")).forEach(c -> {
				params.put(c.split("=")[0], c.split("=")[1]);
			});

		}

		/**
		 * Processes the smart script using the {@linkplain SmartScriptEngine}
		 *
		 * @param requestedFile
		 *            the given script
		 * @param mimeType
		 *            the given mimetype
		 */
		private void processSmartScript(Path requestedFile, String mimeType) {
			String docBody = null;
			try {
				docBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
			} catch (IOException e1) {
				System.out.println("File not found!");
				System.exit(-1);
			}

			RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
			rc.setMimeType(mimeType);

			new SmartScriptEngine(
					new SmartScriptParser(docBody).getDocumentNode(),
					rc).execute();
		}

		/**
		 * Processes the regular http request.
		 *
		 * @param requestedFile
		 *            the requested file. Writes the desired result to the
		 *            output stream.
		 * @param mimeType
		 *            the given mimetype
		 */
		private void processFile(Path requestedFile, String mimeType) {

			RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
			rc.setStatusCode(200);
			rc.setMimeType(mimeType == null ? "application/octet-stream" : mimeType);

			try (BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(requestedFile.toFile()))) {
				byte[] buffer = new byte[1024];
				@SuppressWarnings("unused")
				int read = -1;
				while ((read = bis.read(buffer)) != -1) {
					rc.write(buffer);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the reference to the desired class whih implements
	 * {@linkplain IWebWorker}
	 *
	 * @param fqcn
	 *            the fqcn
	 * @return the web worker by name
	 */
	private IWebWorker getWebWorkerByName(String fqcn) {

		Class<?> referenceToClass;
		IWebWorker iww = null;
		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.newInstance();
			iww = (IWebWorker) newObject;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return iww;
	}
}
