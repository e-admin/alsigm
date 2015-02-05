package es.ieci.tecdoc.isicres.api.business.vo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que se emplea para envolver las plantillas de informes de Jasper
 * Reports recuperadas de base de datos.
 * 
 * @author IECISA
 * 
 */
public class ReportObjects {

	/**
	 * Constructor por defecto de la clase.
	 */
	public ReportObjects() {
	}

	public InputStream getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(InputStream reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public Map getSubreports() {
		if (null == subreports) {
			subreports = new HashMap();
		}
		return subreports;
	}

	public void setSubreports(Map subreports) {
		this.subreports = subreports;
	}

	// Members
	protected InputStream reportTemplate;

	/**
	 * Mapa
	 */
	protected Map subreports;
}
