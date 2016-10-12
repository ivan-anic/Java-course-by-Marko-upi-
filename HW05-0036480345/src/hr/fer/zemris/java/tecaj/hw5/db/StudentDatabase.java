package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashTable;
import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashTable.TableEntry;
import hr.fer.zemris.java.tecaj.hw5.db.filters.IFilter;

/**
 * @author Ivan Anić
 * @version 1.0
 * @param <K> the key of the internal map implementation.
 * @param <V> the value of the internal map implementation.
 */
public class StudentDatabase<K, V> {

	/** The objects. */
	String[] objects;

	/** The table. */
	SimpleHashTable<String, StudentRecord> table = new SimpleHashTable<>();;

	/**
	 * Instantiates a new student database.
	 *
	 * @param objects
	 *            the document in string format, which will be parsed into
	 *            {@link StudentRecord} instances and saved locally.
	 * @throws IllegalArgumentException
	 *             if the document format is invalid.
	 */
	public StudentDatabase(String[] objects) {
		this.objects = objects;
		String[] list;
		for (int i = 0; i < objects.length; i++) {
			list = objects[i].split("\\t");
			if (list.length != 4) {
				throw new IllegalArgumentException("Document format invalid.");
			}

			table.put(list[0], new StudentRecord(list[0], list[1], list[2], Integer.valueOf(list[3])));
		}
	}

	/**
	 * Returns the {@link StudentRecord} from the internal database, searching
	 * for it only by its JMBAG. <br>
	 * This is done in O(1) complexity.
	 * 
	 * @param jmbag
	 *            the JMBAG of the desired student.
	 * @return an instance if {@link StudentRecord} which the user asked for.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return table.get(jmbag);
	}

	/**
	 * Filers the database, returning only the wanted items, specified by the
	 * entries in the {@link IFilter} implementation.
	 *
	 * @param filter
	 *            the filter defined by the user
	 * @return the list populated list of students who match the criteria which
	 *         the user entered.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> tmp = new ArrayList<>();
		for (TableEntry<String, StudentRecord> entry : table) {
			if (filter.accepts(entry.getValue())) {
				tmp.add(entry.getValue());
			}
		}
		return tmp;
	}

}
