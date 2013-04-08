package common.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.pdf.PdfWriter;
import common.Constants;
import common.util.ResponseUtil;

/**
 * Clase que encapsula la funcionalidad de creación de informes.
 */
public class PdfReportFileStream extends PdfReport {
	FileOutputStream fos = null;
	File f = null;

	public PdfReportFileStream(PdfReportPageEventHelper helper, String entity) {
		super(helper, entity);
		try {
			f = File.createTempFile("temp", "");
			fos = new FileOutputStream(f);
			PdfWriter writer = PdfWriter.getInstance(this, fos);
			writer.setPageEvent(helper);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	public final boolean outReport(String reportName,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		FileInputStream fis = new FileInputStream(f);
		if (fis != null && f.length() > 0) {
			int fileSize = (int) f.length();
			response.setContentType(Constants.TIPO_APLICACION_PDF);
			response.setHeader("Content-disposition", "attachment; filename="
					+ reportName + ".pdf");
			ResponseUtil.getInstance().agregarCabecerasHTTP(response);
			response.setContentLength(fileSize);

			ServletOutputStream ouputStream = response.getOutputStream();

			// para no saturar la memoria del servidor
			int lengthBuffer = 1024 * 1024;
			byte[] b = new byte[lengthBuffer];
			int bytesTotalesLeidos = 0;
			while (fileSize > bytesTotalesLeidos) {
				int bytesLeidos = fis.read(b, 0, lengthBuffer);
				if (bytesLeidos == -1)
					break;
				ouputStream.write(b, 0, bytesLeidos);
				bytesTotalesLeidos += bytesLeidos;
			}

			ouputStream.flush();
			ouputStream.close();
			fis.close();
			f.delete();
			return true;
		}
		return false;
		// setReturnActionFordward(request, actionForward);
	}
}
