package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * A simple web servlet which generates an .xls file which contains the voting
 * results.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet(name = "votingXls", urlPatterns = { "/voting-xls" })
public class VotingXlsServlet extends HttpServlet {

	/** The generated serial user ID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/voting-results.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<String, Integer> mapResults = new HashMap<>();

		String fileNameDef = req.getServletContext().getRealPath("/WEB-INF/voting-definition.txt");

		List<String> linesDef = Files.readAllLines(Paths.get(fileNameDef));
		List<String> listResults = new ArrayList<>();
		linesDef.forEach(z -> {
			listResults.add(z.split("\t")[1]);
		});

		for (int i = 0; i < lines.size(); i++) {
			String z = lines.get(i);
			String[] linesSplit = z.split("\t");

			mapResults.put(listResults.get(i), Integer.parseInt(linesSplit[1]));
		}

		req.setAttribute("mapResults", mapResults);

		HSSFWorkbook hwb = generateXLS(mapResults);
		hwb.write(resp.getOutputStream());

		resp.setContentType("application/xls; charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");

	}

	/**
	 * Generates an xls workbook.
	 * 
	 * @param mapResults
	 *            the voting results which will be written to the xls file
	 *
	 * @return the instance of {@linkplain HSSFWorkbook}
	 */
	public static HSSFWorkbook generateXLS(Map<String, Integer> mapResults) {

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet;
		HSSFRow row;

		int cnt;
		cnt = 0;
		sheet = hwb.createSheet("sheet 0");
		row = sheet.createRow((short) cnt++);
		row.createCell(0).setCellValue("band");
		row.createCell(1).setCellValue("result");

		for (String z : mapResults.keySet()) {
			row = sheet.createRow((short) cnt++);
			row.createCell(0).setCellValue(z);
			row.createCell(1).setCellValue(mapResults.get(z));
		}

		return hwb;
	}
}
