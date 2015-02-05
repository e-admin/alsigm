package ieci.tecdoc.sgm.rpadmin.manager;

import ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap;
import ieci.tecdoc.sgm.core.services.rpadmin.Oficina;
import ieci.tecdoc.sgm.rpadmin.beans.ListaUsuariosImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUserIdentificacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUserLocalizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUserPermisosImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUsuarioConfigImpl;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSicres;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminUserManager;

public class RPAdminUserManager {

	public static final Logger logger = Logger
			.getLogger(RPAdminUserManager.class);

	public static ListaUsuariosImpl obtenerUsuariosListado(String entidad)
			throws RPAdminDAOException {

		ListaUsuariosImpl usuarios = null;
		try {

			usuarios = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.obtenerUsuariosListado(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;
	}

	public static UsuariosLdap obtenerListadoUsuariosLdap(String entidad) throws RPAdminDAOException {

		UsuariosLdap users = null;
		try {
			users=AdapterVOSigem.adapterSIGEMUsuariosLdap(ISicresRPAdminUserManager.obtenerListadoUsuariosLdap(entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios LDAP", e);
			throw new RPAdminDAOException(RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return users;
	}

	public static ListaUsuariosImpl obtenerUsuariosAsociacion(int idOfic,
			int deptId, String entidad) throws RPAdminDAOException {

		ListaUsuariosImpl usuariosImpl = null;
		try {

			usuariosImpl = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.obtenerUsuariosAsociacion(idOfic, deptId, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios para asociar a la oficina",
					e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;
	}

	public static UsuariosLdap obtenerUsuariosLdapAsociacion(int idOfic,
			String entidad) throws RPAdminDAOException {

		UsuariosLdap usuariosLdap = null;
		try {

			usuariosLdap = AdapterVOSigem
					.adapterSIGEMUsuariosLdap(ISicresRPAdminUserManager
							.obtenerUsuariosLdapAsociacion(idOfic, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error(
					"Error obteniendo usuarios LDAP para asociar a la oficina",
					e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosLdap;
	}

	public static ListaUsuariosImpl obtenerUsuariosOficinaDept(int deptId,
			boolean superUsers, boolean agregados, String entidad)
			throws RPAdminDAOException {

		ListaUsuariosImpl usuarios = null;
		try {

			usuarios = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.obtenerUsuariosOficinaDept(deptId, superUsers,
									agregados, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;

	}

	public static Usuarios obtenerUsuariosDepartamento(int deptId,
			String entidad) throws RPAdminDAOException {

		Usuarios usuarios = null;
		try {

			usuarios = AdapterVOSigem.adapterSIGEMUsuarios(ISicresRPAdminUserManager
					.obtenerUsuariosDepartamento(deptId, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;

	}

	public static Usuarios obtenerUsuariosGrupo(int groupId, String entidad)
			throws RPAdminDAOException {
		Usuarios usuarios = null;
		try {

			usuarios = AdapterVOSigem.adapterSIGEMUsuarios(ISicresRPAdminUserManager
					.obtenerUsuariosGrupo(groupId, entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuarios;
	}

	public static int crearUsuarioLdap(String ldapguid, String name,
			int idPerfil, SicresUserPermisosImpl permisos,
			SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion, String entidad,
			boolean asociarOficianPreferente) throws RPAdminDAOException {

		int idUsuario;
		try {

			idUsuario = ISicresRPAdminUserManager
					.crearUsuarioLdap(
							ldapguid,
							name,
							idPerfil,
							AdapterVOSicres
									.adapterISicresSicresUserPermisosImpl(permisos),
							AdapterVOSicres
									.adapterISicresSicresUserIdentificacionImpl(identificacion),
							AdapterVOSicres
									.adapterISicresSicresUserLocalizacionImpl(localizacion),
							entidad, asociarOficianPreferente);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el usuario", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return idUsuario;

	}

	public static void editarUsuario(int userId, int profileId,
			SicresUserPermisosImpl permisos,
			SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion, String entidad,
			boolean asociarOficianPreferente) throws RPAdminDAOException {

		try {
			ISicresRPAdminUserManager
					.editarUsuario(
							userId,
							profileId,
							AdapterVOSicres
									.adapterISicresSicresUserPermisosImpl(permisos),
							AdapterVOSicres
									.adapterISicresSicresUserIdentificacionImpl(identificacion),
							AdapterVOSicres
									.adapterISicresSicresUserLocalizacionImpl(localizacion),
							entidad, asociarOficianPreferente);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el usuario", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void editarUsuarioLdap(int id, int profileId,
			SicresUserPermisosImpl permisos, SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion, String entidad, boolean asociarOficianPreferente)
			throws RPAdminDAOException {


		try {
			ISicresRPAdminUserManager
					.editarUsuarioLdap(
							id,
							profileId,
							AdapterVOSicres
									.adapterISicresSicresUserPermisosImpl(permisos),
							AdapterVOSicres
									.adapterISicresSicresUserIdentificacionImpl(identificacion),
							AdapterVOSicres
									.adapterISicresSicresUserLocalizacionImpl(localizacion),
							entidad, asociarOficianPreferente);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error editando el usuario",e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}


	public static void eliminarUsuario(int userId, String entidad)
			throws RPAdminDAOException {

		try {

			ISicresRPAdminUserManager.eliminarUsuario(userId, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando el usuario", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void eliminarUsuarioLdap(int id, String entidad) throws RPAdminDAOException {

		try {
			ISicresRPAdminUserManager.eliminarUsuarioLdap(id, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error eliminando el usuario LDAP", e);
			throw new RPAdminDAOException(RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static Usuario getUser(String nombre, String entidad) {

		Usuario user = null;

		user = AdapterVOSigem.adapterSIGEMUsuario(ISicresRPAdminUserManager.getUser(
				nombre, entidad));

		return user;

	}

	public static UsuarioLdap getUserLdapByGuid(String ldapguid, String entidad) {
		UsuarioLdap user = null;

		user = AdapterVOSigem.adapterSIGEMUsuarioLdap(ISicresRPAdminUserManager
				.getUserLdapByGuid(ldapguid, entidad));

		return user;
	}

	public static UsuarioLdap getUserLdap(int id, String entidad) {
		UsuarioLdap user = null;

		user = AdapterVOSigem.adapterSIGEMUsuarioLdap(ISicresRPAdminUserManager
				.getUserLdap(id, entidad));

		return user;
	}

	public static Usuario getUser(int id, String entidad) {
		Usuario user = null;

		user = AdapterVOSigem.adapterSIGEMUsuario(ISicresRPAdminUserManager.getUser(
				id, entidad));

		return user;
	}

	public static SicresUserPermisosImpl getPermisos(int id, String entidad) {

		SicresUserPermisosImpl permisos = null;
		permisos = AdapterVOSigem
				.adapterSIGEMSicresUserPermisosImpl(ISicresRPAdminUserManager
						.getPermisos(id, entidad));
		return permisos;

	}

	public static SicresUserIdentificacionImpl getIdentificacion(int id,
			String entidad) {

		SicresUserIdentificacionImpl identificacion = null;

		identificacion = AdapterVOSigem
				.adapterSIGEMSicresUserIdentificacionImpl(ISicresRPAdminUserManager
						.getIdentificacion(id, entidad));

		return identificacion;
	}

	public static SicresUserLocalizacionImpl getLocalizacion(int id,
			String entidad) {

		SicresUserLocalizacionImpl localizacion = null;

		localizacion = AdapterVOSigem
				.adapterSIGEMSicresUserLocalizacionImpl(ISicresRPAdminUserManager
						.getLocalizacion(id, entidad));

		return localizacion;
	}

	public static Grupos obtenerGrupos(String entidad)
			throws RPAdminDAOException {

		Grupos grupos = null;

		try {
			grupos = AdapterVOSigem.adapterSIGEMGrupos(ISicresRPAdminUserManager
					.obtenerGrupos(entidad));

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo grupos", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return grupos;
	}

	public static Grupo getGrupo(int id, String entidad)
			throws Exception {
		Grupo grupo = null;
		try {
			grupo = AdapterVOSigem.adapterSIGEMGrupo(ISicresRPAdminUserManager.getGrupo(id, entidad));
		} catch (ISicresRPAdminDAOException e){
			logger.error("Error obteniendo un grupo",e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return grupo;
	}

	public static GrupoLdap getGrupoLdap(String ldapguid, String entidad) throws Exception {
		GrupoLdap grupoLdap = null;
		try {
			grupoLdap = AdapterVOSigem.adapterSIGEMGrupoLdap(ISicresRPAdminUserManager.getGrupoLdap(ldapguid, entidad));
		} catch (ISicresRPAdminDAOException e){
			logger.error("Error obteniendo un grupo Ldap",e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return grupoLdap;
	}


	/**
	 * Se encarga de obtener los usuarios que pertenecen a un determinado departamento {users = true}.
	 * Y ademas se pueden obtener los usuarios agregados a la oficina {agregados = true}.
	 * @param deptId
	 * @param perfil
	 * @param users
	 * @param agregados
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	public static ListaUsuariosImpl getUsuarios(int deptId[], String entidad)
			throws Exception {

		ListaUsuariosImpl usuariosImpl = null;

		try{
			usuariosImpl = AdapterVOSigem
				.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
						.getUsuarios(deptId, entidad));
		}catch (Exception e){
			logger.error("Error obteniendo usuarios",e);
			throw new RPAdminDAOException(RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosImpl;

	}

	public static ListaUsuariosImpl getUsuarios(String entidad)
			throws Exception {

		ListaUsuariosImpl usuariosImpl = null;

		try {
			usuariosImpl = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.getUsuarios(entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo usuarios", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;

	}

	public static ListaUsuariosImpl getUsuariosLdap(String entidad)
			throws Exception {

		ListaUsuariosImpl usuariosImpl = null;

		try {
			usuariosImpl = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.getUsuariosLdap(entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo usuarios", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosImpl;
	}

	public static ListaUsuariosImpl getUsuariosLdap(int deptId[], String entidad)
			throws Exception {

		ListaUsuariosImpl usuariosImpl = null;
		try {
			usuariosImpl = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.getUsuariosLdap(deptId, entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo usuarios", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;
	}

	public static ListaUsuariosImpl getSuperusuarios(int deptId, String entidad) throws Exception{

		ListaUsuariosImpl usuariosImpl = null;
		try {

			usuariosImpl = AdapterVOSigem.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager.getSuperusuarios(deptId, entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo superusuarios",e);
			throw new RPAdminDAOException(RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;
	}


	public static ListaUsuariosImpl getUsuariosAgregados(int idOfic,
			String entidad) throws Exception {

		ListaUsuariosImpl usuariosImpl = null;
		try {

			usuariosImpl = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.getUsuariosAgregados(idOfic, entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo usuarios agregados", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;
	}

	public static UsuariosLdap getUsuariosAgregadosLdap(int idOfic, String entidad) throws Exception{
		UsuariosLdap usuariosLdap = null;
		try {

			usuariosLdap = AdapterVOSigem.adapterSIGEMUsuariosLdap(ISicresRPAdminUserManager.getUsuariosAgregadosLdap(idOfic, entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo usuarios agregados LDAP",e);
			throw new RPAdminDAOException(RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosLdap;
	}

	/**
	 * Para obtener los usuarios que pertenecen a alguna de las oficinas del libro que queremos mostrar
	 * siempre y cuando no se encuentren ya como parte de las oficinas (NO agregados).
	 * Solo se miran los de tipo USUARIO porque los SUPERUSUARIOS tiene todos los permisos.
	 * @param idsOfic
	 * @param idsUser
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	public static ListaUsuariosImpl getPermisosUsuariosAgregados(int idsOfic[],
			int idsUser[], String entidad) throws Exception {
		ListaUsuariosImpl usuariosImpl = null;
		try {
			usuariosImpl = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.getPermisosUsuariosAgregados(idsOfic, idsUser,
									entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo permisos usuarios agregados", e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;
	}

	/**
	 * Para obtener los usuarios que pertenecen a alguna de las oficinas del libro que queremos mostrar
	 * siempre y cuando no se encuentren ya como parte de las oficinas (NO agregados).
	 * Solo se miran los de tipo USUARIO porque los SUPERUSUARIOS tiene todos los permisos.
	 * @param idsOfic
	 * @param idsUser
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	public static ListaUsuariosImpl getPermisosUsuariosLdapAgregados(int idsOfic[], int idsUser[], String entidad) throws Exception{
		ListaUsuariosImpl usuariosImpl = null;
		try {

			usuariosImpl = AdapterVOSigem
					.adapterSIGEMListaUsuariosImpl(ISicresRPAdminUserManager
							.getPermisosUsuariosLdapAgregados(idsOfic, idsUser,
									entidad));

		} catch (Exception e) {
			logger.error("Error obteniendo permisos usuarios agregados",e);
			throw new RPAdminDAOException(RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;
	}

	public static void asociarOficinasAUsuario(
			int idUser, int idOfic, String entidad)
			throws RPAdminDAOException {

		try {
			ISicresRPAdminUserManager.asociarOficinasAUsuario(idUser, idOfic, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error asociando oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

	}

	public static void desasociarOficinasAUsuario(int idUser, int idOfic,
			String entidad) throws RPAdminDAOException {

		try {

			ISicresRPAdminUserManager.desasociarOficinasAUsuario(idUser,
					idOfic, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error desasociando oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static void desasociarOficinasAUsuarioLDAP(int idUser, int idOfic,
			String entidad) throws RPAdminDAOException {

		try {

			ISicresRPAdminUserManager.desasociarOficinasAUsuarioLDAP(idUser,
					idOfic, entidad);

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error desasociando oficina");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Obtiene el {@link SicresUsuarioConfigImpl} de un usuario
	 * @param userId Identificador del Usuario
	 * @param entidad Entidad
	 * @param db Conexión a la base de datos.
	 * @return {@link SicresUsuarioConfigImpl}
	 */
	public static SicresUsuarioConfigImpl getSicresUsuarioConfigImplByUserId(
			int userId, String entidad) {

		SicresUsuarioConfigImpl configUsuario = null;

		configUsuario = AdapterVOSigem
				.adapterSIGEMSicresUsuarioConfigImpl(ISicresRPAdminUserManager
						.getSicresUsuarioConfigImplByUserId(userId, entidad));

		return configUsuario;

	}

	public static Oficina getOficinaDeptoUsuario(int userId, String entidad) {

		Oficina oficina = null;

		oficina = AdapterVOSigem.adapterSIGEMOficina(ISicresRPAdminUserManager
				.getOficinaDeptoUsuario(userId, entidad));

		return oficina;
	}


	public static Oficina getOficinaDeptoUsuarioLDAP(int userId, String entidad){
		Oficina oficina = null;

		oficina = AdapterVOSigem.adapterSIGEMOficina(ISicresRPAdminUserManager
				.getOficinaDeptoUsuarioLDAP(userId, entidad));

		return oficina;
	}

	/**
	 * Asocia la Oficina como Preferente
	 * @param userId Identificador del Usuario
	 * @param idOficPref Identificador de la Oficina
	 * @param entidad Entidad
	 * @param db Conexión a la Base De Datos
	 * @throws RPAdminDAOException
	 * @throws Exception
	 */
	public static void asociarOficinaPreferenteAUsuario(int userId, int idOficPref, String entidad) throws RPAdminDAOException{

		try {
			ISicresRPAdminUserManager.asociarOficinaPreferenteAUsuario(userId, idOficPref, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error asociando oficina");
			throw new RPAdminDAOException(
				RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	/**
	 * Desasocia la Oficina como Preferente
	 * @param userId Identificador del Usuario
	 * @param entidad Entidad
	 * @param db Conexión a la Base De Datos
	 * @throws RPAdminDAOException
	 * @throws Exception
	 */
	public static void desasociarOficinaPreferenteAUsuario(int userId, String entidad) throws RPAdminDAOException{

		try {
			ISicresRPAdminUserManager.desasociarOficinaPreferenteAUsuario(userId, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error asociando oficina");
			throw new RPAdminDAOException(
				RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public static Integer getIdOficinaPreferenteUsuario(int userId,
			String entidad) {

		Integer idOficina = null;

		idOficina = ISicresRPAdminUserManager.getIdOficinaPreferenteUsuario(
				userId, entidad);

		return idOficina;
	}

	public static UsuariosLdap getUsuariosOficinaLdap(int deptId, String entidad)
			throws RPAdminDAOException {
		UsuariosLdap usuariosLdap = null;

		try {
			usuariosLdap = AdapterVOSigem
					.adapterSIGEMUsuariosLdap(ISicresRPAdminUserManager
							.getUsuariosOficinaLdap(deptId, entidad));
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios LDAP pertenecientes a la oficina.");
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosLdap;
	}

	/**
	 * Método que valida que el ldapguid exista dentro de la estructura LDAP
	 * @param ldapguid
	 * @param entidad
	 * @return boolean - TRUE si existe dentro de la estructura LDAP
	 * 				   - FALSE si no existe dentro de la estructura LDAP
	 * @throws RPAdminDAOException
	 */
	public static boolean validateUserInStructureLDAP(String ldapguid, String entidad) throws RPAdminDAOException{

		boolean result = false;

		try {
			result = ISicresRPAdminUserManager.validateUserInStructureLDAP(ldapguid, entidad);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo conexion con usuario LDAP.");
			throw new RPAdminDAOException(RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return result;
	}
}