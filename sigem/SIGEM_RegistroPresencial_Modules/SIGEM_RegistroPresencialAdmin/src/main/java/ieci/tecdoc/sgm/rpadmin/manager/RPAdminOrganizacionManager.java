package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.rpadmin.beans.SicresOficinasImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionLocalizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionesImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOrganizacionesImpl;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminOrganizacionManager;

/*$Id*/

public class RPAdminOrganizacionManager {
	private static final Logger logger = Logger
			.getLogger(RPAdminOrganizacionManager.class);

	public static SicresOrganizacionesImpl obtenerOrganizacionesPorTipo(
			int type, String entidad) throws RPAdminDAOException {

		SicresOrganizacionesImpl organizaciones = null;
		try {
			organizaciones = AdapterVOSigem
					.adapterSIGEMSicresOrganizacionesImpl(ISicresRPAdminOrganizacionManager
							.obtenerOrganizacionesPorTipo(type, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo organizaciones");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return organizaciones;

	}

	public static SicresOrganizacionesImpl obtenerOrganizaciones(int idPadre,
			String entidad) throws RPAdminDAOException {

		SicresOrganizacionesImpl lista = null;

		try {
			lista = AdapterVOSigem
					.adapterSIGEMSicresOrganizacionesImpl(ISicresRPAdminOrganizacionManager
							.obtenerOrganizaciones(idPadre, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo organizaciones");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return lista;

	}

	public static SicresOrganizacionImpl getOrganizacion(int id, String entidad) {

		SicresOrganizacionImpl bean = null;

		bean = AdapterVOSigem
				.adapterSIGEMSicresOrganizacionImpl(ISicresRPAdminOrganizacionManager
						.getOrganizacion(id, entidad));

		return bean;

	}

	public static SicresOrganizacionImpl getOrganizacionByCode(String code,
			String entidad) {

		SicresOrganizacionImpl bean = null;
		bean = AdapterVOSigem
				.adapterSIGEMSicresOrganizacionImpl(ISicresRPAdminOrganizacionManager
						.getOrganizacionByCode(code, entidad));

		return bean;

	}

	public static SicresOrganizacionLocalizacionImpl getLocalizacion(int id,
			String entidad) {

		SicresOrganizacionLocalizacionImpl bean = null;

		bean = AdapterVOSigem
				.adapterSIGEMSicresOrganizacionLocalizacionImpl(ISicresRPAdminOrganizacionManager
						.getLocalizacion(id, entidad));

		return bean;

	}

	public static int crearOrganizacion(SicresOrganizacionImpl organizacion,
			SicresOrganizacionLocalizacionImpl localizacion,UnidadRegistralVO unidadRegistral, String entidad)
			throws RPAdminDAOException {

		int idOrg;
		try {
			idOrg = ISicresRPAdminOrganizacionManager
					.crearOrganizacion(
							AdapterVOSicres
									.adapterISicresSicresOrganizacionImpl(organizacion),
							AdapterVOSicres
									.adapterISicresSicresOrganizacionLocalizacionImpl(localizacion), unidadRegistral,
							entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando la organizacion");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return idOrg;

	}

	public static void editarOrganizacion(SicresOrganizacionImpl organizacion,
			SicresOrganizacionLocalizacionImpl localizacion, UnidadRegistralVO unidadRegistral, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminOrganizacionManager.editarOrganizacion(AdapterVOSicres
										.adapterISicresSicresOrganizacionImpl(organizacion),
								AdapterVOSicres
										.adapterISicresSicresOrganizacionLocalizacionImpl(localizacion), unidadRegistral, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando la organizacion");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void eliminarOrganizacion(int idOrg, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminOrganizacionManager.eliminarOrganizacion(idOrg,
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando la organizacion");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static SicresTiposOrganizacionesImpl obtenerTiposOrganizaciones(
			String entidad) throws RPAdminDAOException {

		SicresTiposOrganizacionesImpl lista = null;
		try {

			lista = AdapterVOSigem
					.adapterSIGEMSicresTiposOrganizacionesImpl(ISicresRPAdminOrganizacionManager
							.obtenerTiposOrganizaciones(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo organizaciones");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return lista;
	}

	public static SicresTipoOrganizacionImpl getTipoOrganizacion(int id, String entidad) {
		SicresTipoOrganizacionImpl bean = null;

		bean = AdapterVOSigem
				.adapterSIGEMSicresTipoOrganizacionImpl(ISicresRPAdminOrganizacionManager
						.getTipoOrganizacion(id, entidad));

		return bean;
	}

	public static SicresTipoOrganizacionImpl getTipoOrganizacionByCode(String code, String entidad) {
		SicresTipoOrganizacionImpl bean = null;

		bean = AdapterVOSigem
				.adapterSIGEMSicresTipoOrganizacionImpl(ISicresRPAdminOrganizacionManager
						.getTipoOrganizacionByCode(code, entidad));

		return bean;
	}

	public static int crearTipoOrganizacion(SicresTipoOrganizacionImpl tipoOrganizacion, String entidad)
			throws RPAdminDAOException {

		int idTipoOrg;
		try {
			idTipoOrg = ISicresRPAdminOrganizacionManager.crearTipoOrganizacion(AdapterVOSicres.adapterISicresSicresTipoOrganizacionImpl(tipoOrganizacion), entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando el tipo de organización");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return idTipoOrg;
	}

	public static void editarTipoOrganizacion(SicresTipoOrganizacionImpl tipoOrganizacion, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminOrganizacionManager
					.editarTipoOrganizacion(
							AdapterVOSicres
									.adapterISicresSicresTipoOrganizacionImpl(tipoOrganizacion),
							entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el tipo de organizacion");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void eliminarTipoOrganizacion(int idTypeOrg, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminOrganizacionManager.eliminarTipoOrganizacion(idTypeOrg, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando el tipo de organizacion");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void importarDepartamentos(int deptId, boolean isSelected,
			String idUnidad, String entidad) throws RPAdminDAOException {

		try {
			ISicresRPAdminOrganizacionManager.importarDepartamentos(deptId,
					isSelected, idUnidad, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error importando departamentos estructura organizativa a unidades administrativas.");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void importarGruposLdap(String nodeDn, int maxChildrenLdap,
			int treeType, boolean isSelected, String idUnidad, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminOrganizacionManager.importarGruposLdap(nodeDn,
					maxChildrenLdap, treeType, isSelected, idUnidad, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error importando grupos LDAP a unidades administrativas.");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static SicresOficinasImpl getOficinasUsuarioLdap(String ldapguid,
			String entidad) throws RPAdminDAOException {
		SicresOficinasImpl oficinas = null;
		try {
			oficinas = AdapterVOSigem
					.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOrganizacionManager
							.getOficinasUsuarioLdap(ldapguid, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas de un usuario LDAP.");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}
}