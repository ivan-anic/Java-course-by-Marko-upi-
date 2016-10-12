package hr.fer.zemris.java.tecaj.hw6.demo5;

/**
 * Represents a record of a single student stored in the textual database.
 * Attributes of a single student are:
 * <ul>
 * <li>JMBAG</li>
 * <li>first name</li>
 * <li>last name</li>
 * <li>final grade</li>
 * <li>midterm exam score</li>
 * <li>final exam score</li>
 * <li>laboratory practices score</li>
 * </ul>
 */
public class StudentRecord {

	/** The JMBAG of the student. */
	private String JMBAG;

	/** The last name of the student. */
	private String lastName;

	/** The first name of the student. */
	private String firstName;

	/** The number of points this student scored on the midterm exam. */
	private double midtermExamScore;

	/** The number of points this student scored on the final exam. */
	private double finalExamScore;

	/** The number of points this student scored on the laboratory practices. */
	private double laboratoryPracticesScore;

	/** The final grade of the student. */
	private int finalGrade;

	/**
	 * Instantiates a new student record.
	 * 
	 * @param student
	 *            an array of strings which represent the data about the
	 *            student, in matching order:
	 * 
	 *            <ul>
	 *            <li>{@linkplain #JMBAG} the JMBAG of the student</li>
	 *            <li>{@linkplain #lastName} the last name of the student</li>
	 *            <li>{@linkplain #firstName} the first name of the student</li>
	 *            <li>{@linkplain #finalGrade} the final grade of the student
	 *            </li>
	 *            <li>{@linkplain #midtermExamScore} the number of points this
	 *            student scored on the midterm exam.</li>
	 *            <li>{@linkplain #finalExamScore} the number of points this
	 *            student scored on the final exam.</li>
	 *            <li>{@linkplain #laboratoryPracticesScore} the number of
	 *            points this student scored on the laboratory practices</li>
	 *            </ul>
	 */
	public StudentRecord(String[] student) {

		this.JMBAG = student[0];
		this.lastName = student[1];
		this.firstName = student[2];
		this.midtermExamScore = Double.parseDouble(student[3]);
		this.finalExamScore = Double.parseDouble(student[4]);
		this.laboratoryPracticesScore = Double.parseDouble(student[5]);
		this.finalGrade = Integer.parseInt(student[6]);
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

	/**
	 * Gets the midterm exam score of the student.
	 *
	 * @return the midterm exam score
	 */
	public double getMidtermExamScore() {
		return midtermExamScore;
	}

	/**
	 * Gets the final exam score score of the student.
	 *
	 * @return the final exam score
	 */
	public double getFinalExamScore() {
		return finalExamScore;
	}

	/**
	 * Gets the laboratory practices score of the student.
	 *
	 * @return the laboratory practices score
	 */
	public double getLaboratoryPracticesScore() {
		return laboratoryPracticesScore;
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
