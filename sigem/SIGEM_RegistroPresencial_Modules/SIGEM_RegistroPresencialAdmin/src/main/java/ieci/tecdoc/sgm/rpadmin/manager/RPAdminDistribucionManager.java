package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionesImpl;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminDistribucionManager;

/*$Id*/

public class RPAdminDistribucionManager {
	private static final Logger logger = Logger
			.getLogger(RPAdminDistribucionManager.class);

	public static SicresListaDistribucionesImpl obtenerDistribuciones(
			int idOrg, String entidad) throws RPAdminDAOException {


		SicresListaDistribucionesImpl lista = null;

		try {
			lista = AdapterVOSigem
					.adapterSIGEMListaDistribucionesImpl(ISicresRPAdminDistribucionManager
							.obtenerDistribuciones(idOrg, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo el listado de distribuciones");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;
	}

	public static void crearDistribuciones(SicresListaDistribucionesImpl lista,
			String entidad) throws RPAdminDAOException {

		try {
			ISicresRPAdminDistribucionManager
					.crearDistribuciones(
							AdapterVOSicres.adapterISicresListaDistribucionesImpl(lista),
							entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando distribuciones");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void eliminarDistribucion(int id, String entidad)
			throws RPAdminDAOException {

		try {

			ISicresRPAdminDistribucionManager.eliminarDistribucion(id, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando distribuciones");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}
}
