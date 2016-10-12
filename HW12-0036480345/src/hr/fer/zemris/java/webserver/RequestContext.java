package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a context for an arbitrary http request. Accepts parameters and
 * persistent paramteres as instances of {@linkplain Map}, and a
 * {@linkplain List} of {@linkplain RCCookie}s.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class RequestContext {

	/** A constant used for a line ending symbol. */
	private static final String LINE_END = "\r\n";

	/** Keeps reference to the output stream used in this class instance. */
	private OutputStream outputStream;
	/** Keeps reference to the charset used in this class instance. */
	private Charset charset;

	/** Holds a reference to the desired character encoding. */
	private String encoding = "UTF-8";
	/** Holds a reference to the desired status code. */
	private int statusCode = 200;
	/** Holds a reference to the desired status text. */
	private String statusText = "OK";
	/** Holds a reference to the desired mimetype. */
	private String mimeType = "text/html";

	/** Holds a reference to the given parameters. */
	private Map<String, String> parameters;
	/** Holds a reference to the temporary parameters. */
	private Map<String, String> temporaryParameters;
	/** Holds a reference to the persistent parameters. */
	private Map<String, String> persistentParameters;
	/** Holds a reference to the output cookies. */
	private List<RCCookie> outputcookies;

	/** Remembers whether the header has been generated or not. */
	private boolean headerGenerated;

	/**
	 * Instantiates a new request context with the desired parameters.
	 *
	 * @param outputStream
	 *            - the output stream
	 * @param parameters
	 *            - the input parameters
	 * @param persistentParameters
	 *            - the persistent parameters
	 * @param outputcookies
	 *            - the output {code RCCookie} list
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputcookies) {
		super();
		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputcookies = outputcookies;
		temporaryParameters = new HashMap<>();
		if (this.parameters == null)
			this.persistentParameters = new HashMap<>();
		if (this.persistentParameters == null)
			this.persistentParameters = new HashMap<>();
		if (this.outputcookies == null)
			this.outputcookies = new ArrayList<>();
	}

	/**
	 * Represents an instance of a cookie used by the
	 * {@linkplain RequestContext} class. Keeps the following parameters:
	 * <ul>
	 * <li>{@linkplain #name}</li>
	 * <li>{@linkplain #value}</li>
	 * <li>{@linkplain #domain}</li>
	 * <li>{@linkplain #path}</li>
	 * <li>{@linkplain #maxAge}</li>
	 * </ul>
	 * 
	 * @author Ivan Anić
	 * @version 1.0
	 */
	public static class RCCookie {
		/** Keeps reference to the name of the cookie. */
		private String name;
		/** Keeps reference to the cookie value. */
		private String value;
		/** Keeps reference to the cookie domain. */
		private String domain;
		/** Keeps reference to the path of the cookie. */
		private String path;
		/** Keeps reference to the cookie maximum age. */
		private Integer maxAge;
		/**
		 * Keeps the information about whether the cookie is treated as a http
		 * only cookie.
		 */
		private boolean httpOnly;

		/**
		 * Instantiates a new RC cookie with the desired paramteres.
		 *
		 * @param name
		 *            - the name of the cookie
		 * @param value
		 *            - the value of the cookie
		 * @param domain
		 *            - the domain of the cookie
		 * @param path
		 *            - the path to the cookie
		 * @param maxAge
		 *            - the max age of the cookie
		 * @param httpOnly
		 *            - indicates if the rccookie is http-only or not
		 */
		public RCCookie(String name, String value, String domain, String path, Integer maxAge, boolean httpOnly) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}

		/**
		 * Instantiates a new RC cookie with the desired paramteres, sets the
		 * {@link #httpOnly} parameter to false.
		 *
		 * @param name
		 *            - the name of the cookie
		 * @param value
		 *            - the value of the cookie
		 * @param domain
		 *            - the domain of the cookie
		 * @param path
		 *            - the path to the cookie
		 * @param maxAge
		 *            - the max age of the cookie
		 */
		public RCCookie(String name, String value, String domain, String path, Integer maxAge) {
			this(name, value, domain, path, maxAge, false);
		}

		/**
		 * Gets the name of the cookie.
		 *
		 * @return the name of the cookie
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the value of the cookie.
		 *
		 * @return the value of the cookie
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets the domain of the cookie.
		 *
		 * @return the domain of the cookie
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets the path of the cookie.
		 *
		 * @return the path of the cookie
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets the max age of the cookie.
		 *
		 * @return the max age of the cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Set-Cookie: ");

			sb.append(name).append("=\"").append(value).append('"');

			if (domain != null) {
				sb.append("; Domain=").append(domain);
			}

			if (path != null) {
				sb.append("; Path=").append(path);
			}

			if (maxAge != null) {
				sb.append("; Max-Age=").append(maxAge);
			}

			if (httpOnly == true) {
				sb.append("; HttpOnly");
			}

			sb.append(LINE_END);

			return sb.toString();
		}

	}

	/**
	 * Gets the output stream.
	 *
	 * @return the output stream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * Sets the encoding.
	 *
	 * @param encoding
	 *            the new encoding
	 */
	public void setEncoding(String encoding) {
		checkHeaderGenerated();
		this.encoding = encoding;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode
	 *            the new status code
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text.
	 *
	 * @param statusText
	 *            the new status text
	 */
	public void setStatusText(String statusText) {
		checkHeaderGenerated();
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType
	 *            the new mime type
	 */
	public void setMimeType(String mimeType) {
		checkHeaderGenerated();
		this.mimeType = mimeType;
	}

	/**
	 * Adds the rc cookie.
	 *
	 * @param rcCookie
	 *            the rc cookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		checkHeaderGenerated();
		outputcookies.add(rcCookie);
	}

	/**
	 * Gets the parameter.
	 *
	 * @param name
	 *            the name
	 * @return the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Gets the parameter names.
	 *
	 * @return the parameter names
	 */
	public Set<String> getParameterNames() {
		return new HashSet<String>(parameters.keySet());
	}

	/**
	 * Gets the persistent parameter.
	 *
	 * @param name
	 *            the name
	 * @return the persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Gets the persistent parameter names.
	 *
	 * @return the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return new HashSet<String>(persistentParameters.keySet());
	}

	/**
	 * Sets the persistent parameter.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter.
	 *
	 * @param name
	 *            the name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Gets the temporary parameter.
	 *
	 * @param name
	 *            the name
	 * @return the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Gets the temporary parameter names.
	 *
	 * @return the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return new HashSet<String>(temporaryParameters.keySet());
	}

	/**
	 * Sets the temporary parameter.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes the temporary parameter.
	 *
	 * @param name
	 *            the name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Writes the data to the output stream.
	 *
	 * @param data
	 *            the data to be written
	 * @return the request context
	 */
	public RequestContext write(byte[] data) {
		if (!headerGenerated) {
			generateHeader();
		}

		try {
			outputStream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Writes the data to the output stream.
	 *
	 * @param text
	 *            the text to be written
	 * @return the request context
	 */
	public RequestContext write(String text) {
		if (!headerGenerated) {
			generateHeader();
		}

		byte[] data = text.getBytes(charset);
		return write(data);
	}

	/**
	 * Generates the request header based on the given resources.
	 */
	private void generateHeader() {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append(LINE_END);
		sb.append("Content-Type: ").append(mimeType);
		sb.append(mimeType.startsWith("text/") ? "; charset=" + encoding + LINE_END : LINE_END);

		try {
			outputcookies.forEach(c -> sb.append(c.toString()));
		} catch (NullPointerException ignorable) {
		}

		sb.append(LINE_END);

		headerGenerated = true;

		write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
	}

	/**
	 * Checks if the request header has already been generated.
	 * 
	 * @throws RuntimeException
	 *             if the header has already been generated
	 */
	private void checkHeaderGenerated() {
		if (headerGenerated) {
			throw new RuntimeException("Can't edit, header is already generated!");
		}
	}

}
