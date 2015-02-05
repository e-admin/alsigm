package es.ieci.tecdoc.fwktd.csv.ws.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.csv.ws.delegate.AplicacionesDelegate;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.ws.service.AplicacionCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.ServicioAplicacionesPortType;

/**
 * Implementación del servicio web de gestión de aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioAplicacionesWSImpl implements ServicioAplicacionesPortType {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServicioAplicacionesWSImpl.class);

	/**
	 * Delegate para la gestión de aplicaciones externas.
	 */
	private AplicacionesDelegate aplicacionesDelegate = null;

	/**
	 * Constructor.
	 */
	public ServicioAplicacionesWSImpl() {
		super();
	}

	public AplicacionesDelegate getAplicacionesDelegate() {
		return aplicacionesDelegate;
	}

	public void setAplicacionesDelegate(AplicacionesDelegate aplicacionesDelegate) {
		this.aplicacionesDelegate = aplicacionesDelegate;
	}

	/**
	 * Obtiene el listado de aplicaciones externas.
	 *
	 * @return Listado de aplicaciones.
	 */
	public List<AplicacionCSV> getAplicaciones() {

		logger.info("Llamada a getAplicaciones");

		return getAplicacionesDelegate().getAplicaciones();
	}

	/**
	 * Obtiene la información de la aplicación externa.
	 *
	 * @param id
	 *            Identificador de la aplicación externa.
	 * @return Información de la aplicación externa.
	 */
	public AplicacionCSV getAplicacion(String id) {

		logger.info("Llamada a getAplicacion: id=[{}]", id);

		return getAplicacionesDelegate().getAplicacion(id);
	}

	/**
	 * Obtiene la información de la aplicación externa.
	 *
	 * @param codigo
	 *            Código de la aplicación externa.
	 * @return Información de la aplicación externa.
	 */
	public AplicacionCSV getAplicacionByCodigo(String codigo) {

		logger.info("Llamada a getAplicacionByCodigo: codigo=[{}]", codigo);

		return getAplicacionesDelegate().getAplicacionByCodigo(codigo);
	}

	/**
	 * Guarda la información de una aplicación externa.
	 *
	 * @param aplicacionForm
	 *            Información de la nueva aplicación externa.
	 * @return Información de la aplicación externa creada.
	 */
	public AplicacionCSV saveAplicacion(AplicacionCSVForm aplicacionForm) {

		logger.info("Llamada a saveAplicacion: aplicacionForm=[{}]", aplicacionForm);

		return getAplicacionesDelegate().saveAplicacion(aplicacionForm);
	}

	/**
	 * Actualiza la información de una aplicación externa existente.
	 *
	 * @param aplicacion
	 *            Información de la aplicación externa.
	 * @return Información de la aplicación externa modificada.
	 */
	public AplicacionCSV updateAplicacion(AplicacionCSV aplicacion) {

		logger.info("Llamada a updateAplicacion: aplicacion=[{}]", aplicacion);

		return getAplicacionesDelegate().updateAplicacion(aplicacion);
	}

	/**
	 * Elimina la información de la aplicación externa.
	 *
	 * @param id
	 *            Identificador de la aplicación externa.
	 */
	public void deleteAplicacion(String id) {

		logger.info("Llamada a deleteAplicacion: id=[{}]", id);

		getAplicacionesDelegate().deleteAplicacion(id);
	}
}
