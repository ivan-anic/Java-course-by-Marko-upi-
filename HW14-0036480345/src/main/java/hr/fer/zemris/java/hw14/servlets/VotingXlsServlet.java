package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOptions;

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

		List<PollOptions> optionsList = DAOProvider.getDao()
				.getPollOptions((int) req.getSession().getAttribute("pollID"));

		HSSFWorkbook hwb = generateXLS(optionsList);
		hwb.write(resp.getOutputStream());

		resp.setContentType("application/xls; charset=UTF-8");
		resp.setHeader("Content-Disposition", "attachment; filename=voting-results.xls");

	}

	/**
	 * Generates an xls workbook.
	 * 
	 * @param optionsList
	 *            the voting results which will be written to the xls file
	 *
	 * @return the instance of {@linkplain HSSFWorkbook}
	 */
	public static HSSFWorkbook generateXLS(List<PollOptions> optionsList) {

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet;
		HSSFRow row;

		int cnt;
		cnt = 0;
		sheet = hwb.createSheet("sheet 0");
		row = sheet.createRow((short) cnt++);
		row.createCell(0).setCellValue("item");
		row.createCell(1).setCellValue("result");

		for (PollOptions z : optionsList) {
			row = sheet.createRow((short) cnt++);
			row.createCell(0).setCellValue(z.getOptionTitle());
			row.createCell(1).setCellValue(z.getVotesCount());
		}

		return hwb;
	}
}
