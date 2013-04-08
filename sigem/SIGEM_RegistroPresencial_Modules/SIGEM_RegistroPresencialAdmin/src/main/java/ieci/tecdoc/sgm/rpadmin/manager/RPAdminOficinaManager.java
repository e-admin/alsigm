package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaLocalizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOficinasImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOficinaImpl;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.business.exception.ISicresAdminIntercambioRegistralException;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminOficinaManager;

public class RPAdminOficinaManager {
	public static final Logger logger = Logger
			.getLogger(RPAdminOficinaManager.class);

	public static SicresOficinasImpl obtenerOficinasListado(String entidad)
			throws RPAdminDAOException {

		SicresOficinasImpl oficinas = null;
		try {

			oficinas = AdapterVOSigem
					.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOficinaManager
							.obtenerOficinasListado(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	public static SicresOficinasImpl obtenerOficinas(String entidad)
			throws RPAdminDAOException {

		SicresOficinasImpl oficinas = null;
		try {
			oficinas = AdapterVOSigem
					.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOficinaManager
							.obtenerOficinas(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}

	public static void asociarUsuariosAOficina(String[] idsUser, int idOfic,
			String entidad) throws RPAdminDAOException {

		try {
			ISicresRPAdminOficinaManager.asociarUsuariosAOficina(idsUser,
					idOfic, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando la oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void crearOficina(SicresOficinaImpl oficina,
			SicresOficinaLocalizacionImpl localizacion, EntidadRegistralVO entidadRegistral, String entidad)
			throws RPAdminDAOException, ISicresAdminIntercambioRegistralException {

		try {
			ISicresRPAdminOficinaManager.crearOficina(AdapterVOSicres
					.adapterISicresSicresOficinaImpl(oficina), AdapterVOSicres
					.adapterISicresSicresOficinaLocalizacionImpl(localizacion), entidadRegistral,
					entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error creando la oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void editarOficina(SicresOficinaImpl oficina,
			SicresOficinaLocalizacionImpl localizacion, EntidadRegistralVO entidadRegistral, String entidad)
			throws RPAdminDAOException, ISicresAdminIntercambioRegistralException {

		try {
			ISicresRPAdminOficinaManager.editarOficina(AdapterVOSicres
					.adapterISicresSicresOficinaImpl(oficina), AdapterVOSicres
					.adapterISicresSicresOficinaLocalizacionImpl(localizacion), entidadRegistral, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando la oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void eliminarOficina(int id, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminOficinaManager.eliminarOficina(id, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando la oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void eliminarOficinaLDAP(int idOficina, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminOficinaManager.eliminarOficinaLDAP(idOficina, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando la oficina LDAP");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static SicresTiposOficinaImpl obtenerTiposOficina(String entidad)
			throws RPAdminDAOException {

		SicresTiposOficinaImpl oficinas = null;
		try {
			oficinas = AdapterVOSigem
					.adapterSIGEMSicresTiposOficinaImpl(ISicresRPAdminOficinaManager
							.obtenerTiposOficina(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	public static Departamentos obtenerDepartamentos(boolean oficinas,
			String entidad) throws RPAdminDAOException {

		Departamentos departamentos = null;
		try {

			departamentos = AdapterVOSigem
					.adapterSIGEMDepartamentos(ISicresRPAdminOficinaManager
							.obtenerDepartamentos(oficinas, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo departamentos");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return departamentos;
	}

	public static Departamentos obtenerDepartamentosHijos(int parentId,
			String entidad) throws RPAdminDAOException {
		Departamentos departamentos = null;
		try {
			departamentos = AdapterVOSigem
					.adapterSIGEMDepartamentos(ISicresRPAdminOficinaManager
							.obtenerDepartamentosHijos(parentId, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo departamentos");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return departamentos;
	}

	public static SicresOficinaImpl getOficinaByDeptId(int id, String entidad)
			throws Exception {

		SicresOficinaImpl oficina = null;

		oficina = AdapterVOSigem
				.adapterSIGEMSicresOficinaImpl(ISicresRPAdminOficinaManager
						.getOficinaByDeptId(id, entidad));

		return oficina;

	}

	public static SicresOficinaImpl getOficinaByLdapGroup(String ldapGuid, String entidad)
			throws Exception {

		SicresOficinaImpl oficina = null;
		oficina = AdapterVOSigem
				.adapterSIGEMSicresOficinaImpl(ISicresRPAdminOficinaManager
						.getOficinaByLdapGroup(ldapGuid, entidad));
		return oficina;

	}

	public static SicresOficinasImpl getOficinasByDeptsId(int ids[], String entidad) throws Exception {
		SicresOficinasImpl oficinas = null;

		oficinas = AdapterVOSigem
				.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOficinaManager
						.getOficinasByDeptsId(ids, entidad));

		return oficinas;

	}

	public static SicresOficinaImpl getOficinaById(int id, String entidad)
			throws Exception {

		SicresOficinaImpl oficina = null;

		oficina = AdapterVOSigem
				.adapterSIGEMSicresOficinaImpl(ISicresRPAdminOficinaManager
						.getOficinaById(id, entidad));

		return oficina;

	}

	public static SicresOficinaImpl getOficinaByCode(String codigo, String entidad)
			throws Exception {

		SicresOficinaImpl oficina = null;

		oficina = AdapterVOSigem
				.adapterSIGEMSicresOficinaImpl(ISicresRPAdminOficinaManager
						.getOficinaByCode(codigo, entidad));

		return oficina;

	}

	public static Departamento getDepartamento(int id, String entidad)
			throws Exception {
		Departamento departamento = null;

		departamento = AdapterVOSigem
				.adapterSIGEMDepartamento(ISicresRPAdminOficinaManager
						.getDepartamento(id, entidad));

		return departamento;

	}

	public static SicresOficinaLocalizacionImpl getLocalizacion(int id,
			String entidad) {

		SicresOficinaLocalizacionImpl localizacion = null;

		localizacion = AdapterVOSigem
				.adapterSIGEMSicresOficinaLocalizacionImpl(ISicresRPAdminOficinaManager
						.getLocalizacion(id, entidad));

		return localizacion;
	}

	public static SicresOficinasImpl obtenerOficinasDesasociadasALibro(
			int bookId, String entidad) throws RPAdminDAOException {

		SicresOficinasImpl oficinas = null;
		try {
			oficinas = AdapterVOSigem
					.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOficinaManager
							.obtenerOficinasDesasociadasALibro(bookId, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	public static SicresOficinasImpl obtenerOficinasDesagregadasAUsuario(
			int idUser, String entidad) throws RPAdminDAOException {

		SicresOficinasImpl oficinas = null;
		try {
			oficinas = AdapterVOSigem
					.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOficinaManager
							.obtenerOficinasDesagregadasAUsuario(idUser,
									entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	public static SicresOficinasImpl getOficinasDesasociadasAUsuarioLdap(
			String ldapguid, String entidad) throws RPAdminDAOException {

		SicresOficinasImpl oficinas = null;
		try {

			oficinas = AdapterVOSigem
					.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOficinaManager
							.getOficinasDesasociadasAUsuarioLdap(ldapguid,
									entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo posibles oficinas para asociar a un usuario LDAP.");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	public static SicresOficinasImpl obtenerOficinasAgregadasAUsuario(
			int idUser, String entidad) throws RPAdminDAOException {

		SicresOficinasImpl oficinas = null;
		try {
			oficinas = AdapterVOSigem
					.adapterSIGEMSicresOficinasImpl(ISicresRPAdminOficinaManager
							.obtenerOficinasAgregadasAUsuario(idUser, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	/**
	 * Actualizando oficina preferente asignada a los usuarios asociados a la oficina pasada como parametro.
	 * @param idOfic Identificador de la oficina
	 * @param entidad Entidad
	 * @param db Conexión a la Base De Datos
	 * @throws RPAdminDAOException
	 * @throws Exception
	 */
	public static void updateOficPrefUsuario(int idOfic, String entidad)
			throws RPAdminDAOException {
		try {
			ISicresRPAdminOficinaManager.updateOficPrefUsuario(idOfic, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error actualizando oficina preferente de los usuarios asociados a la oficina.");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}



}
