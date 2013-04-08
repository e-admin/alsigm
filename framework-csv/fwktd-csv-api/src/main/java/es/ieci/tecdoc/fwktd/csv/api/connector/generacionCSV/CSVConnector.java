package es.ieci.tecdoc.fwktd.csv.api.connector.generacionCSV;

import es.ieci.tecdoc.fwktd.csv.api.connector.Connector;
import es.ieci.tecdoc.fwktd.csv.core.vo.InfoDocumentoCSVForm;

/**
 * Interfaz para los conectores de generación de Códigos Seguros de Verificación
 * (CSV).
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface CSVConnector extends Connector {

	/**
	 * Genera el CSV para un documento.
	 *
	 * @param infoDocumentoForm
	 *            Información del documento.
	 * @return Código Seguro de Verificación (CSV)
	 */
	public String generarCSV(InfoDocumentoCSVForm infoDocumentoForm);

}
