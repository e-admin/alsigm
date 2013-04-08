package export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExportReport {
	void addCelda(String valor) throws Exception;

	void nuevaTabla(float[] anchuras, String[] titulos);

	void setInfoReport(String titulo, String nombreUsuario) throws Exception;

	void enviaReport(HttpServletRequest request, HttpServletResponse response,
			String nombreInforme) throws Exception;

	void addFila();

	Object newRow();

	void saltoDePagina();

	void setNumResultadosReport(String message, int numResultados)
			throws Exception;
}
