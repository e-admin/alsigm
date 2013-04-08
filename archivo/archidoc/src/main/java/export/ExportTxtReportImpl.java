package export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.ExceptionConverter;
import common.util.ResponseUtil;

public class ExportTxtReportImpl implements ExportReport {
	int numColumnas = 0;
	int columnasEnFilaActual = 0;
	File f = null;
	// FileWriter fw=null;
	PrintWriter pw;
	final int BUFFER_SIZE = 5000; // Bytes

	public ExportTxtReportImpl() {
		try {
			f = File.createTempFile("temp", "");
			pw = new PrintWriter(new FileWriter(f));
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	public void setInfoReport(String titulo, String nombreUsuario)
			throws Exception {
		pw.println(titulo.toUpperCase());
		pw.println();
		pw.flush();
	}

	public void nuevaTabla(float[] anchuras, String[] titulos) {
		numColumnas = anchuras.length;
		for (short i = 0; i < numColumnas; i++) {
			try {
				pw.print(titulos[i] + "\t");
			} catch (Exception e) {
				continue;
			}
		}
		try {
			pw.println();
			pw.flush();
		} catch (Exception e) {
		}
		columnasEnFilaActual = 0;
	}

	public void addCelda(String valor) throws Exception {
		if (valor != null)
			pw.print(valor);
		columnasEnFilaActual++;
		if (columnasEnFilaActual == numColumnas) {
			pw.println();
			columnasEnFilaActual = 0;
		} else {
			pw.print("\t");
		}
		pw.flush();
	}

	public void enviaReport(HttpServletRequest request,
			HttpServletResponse response, String nombreInforme)
			throws Exception {
		response.setContentType("application/text");
		response.setHeader("Content-disposition", new StringBuffer(
				"attachment; filename=\"").append(nombreInforme + ".txt")
				.append("\"").toString());
		ResponseUtil.getInstance().agregarCabecerasHTTP(response);
		pw.flush();
		pw.close();
		int length = (int) f.length();
		response.setContentLength(length);

		ServletOutputStream os = response.getOutputStream();
		FileInputStream fis = new FileInputStream(f);
		// int bytesReadedInBuffer=0;

		// byte [] buffer=new byte [BUFFER_SIZE];
		// while((bytesReadedInBuffer=fr.read(buffer))>0){
		// os.write(buffer, 0, bytesReadedInBuffer);
		// }

		byte[] b = new byte[BUFFER_SIZE];
		int bytesTotalesLeidos = 0;
		while (length > bytesTotalesLeidos) {
			int bytesLeidos = fis.read(b, 0, BUFFER_SIZE);
			if (bytesLeidos == -1)
				break;
			os.write(b, 0, bytesLeidos);
			bytesTotalesLeidos += bytesLeidos;
		}

		os.flush();
		os.close();
		fis.close();
		f.delete();
	}

	public Object newRow() {
		return null;
	}

	public void saltoDePagina() {

	}

	public void addFila() {

	}

	public void setNumResultadosReport(String message, int numResultados)
			throws Exception {
		pw.println();
		pw.print(message.toUpperCase() + " ");
		pw.println(numResultados);
		pw.println();
		pw.flush();
	}
}
