package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException;
import ieci.tecdoc.sgm.core.services.rpadmin.TipoAsunto;
import ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.TiposAsuntoBean;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminTipoAsuntoManager;

public class RPAdminTipoAsuntoManager {
	public static final Logger logger = Logger
			.getLogger(RPAdminTipoAsuntoManager.class);

	public static TiposAsuntoBean obtenerTiposAsunto(String entidad)
			throws RPAdminDAOException {

		TiposAsuntoBean tiposAsunto = null;
		try {
			tiposAsunto = AdapterVOSigem
					.adapterSIGEMTiposAsuntoBean(ISicresRPAdminTipoAsuntoManager
							.obtenerTiposAsunto(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo tipos de Asunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return tiposAsunto;
	}

	public static void crearTipoAsunto(TipoAsuntoBean tipoAsuntoBean,
			String entidad) throws RPAdminDAOException {

		try {
			ISicresRPAdminTipoAsuntoManager.crearTipoAsunto(
					AdapterVOSicres.adapterISicresTipoAsuntoBean(tipoAsuntoBean),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando Tipo de Asunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void editarTipoAsunto(TipoAsuntoBean tipoAsuntoBean,
			String entidad) throws RPAdminDAOException {

		try {

			ISicresRPAdminTipoAsuntoManager.editarTipoAsunto(
					AdapterVOSicres.adapterISicresTipoAsuntoBean(tipoAsuntoBean),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el TipoAsunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void eliminarTipoAsunto(int id, String entidad)
			throws RPAdminDAOException {

		try {

			ISicresRPAdminTipoAsuntoManager.eliminarTipoAsunto(id, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando el tipoasunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static TipoAsuntoBean obtenerTipoAsunto(int id, String entidad)
			throws RPAdminDAOException, Exception {

		TipoAsuntoBean tipoAsuntoBean = null;

		tipoAsuntoBean = AdapterVOSigem
				.adapterSIGEMTipoAsuntoBean(ISicresRPAdminTipoAsuntoManager
						.obtenerTipoAsunto(id, entidad));

		return tipoAsuntoBean;

	}

	public static void asociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, String entidad)
			throws RPAdminDAOException {

		try {

			ISicresRPAdminTipoAsuntoManager.asociarDocumentoTipoAsunto(
					AdapterVOSicres.adapterISicresDocumentoTipoAsuntoBean(documento),
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando el documento del tipo de asunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void asociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			String entidad) throws RPAdminException {

		try {

			ISicresRPAdminTipoAsuntoManager.asociarOficinaTipoAsunto(
					AdapterVOSicres.adapterISicresOficinaTipoAsuntoBean(oficina),
					entidad);

		} catch (ISicresRPAdminException e) {
			logger.error("Error creando la oficina del tipo de asunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void desasociarOficinaTipoAsunto(
			OficinaTipoAsuntoBean oficina, String entidad)
			throws RPAdminException {

		try {

			ISicresRPAdminTipoAsuntoManager.desasociarOficinaTipoAsunto(
					AdapterVOSicres.adapterISicresOficinaTipoAsuntoBean(oficina),
					entidad);

		} catch (ISicresRPAdminException e) {
			logger.error("Error eliminando la oficina del tipo asunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void desasociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, String entidad)
			throws RPAdminException {

		try {
			ISicresRPAdminTipoAsuntoManager.desasociarDocumentoTipoAsunto(
					AdapterVOSicres.adapterISicresDocumentoTipoAsuntoBean(documento),
					entidad);
		} catch (ISicresRPAdminException e) {
			logger.error("Error eliminando la oficina del tipo asunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void editarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, String entidad)
			throws RPAdminException {

		try {

			ISicresRPAdminTipoAsuntoManager.editarDocumentoTipoAsunto(
					AdapterVOSicres.adapterISicresDocumentoTipoAsuntoBean(documento),
					entidad);

		} catch (ISicresRPAdminException e) {
			logger.error("Error creando el documento del tipo de asunto");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static TipoAsunto getTipoAsuntoByCode(String codigo, String entidad)
			throws Exception {

		TipoAsunto tipoAsunto = null;

		tipoAsunto = AdapterVOSigem
				.adapterSIGEMTipoAsunto(ISicresRPAdminTipoAsuntoManager
						.getTipoAsuntoByCode(codigo, entidad));

		return tipoAsunto;

	}

}
