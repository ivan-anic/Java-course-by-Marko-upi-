package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Represents a record of a single student stored in the {@link StudentDatabase}
 * database.
 */
public class StudentRecord {

	/** The JMBAG of the student. */
	private String JMBAG;

	/** The last name of the student. */
	private String lastName;

	/** The first name of the student. */
	private String firstName;

	/** The final grade of the student. */
	private int finalGrade;

	/**
	 * Instantiates a new student record.
	 *
	 * @param jMBAG
	 *            the JMBAG of the student
	 * @param lastName
	 *            the last name of the student
	 * @param firstName
	 *            the first name of the student
	 * @param finalGrade
	 *            the final grade of the student
	 */
	public StudentRecord(String jMBAG, String lastName, String firstName, int finalGrade) {
		JMBAG = jMBAG;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets the JMBAG of the student.
	 *
	 * @return the JMBAG
	 */
	public String getJMBAG() {
		return JMBAG;
	}

	/**
	 * Gets the last name of the student.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the first name of the student.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the final grade of the student.
	 *
	 * @return the final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((JMBAG == null) ? 0 : JMBAG.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (JMBAG == null) {
			if (other.JMBAG != null)
				return false;
		} else if (!JMBAG.equals(other.JMBAG))
			return false;
		return true;
	}
}
