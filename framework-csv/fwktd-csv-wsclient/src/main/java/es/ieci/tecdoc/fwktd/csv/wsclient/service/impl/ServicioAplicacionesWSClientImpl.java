package es.ieci.tecdoc.fwktd.csv.wsclient.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;
import es.ieci.tecdoc.fwktd.csv.ws.service.ServicioAplicacionesPortType;
import es.ieci.tecdoc.fwktd.csv.wsclient.helper.AplicacionAdapterHelper;

/**
 * Implementación del servicio de gestión de aplicaciones externas que utiliza
 * el servicio web.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioAplicacionesWSClientImpl implements ServicioAplicaciones {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServicioAplicacionesWSClientImpl.class);

	/**
	 * Cliente del servicio web de gestión de aplicaciones.
	 */
	private ServicioAplicacionesPortType servicioAplicaciones = null;

	/**
	 * Constructor.
	 */
	public ServicioAplicacionesWSClientImpl() {
		super();
	}

	public ServicioAplicacionesPortType getServicioAplicaciones() {
		return servicioAplicaciones;
	}

	public void setServicioAplicaciones(
			ServicioAplicacionesPortType servicioAplicaciones) {
		this.servicioAplicaciones = servicioAplicaciones;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#getAplicaciones()
	 */
	public List<AplicacionCSV> getAplicaciones() {

		logger.info("Llamada a getAplicaciones");

		return AplicacionAdapterHelper
				.getListaAplicacionCSV(getServicioAplicaciones()
						.getAplicaciones());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#getAplicacion(java.lang.String)
	 */
	public AplicacionCSV getAplicacion(String id) {

		logger.info("Llamada a getAplicacion: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones()
						.getAplicacion(id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#getAplicacionByCodigo(java.lang.String)
	 */
	public AplicacionCSV getAplicacionByCodigo(String codigo) {

		logger.info("Llamada a getAplicacionByCodigo: codigo=[{}]", codigo);

		Assert.hasText(codigo, "'codigo' must not be empty");

		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones()
						.getAplicacionByCodigo(codigo));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#saveAplicacion(es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm)
	 */
	public AplicacionCSV saveAplicacion(AplicacionCSVForm aplicacionForm) {

		logger.info("Llamada a saveAplicacion: aplicacion=[{}]", aplicacionForm);

		Assert.notNull(aplicacionForm, "'aplicacion' must not be null");
		Assert.hasText(aplicacionForm.getCodigo(),
				"'aplicacion.codigo' must not be empty");
		Assert.hasText(aplicacionForm.getNombre(),
				"'aplicacion.nombre' must not be empty");
		Assert.hasText(aplicacionForm.getInfoConexion(),
				"'aplicacion.infoConexion' must not be empty");

		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones()
						.saveAplicacion(
								AplicacionAdapterHelper
										.getWSAplicacionCSVForm(aplicacionForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#updateAplicacion(es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV)
	 */
	public AplicacionCSV updateAplicacion(AplicacionCSV aplicacion) {

		logger.info("Llamada a updateAplicacion: aplicacion=[{}]", aplicacion);

		Assert.notNull(aplicacion, "'aplicacion' must not be null");
		Assert.hasText(aplicacion.getId(), "'aplicacion.id' must not be empty");
		Assert.hasText(aplicacion.getCodigo(),
				"'aplicacion.codigo' must not be empty");
		Assert.hasText(aplicacion.getNombre(),
				"'aplicacion.nombre' must not be empty");
		Assert.hasText(aplicacion.getInfoConexion(),
				"'aplicacion.infoConexion' must not be empty");

		return AplicacionAdapterHelper
				.getAplicacionCSV(getServicioAplicaciones().updateAplicacion(
						AplicacionAdapterHelper.getWSAplicacionCSV(aplicacion)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#deleteAplicacion(java.lang.String)
	 */
	public void deleteAplicacion(String id) {

		logger.info("Llamada a deleteAplicacion: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		getServicioAplicaciones().deleteAplicacion(id);
	}
}
