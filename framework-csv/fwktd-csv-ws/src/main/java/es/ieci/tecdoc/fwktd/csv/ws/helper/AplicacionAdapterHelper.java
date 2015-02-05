package es.ieci.tecdoc.fwktd.csv.ws.helper;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm;

/**
 * Utilidad para la adaptación de objetos con la información de aplicaciones
 * externas desde el fwktd-csv-api.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionAdapterHelper {

	public static List<AplicacionCSV> getListaAplicacionCSV(List<es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV> aplicacionesCore) {

		List<AplicacionCSV> aplicaciones = null;

		if (aplicacionesCore != null) {
			aplicaciones = new ArrayList<AplicacionCSV>();
			for (es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV aplicacionCore : aplicacionesCore) {
				aplicaciones.add(getAplicacionCSV(aplicacionCore));
			}
		}

		return aplicaciones;
	}

	public static AplicacionCSV getAplicacionCSV(es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV aplicacionCore) {

		AplicacionCSV aplicacion = null;

		if (aplicacionCore != null) {
			aplicacion = new AplicacionCSV();
			aplicacion.setId(aplicacionCore.getId());
			aplicacion.setCodigo(aplicacionCore.getCodigo());
			aplicacion.setNombre(aplicacionCore.getNombre());
			aplicacion.setInfoConexion(aplicacionCore.getInfoConexion());
		}

		return aplicacion;
	}

	public static es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm getAplicacionCSVFormCore(AplicacionCSVForm aplicacionForm) {

		es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm aplicacionFormCore = null;

		if (aplicacionForm != null) {
			aplicacionFormCore = new es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm();
			aplicacionFormCore.setCodigo(aplicacionForm.getCodigo());
			aplicacionFormCore.setNombre(aplicacionForm.getNombre());
			aplicacionFormCore.setInfoConexion(aplicacionForm.getInfoConexion());
		}

		return aplicacionFormCore;
	}

	public static es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV getAplicacionCSVCore(AplicacionCSV aplicacion) {

		es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV aplicacionCore = null;

		if (aplicacion != null) {
			aplicacionCore = new es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV();
			aplicacionCore.setId(aplicacion.getId());
			aplicacionCore.setCodigo(aplicacion.getCodigo());
			aplicacionCore.setNombre(aplicacion.getNombre());
			aplicacionCore.setInfoConexion(aplicacion.getInfoConexion());
		}

		return aplicacionCore;
	}
}
