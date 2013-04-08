package common.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xml.config.Busqueda;

import common.bi.GestionCuadroClasificacionBI;

import fondos.FondosConstants;
import fondos.model.CamposBusquedas;

/**
 * Action del que heredan todos los action de la aplicación, y que define un
 * conjunto de métodos base utilizados en la capa de vista de la aplicación
 */
public abstract class BusquedaBaseAction extends BaseAction {

	protected final Map getProductores(Busqueda busqueda,
			List listaIdsElementos, GestionCuadroClasificacionBI serviceCF) {
		if (busqueda.getMapSalida().containsKey(
				CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_PRODUCTOR)) {
			return serviceCF.getProductoresByIdsElementoCF(listaIdsElementos);
		}
		return null;
	}

	protected final Map getInteresados(Busqueda busqueda,
			List listaIdsElementos, GestionCuadroClasificacionBI serviceCF) {
		if (busqueda.getMapSalida().containsKey(
				CamposBusquedas.CAMPO_SALIDA_BUSQUEDA_INTERESADOS)) {
			return serviceCF.getInteresadosByIdsElementoCF(listaIdsElementos);
		}
		return null;
	}

	protected final List getListaIdsElementos(HttpServletRequest request) {
		List listaIdsElementos = (List) getFromTemporalSession(request,
				FondosConstants.LISTA_IDS_ELEMENTOS_CF);

		if (listaIdsElementos == null)
			listaIdsElementos = new ArrayList();

		return listaIdsElementos;
	}

}