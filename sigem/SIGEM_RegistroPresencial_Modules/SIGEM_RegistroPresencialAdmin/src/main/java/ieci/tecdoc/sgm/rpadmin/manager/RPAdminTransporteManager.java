package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.core.services.rpadmin.Transporte;
import ieci.tecdoc.sgm.core.services.rpadmin.Transportes;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminTransporteManager;

public class RPAdminTransporteManager{
	public static final Logger logger = Logger
			.getLogger(RPAdminTransporteManager.class);

	public static Transportes obtenerTransportes(String entidad)
			throws RPAdminDAOException {
		Transportes transportes = null;
		try {

			transportes = AdapterVOSigem
					.adapterSIGEMTransportes(ISicresRPAdminTransporteManager
							.obtenerTransportes(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo transportes");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return transportes;
	}

	public static void crearTransporte(Transporte transporte, String entidad)
			throws RPAdminDAOException {

		try {

			ISicresRPAdminTransporteManager.crearTransporte(
					AdapterVOSicres.adapterISicresTransporte(transporte), entidad);

		} catch (ISicresRPAdminDAOException e) {
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void editarTransporte(Transporte transporte,String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminTransporteManager.editarTransporte(AdapterVOSicres.adapterISicresTransporte(transporte), entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el Transporte");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void eliminarTransporte(int id, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminTransporteManager.eliminarTransporte(id, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando el transporte");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}


	public static Transporte obtenerTransporte(int id, String entidad)
			throws Exception {

		Transporte transporte = null;

		transporte = AdapterVOSigem
				.adapterSIGEMTransporte(ISicresRPAdminTransporteManager
						.obtenerTransporte(id, entidad));
		return transporte;
	}

}
