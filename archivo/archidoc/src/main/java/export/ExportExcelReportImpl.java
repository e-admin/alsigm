package export;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.util.ResponseUtil;

public class ExportExcelReportImpl implements ExportReport {
	ExcelReport report = null;

	int numColumnas = 0;
	int columnasEnFilaActual = 0;
	int numFilas = 0;
	int numHojas = 0;
	String[] titulosColumnas = null;

	private final static int MAX_ROW_EXCEL = 64000;

	public ExportExcelReportImpl() {
		report = new ExcelReport();
	}

	public void setInfoReport(String titulo, String nombreUsuario)
			throws Exception {
		numHojas++;
		report.addHoja("" + numHojas);
	}

	public void nuevaTabla(float[] anchuras, String[] titulos) {
		titulosColumnas = titulos;
		numColumnas = anchuras.length;
		numFilas++;
		for (short i = 0; i < numColumnas; i++) {
			report.addCelda(i, titulos[i]);
		}
		report.addFila();
		columnasEnFilaActual = 0;
	}

	public void addCelda(String valor) throws Exception {
		report.addCelda(((short) columnasEnFilaActual), valor);
		columnasEnFilaActual++;
		if (columnasEnFilaActual == numColumnas) {
			report.addFila();
			numFilas++;
			columnasEnFilaActual = 0;
			if (numFilas == MAX_ROW_EXCEL) {
				numHojas++;
				report.addHoja("" + numHojas);

				numFilas = 1;
				for (short i = 0; i < numColumnas; i++) {
					report.addCelda(i, titulosColumnas[i]);
				}
				report.addFila();
			}
		}
	}

	public void enviaReport(HttpServletRequest request,
			HttpServletResponse response, String nombreInforme)
			throws Exception {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", new StringBuffer(
				"attachment; filename=\"").append(nombreInforme + ".xls")
				.append("\"").toString());
		ResponseUtil.getInstance().agregarCabecerasHTTP(response);
		response.setContentLength(report.getBytes().length);

		ServletOutputStream os = response.getOutputStream();
		os.write(report.getBytes(), 0, report.getBytes().length);
		os.flush();
		os.close();
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
		report.addFila();
		report.addCelda((short) 1, message + " " + numResultados);
		report.addFila();
		report.addFila();
	}
}
