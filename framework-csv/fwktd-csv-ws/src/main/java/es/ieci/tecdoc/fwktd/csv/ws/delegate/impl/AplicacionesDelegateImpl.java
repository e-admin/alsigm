package es.ieci.tecdoc.fwktd.csv.ws.delegate.impl;

import java.util.List;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones;
import es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate;
import es.ieci.tecdoc.fwktd.csv.ws.helper.AplicacionAdapterHelper;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm;

/**
 * Implementación del delegate para gestionar aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionesDelegateImpl implements AplicacionesDelegate {

	/**
	 * Servicio de gestión de aplicaciones.
	 */
	private ServicioAplicaciones servicioAplicaciones = null;

	/**
	 * Constructor.
	 */
	public AplicacionesDelegateImpl() {
		super();
	}

	public ServicioAplicaciones getServicioAplicaciones() {
		return servicioAplicaciones;
	}

	public void setServicioAplicaciones(ServicioAplicaciones servicioAplicaciones) {
		this.servicioAplicaciones = servicioAplicaciones;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate#getAplicaciones()
	 */
	public List<AplicacionCSV> getAplicaciones() {
		return AplicacionAdapterHelper
				.getListaAplicacionCSV(getServicioAplicaciones()
						.getAplicaciones());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate#getAplicacion(java.lang.String)
	 */
	public AplicacionCSV getAplicacion(String id) {
		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones().getAplicacion(id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate#getAplicacionByCodigo(java.lang.String)
	 */
	public AplicacionCSV getAplicacionByCodigo(String codigo) {
		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones()
						.getAplicacionByCodigo(codigo));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate#saveAplicacion(es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm)
	 */
	public AplicacionCSV saveAplicacion(AplicacionCSVForm aplicacionForm) {
		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones().saveAplicacion(
						AplicacionAdapterHelper
								.getAplicacionCSVFormCore(aplicacionForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate#updateAplicacion(es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV)
	 */
	public AplicacionCSV updateAplicacion(AplicacionCSV aplicacion) {
		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones().updateAplicacion(
						AplicacionAdapterHelper
								.getAplicacionCSVCore(aplicacion)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate#deleteAplicacion(java.lang.String)
	 */
	public void deleteAplicacion(String id) {
		getServicioAplicaciones().deleteAplicacion(id);
	}
}
