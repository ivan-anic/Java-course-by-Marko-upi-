package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * A simple web servlet which generates an .xls file which contains powers of
 * numbers from the given range. <br>
 * Arguments are given as following: <br>
 * <ul>
 * <li>a - (integer from [-100,100]</li>
 * <li>b - (integer from [-100,100]</li>
 * <li>n - (integer from [1,5] (number of pages)</li>
 * </ul>
 * 
 * 
 * @author Ivan Anić
 * @version 1.0
 */
@WebServlet(name = "powers", urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	/** The generated serial user ID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("application/xls; charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");

		int startRange = 0;
		int endRange = 0;
		int numPages = 0;

		try {
			startRange = Integer.parseInt((String) req.getParameter("a"));
			endRange = Integer.parseInt((String) req.getParameter("b"));
			numPages = Integer.parseInt((String) req.getParameter("n"));
		} catch (Exception e) {
			resp.sendRedirect("invalidArguments.jsp");
			return;
		}

		if (startRange < -100 || startRange > 100
				|| endRange < -100 || endRange > 100
				|| numPages < 1 || numPages > 5) {
			resp.sendRedirect("invalidArguments.jsp");
			return;
		}

		HSSFWorkbook hwb = generateXLS(startRange, endRange, numPages);
		hwb.write(resp.getOutputStream());
	}

	/**
	 * Generate an xls workbook..
	 *
	 * @param startRange
	 *            the start range
	 * @param endRange
	 *            the end range
	 * @param numPages
	 *            the num pages
	 * @return the instance of {@linkplain HSSFWorkbook}
	 */
	public static HSSFWorkbook generateXLS(int startRange, int endRange, int numPages) {

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet;
		HSSFRow row;

		int cnt;
		for (int i = 0; i < numPages; i++) {
			cnt = 0;
			sheet = hwb.createSheet("sheet " + (i + 1));
			row = sheet.createRow((short) cnt++);
			row.createCell(0).setCellValue("number");
			row.createCell(1).setCellValue("power");
			for (int j = startRange; j <= endRange; j++, cnt++) {
				row = sheet.createRow((short) cnt);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i + 1));
			}
		}
		return hwb;
	}
}