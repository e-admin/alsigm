package es.ieci.tecdoc.fwktd.csv.wsclient.helper;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;


/**
 * Utilidad para la adaptación de objetos con la información de aplicaciones
 * externas desde el fwktd-csv-ws.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionAdapterHelper {

	public static List<AplicacionCSV> getListaAplicacionCSV(List<es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV> aplicacionesWS) {

		List<AplicacionCSV> aplicacionesCSV = null;

		if (aplicacionesWS != null) {
			aplicacionesCSV = new ArrayList<AplicacionCSV>();
			for (es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacionWS : aplicacionesWS) {
				aplicacionesCSV.add(getAplicacionCSV(aplicacionWS));
			}
		}

		return aplicacionesCSV;
	}

	public static AplicacionCSV getAplicacionCSV(es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacionWS) {

		AplicacionCSV aplicacionCSV = null;

		if (aplicacionWS != null) {
			aplicacionCSV = new AplicacionCSV();
			aplicacionCSV.setId(aplicacionWS.getId());
			aplicacionCSV.setCodigo(aplicacionWS.getCodigo());
			aplicacionCSV.setNombre(aplicacionWS.getNombre());
			aplicacionCSV.setInfoConexion(aplicacionWS.getInfoConexion());
		}

		return aplicacionCSV;
	}

	public static es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm getWSAplicacionCSVForm(AplicacionCSVForm aplicacionForm) {

		es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm aplicacionFormWS = null;

		if (aplicacionForm != null) {
			aplicacionFormWS = new es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm();
			aplicacionFormWS.setCodigo(aplicacionForm.getCodigo());
			aplicacionFormWS.setNombre(aplicacionForm.getNombre());
			aplicacionFormWS.setInfoConexion(aplicacionForm.getInfoConexion());
		}

		return aplicacionFormWS;
	}

	public static es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV getWSAplicacionCSV(AplicacionCSV aplicacionCSV) {

		es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV aplicacionWS = null;

		if (aplicacionCSV != null) {
			aplicacionWS = new es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV();
			aplicacionWS.setId(aplicacionCSV.getId());
			aplicacionWS.setCodigo(aplicacionCSV.getCodigo());
			aplicacionWS.setNombre(aplicacionCSV.getNombre());
			aplicacionWS.setInfoConexion(aplicacionCSV.getInfoConexion());
		}

		return aplicacionWS;
	}
}
