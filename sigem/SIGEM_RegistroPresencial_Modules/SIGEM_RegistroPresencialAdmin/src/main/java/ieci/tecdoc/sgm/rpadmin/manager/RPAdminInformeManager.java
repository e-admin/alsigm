package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.core.services.rpadmin.InformeBean;
import ieci.tecdoc.sgm.core.services.rpadmin.InformesBean;
import ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminInformeManager;

public class RPAdminInformeManager {
	public static final Logger logger = Logger
			.getLogger(RPAdminInformeManager.class);

	/**
	 * Listado de Informes
	 *
	 * @param entidad
	 * @return
	 * @throws RPAdminDAOException
	 */
	public static InformesBean obtenerInformes(String entidad)
			throws RPAdminDAOException {

		InformesBean informesBean;
		try {
			informesBean = AdapterVOSigem.adapterSIGEMInformesBean(ISicresRPAdminInformeManager.obtenerInformes(entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo informes");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return informesBean;

	}

	/**
	 * Recupera un informe
	 *
	 * @param id
	 * @param entidad
	 * @return
	 * @throws RPAdminDAOException
	 * @throws Exception
	 */
	public static InformeBean obtenerInforme(int id, String entidad,
			OptionsBean listaPerfiles) throws RPAdminDAOException, Exception {

		InformeBean informeBean = AdapterVOSigem
				.adapterSIGEMInformeBean(ISicresRPAdminInformeManager
						.obtenerInforme(id, entidad, AdapterVOSicres
								.adapterISicresOptionsBean(listaPerfiles)));

		return informeBean;

	}

	/**
	 * Recupera un informe
	 *
	 * @param id
	 * @param entidad
	 * @return
	 * @throws RPAdminDAOException
	 * @throws Exception
	 */
	public static InformeBean descargarInforme(int id, String entidad)
			throws RPAdminDAOException, Exception {

		InformeBean informeBean = AdapterVOSigem
				.adapterSIGEMInformeBean(ISicresRPAdminInformeManager
						.descargarInforme(id, entidad));

		return informeBean;

	}

	public static void editarInforme(InformeBean informeBean, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminInformeManager.editarInforme(
					AdapterVOSicres.adapterISicresInformeBean(informeBean), entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el Informe");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void crearInforme(InformeBean informeBean, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminInformeManager.crearInforme(
					AdapterVOSicres.adapterISicresInformeBean(informeBean), entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando el informe");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void eliminarInforme(int id, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminInformeManager.eliminarInforme(id, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando el Informe");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}
}
