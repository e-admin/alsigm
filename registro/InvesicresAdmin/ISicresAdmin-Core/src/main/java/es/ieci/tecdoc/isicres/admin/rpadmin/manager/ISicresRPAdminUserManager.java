package es.ieci.tecdoc.isicres.admin.rpadmin.manager;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.ldap.LdapScope;
import es.ieci.tecdoc.isicres.admin.base.types.IeciTdType;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Oficina;
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserIdentificacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserPermisosImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioConfigImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.core.database.IUserDeptHdrDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IUserObjPermDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUserIdentificacionDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUserLocalizacionDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUserPermisosDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUsuarioConfigDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUsuarioOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.core.manager.ManagerUtils;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresAdminEstructuraAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo;
import es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Grupos;
import es.ieci.tecdoc.isicres.admin.estructura.beans.PerfilUsuario;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuarios;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuariosLdap;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;
import es.ieci.tecdoc.isicres.admin.service.ISicresAdminEstructuraService;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ISicresRPAdminUserManager {

	public static final Logger logger = Logger
			.getLogger(ISicresRPAdminUserManager.class);

	/*public static ListaUsuariosImpl obtenerUsuariosListado(String entidad)
			throws RPAdminDAOException {
		ListaUsuariosImpl usuarios = null;
		try {
			usuarios = getUsuariosMain(-1, ManagerUtils.CON_SUPERUSUARIOS, ManagerUtils.SIN_AGREGADOS, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios",e);
			throw new RPAdminDAOException(
					RPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;
	}*/

	public static ListaUsuariosImpl obtenerUsuariosListado(String entidad) throws ISicresRPAdminDAOException {
		ListaUsuariosImpl usuarios = null;
		try {
			usuarios = getUsuariosAplicacion(ManagerUtils.CON_SUPERUSUARIOS, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;
	}

	private static UsuariosLdap fillIdentificacionUsuariosLdap (String entidad, UsuariosLdap users)
		throws ISicresRPAdminDAOException {

		if(users.get_list() != null && users.get_list().size() > 0) {
			DbConnection db = new DbConnection();
			try {
				db.open(DBSessionManager.getSession());
				for (Iterator iterator = users.get_list().iterator(); iterator
						.hasNext();) {
					UsuarioLdap usuario = (UsuarioLdap) iterator.next();

					SicresUserIdentificacionDatos identificacion = new SicresUserIdentificacionDatos();
					try {
						identificacion.load(usuario.get_id(), db);
						usuario.set_firstName(identificacion.getFirstName());
						usuario.set_secondName(identificacion.getSecondName());
						usuario.set_surName(identificacion.getSurname());
					} catch (Exception e) {
						usuario.set_surName(usuario.get_ldapfullname());
						usuario.set_firstName("");
						usuario.set_secondName("");
					}
				}
			} catch (Exception e) {
				logger.error("Error obteniendo usuarios", e);
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			} finally {
				try {
					if (db != null && db.existConnection()){
						db.close();
					}
				} catch (Exception e) {
					logger.error("No se ha podido cerrar la conexión a la BBDD");
				}
			}
		}

		return users;
	}

	public static UsuariosLdap obtenerListadoUsuariosLdap(String entidad) throws ISicresRPAdminDAOException {
		UsuariosLdap users = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			users = oServicio.getUsuariosLdap(entidad);
			users = fillIdentificacionUsuariosLdap(entidad, users);
		} catch (Exception e) {
			logger.error("No se ha podido recuperar los usuarios LDAP", e);
			return null;
		}
		return users;
	}

	public static ListaUsuariosImpl obtenerUsuariosAsociacion(int idOfic, int deptId, String entidad) throws ISicresRPAdminDAOException {
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		try{
			Usuarios users = null;
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			users = oServicio.getUsuariosAsociation(ManagerUtils.ISICRES_PROD_ID, deptId, ManagerUtils.SIN_SUPERUSUARIOS, entidad);
			users = eliminarUsuariosYaAsociados(users, idOfic, entidad);
			usuariosImpl.getLista().addAll(getTransformUsers(users, ListaUsuarioImpl.TIPO_USUARIO, entidad).getLista());
			users = oServicio.getUsuariosAsociation(ManagerUtils.ISICRES_PROD_ID, deptId, ManagerUtils.CON_SUPERUSUARIOS, entidad);
			users = eliminarUsuariosYaAsociados(users, idOfic, entidad);
			usuariosImpl.getLista().addAll(getTransformUsers(users, ListaUsuarioImpl.TIPO_SUPER_USUARIO, entidad).getLista());
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios para asociar a la oficina",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosImpl;
	}

	public static UsuariosLdap obtenerUsuariosLdapAsociacion(int idOfic, String entidad) throws ISicresRPAdminDAOException {
		UsuariosLdap usuariosLdap = new UsuariosLdap();
		try{
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			//Obtenemos todos los usuarios LDAP de la aplicacion
			usuariosLdap = oServicio.getUsuariosLdap(entidad);
			usuariosLdap = eliminarUsuariosLdapYaAsociados(usuariosLdap, idOfic, entidad);
			usuariosLdap = fillIdentificacionUsuariosLdap(entidad, usuariosLdap);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios LDAP para asociar a la oficina",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosLdap;
	}

	private static Usuarios eliminarUsuariosYaAsociados(Usuarios usuarios, int idOfic, String entidad)throws ISicresRPAdminDAOException {
		Usuarios users = new Usuarios();
		try {
			if(usuarios.count() > 0) {
				int []idsUser = new int[usuarios.count()];
				for(int i=0; i<usuarios.count(); i++) {
					idsUser[i] = usuarios.get(i).get_id();
				}
				// SicresOficinaImpl oficina =
				// RPAdminOficinaManager.getOficinaByDeptId(deptId, entidad);
				ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
				users = oServicio.getUsuariosYaAsociados(idsUser, idOfic, entidad);
			}
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios ya asociados a una oficina",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return users;
	}

	private static UsuariosLdap eliminarUsuariosLdapYaAsociados(UsuariosLdap usuariosLdap, int idOfic, String entidad)throws ISicresRPAdminDAOException {
		UsuariosLdap result = new UsuariosLdap();

		try {
			if(usuariosLdap.count() > 0) {
				int []idsUser = new int[usuariosLdap.count()];
				for(int i=0; i<usuariosLdap.count(); i++) {
					idsUser[i] = usuariosLdap.get(i).get_id();
				}
				//obtiene el listado de usuarios excluyendo los asocidados
				ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
				UsuariosLdap usersLdap = oServicio.getUsuariosLdapYaAsociados(idsUser, idOfic, entidad);

				//Obtenemos los usuarios que pertenecen a la oficina
				Entidad entity = new Entidad();
				entity.setIdentificador(entidad);
				OficinaBean oficina = new ISicresServicioRPAdminAdapter().obtenerOficina(idOfic, entity);
				UsuariosLdap listado = getUsuariosOficinaLdap(oficina.getDeptId(), entidad);
				//transformamos el array anterior a un HashMap
				Map mapListado = new HashMap();
				for(int i=0; i<listado.count(); i++){
					UsuarioLdap user = listado.get(i);
					mapListado.put(new Integer(user.get_id()), user);
				}

				//comparamos los usuarios que estan en un array y en otro
				for(int i=0; i<usersLdap.count();i++){
					UsuarioLdap user = usersLdap.get(i);
					// comprobamos si existe o no, el id de los usuarios que
					// pertenecen a la oficina con los que devuelve el metodo
					// getUsuariosLdapYaAsociados
					if(mapListado.get(new Integer(user.get_id()))==null){
						//sino existe añadimos el user a result
						result.add(user);
					}
				}

			}
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios LDAP ya asociados a una oficina",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return result;
	}

	public static ListaUsuariosImpl obtenerUsuariosOficinaDept(int deptId,
			boolean superUsers, boolean agregados, String entidad) throws ISicresRPAdminDAOException {
		ListaUsuariosImpl usuarios = null;
		try {
			usuarios = getUsuariosMain(deptId, superUsers, agregados, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;
	}

	public static Usuarios obtenerUsuariosDepartamento(int deptId,
			String entidad) throws ISicresRPAdminDAOException {
		Usuarios usuarios = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			usuarios = oServicio.getUsuariosDepartamento(deptId, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;
	}

	public static Usuarios obtenerUsuariosGrupo(int groupId,
			String entidad) throws ISicresRPAdminDAOException {
		Usuarios usuarios = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			usuarios = oServicio.getUsuariosGrupo(groupId, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarios;
	}

	public static int crearUsuarioLdap(String ldapguid, String name, int idPerfil,
			SicresUserPermisosImpl permisos,
			SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion, String entidad, boolean asociarOficianPreferente)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			UsuarioLdap usuario = oServicio.crearUsuarioLdap(ldapguid, name, idPerfil, entidad);

			/*Usuario user = oServicio.getUsuario(userId, entidad);
			user.get_Perfil(ManagerUtils.ISICRES_PROD_ID).set_profile(profileId);
			oServicio.editarUsuario(user, entidad);*/

			db.open(DBSessionManager.getSession());
			db.beginTransaction();

			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserPermisosDatos permisosDAO = new SicresUserPermisosDatos();
			if (permisos == null) {
				permisosDAO.setIdUsr(usuario.get_id());
				permisosDAO.delete(db);
			} else {
				permisos.setIdUsr(usuario.get_id());
				try {
					permisosDAO.load(usuario.get_id(), db);
					new SicresUserPermisosDatos(permisos).update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserPermisosDatos(permisos).add(db);
				}
			}

			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserIdentificacionDatos identificacionDAO = new SicresUserIdentificacionDatos();
			if (identificacion == null) {
				identificacionDAO.setUserId(usuario.get_id());
				identificacionDAO.delete(db);
			} else {
				identificacion.setUserId(usuario.get_id());
				try {
					identificacionDAO.load(usuario.get_id(), db);
					new SicresUserIdentificacionDatos(identificacion)
							.update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserIdentificacionDatos(identificacion).add(db);
				}
			}
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserLocalizacionDatos localizacionDAO = new SicresUserLocalizacionDatos();
			if (localizacion == null) {
				localizacionDAO.setUserId(usuario.get_id());
				localizacionDAO.delete(db);
			} else {
				localizacion.setUserId(usuario.get_id());
				try {
					localizacionDAO.load(usuario.get_id(), db);
					new SicresUserLocalizacionDatos(localizacion).update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserLocalizacionDatos(localizacion).add(db);
				}
			}

			//Si viene oficina preferente asociarsela
			if(asociarOficianPreferente){
				asociarOficinaPreferenteDefectoAUsuario(usuario.get_id(), entidad);
			}
			db.endTransaction(true);

			return usuario.get_id();
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el usuario",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void editarUsuario(int userId, int profileId,
			SicresUserPermisosImpl permisos,
			SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion, String entidad, boolean asociarOficianPreferente)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			Usuario user = oServicio.getUsuario(userId, entidad);
			user.get_Perfil(ManagerUtils.ISICRES_PROD_ID).set_profile(profileId);
			oServicio.editarUsuario(user, entidad);

			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserPermisosDatos permisosDAO = new SicresUserPermisosDatos();
			if (permisos == null || profileId == ListaUsuarioImpl.TIPO_SUPER_USUARIO) {
				permisosDAO.setIdUsr(user.get_id());
				permisosDAO.delete(db);
			} else {
				try {
					permisosDAO.load(user.get_id(), db);
					new SicresUserPermisosDatos(permisos).update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserPermisosDatos(permisos).add(db);
				}
			}
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserIdentificacionDatos identificacionDAO = new SicresUserIdentificacionDatos();
			if (identificacion == null) {
				identificacionDAO.setUserId(user.get_id());
				identificacionDAO.delete(db);
			} else {
				try {
					identificacionDAO.load(user.get_id(), db);
					new SicresUserIdentificacionDatos(identificacion)
							.update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserIdentificacionDatos(identificacion).add(db);
				}
			}
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserLocalizacionDatos localizacionDAO = new SicresUserLocalizacionDatos();
			if (localizacion == null) {
				localizacionDAO.setUserId(user.get_id());
				localizacionDAO.delete(db);
			} else {
				try {
					localizacionDAO.load(user.get_id(), db);
					new SicresUserLocalizacionDatos(localizacion).update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserLocalizacionDatos(localizacion).add(db);
				}
			}

			//Si viene oficina preferente asociarsela
			if(asociarOficianPreferente){
				asociarOficinaPreferenteDefectoAUsuario(userId, entidad);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el usuario",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void editarUsuarioLdap(int id, int profileId,
			SicresUserPermisosImpl permisos, SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion, String entidad, boolean asociarOficianPreferente)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			UsuarioLdap userLdap = oServicio.getUsuarioLdap(id, entidad);
			userLdap.get_Perfil(ManagerUtils.ISICRES_PROD_ID).set_profile(profileId);
			oServicio.editarUsuarioLdap(userLdap, entidad);

			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserPermisosDatos permisosDAO = new SicresUserPermisosDatos();
			if (permisos == null || profileId == PerfilUsuario.SUPERUSUARIO) {
				permisosDAO.setIdUsr(userLdap.get_id());
				permisosDAO.delete(db);
			} else {
				try {
					permisosDAO.load(userLdap.get_id(), db);
					new SicresUserPermisosDatos(permisos).update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserPermisosDatos(permisos).add(db);
				}
			}
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserIdentificacionDatos identificacionDAO = new SicresUserIdentificacionDatos();
			if (identificacion == null) {
				identificacionDAO.setUserId(userLdap.get_id());
				identificacionDAO.delete(db);
			} else {
				try {
					identificacionDAO.load(userLdap.get_id(), db);
					new SicresUserIdentificacionDatos(identificacion)
							.update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserIdentificacionDatos(identificacion).add(db);
				}
			}
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresUserLocalizacionDatos localizacionDAO = new SicresUserLocalizacionDatos();
			if (localizacion == null) {
				localizacionDAO.setUserId(userLdap.get_id());
				localizacionDAO.delete(db);
			} else {
				try {
					localizacionDAO.load(userLdap.get_id(), db);
					new SicresUserLocalizacionDatos(localizacion).update(db);
				} catch (ISicresRPAdminDAOException e) {
					new SicresUserLocalizacionDatos(localizacion).add(db);
				}
			}

			//Si viene oficina preferente asociarsela
			if(asociarOficianPreferente){
				asociarOficinaPreferenteDefectoAUsuario(id, entidad);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el usuario",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}


	public static void eliminarUsuario(int userId, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			Usuario user = oServicio.getUsuario(userId, entidad);
			user.get_Perfil(ManagerUtils.ISICRES_PROD_ID).set_profile(0);
			oServicio.editarUsuario(user, entidad);

			db.open(DBSessionManager.getSession());
			db.beginTransaction();

				SicresUserPermisosDatos permisos = new SicresUserPermisosDatos();
			permisos.setIdUsr(userId);
			permisos.delete(db);

			SicresUserIdentificacionDatos identificacion = new SicresUserIdentificacionDatos();
			identificacion.setUserId(userId);
			identificacion.delete(db);

			SicresUserLocalizacionDatos localizacion = new SicresUserLocalizacionDatos();
			localizacion.setUserId(userId);
			localizacion.delete(db);

			// Permisos
			IUserObjPermDatos.deleteDestino(IUserObjPermImpl.TIPO_USUARIO,
					userId, db);

			//Oficinas Agregadas
			SicresUsuarioOficinaDatos.deleteUser(userId, db);

			//Eliminar la Oficina Preferente del Usuario
			Integer idOficina = getIdOficinaPreferenteUsuario(userId, entidad);

			if(idOficina != null){
				SicresUsuarioConfigDatos usuarioConfig = new SicresUsuarioConfigDatos();
				usuarioConfig.loadById(userId, db);
				usuarioConfig.setIdOficPref(IeciTdType.NULL_LONG_INTEGER);
				usuarioConfig.update(db);
			}

			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el usuario", e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void eliminarUsuarioLdap(int id, String entidad) throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();

			db.open(DBSessionManager.getSession());
			db.beginTransaction();

			// Borramos permisos scr_usrperms
			SicresUserPermisosDatos permisos = new SicresUserPermisosDatos();
			permisos.setIdUsr(id);
			permisos.delete(db);

			//Borramos identificacion scr_usrident
			SicresUserIdentificacionDatos identificacion = new SicresUserIdentificacionDatos();
			identificacion.setUserId(id);
			identificacion.delete(db);

			//Borramos localizacion scr_usrloc
			SicresUserLocalizacionDatos localizacion = new SicresUserLocalizacionDatos();
			localizacion.setUserId(id);
			localizacion.delete(db);

			//Borramos permisos iusergenperms
			IUserObjPermDatos.deleteDestino(IUserObjPermImpl.TIPO_USUARIO, id, db);

			//Oficinas Agregadas scr_userofic
			SicresUsuarioOficinaDatos.deleteUser(id, db);

			//Eliminar la Oficina Preferente del Usuario
			Integer idOficina = getIdOficinaPreferenteUsuario(id, entidad);

			if(idOficina != null){
				SicresUsuarioConfigDatos usuarioConfig = new SicresUsuarioConfigDatos();
				usuarioConfig.loadById(id, db);
				usuarioConfig.setIdOficPref(IeciTdType.NULL_LONG_INTEGER);
				usuarioConfig.update(db);
			}
			db.endTransaction(true);

			// Una vez que se ha borrado todos los datos del usuario, se procede
			// a eliminar el usuario LDAP de iuserldapuserhdr
			oServicio.eliminarUsuarioLdap(id, entidad);

		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el usuario LDAP", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static Usuario getUser(String nombre, String entidad) {
		Usuario user = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			user = oServicio.getUsuario(nombre, entidad);
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el usuario", e);
			return null;
		}
		return user;
	}

	public static UsuarioLdap getUserLdapByGuid(String ldapguid, String entidad) {
		UsuarioLdap user = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			user = oServicio.getUsuarioLdapByGuid(ldapguid, entidad);
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el usuario LDAP por ldapguid", e);
			return null;
		}
		return user;
	}

	public static UsuarioLdap getUserLdap(int id, String entidad) {
		UsuarioLdap user = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			user = oServicio.getUsuarioLdap(id, entidad);
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el usuario LDAP por id", e);
			return null;
		}
		return user;
	}

	public static Usuario getUser(int id, String entidad) {
		Usuario user = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			user = oServicio.getUsuario(id, entidad);
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el usuario", e);
			return null;
		}
		return user;
	}

	/**
	 * Se encarga de transformar los usuarios agregados como usuarios para poder tratarlos y mostrarlos en pantalla.
	 * @param usuariosAgregados
	 * @param entidad
	 * @return
	 */
	private static Usuarios getUsers(ListaUsuariosOficinaImpl usuariosAgregados, boolean superusers, String entidad) {
		Usuarios users = new Usuarios();
		int idsUser[] = null;
		try {
			if(usuariosAgregados.count() > 0){
				idsUser = new int[usuariosAgregados.count()];
				ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
				for(int i=usuariosAgregados.count()-1; i>=0; i--){
					SicresUsuarioOficinaImpl usuario = (SicresUsuarioOficinaImpl)usuariosAgregados.get(i);
					idsUser[i] = usuario.getIdUser();
				}
				users = oServicio.getUsuarios(ManagerUtils.ISICRES_PROD_ID, idsUser, superusers, entidad);
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el usuario", e);
			return null;
		}
		return users;
	}

	/**
	 * Se encarga de transformar los usuarios agregados como usuarios para poder tratarlos y mostrarlos en pantalla.
	 * @param usuariosAgregados
	 * @param entidad
	 * @return
	 */
	private static UsuariosLdap getUsersLdap(ListaUsuariosOficinaImpl usuariosAgregados, boolean superusers, String entidad) {
		UsuariosLdap users = new UsuariosLdap();
		int idsUser[] = null;
		try {
			if(usuariosAgregados.count() > 0){
				idsUser = new int[usuariosAgregados.count()];
				ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
				for(int i=usuariosAgregados.count()-1; i>=0; i--){
					SicresUsuarioOficinaImpl usuario = (SicresUsuarioOficinaImpl)usuariosAgregados.get(i);
					idsUser[i] = usuario.getIdUser();
				}
				users = oServicio.getUsuariosLdap(ManagerUtils.ISICRES_PROD_ID, idsUser, superusers, entidad);
				users = fillIdentificacionUsuariosLdap(entidad, users);
			}
		} catch (Exception e) {
			logger.error("No se ha podido recuperar el usuario", e);
			return null;
		}
		return users;
	}

	public static SicresUserPermisosImpl getPermisos(int id, String entidad) {
		DbConnection db = new DbConnection();
		SicresUserPermisosDatos permisos = new SicresUserPermisosDatos();
		try {
			db.open(DBSessionManager.getSession());
			permisos.load(id, db);
		} catch (ISicresRPAdminDAOException iRPAEx){
			logger.warn("No se han encontrado los permisos especiales del usuario");
			return null;
		} catch (Exception e) {
			logger
					.error("No se ha podido recuperar los permisos especiales del usuario", e);
			return null;
		} finally {
			try {
				if (db != null && db.existConnection())
					db.close();
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return permisos;
	}

	public static SicresUserIdentificacionImpl getIdentificacion(int id,
			String entidad) {
		DbConnection db = new DbConnection();
		SicresUserIdentificacionDatos identificacion = new SicresUserIdentificacionDatos();
		try {
			db.open(DBSessionManager.getSession());
			identificacion.load(id, db);
		} catch (ISicresRPAdminDAOException iRPADAOException){
			logger.warn("No se ha encontrado la identificacion del usuario");
			return null;
		} catch (Exception e) {
			logger
					.error("No se ha podido recuperar la identificacion del usuario", e);
			return null;
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return identificacion;
	}

	public static SicresUserLocalizacionImpl getLocalizacion(int id,
			String entidad) {
		DbConnection db = new DbConnection();
		SicresUserLocalizacionDatos localizacion = new SicresUserLocalizacionDatos();
		try {
			db.open(DBSessionManager.getSession());
			localizacion.load(id, db);
		} catch (ISicresRPAdminDAOException iRPADAOException){
			logger.warn("No se ha encontrado la localizacion del usuario");
			return null;
		} catch (Exception e) {
			logger
					.error("No se ha podido recuperar la localizacion del usuario", e);
			return null;
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				};
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return localizacion;
	}

	public static Grupos obtenerGrupos(String entidad)
			throws ISicresRPAdminDAOException {
		Grupos grupos = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			grupos = oServicio.getGrupos(entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo grupos",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return grupos;
	}

	public static Grupo getGrupo(int id, String entidad)
			throws Exception {
		Grupo grupo = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			grupo = oServicio.getGrupo(id,entidad);
		} catch (Exception e){
			logger.error("Error obteniendo un grupo",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return grupo;
	}

	public static GrupoLdap getGrupoLdap(String ldapguid, String entidad) throws Exception {
		GrupoLdap grupoLdap = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			grupoLdap = oServicio.getGrupoLdap(ldapguid, entidad);
		} catch (Exception e){
			logger.error("Error obteniendo un grupo Ldap",e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return grupoLdap;
	}

	/*private static ListaUsuariosImpl getUsuariosMain(int deptId, boolean superusuarios, boolean agregados, String entidad) throws Exception{
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		usuariosImpl.getLista().addAll(getUsuarios(deptId,ListaUsuarioImpl.TIPO_USUARIO, agregados, entidad).getLista());
		if(superusuarios) {
			usuariosImpl.getLista().addAll(getUsuarios(deptId,ListaUsuarioImpl.TIPO_SUPER_USUARIO, agregados, entidad).getLista());
		}
		return usuariosImpl;
	}*/

	private static ListaUsuariosImpl getUsuariosMain(int deptId, boolean superusuarios,
			boolean agregados, String entidad) throws Exception{
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		usuariosImpl.getLista().addAll(getUsuarios(new int[]{deptId}, entidad).getLista());
		if(superusuarios)
			usuariosImpl.getLista().addAll(getSuperusuarios(deptId, entidad).getLista());
		if(agregados)
			usuariosImpl.getLista().addAll(getUsuariosAgregados(deptId, entidad).getLista());
		return usuariosImpl;
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
	public static ListaUsuariosImpl getUsuarios(int deptId[], String entidad) throws Exception{

		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			Usuarios usuarios = oServicio.getUsuariosAplicacionPorDepartamento(ManagerUtils.ISICRES_PROD_ID, deptId, ManagerUtils.SIN_PERMISOS,
					ManagerUtils.CON_USUARIOS, ManagerUtils.SIN_SUPERUSUARIOS, entidad);
			usuariosImpl.getLista().addAll(getTransformUsers(usuarios, ListaUsuarioImpl.TIPO_USUARIO, entidad).getLista());
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return usuariosImpl;
	}

	public static ListaUsuariosImpl getUsuarios(String entidad)
			throws Exception {

		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			Usuarios usuarios = oServicio.getUsuariosAplicacion(
					ManagerUtils.ISICRES_PROD_ID, ManagerUtils.SIN_PERMISOS,
					ManagerUtils.CON_PERMISOS, ManagerUtils.SIN_SUPERUSUARIOS,
					entidad);
			usuariosImpl.getLista().addAll(
					getTransformUsers(usuarios, ListaUsuarioImpl.TIPO_USUARIO,
							entidad).getLista());
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios", e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return usuariosImpl;
	}

	public static ListaUsuariosImpl getUsuariosLdap(String entidad)
			throws Exception {

		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			UsuariosLdap usuarios = oServicio
					.getUsuariosLdapAplicacion(
							ManagerUtils.ISICRES_PROD_ID,
							ManagerUtils.SIN_PERMISOS,
							ManagerUtils.CON_USUARIOS,
							ManagerUtils.SIN_SUPERUSUARIOS, entidad);
			usuariosImpl.getLista().addAll(
					getTransformUsersLdap(usuarios,
							ListaUsuarioImpl.TIPO_USUARIO, entidad).getLista());
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios", e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return usuariosImpl;
	}

	public static ListaUsuariosImpl getUsuariosLdap(int deptId[], String entidad)
			throws Exception {

		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			UsuariosLdap usuarios = oServicio
					.getUsuariosLdapAplicacionPorDepartamento(
							ManagerUtils.ISICRES_PROD_ID, deptId,
							ManagerUtils.SIN_PERMISOS,
							ManagerUtils.CON_USUARIOS,
							ManagerUtils.SIN_SUPERUSUARIOS, entidad);
			usuariosImpl.getLista().addAll(
					getTransformUsersLdap(usuarios,
							ListaUsuarioImpl.TIPO_USUARIO, entidad).getLista());
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios", e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return usuariosImpl;
	}

	public static ListaUsuariosImpl getSuperusuarios(int deptId, String entidad) throws Exception{

		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			Usuarios usuarios = oServicio.getUsuariosAplicacionPorDepartamento(ManagerUtils.ISICRES_PROD_ID, new int[]{deptId}, ManagerUtils.SIN_PERMISOS,
					ManagerUtils.SIN_USUARIOS, ManagerUtils.CON_SUPERUSUARIOS, entidad);
			usuariosImpl.getLista().addAll(getTransformUsers(usuarios, ListaUsuarioImpl.TIPO_SUPER_USUARIO, entidad).getLista());
		} catch (Exception e) {
			logger.error("Error obteniendo superusuarios",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return usuariosImpl;
	}


	public static ListaUsuariosImpl getUsuariosAgregados(int idOfic, String entidad) throws Exception{

		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			//Obtenemos los usuarios de la oficina en la tabla scr_usrofic
			ListaUsuariosOficinaImpl usuariosAgregados = new SicresUsuarioOficinaDatos().getUsuariosOficina(idOfic, db);
			//Obtenemos los usuarios de tipo operador de registro
			Usuarios usuarios = getUsers(usuariosAgregados, ManagerUtils.SIN_SUPERUSUARIOS, entidad);
			usuariosImpl.getLista().addAll(getTransformUsers(usuarios, ListaUsuarioImpl.TIPO_USUARIO, entidad).getLista());

			//Obtenemos los superusuarios
			Usuarios superusuarios = getUsers(usuariosAgregados, ManagerUtils.CON_SUPERUSUARIOS, entidad);
			usuariosImpl.getLista().addAll(getTransformUsers(superusuarios, ListaUsuarioImpl.TIPO_SUPER_USUARIO, entidad).getLista());
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios agregados",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return usuariosImpl;
	}

	public static UsuariosLdap getUsuariosAgregadosLdap(int idOfic, String entidad) throws Exception{
		UsuariosLdap usuariosLdap = new UsuariosLdap();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			//SicresOficinaImpl oficina = RPAdminOficinaManager.getOficinaByDeptId(deptId, entidad);

			//Obtenemos todos los usuarios de la aplicacion
			UsuariosLdap usersLdap = obtenerListadoUsuariosLdap(entidad);
			UsuarioLdap userLdap = null;
			SicresUsuarioOficinaDatos scr_usrofic = new SicresUsuarioOficinaDatos();
			//Para cada usuario LDAP comprobamos si en scr_usrofic existe relacion entre el usuario y la oficina
			for(int i=0; i<usersLdap.count(); i++){
				userLdap = usersLdap.get(i);
				try{
					scr_usrofic.load(userLdap.get_id(), idOfic, db);
					usuariosLdap.add(userLdap);
				}catch(Exception f){
					//Si salta la excepcion es que no se encontro ningun registro en la tabla
				}
			}
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios agregados LDAP",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
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
	public static ListaUsuariosImpl getPermisosUsuariosAgregados(int idsOfic[], int idsUser[], String entidad) throws Exception{
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			ListaUsuariosOficinaImpl usuariosAgregados = new SicresUsuarioOficinaDatos().getAgregadosOficinas(idsOfic, idsUser, db);
			Usuarios usuarios = getUsers(usuariosAgregados, ManagerUtils.SIN_SUPERUSUARIOS, entidad);
			usuariosImpl = getTransformUsers(usuarios, ListaUsuarioImpl.TIPO_USUARIO, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo permisos usuarios agregados",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
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
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			ListaUsuariosOficinaImpl usuariosAgregados = new SicresUsuarioOficinaDatos().getAgregadosOficinas(idsOfic, idsUser, db);
			UsuariosLdap usuarios = getUsersLdap(usuariosAgregados, ManagerUtils.SIN_SUPERUSUARIOS, entidad);
			usuariosImpl = getTransformUsersLdap(usuarios, ListaUsuarioImpl.TIPO_USUARIO, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo permisos usuarios agregados",e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return usuariosImpl;
	}

	//Si deptid vale -1 se devuelven todos los usuarios.
	/*private static ListaUsuariosImpl getUsuarios(int deptId, int perfil, boolean agregados, String entidad) throws Exception{
		ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
		Usuarios usuarios = null;
		switch(perfil) {
		case ListaUsuarioImpl.TIPO_SUPER_USUARIO:
			usuarios = oServicio.getUsuariosAplicacion(ManagerUtils.ISICRES_PROD_ID, false, false, true, entidad);
			break;
		case ListaUsuarioImpl.TIPO_USUARIO:
			usuarios = oServicio.getUsuariosAplicacion(ManagerUtils.ISICRES_PROD_ID, false, true, false, entidad);
			break;
		default:
			usuarios = oServicio.getUsuariosAplicacion(ManagerUtils.ISICRES_PROD_ID, true, false, false, entidad);
			break;
		}

		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		if(usuarios.get_list().size()>0) {
			DbConnection db = new DbConnection();

			try {
				db.open(DBSessionManager.getSession());

				for(int i=usuarios.get_list().size()-1;i>=0;i--) {
					Usuario usuario = (Usuario)usuarios.get_list().get(i);
					//Si buscamos de un determinado departamento quedarnos sólo con los de él.
					if(deptId!=-1) {
						if(deptId!=usuario.get_deptId()) {
							//Si queremos los agregados, hay que ver si se encuentra en la tabla de agregados para esa oficina. Sino lo descartamos directamente
							if(agregados) {
								try {
									SicresOficinaImpl oficina = RPAdminOficinaManager.getOficinaByDeptId(deptId, entidad);
									new SicresUsuarioOficinaDatos().load(usuario.get_id(), oficina.getId(), db);
								} catch (Exception e) {
									//Si salta una excepción es porque tampoco está agregado a esa oficina, por lo que ya definitivamente lo dejamos
									//usuarios.get_list().remove(i);
									continue;
								}
							} else {
								continue;
							}

						}
					}
					ListaUsuarioImpl usuarioImpl = new ListaUsuarioImpl();

					usuarioImpl.setId(usuario.get_id());
					usuarioImpl.setNombre(usuario.get_name());
					usuarioImpl.setType(perfil);

					SicresOficinaDatos oficina = new SicresOficinaDatos();
					try {
						oficina.loadDeptId(usuario.get_deptId(), db);
						usuarioImpl.setCode(oficina.getCode());
					} catch (Exception e) {;}

					SicresUserIdentificacionDatos identificacion = new SicresUserIdentificacionDatos();
					try {
						identificacion.load(usuario.get_id(), db);
						usuarioImpl.setFirstName(identificacion.getFirstName());
						usuarioImpl.setSecondName(identificacion.getSecondName());
						usuarioImpl.setSurName(identificacion.getSurname());
					} catch (Exception e) {
						usuarioImpl.setSurName(usuario.get_name());
						usuarioImpl.setFirstName("");
						usuarioImpl.setSecondName("");
					}
					usuariosImpl.add(usuarioImpl);
				}
			} catch (Exception e) {
				logger.error("Error obteniendo usuarios",e);
				throw new ISicresRPAdminDAOException(
						ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			} finally {
				try {
					if (db != null && db.existConnection())
						db.close();
				} catch (Exception e) {
					logger.error("No se ha podido cerrar la conexión a la BBDD");
				}
			}

		}
		return usuariosImpl;
	}*/

	/**
	 * Se encarga de obtener todos los usuarios de la aplicacion.
	 * Ademas se puede indicar si queremos que nos saque tambien los superusuarios.
	 * @param superusuarios
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	private static ListaUsuariosImpl getUsuariosAplicacion(boolean superusuarios, String entidad) throws Exception{
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		Usuarios users = null;
		ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
		users = oServicio.getUsuariosAplicacion(ManagerUtils.ISICRES_PROD_ID, ManagerUtils.SIN_PERMISOS,
												ManagerUtils.CON_USUARIOS, ManagerUtils.SIN_SUPERUSUARIOS, entidad);
		usuariosImpl.getLista().addAll(getTransformUsers(users, ListaUsuarioImpl.TIPO_USUARIO, entidad).getLista());
		if(superusuarios) {
			users = oServicio.getUsuariosAplicacion(ManagerUtils.ISICRES_PROD_ID, ManagerUtils.SIN_PERMISOS,
					ManagerUtils.SIN_USUARIOS, ManagerUtils.CON_SUPERUSUARIOS, entidad);
			usuariosImpl.getLista().addAll(getTransformUsers(users, ListaUsuarioImpl.TIPO_SUPER_USUARIO, entidad).getLista());
		}
		return usuariosImpl;
	}

	/**
	 * Obtiene la lista de usuarios de tipo {@link ListaUsuariosImpl} a partir de unos Usuarios de un determinado perfil.
	 * @param usuarios
	 * @param perfil
	 * @param entidad
	 * @return
	 * @throws Exception
	 */
	private static ListaUsuariosImpl getTransformUsers(Usuarios usuarios, int perfil, String entidad) throws Exception {
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		if(usuarios.get_list().size() > 0) {
			DbConnection db = new DbConnection();
			try {
				db.open(DBSessionManager.getSession());
				for(int i=usuarios.get_list().size()-1;i>=0;i--) {
					Usuario usuario = (Usuario)usuarios.get_list().get(i);
					ListaUsuarioImpl usuarioImpl = new ListaUsuarioImpl();

					usuarioImpl.setId(usuario.get_id());
					usuarioImpl.setNombre(usuario.get_name());
					usuarioImpl.setType(perfil);

					SicresOficinaDatos oficina = new SicresOficinaDatos();
					try {
						oficina.loadDeptId(usuario.get_deptId(), db);
						usuarioImpl.setCode(oficina.getCode());
					} catch (Exception e) {;}

					SicresUserIdentificacionDatos identificacion = new SicresUserIdentificacionDatos();
					try {
						identificacion.load(usuario.get_id(), db);
						usuarioImpl.setFirstName(identificacion.getFirstName());
						usuarioImpl.setSecondName(identificacion.getSecondName());
						usuarioImpl.setSurName(identificacion.getSurname());
					} catch (Exception e) {
						usuarioImpl.setSurName(usuario.get_name());
						usuarioImpl.setFirstName("");
						usuarioImpl.setSecondName("");
					}
					usuariosImpl.add(usuarioImpl);
				}
			} catch (Exception e) {
				logger.error("Error obteniendo usuarios", e);
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			} finally {
				try {
					if (db != null && db.existConnection()){
						db.close();
					}
				} catch (Exception e) {
					logger.error("No se ha podido cerrar la conexión a la BBDD");
				}
			}
		}
		return usuariosImpl;
	}

	private static ListaUsuariosImpl getTransformUsersLdap(UsuariosLdap usuarios, int perfil, String entidad) throws Exception {
		ListaUsuariosImpl usuariosImpl = new ListaUsuariosImpl();
		if(usuarios.get_list().size() > 0) {
			DbConnection db = new DbConnection();
			try {
				db.open(DBSessionManager.getSession());
				for (Iterator iterator = usuarios.get_list().iterator(); iterator
						.hasNext();) {
					UsuarioLdap usuario = (UsuarioLdap) iterator.next();
					ListaUsuarioImpl usuarioImpl = new ListaUsuarioImpl();

					usuarioImpl.setId(usuario.get_id());
					usuarioImpl.setNombre(usuario.get_ldapfullname());
					usuarioImpl.setType(perfil);

					SicresUserIdentificacionDatos identificacion = new SicresUserIdentificacionDatos();
					try {
						identificacion.load(usuario.get_id(), db);
						usuarioImpl.setFirstName(identificacion.getFirstName());
						usuarioImpl.setSecondName(identificacion.getSecondName());
						usuarioImpl.setSurName(identificacion.getSurname());
					} catch (Exception e) {
						usuarioImpl.setSurName(usuario.get_ldapfullname());
						usuarioImpl.setFirstName("");
						usuarioImpl.setSecondName("");
					}

					usuariosImpl.add(usuarioImpl);
				}
			} catch (Exception e) {
				logger.error("Error obteniendo usuarios", e);
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			} finally {
				try {
					if (db != null && db.existConnection()){
						db.close();
					}
				} catch (Exception e) {
					logger.error("No se ha podido cerrar la conexión a la BBDD");
				}
			}
		}
		return usuariosImpl;
	}

	public static void asociarOficinasAUsuario(
			int idUser, int idOfic, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			SicresUsuarioOficinaDatos relacion = new SicresUsuarioOficinaDatos();
			relacion.setId(IdsGenerator.generateNextId(IdsGenerator.SCR_USEROFIC, entidad));
			relacion.setIdUser(idUser);
			relacion.setIdOfic(idOfic);

			db.open(DBSessionManager.getSession());
			relacion.add(db);

			//Comprobar si el usuario tiene oficina prferente
			SicresUsuarioConfigImpl usuarioConfig = getSicresUsuarioConfigImplByUserId(idUser, entidad);

			//Si el usuario no tiene oficina preferente, asociarle esta como preferente
			if(usuarioConfig == null || usuarioConfig.getIdOficPref() == 0 || usuarioConfig.getIdOficPref() == IeciTdType.NULL_LONG_INTEGER){
				asociarOficinaPreferenteAUsuario(idUser, idOfic, entidad);
			}
		} catch (Exception e) {
			logger.error("Error asociando oficina");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static void desasociarOficinasAUsuario(
			int idUser, int idOfic, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			//Traducir idDept por idOfic
			// SicresOficinaImpl oficina =
			// RPAdminOficinaManager.getOficinaById(idOfic, entidad);
			// int idRealOfic = oficina.getId();

			db.open(DBSessionManager.getSession());
			SicresUsuarioOficinaDatos relacion = new SicresUsuarioOficinaDatos();
			relacion.load(idUser, idOfic, db);
			relacion.delete(db);

			//Eliminar la Oficina Preferente del Usuario
			Integer idOficina = getIdOficinaPreferenteUsuario(idUser, entidad);

			if(idOficina != null && idOficina.intValue() == idOfic){
				int idOficPref = IeciTdType.NULL_LONG_INTEGER;

				Oficina oficinaDepto = getOficinaDeptoUsuario(idUser, entidad);
				if (oficinaDepto != null){
					idOficPref = oficinaDepto.getId();
				} else {
					SicresOficinasImpl oficinasAsociadas = ISicresRPAdminOficinaManager
							.obtenerOficinasAgregadasAUsuario(idUser, entidad);
					if (oficinasAsociadas != null && oficinasAsociadas.count() > 0){
						SicresOficinaImpl oficinaAsociada = oficinasAsociadas.get(0);
						idOficPref = oficinaAsociada.getId();
					}
				}

				SicresUsuarioConfigDatos usuarioConfig = new SicresUsuarioConfigDatos();
				usuarioConfig.loadById(idUser, db);
				usuarioConfig.setIdOficPref(idOficPref);
				usuarioConfig.update(db);
			}

		} catch (Exception e) {
			logger.error("Error desasociando oficina");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}


	public static void desasociarOficinasAUsuarioLDAP(
			int idUser, int idOfic, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			//Traducir idDept por idOfic
			// SicresOficinaImpl oficina =
			// RPAdminOficinaManager.getOficinaById(idOfic, entidad);
			// int idRealOfic = oficina.getId();

			db.open(DBSessionManager.getSession());
			SicresUsuarioOficinaDatos relacion = new SicresUsuarioOficinaDatos();
			relacion.load(idUser, idOfic, db);
			relacion.delete(db);

			//Eliminar la Oficina Preferente del Usuario
			Integer idOficina = getIdOficinaPreferenteUsuario(idUser, entidad);

			if(idOficina != null && idOficina.intValue() == idOfic){
				int idOficPref = IeciTdType.NULL_LONG_INTEGER;

				Oficina oficinaDepto = getOficinaDeptoUsuarioLDAP(idUser, entidad);
				if (oficinaDepto != null){
					idOficPref = oficinaDepto.getId();
				} else {
					SicresOficinasImpl oficinasAsociadas = ISicresRPAdminOficinaManager
							.obtenerOficinasAgregadasAUsuario(idUser, entidad);
					if (oficinasAsociadas != null && oficinasAsociadas.count() > 0){
						SicresOficinaImpl oficinaAsociada = oficinasAsociadas.get(0);
						idOficPref = oficinaAsociada.getId();
					}
				}

				SicresUsuarioConfigDatos usuarioConfig = new SicresUsuarioConfigDatos();
				usuarioConfig.loadById(idUser, db);
				usuarioConfig.setIdOficPref(idOficPref);
				usuarioConfig.update(db);
			}

		} catch (Exception e) {
			logger.error("Error desasociando oficina");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}
	/**
	 * Obtiene el {@link SicresUsuarioConfigImpl} de un usuario
	 * @param userId Identificador del Usuario
	 * @param entidad Entidad
	 * @param db Conexión a la base de datos.
	 * @return {@link SicresUsuarioConfigImpl}
	 */
	public static SicresUsuarioConfigImpl getSicresUsuarioConfigImplByUserId(int userId, String entidad){
		DbConnection db = new DbConnection();
		SicresUsuarioConfigDatos configUsuario = new SicresUsuarioConfigDatos();
		try {
			db.open(DBSessionManager.getSession());
			configUsuario.loadById(userId, db);
		} catch (Exception e) {
			if(logger.isDebugEnabled())
				logger.debug("Error al obtener la configuración del usuario");
			return null;
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}

		return configUsuario;
	}

	/**
	 * Obtiene el {@link SicresOficinaImpl} asociado a un usuario
	 * @param userId Identificador del usuario
	 * @param entidad Entidad
	 * @return {@link SicresOficinaImpl}
	 */
	private static SicresOficinaImpl getSicresOficinaImplByDeptId(int userId, String entidad){
		SicresOficinaImpl sicresOficinaImpl = null;

		Usuario usuario = getUser(userId, entidad);

		if(usuario != null){
			try {
				sicresOficinaImpl = ISicresRPAdminOficinaManager.getOficinaByDeptId(usuario.get_deptId(), entidad);
			} catch (Exception e) {
				if(logger.isDebugEnabled())
					logger.debug("Error al obtener la oficina asociada al departamento del usuario");

				return null;
			}
		}
		return sicresOficinaImpl;

	}

	/**
	 * Obtiene el {@link SicresOficinaImpl} asociado a un usuario LDAP
	 * @param userId Identificador del usuario LDAP
	 * @param entidad Entidad
	 * @return {@link SicresOficinaImpl}
	 */
	private static SicresOficinaImpl getSicresOficinaLDAPImplByDeptId(int userId, String entidad){
		SicresOficinaImpl sicresOficinaImpl = null;

		UsuarioLdap usuario = getUserLdap(userId, entidad);

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		try {
			Entidad entity = new Entidad();
			entity.setIdentificador(entidad);

			Oficinas oficinasDepto = oServicio.obtenerOficinasUsuarioLdap(usuario.get_ldapguid(), entity);

			if((usuario != null) && (oficinasDepto.getSize()>0)){
				//seleccionamos para trabajar el primer departamento del array
				sicresOficinaImpl = ISicresRPAdminOficinaManager.getOficinaByDeptId(oficinasDepto.get(0).getId(), entidad);
			}
		} catch (Exception e) {
			if(logger.isDebugEnabled())
				logger.debug("Error al obtener la oficina asociada al departamento del usuario");
		}
		return sicresOficinaImpl;

	}

	public static Oficina getOficinaDeptoUsuario(int userId, String entidad){
		Oficina oficina = null;

		SicresOficinaImpl sicresOficinaImpl = getSicresOficinaImplByDeptId(userId, entidad);

		if(sicresOficinaImpl != null){
			oficina = new Oficina();
			oficina.setCodigo(sicresOficinaImpl.getCode());
			oficina.setAbreviatura(sicresOficinaImpl.getAcron());
			oficina.setId(sicresOficinaImpl.getId());
			oficina.setNombre(sicresOficinaImpl.getName());
		}

		return oficina;
	}

	public static Oficina getOficinaDeptoUsuarioLDAP(int userId, String entidad){
		Oficina oficina = null;

		SicresOficinaImpl sicresOficinaImpl = getSicresOficinaLDAPImplByDeptId(userId, entidad);

		if(sicresOficinaImpl != null){
			oficina = new Oficina();
			oficina.setCodigo(sicresOficinaImpl.getCode());
			oficina.setAbreviatura(sicresOficinaImpl.getAcron());
			oficina.setId(sicresOficinaImpl.getId());
			oficina.setNombre(sicresOficinaImpl.getName());
		}

		return oficina;
	}

	/**
	 * Asocia la Oficina como Preferente
	 * @param userId Identificador del Usuario
	 * @param idOficPref Identificador de la Oficina
	 * @param entidad Entidad
	 * @param db Conexión a la Base De Datos
	 * @throws ISicresRPAdminDAOException
	 * @throws Exception
	 */
	public static void asociarOficinaPreferenteAUsuario(int userId, int idOficPref, String entidad) throws ISicresRPAdminDAOException{
		DbConnection db = new DbConnection();
		try{
			db.open(DBSessionManager.getSession());
			insertarOActualizarOficinaPrefenterAUsuario(userId,idOficPref,entidad,db);
		} catch (Exception e) {
			logger.error("Error asociando oficina");
			throw new ISicresRPAdminDAOException(
				ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	/**
	 * Desasocia la Oficina como Preferente
	 * @param userId Identificador del Usuario
	 * @param entidad Entidad
	 * @param db Conexión a la Base De Datos
	 * @throws ISicresRPAdminDAOException
	 * @throws Exception
	 */
	public static void desasociarOficinaPreferenteAUsuario(int userId, String entidad) throws ISicresRPAdminDAOException{
		DbConnection db = new DbConnection();
		try{
			db.open(DBSessionManager.getSession());
			SicresUsuarioConfigImpl sicresUsuarioConfigImpl = getSicresUsuarioConfigImplByUserId(userId, entidad);

			if(sicresUsuarioConfigImpl != null){
				//Actualizar el valor
				sicresUsuarioConfigImpl.setIdOficPref(IeciTdType.NULL_LONG_INTEGER);
				new SicresUsuarioConfigDatos(sicresUsuarioConfigImpl).update(db);
			}

		} catch (Exception e) {
			logger.error("Error asociando oficina");
			throw new ISicresRPAdminDAOException(
				ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	private static void insertarOActualizarOficinaPrefenterAUsuario(int userId,
			int idOficPref,String entidad ,DbConnection db) throws ISicresRPAdminDAOException, Exception {
		SicresUsuarioConfigImpl sicresUsuarioConfigImpl = getSicresUsuarioConfigImplByUserId(userId, entidad);

		if(sicresUsuarioConfigImpl != null){
			//Actualizar el valor
			sicresUsuarioConfigImpl.setIdOficPref(idOficPref);
			new SicresUsuarioConfigDatos(sicresUsuarioConfigImpl).update(db);
		}
		else{
			//Insertar nuevo valor
			sicresUsuarioConfigImpl = new SicresUsuarioConfigImpl();
			sicresUsuarioConfigImpl.setUserId(userId);
			sicresUsuarioConfigImpl.setIdOficPref(idOficPref);
			new SicresUsuarioConfigDatos(sicresUsuarioConfigImpl).add(db);
		}

	}

	/**
	 * Asocia al Usuario la Oficina asociada al Departamento como Oficina Preferente
	 * @param userId Identificador del Usuario
	 * @param entidad Entidad
	 * @throws ISicresRPAdminDAOException
	 */
	private static void asociarOficinaPreferenteDefectoAUsuario(int userId, String entidad)
		throws ISicresRPAdminDAOException {
			DbConnection db = new DbConnection();
			try {
				db.open(DBSessionManager.getSession());

				//Obtener la la Oficina Preferente
				SicresOficinaImpl sicresOficinaImpl = getSicresOficinaImplByDeptId(userId, entidad);

				if(sicresOficinaImpl != null){
					//Obtener la Configuración del Usuario
					int idOficPref = sicresOficinaImpl.getId();
					insertarOActualizarOficinaPrefenterAUsuario(userId, idOficPref, entidad, db);

				}

			} catch (Exception e) {
				if(logger.isDebugEnabled())
					logger.debug("Error al Asociar al usuario la Oficina Preferente");

				throw new ISicresRPAdminDAOException(
						ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			} finally {
				try {
					if (db != null && db.existConnection()){
						db.close();
					}
				} catch (Exception e) {
					logger.error("No se ha podido cerrar la conexión a la BBDD");
				}
			}
	}

	public static Integer getIdOficinaPreferenteUsuario(int userId, String entidad){

		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			SicresUsuarioConfigImpl sicresUsuarioConfigImpl = getSicresUsuarioConfigImplByUserId(userId, entidad);

			if(sicresUsuarioConfigImpl != null){
				int idOficina = sicresUsuarioConfigImpl.getIdOficPref();
				if(idOficina == 0 || idOficina == IeciTdType.NULL_LONG_INTEGER) {
					return null;
				}
				else{
					return new Integer(idOficina);
				}
			}
		} catch (ISicresRPAdminDAOException rpa){
			if(logger.isDebugEnabled())	logger.debug("El usuario no tiene asociada oficina preferente", rpa);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}

		return null;
	}

	public static UsuariosLdap getUsuariosOficinaLdap(int deptId, String entidad) throws ISicresRPAdminDAOException {
		UsuariosLdap usuariosLdap = new UsuariosLdap();
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();

			LdapConnection ldapConn = new LdapConnection();

			// Obtener la información del servidor
			LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn.open(connCfg);

			//Obtenemos todos los usuarios de la aplicacion
			UsuariosLdap usersLdap = obtenerListadoUsuariosLdap(entidad);
			for(int i=0; i<usersLdap.count(); i++){
				//Obtenemos el Dn del usuario
				String ldapguiduser = usersLdap.get(i).get_ldapguid();
				String userDn = LdapUasFns.findUserDnByGuid(ldapConn, "", LdapScope.SUBTREE, ldapguiduser, entidad);
				if(userDn != null){
					// Obtener los guids de los grupos a los que pertenece el usuario
					IeciTdShortTextArrayList guidGroups = LdapUasFns.findUserGroupGuids(ldapConn, "", LdapScope.SUBTREE, userDn);

					String ldapGuidsGroup[] = new String[guidGroups.count()];
					for(int j=0; j<guidGroups.count(); j++)
						ldapGuidsGroup[j] = guidGroups.get(j);

					//Comprobar si existe alguna oficina creada que pertenezca a alguno de los grupos del usuario
					//Si fuese asi entonces cogeriamos el usuario par amostrar si no lo eliminamos de la lista
					IUserDeptHdrDatos oficinas = new IUserDeptHdrDatos();
					if((ldapGuidsGroup.length>0) && (oficinas.loadByGuidsGroupAndDeptId(deptId, ldapGuidsGroup, db))){
						usuariosLdap.add(usersLdap.get(i));
					}
				}
			}

			db.endTransaction(true);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios LDAP pertenecientes a la oficina.");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
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
	public static boolean validateUserInStructureLDAP(String ldapguid, String entidad) throws ISicresRPAdminDAOException{

		boolean result = false;
		LdapConnection ldapConn = new LdapConnection();

		try {
			// Obtener la información del servidor
			LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn.open(connCfg);

			//validamos que el usuario aun este activo en el SISTEMA LDAP
			String userDn = LdapUasFns.findUserDnByGuid(ldapConn, "", LdapScope.SUBTREE, ldapguid, entidad);
			if(userDn != null){
				result = true;
			}
		} catch (Exception e) {
			logger.error("Error obteniendo conexion con usuario LDAP.");
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		return result;
	}
}