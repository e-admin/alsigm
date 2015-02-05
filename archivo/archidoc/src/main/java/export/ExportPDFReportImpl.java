package export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.reports.PdfReportFileStream;
import common.reports.PdfReportPageEventHelper;
import common.reports.PdfReportTable;

public class ExportPDFReportImpl implements ExportReport {
	PdfReportFileStream report = null;
	PdfReportTable table = null;

	String tituloInforme = "";
	float[] anchurasColumnas;
	String[] titulosColumnas;

	int numColumnas = 0;
	int columnasEnFilaActual = 0;

	double alturaFilas = 0.0;
	double pageHeight = 0.0;
	double alturaInicialTabla = 0.0;
	String[] valoresFila = null;

	String numResultadosInforme = "";
	int numPagina = 1;

	public ExportPDFReportImpl(PdfReportPageEventHelper helper, String entity) {
		report = new PdfReportFileStream(helper, entity);
		pageHeight = report.getPageSize().height() - report.bottomMargin()
				- report.topMargin() - 40.0;
	}

	public void setInfoReport(String titulo, String nombreUsuario)
			throws Exception {
		report.addTitle(titulo);
		report.addAuthor(nombreUsuario);
		report.addSubject(titulo);
		report.open();
		report.addReportTitle(titulo);
		tituloInforme = titulo;
	}

	public void nuevaTabla(float[] anchuras, String[] titulos) {
		titulosColumnas = titulos;
		anchurasColumnas = anchuras;
		table = report.createTable(anchurasColumnas);
		table.addColumnTitles(titulosColumnas);

		numColumnas = anchurasColumnas.length;
		columnasEnFilaActual = 0;
		alturaInicialTabla = table.getHeaderHeight() + 30.0;
		alturaFilas = alturaInicialTabla;
		if (valoresFila == null)
			valoresFila = new String[titulos.length];
	}

	private void resetValoresFila() {
		columnasEnFilaActual = 0;
		for (int i = 0; i < numColumnas; i++)
			valoresFila[i] = null;
	}

	private void reAddLastRow() throws Exception {
		for (int i = 0; i < numColumnas; i++)
			addCelda(valoresFila[i]);
	}

	public void addCelda(String valor) throws Exception {
		table.addRowValue(valor);
		valoresFila[columnasEnFilaActual] = valor;
		columnasEnFilaActual++;
		if (columnasEnFilaActual == numColumnas) {
			int filas = table.getRows().size();
			alturaFilas += table.getRow(filas - 1).getMaxHeights();

			double currentPageHeight = pageHeight;
			if (numPagina == 1) {
				currentPageHeight = currentPageHeight - 15.0;
			}

			if (alturaFilas >= currentPageHeight) {
				table.deleteLastRow();
				report.add(table);
				report.newPage();
				numPagina++;
				report.addReportTitle(tituloInforme);
				alturaFilas = alturaInicialTabla;
				nuevaTabla(anchurasColumnas, titulosColumnas);
				reAddLastRow();
			}
			resetValoresFila();
		}
	}

	public void enviaReport(HttpServletRequest request,
			HttpServletResponse response, String nombreInforme)
			throws Exception {
		report.add(table);
		report.addMessage(numResultadosInforme);
		report.close();
		report.outReport(nombreInforme, response, request);
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
		numResultadosInforme = message + " " + numResultados;
		report.addMessage(numResultadosInforme);
	}
}
