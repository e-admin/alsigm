package es.ieci.tecdoc.fwktd.csv.api.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import es.ieci.tecdoc.fwktd.csv.api.helper.AplicacionHelper;
import es.ieci.tecdoc.fwktd.csv.api.manager.AplicacionManager;
import es.ieci.tecdoc.fwktd.csv.core.exception.CSVException;
import es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;

/**
 * Implementación local del servicio de gestión de aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ServicioAplicacionesImpl implements ServicioAplicaciones {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServicioAplicacionesImpl.class);

	/**
	 * Manager de aplicaciones.
	 */
	private AplicacionManager aplicacionManager = null;

	/**
	 * Constructor.
	 */
	public ServicioAplicacionesImpl() {
		super();
	}

	public AplicacionManager getAplicacionManager() {
		return aplicacionManager;
	}

	public void setAplicacionManager(AplicacionManager aplicacionManager) {
		this.aplicacionManager = aplicacionManager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#getAplicaciones()
	 */
	public List<AplicacionCSV> getAplicaciones() {

		logger.info("Llamada a getAplicaciones");

		return AplicacionHelper.getListaAplicacionCSV(getAplicacionManager()
				.getAll());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#getAplicacion(java.lang.String)
	 */
	public AplicacionCSV getAplicacion(String id) {

		logger.info("Llamada a getAplicacion: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		return AplicacionHelper
				.getAplicacionCSV(getAplicacionManager().get(id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#getAplicacionByCodigo(java.lang.String)
	 */
	public AplicacionCSV getAplicacionByCodigo(String codigo) {

		logger.info("Llamada a getAplicacionByCodigo: codigo=[{}]", codigo);

		Assert.hasText(codigo, "'codigo' must not be empty");

		return AplicacionHelper.getAplicacionCSV(getAplicacionManager()
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

		return AplicacionHelper.getAplicacionCSV(getAplicacionManager().save(
				AplicacionHelper.getAplicacionVO(aplicacionForm)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#updateAplicacion(es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV)
	 */
	public AplicacionCSV updateAplicacion(AplicacionCSV aplicacion) {

		logger.info("Llamada a updateAplicacion: aplicacion=[{}]", aplicacion);

		Assert.notNull(aplicacion, "'aplicacion' must not be null");
		Assert.hasText(aplicacion.getId(),
				"'aplicacion.id' must not be empty");
		Assert.hasText(aplicacion.getCodigo(),
				"'aplicacion.codigo' must not be empty");
		Assert.hasText(aplicacion.getNombre(),
				"'aplicacion.nombre' must not be empty");
		Assert.hasText(aplicacion.getInfoConexion(),
				"'aplicacion.infoConexion' must not be empty");

		if (!getAplicacionManager().exists(aplicacion.getId())) {
			throw new CSVException(
					"error.csv.application.idNotFound",
					new String[] { aplicacion.getId() },
					"No existe ninguna aplicación con el identificador: "
							+ aplicacion.getId());
		}

		return AplicacionHelper.getAplicacionCSV(getAplicacionManager().update(
				AplicacionHelper.getAplicacionVO(aplicacion)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.core.service.ServicioAplicaciones#deleteAplicacion(java.lang.String)
	 */
	public void deleteAplicacion(String id) {

		logger.info("Llamada a deleteAplicacion: id=[{}]", id);

		Assert.hasText(id, "'id' must not be empty");

		getAplicacionManager().delete(id);
	}

}
