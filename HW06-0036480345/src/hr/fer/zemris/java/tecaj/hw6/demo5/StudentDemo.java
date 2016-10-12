package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Class StudentDemo. Provides various output functions performed on the
 * provided textual database.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class StudentDemo {

	/**
	 * The main method, first one to be executed when the program starts.
	 * 
	 * @param args
	 *            - the input arguments (not used)
	 * @throws IOException
	 *             if there is an error during parsing data from the file.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		List<StudentRecord> records = convertToStudentsArray(lines);

		long broj = records.stream().filter(z -> (z.getMidtermExamScore() +
				z.getFinalExamScore() +
				z.getLaboratoryPracticesScore()) > 25)
				.count();

		long broj5 = records.stream().filter(z -> z.getFinalGrade() == 5).count();

		List<StudentRecord> odlikasi = records.stream()
				.filter(z -> z.getFinalGrade() == 5)
				.collect(Collectors.toList());

		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(z -> z.getFinalGrade() == 5)
				.sorted((z1, z2) -> Double.compare(
						z1.getMidtermExamScore() +
								z1.getFinalExamScore() +
								z1.getLaboratoryPracticesScore(),
						z2.getMidtermExamScore() +
								z2.getFinalExamScore() +
								z2.getLaboratoryPracticesScore()))
				.collect(Collectors.toList());

		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(z -> z.getFinalGrade() == 1)
				.map(z -> z.getJMBAG())
				.sorted(String.CASE_INSENSITIVE_ORDER.reversed())
				.collect(Collectors.toList());

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));

		Map<Integer, Long> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade, Collectors
						.mapping(StudentRecord::getFinalGrade, Collectors.counting())));

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream().collect(
				Collectors.partitioningBy(z -> z.getFinalGrade() > 1));

	}

	//
	/**
	 * Convert the input list containing the file lines to an array of
	 * {@linkplain StudentRecord} objects.
	 *
	 * @param lines
	 *            the textual document, as strings
	 * @return the list of {@linkplain StudentRecord} objects
	 */
	private static List<StudentRecord> convertToStudentsArray(List<String> lines) {
		List<StudentRecord> list = new ArrayList<>();
		String[] student;

		for (String line : lines) {
			student = line.trim().split("\t");
			list.add(new StudentRecord(student));
		}

		return list;
	}
}
