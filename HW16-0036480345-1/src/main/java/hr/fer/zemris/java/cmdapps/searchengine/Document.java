package hr.fer.zemris.java.cmdapps.searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAccumulator;

/**
 * A wrapper class for representing a single document. Parameters kept:
 * <ul>
 * <li>The path to the textual document on the disk.</li>
 * <li>A {@link #words} map.</li>
 * </ul>
 * And offers the option to calculate the {@link #tfidf} vector for this
 * document, and to get a similarity coefficient between this and some other
 * document.
 * 
 * <ul>
 * <li>{@link #getTfidf()}</li>
 * <li>{@link #getDifference()}</li>
 * <ul>
 * <br>
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class Document {

	/** Contains the number of times each word has occurred in this document. */
	private Map<String, AtomicInteger> words;
	/**
	 * Containsthe TF-IDF values of each word in this document, based on all of
	 * the other documents.
	 */
	private Map<String, Double> tfidf = new HashMap<>();

	/** Holds the path to the document. */
	private String path;

	/**
	 * Instantiates a new document.
	 *
	 * @param path
	 *            the path pointing to the textual document
	 * @param words
	 *            the {@link #words} map
	 */
	public Document(String path, Map<String, AtomicInteger> words) {
		this.words = words;
		this.path = path;
	}

	/**
	 * Gets the {@link #words} map.
	 *
	 * @return the {@link #words} map
	 */
	public Map<String, AtomicInteger> getWords() {
		return words;
	}

	/**
	 * Gets the path pointing to the textual document
	 *
	 * @return the path pointing to the textual document
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the {@link #tfidf} values.
	 *
	 * @return the {@link #tfidf} values
	 */
	public Map<String, Double> getTfidf() {
		return tfidf;
	}

	/**
	 * Generates the TF-IDF vectors for this document, given the list of other
	 * documents.
	 *
	 * @param documents
	 *            the list of all other documents taken into account
	 */
	public void generateTFIDF(List<Document> documents) {
		Map<String, AtomicInteger> idf = new HashMap<>();

		documents.forEach(z -> {
			// if (!path.equals(z.getPath())) {
			if (true) {
				// for each other document, and for each word in this document,
				// calculate idf
				words.forEach((otherWord, otherCcount) -> {
					z.words.forEach((word, count) -> {
						if (word.equals(otherWord)) {
							if (!idf.containsKey(word)) {
								idf.put(word, new AtomicInteger(1));
							} else {
								idf.get(word).incrementAndGet();
							}
						}
					});
				});
			}
		});
		idf.forEach((word, count) -> {
			tfidf.put(word, words.get(word).get() * Math.log10((documents.size() - 1) / count.get()));
		});

	}

	/**
	 * Gets the difference between this document and the other. Works with
	 * vector representations of the documents, calculating the cosine of the
	 * angle between them, which indicates the similarity coefficient. <br>
	 * Calculated as:
	 * <p>
	 * ( vd1 * vd2 ) / ( | vd1 |*| vd2 | )
	 * 
	 * @see #getNorm()
	 * @return the difference
	 */
	public double getDifference() {
		Document other = Engine.getDoc();

		DoubleAccumulator result = new DoubleAccumulator((a, b) -> (a * b), 1);
		DoubleAccumulator sum = new DoubleAccumulator((a, b) -> (a + b), 0);

		// scalar multiply / both vectors norm

		other.tfidf.forEach((word, count) -> {
			tfidf.forEach((thisword, tfidf) -> {
				if (word.equals(thisword)) {
					result.accumulate(tfidf);
					result.accumulate(count);
					sum.accumulate(result.get());
					result.reset();
				}
			});
		});

		double res = sum.get() / getNorm() / other.getNorm();

		return res;

		// return result.doubleValue();
	}

	/**
	 * Gets the norm of this vector. <br>
	 * Calculated as:
	 * <p>
	 * a1+a2+...an / sqrt(a1^2+a2^2+...+an^2)
	 *
	 * @return the norm
	 */
	private double getNorm() {
		DoubleAccumulator sum = new DoubleAccumulator((a, b) -> (a + b), 0);
		if (tfidf.isEmpty()) {
			for (Map.Entry<String, AtomicInteger> entry : words.entrySet()) {
				tfidf.put(entry.getKey(), (double) entry.getValue().get());
			}
		}

		tfidf.values().forEach(z -> {
			sum.accumulate(z.doubleValue() * z.doubleValue());
		});
		return Math.sqrt(sum.doubleValue());
	}

}
