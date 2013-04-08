package ieci.tecdoc.sgm.rpadmin;

import ieci.tecdoc.sgm.base.dbex.DbDataType;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Lista;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap;
import ieci.tecdoc.sgm.core.services.rpadmin.Campo;
import ieci.tecdoc.sgm.core.services.rpadmin.Campos;
import ieci.tecdoc.sgm.core.services.rpadmin.Contador;
import ieci.tecdoc.sgm.core.services.rpadmin.Contadores;
import ieci.tecdoc.sgm.core.services.rpadmin.Distribucion;
import ieci.tecdoc.sgm.core.services.rpadmin.Distribuciones;
import ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Filtro;
import ieci.tecdoc.sgm.core.services.rpadmin.Filtros;
import ieci.tecdoc.sgm.core.services.rpadmin.InformeBean;
import ieci.tecdoc.sgm.core.services.rpadmin.InformesBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Libro;
import ieci.tecdoc.sgm.core.services.rpadmin.LibroBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Libros;
import ieci.tecdoc.sgm.core.services.rpadmin.Oficina;
import ieci.tecdoc.sgm.core.services.rpadmin.OficinaBean;
import ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Oficinas;
import ieci.tecdoc.sgm.core.services.rpadmin.OptionBean;
import ieci.tecdoc.sgm.core.services.rpadmin.OptionsBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizacion;
import ieci.tecdoc.sgm.core.services.rpadmin.OrganizacionBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizaciones;
import ieci.tecdoc.sgm.core.services.rpadmin.PermisoSicres;
import ieci.tecdoc.sgm.core.services.rpadmin.PermisosSicres;
import ieci.tecdoc.sgm.core.services.rpadmin.RPAdminException;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;
import ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.TiposAsuntoBean;
import ieci.tecdoc.sgm.core.services.rpadmin.Transporte;
import ieci.tecdoc.sgm.core.services.rpadmin.Transportes;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistrador;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistradorBean;
import ieci.tecdoc.sgm.core.services.rpadmin.UsuariosRegistradores;
import ieci.tecdoc.sgm.rpadmin.beans.FiltroImpl;
import ieci.tecdoc.sgm.rpadmin.beans.FiltrosImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IDocArchHDRImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IDocArchsHDRImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IUserObjPermImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IUserObjsPermsImpl;
import ieci.tecdoc.sgm.rpadmin.beans.IVolArchListImpl;
import ieci.tecdoc.sgm.rpadmin.beans.ListaUsuarioImpl;
import ieci.tecdoc.sgm.rpadmin.beans.ListaUsuariosImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresContadorCentralImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresContadorOficinaImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresContadoresOficinasImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresLibroEstadoImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresLibroOficinaImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresLibrosOficinasImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresListaDistribucionesImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOficinaLocalizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOficinasImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionLocalizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresOrganizacionesImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOficinaImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresTipoOrganizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOficinaImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresTiposOrganizacionesImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUserIdentificacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUserLocalizacionImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUserPermisosImpl;
import ieci.tecdoc.sgm.rpadmin.beans.SicresUsuarioAgregadoImpl;
import ieci.tecdoc.sgm.rpadmin.beans.adapter.AdapterVOSigem;
import ieci.tecdoc.sgm.rpadmin.config.spring.RegistroPresencialAdminSpringApplicationContext;
import ieci.tecdoc.sgm.rpadmin.exception.RPAdminDAOException;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminDistribucionManager;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminInformeManager;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminLibroManager;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminOficinaManager;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminOrganizacionManager;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminTipoAsuntoManager;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminTransporteManager;
import ieci.tecdoc.sgm.rpadmin.manager.RPAdminUserManager;

import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterField;
import es.ieci.tecdoc.isicres.admin.core.manager.ManagerUtils;

/*
 * Consideraciones generales, la oficina está asociada inicialmente a un
 * departamento, además al usar tablas invesdoc y tablas isicres usa indistintamente
 * el id de departamento como el el isicres, a la parte web he decidido
 * pasarle siempre el de departamento.
 * Esto podría dificultar la administración de varias oficinas para un departamento
 * por lo que convendría pensarlo con más tranquilidad tras la entrega de Abril del 2008
 */

public class ServicioRPAdminAdapter implements ServicioRPAdmin {

	private static Logger logger = Logger
			.getLogger(ServicioRPAdminAdapter.class);

	private static Map perfiles = new TreeMap();

	private static ApplicationContext contexto = RegistroPresencialAdminSpringApplicationContext
			.getInstance().getApplicationContext();

	static {
		// perfiles.put("0", "Sin perfil");
		perfiles.put("1", "operador");
		// perfiles.put("2", "administrador");
		perfiles.put("3", "superusuario");
	}

	public UsuariosRegistradores obtenerUsuarios(Entidad entidad)
			throws RPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			ListaUsuariosImpl usuariosDAO = RPAdminUserManager
					.obtenerUsuariosListado(entidad.getIdentificador());
			usuariosAPI = getAPIUsuarios(usuariosDAO, ManagerUtils.SIN_AGREGADOS);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo usuarios");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosLdap(Entidad entidad)
			throws RPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			UsuariosLdap usuariosDAO = RPAdminUserManager.obtenerListadoUsuariosLdap(entidad.getIdentificador());
			usuariosAPI = getAPIUsuariosLdap(usuariosDAO, ManagerUtils.SIN_AGREGADOS);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo usuarios de LDAP");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosAsociacion(int idOfic, Entidad entidad) throws RPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			OficinaBean oficina = obtenerOficina(idOfic, entidad);
			ListaUsuariosImpl usuariosDAO = RPAdminUserManager
					.obtenerUsuariosAsociacion(idOfic, oficina.getDeptId(),
							entidad.getIdentificador());
			usuariosAPI = getAPIUsuarios(usuariosDAO, ManagerUtils.CON_AGREGADOS);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo posibles usuarios para asociar a una oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosLdapAsociacion(int idOfic, Entidad entidad) throws RPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			UsuariosLdap usuariosDAO = RPAdminUserManager.obtenerUsuariosLdapAsociacion(idOfic, entidad.getIdentificador());
			usuariosAPI = getAPIUsuariosLdap(usuariosDAO, ManagerUtils.CON_AGREGADOS);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo usuarios LDAP para asociar a una oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosAPI;
	}

	public UsuarioRegistradorBean obtenerUsuario(int id, Entidad entidad)
			throws RPAdminException {
		UsuarioRegistradorBean usuarioAPI = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			Usuario userDAO = RPAdminUserManager.getUser(id, entidad
					.getIdentificador());
			SicresUserPermisosImpl permisos = RPAdminUserManager.getPermisos(
					id, entidad.getIdentificador());
			SicresUserIdentificacionImpl identificacion = RPAdminUserManager
					.getIdentificacion(id, entidad.getIdentificador());
			SicresUserLocalizacionImpl localizacion = RPAdminUserManager
					.getLocalizacion(id, entidad.getIdentificador());
			usuarioAPI = getAPIUsuarioBean(userDAO, permisos,
					identificacion, localizacion);
		} catch (Exception e) {
			logger.error("Error al obtener el usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarioAPI;
	}

	public UsuarioRegistradorBean obtenerUsuarioLdap(int id, Entidad entidad)
			throws RPAdminException {
		UsuarioRegistradorBean usuarioAPI = null;
		UsuarioLdap userLdapDAO = null;
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		try {
			userLdapDAO = RPAdminUserManager.getUserLdap(id, entidad.getIdentificador());
			SicresUserPermisosImpl permisos = RPAdminUserManager.getPermisos(
					id, entidad.getIdentificador());
			SicresUserIdentificacionImpl identificacion = RPAdminUserManager
					.getIdentificacion(id, entidad.getIdentificador());
			SicresUserLocalizacionImpl localizacion = RPAdminUserManager
					.getLocalizacion(id, entidad.getIdentificador());
			usuarioAPI = getAPIUsuarioBeanLdap(userLdapDAO, permisos, identificacion, localizacion);
		} catch (Exception e) {
			logger.error("Error al obtener el usuario LDAP", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		//validamos la existencia del usuario en la estructura LDAP
		if(userLdapDAO != null){
			if(!RPAdminUserManager.validateUserInStructureLDAP(userLdapDAO.get_ldapguid(), entidad.getIdentificador())){
				logger.error("El usuario ["+ userLdapDAO.get_ldapfullname() + "] no ha sido encontrado dentro del sistema LDAP");
				throw new RPAdminDAOException(RPAdminDAOException.IUSER_NOT_FOUND_IN_LDAP);
			}
		}

		return usuarioAPI;
	}

	public UsuariosRegistradores obtenerUsuariosOficina(int idOfic, boolean usuarios,
			boolean agregados, Entidad entidad)	throws RPAdminException {

		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			OficinaBean oficina = obtenerOficina(idOfic, entidad);
			if(usuarios){
				usuariosDAO = RPAdminUserManager.getUsuarios(new int[]{oficina.getDeptId()}, entidad.getIdentificador());
				usuariosDAO.getLista().addAll(RPAdminUserManager.getSuperusuarios(oficina.getDeptId(), entidad.getIdentificador()).getLista());
				usuariosAPI.getLista().addAll(getAPIUsuarios(usuariosDAO, ManagerUtils.SIN_AGREGADOS).getLista());
			}
			if(agregados){
				usuariosDAO = RPAdminUserManager.getUsuariosAgregados(idOfic, entidad.getIdentificador());
				usuariosAPI.getLista().addAll(getAPIUsuarios(usuariosDAO, ManagerUtils.CON_AGREGADOS).getLista());
			}
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios de la oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosOficinaLdap(int idOfic, boolean usuarios,
			boolean agregados, Entidad entidad)	throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {OficinaBean oficina = obtenerOficina(idOfic, entidad);
			if(usuarios){
				UsuariosLdap usuariosDAO = RPAdminUserManager.getUsuariosOficinaLdap(oficina.getDeptId(), entidad.getIdentificador());
				usuariosAPI = getAPIUsuariosLdap(usuariosDAO, ManagerUtils.SIN_AGREGADOS);
			}
			if(agregados){
				UsuariosLdap usuariosAgregadosDAO = RPAdminUserManager.getUsuariosAgregadosLdap(idOfic, entidad.getIdentificador());
				usuariosAPI.getLista().addAll(getAPIUsuariosLdap(usuariosAgregadosDAO, ManagerUtils.CON_AGREGADOS).getLista());
			}
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios LDAP pertenecientes a una determinada oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosAPI;
	}

	public int crearUsuario(UsuarioRegistradorBean usuario, Entidad entidad)
			throws RPAdminException {

		Usuario user = null;

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		// Comprobar que existe el usuario
		try {
			user = RPAdminUserManager.getUser(usuario.getNombre(), entidad
					.getIdentificador());
			if (user == null) {
				throw new RPAdminException(RPAdminException.USUARIO_INEXISTENTE);
			}
		} catch (RPAdminDAOException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Se ha intentado asociar un usuario sin conseguirlo");
			}
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		// Comprobar que existe la oficina de registro
		/*
		try {
			RPAdminOficinaManager.getOficinaByDeptId(user.get_deptId(), entidad
					.getIdentificador());
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Se ha intentado asociar un usuario sin conseguirlo");
			}
			throw new RPAdminException(RPAdminException.OFICINA_INEXISTENTE, e);
		}
		*/

		// Comprobar que no esté asociado ya
		try {
			Usuario userDAO = RPAdminUserManager.getUser(user.get_id(),
					entidad.getIdentificador());
			if (userDAO.get_Perfil(ManagerUtils.ISICRES_PROD_ID).get_profile() != 0) {
				throw new RPAdminException(
						RPAdminException.USUARIO_SICRES_EXISTENTE);
			}
		} catch (RPAdminDAOException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Se ha intentado asociar un usuario sin conseguirlo");
			}
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		try {
			usuario.setId(user.get_id());
			RPAdminUserManager.editarUsuario(usuario.getId(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario),
					getSicresUserIdentificacionImpl(usuario),
					getSicresUserLocalizacionImpl(usuario),entidad
							.getIdentificador(), true);
		} catch (Exception e) {
			logger.error("Error al crear el usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}


		return user.get_id();
	}

	public int crearUsuarioLdap(UsuarioRegistradorBean usuario, Entidad entidad)
			throws RPAdminException {

		UsuarioLdap user = null;

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		// Comprobar que existe el usuario
		try {
			user = RPAdminUserManager.getUserLdapByGuid(usuario.getLdapGuid(), entidad
					.getIdentificador());
			if (user != null) {
				throw new RPAdminException(RPAdminException.USUARIO_SICRES_EXISTENTE);
			}
		} catch (RPAdminDAOException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Se ha intentado asociar un usuario sin conseguirlo");
			}
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		int id = -1;

		try {

			id = RPAdminUserManager.crearUsuarioLdap(usuario.getLdapGuid(), usuario.getLdapFullName(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario), getSicresUserIdentificacionImpl(usuario), getSicresUserLocalizacionImpl(usuario), entidad.getIdentificador(), false);

		} catch (Exception e) {
			logger.error("Error al crear el usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return id;
	}

	public void editarUsuario(UsuarioRegistradorBean usuario, Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminUserManager.editarUsuario(usuario.getId(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario),
					getSicresUserIdentificacionImpl(usuario),
					getSicresUserLocalizacionImpl(usuario), entidad.getIdentificador(), false);
		} catch (Exception e) {
			logger.error("Error al editar el usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void editarUsuarioLdap(UsuarioRegistradorBean usuario, Entidad entidad) throws RPAdminException {
		try {

			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminUserManager.editarUsuarioLdap(usuario.getId(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario),
					getSicresUserIdentificacionImpl(usuario),
					getSicresUserLocalizacionImpl(usuario), entidad.getIdentificador(), false);
		} catch (Exception e) {
			logger.error("Error al editar el usuario de LDAP", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarUsuario(int id, Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminUserManager.eliminarUsuario(id, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar el usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarUsuarioLdap(int id, Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminUserManager.eliminarUsuarioLdap(id, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar el usuario LDAP", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public OptionsBean obtenerOficinasCombo(Entidad entidad) throws RPAdminException {
		OptionsBean optionsAPI = new OptionsBean();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager.obtenerOficinas(entidad.getIdentificador());
			optionsAPI = getAPIOptionsBean(oficinasDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo usuarios");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,e);
		}
		return optionsAPI;
	}


	public Oficinas obtenerOficinasParaLista(Entidad entidad) throws RPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager.obtenerOficinas(entidad.getIdentificador());
			oficinas = getOficinasFromDAO(oficinasDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo las oficinas");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	/**
	 * Obtiene los tipos de perfiles de informes y los devuelve en un bean
	 * @param entidad
	 * @return
	 * @throws RPAdminException
	 */
	public OptionsBean obtenerTiposInformesCombo(Entidad entidad)throws RPAdminException{
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		OptionsBean options = new OptionsBean();
		ResourceBundle rs = ResourceBundle.getBundle("ieci.tecdoc.sgm.rpadmin.manager.tipoInforme");
		Enumeration keys =  rs.getKeys();
		while(keys.hasMoreElements()){
			OptionBean option = new OptionBean();
			String key = keys.nextElement().toString();
			String value = rs.getString(key);
			option.setCodigo(key);
			option.setDescripcion(value);
			options.add(option);
		}
		return options;
	}

	public OptionsBean obtenerPerfilesCombo(Entidad entidad) throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		OptionsBean options = new OptionsBean();
		for (Iterator iter = perfiles.keySet().iterator(); iter.hasNext();) {
			OptionBean option = new OptionBean();
			Object key = iter.next();
			option.setCodigo(key.toString());
			option.setDescripcion(perfiles.get(key).toString());
			options.add(option);
		}
		return options;
	}

	public Oficinas obtenerOficinas(Entidad entidad) throws RPAdminException {
		Oficinas oficinasAPI = new Oficinas();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager
					.obtenerOficinasListado(entidad.getIdentificador());
			oficinasAPI = getAPIOficinas(oficinasDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinasAPI;
	}

	public OficinaBean obtenerOficina(int id, Entidad entidad) throws RPAdminException {
		OficinaBean oficinaAPI = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinaImpl oficina = RPAdminOficinaManager
					.getOficinaById(id, entidad.getIdentificador());
			SicresOficinaLocalizacionImpl localizacion = RPAdminOficinaManager
					.getLocalizacion(oficina.getId(), entidad.getIdentificador());
			oficinaAPI = getAPIOficinaBean(oficina, localizacion);
		} catch (Exception e) {
			logger.error("Error al obtener la oficina", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinaAPI;
	}

	public int crearOficina(OficinaBean oficina, Entidad entidad)
			throws RPAdminException {

		SicresOficinaImpl oficinaDAO = null;

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		if ((oficina!=null) && (oficina.getTipoDepartamento() == Departamento.LDAP_DEPT_TYPE)){
			// Comprobar que no esté asociado ya para Grupos LDAP
			try {
				oficinaDAO = RPAdminOficinaManager.getOficinaByLdapGroup(oficina.getLdapGuid(), entidad.getIdentificador());
			} catch (Exception e) {
				// Si hay excepción es porque no existia la oficina.
			}
			if (oficinaDAO != null) {
				logger.debug("Se intenta crear una oficina cuando su grupo LDAP ya está asociado a otra");

				// TODO NACHO Cambiar esta excepción
				throw new RPAdminException(
						RPAdminException.OFICINA_SICRES_EXISTENTE);
			}
		} else {
			// Comprobar que no esté asociado ya para departamentos Invesdoc
			try {
				oficinaDAO = RPAdminOficinaManager.getOficinaByDeptId(oficina
						.getDeptId(), entidad.getIdentificador());
			} catch (Exception e) {
				// Si hay excepción es porque no existia la oficina.
			}
			if (oficinaDAO != null) {
				logger.debug("Se intenta crear una oficina cuando esta ya existe");
				throw new RPAdminException(
						RPAdminException.OFICINA_SICRES_EXISTENTE);
			}
		}

		// Comprobar que el código no exista
		oficinaDAO = null;
		try {
			oficinaDAO = RPAdminOficinaManager.getOficinaByCode(
					safeUpperCase(oficina.getCodigo()), entidad
							.getIdentificador());
		} catch (Exception e) {
			// Si hay excepción es porque no existia la oficina.
		}
		if (oficinaDAO != null) {
			logger
					.debug("Se intenta crear una oficina cuando el código existe");
			throw new RPAdminException(
					RPAdminException.CODIGO_OFICINA_SICRES_EXISTENTE);
		}

		try {
			oficina.setFecha(new Date());
			RPAdminOficinaManager.crearOficina(getSicresOficinaImpl(oficina),
					getSicresOficinaLocalizacionImpl(oficina), getEntidadRegistralOficina(oficina), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al crear el usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return oficina.getId();
	}

	public void editarOficina(OficinaBean oficina, Entidad entidad)
			throws RPAdminException {

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminOficinaManager.editarOficina(getSicresOficinaImpl(oficina),
					getSicresOficinaLocalizacionImpl(oficina), getEntidadRegistralOficina(oficina), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al editar la oficina", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void eliminarOficina(int id, Entidad entidad)
			throws RPAdminException {

		// Comprobar que no existen usuarios
		ListaUsuariosImpl usuarios = null;

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		try {
			OficinaBean oficina = obtenerOficina(id, entidad);
			usuarios = RPAdminUserManager.obtenerUsuariosOficinaDept(oficina
					.getDeptId(), ManagerUtils.CON_SUPERUSUARIOS,
					ManagerUtils.SIN_AGREGADOS, entidad.getIdentificador());
		} catch (Exception e) {
			logger
					.error("Error al comprobar si existen usuarios en la oficina a borrar");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		if (usuarios == null || usuarios.count() > 0) {
			if (logger.isDebugEnabled()) {
				logger
						.error("Se ha intentado eliminar una oficina con usuarios asociados");
			}
			throw new RPAdminException(RPAdminException.OFICINA_CON_USUARIOS);
		}

		try {
			RPAdminOficinaManager.eliminarOficina(id, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar la oficina", e);
			throw new RPAdminException(
					RPAdminException.OFICINA_CON_REFERENCIAS, e);
		}
		return;
	}

	public void eliminarOficinaLDAP(int idOficina, Entidad entidad)
			throws RPAdminException {

		// Comprobar que no existen usuarios
		ListaUsuariosImpl usuarios = null;
		OficinaBean oficina = null;

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		try {
			oficina = obtenerOficina(idOficina, entidad);
			usuarios = RPAdminUserManager.obtenerUsuariosOficinaDept(
					oficina.getDeptId(), ManagerUtils.CON_SUPERUSUARIOS,
					ManagerUtils.SIN_AGREGADOS, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al comprobar si existen usuarios en la oficina a borrar");
			throw new RPAdminException(
					RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		if (usuarios == null || usuarios.count() > 0) {
			if (logger.isDebugEnabled()) {
				logger.error("Se ha intentado eliminar una oficina con usuarios asociados");
			}
			throw new RPAdminException(
					RPAdminException.OFICINA_CON_USUARIOS);
		}

		try {
			RPAdminOficinaManager.eliminarOficinaLDAP(idOficina,
					entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar la oficina", e);
			throw new RPAdminException(
					RPAdminException.OFICINA_CON_REFERENCIAS, e);
		}
		return;
	}

	public OptionsBean obtenerDepartamentosCombo(boolean oficinas,
			Entidad entidad) throws RPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			Departamentos departamentosDAO = RPAdminOficinaManager
					.obtenerDepartamentos(oficinas, entidad.getIdentificador());
			optionsAPI = getAPIOptionsBean(departamentosDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public OptionsBean obtenerEntidadesRegistralesCombo(Entidad entidad)
			throws RPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOrganizacionesImpl organizacionesDAO = RPAdminOrganizacionManager
					.obtenerOrganizacionesPorTipo(
							ManagerUtils.ID_TYPE_ENTIDAD_REGISTRAL, entidad
									.getIdentificador());
			optionsAPI = getAPIOptionsBean(organizacionesDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public OptionsBean obtenerTipoOficinasCombo(Entidad entidad)
			throws RPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresTiposOficinaImpl tiposDAO = RPAdminOficinaManager
					.obtenerTiposOficina(entidad.getIdentificador());
			optionsAPI = getAPIOptionsBean(tiposDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public Libros obtenerLibros(int tipoLibro, Entidad entidad)
			throws RPAdminException {
		Libros librosAPI = new Libros();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IDocArchsHDRImpl librosDAO = RPAdminLibroManager.obtenerLibros(
					tipoLibro, entidad.getIdentificador());
			librosAPI = getAPILibros(librosDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo libros");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return librosAPI;
	}

	public PermisosSicres obtenerPermisosOficinasLibro(int bookId,
			Entidad entidad) throws RPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IUserObjsPermsImpl permisosDAO = RPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_OFICINA,
							entidad.getIdentificador());

			SicresLibrosOficinasImpl oficinasDAO = RPAdminLibroManager
					.obtenerOficinas(bookId, entidad.getIdentificador());

			permisosAPI = getAPIPermisos(oficinasDAO, permisosDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo permisos");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return permisosAPI;
	}

	public Oficinas obtenerOficinasDesasociadasALibro(int bookId,
			Entidad entidad) throws RPAdminException {
		Oficinas optionsAPI = new Oficinas();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager
					.obtenerOficinasDesasociadasALibro(bookId, entidad
							.getIdentificador());

			optionsAPI = getAPIOficinas(oficinasDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo oficinas desasociadas");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public void asociarOficinaALibro(int bookId, int[] destIds, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresLibroEstadoImpl libro = RPAdminLibroManager.getLibro(bookId, entidad.getIdentificador());
			RPAdminLibroManager.asociarOficinasALibro(
					getSicresLibrosOficinasImpl(bookId, destIds, libro
							.getNumerationType(), entidad), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asociar oficinas a un libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void desasociarOficinaALibro(int bookId, int destId, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminLibroManager.desasociarOficinasALibro(
					getSicresLibrosOficinasImpl(bookId, new int[] { destId }, entidad),
					entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asociar oficinas a un libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void modificarPermisos(PermisosSicres permisos, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminLibroManager
					.modificarPermisos(getToDeleteIUserObjsPermsImpl(permisos),
							getIUserObjsPermsImpl(permisos), entidad
									.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asociar oficinas a un libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public PermisosSicres obtenerPermisosUsuarios(int bookId, Entidad entidad)
			throws RPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IUserObjsPermsImpl permisosDAO = RPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO,
							entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = RPAdminUserManager.getUsuarios(entidad
					.getIdentificador());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return permisosAPI;
	}

	public PermisosSicres obtenerPermisosUsuariosOficinas(int bookId, int idsDept[], Entidad entidad) throws RPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IUserObjsPermsImpl permisosDAO = RPAdminLibroManager.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO, entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = RPAdminUserManager.getUsuarios(idsDept, entidad.getIdentificador());
			int idsUser[] = null;
			if(usuariosDAO.count() > 0){
				idsUser = new int[usuariosDAO.count()];
				for(int i=0; i < usuariosDAO.count(); i++){
					ListaUsuarioImpl user = usuariosDAO.get(i);
					idsUser[i] = user.getId();
				}
			}
			SicresOficinasImpl oficinas = RPAdminOficinaManager.getOficinasByDeptsId(idsDept, entidad.getIdentificador());
			int idsOfic[] = new int[idsDept.length];
			for(int i=0; i<oficinas.count(); i++)
				idsOfic[i] = oficinas.get(i).getId();

			usuariosDAO.getLista().addAll(RPAdminUserManager.getPermisosUsuariosAgregados(idsOfic, idsUser, entidad.getIdentificador()).getLista());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return permisosAPI;
	}

	public PermisosSicres obtenerPermisosUsuariosLdap(int bookId,
			Entidad entidad) throws RPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IUserObjsPermsImpl permisosDAO = RPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO,
							entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = RPAdminUserManager.getUsuariosLdap(entidad
					.getIdentificador());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);

		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return permisosAPI;
	}

	public PermisosSicres obtenerPermisosUsuariosLdapOficinas(int bookId,
			int[] idsDept, Entidad entidad) throws RPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IUserObjsPermsImpl permisosDAO = RPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO,
							entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = RPAdminUserManager.getUsuariosLdap(idsDept, entidad
					.getIdentificador());
			int idsUser[] = null;
			if (usuariosDAO.count() > 0) {
				idsUser = new int[usuariosDAO.count()];
				for (int i = 0; i < usuariosDAO.count(); i++) {
					ListaUsuarioImpl user = usuariosDAO.get(i);
					idsUser[i] = user.getId();
				}
			}

			SicresOficinasImpl oficinas = RPAdminOficinaManager
					.getOficinasByDeptsId(idsDept, entidad.getIdentificador());
			int idsOfic[] = new int[idsDept.length];
			for (int i = 0; i < oficinas.count(); i++) {
				idsOfic[i] = oficinas.get(i).getId();
			}

			usuariosDAO.getLista().addAll(
					RPAdminUserManager.getPermisosUsuariosLdapAgregados(
							idsOfic, idsUser, entidad.getIdentificador())
							.getLista());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);

		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return permisosAPI;
	}


	public void asociarListaALibro(int idBook, int idLista, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IVolArchListImpl lista = new IVolArchListImpl();
			lista.setArchId(idBook);
			lista.setListId(idLista);
			RPAdminLibroManager.asociarListaALibro(lista, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asocial la lista al libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

	}

	public void asociarUsuarioAOficinas(String[] idsUser, int idOfic, String entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad);

			RPAdminOficinaManager.asociarUsuariosAOficina(idsUser, idOfic, entidad);
		} catch (Exception e) {
			logger.error("Error al asociar usuarios a una oficina", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public OptionsBean obtenerListas(Entidad entidad) throws RPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			Listas listasDAO = RPAdminLibroManager.obtenerListas(entidad
					.getIdentificador());
			optionsAPI = getAPIOptionsBean(listasDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public LibroBean obtenerLibroBean(int idBook, Entidad entidad)
			throws RPAdminException {
		LibroBean beanAPI = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			IDocArchHDRImpl archivador = RPAdminLibroManager.getArchivador(
					idBook, entidad.getIdentificador());
			IVolArchListImpl lista = RPAdminLibroManager.getLista(idBook,
					entidad.getIdentificador());
			SicresLibroEstadoImpl libro = RPAdminLibroManager.getLibro(idBook,
					entidad.getIdentificador());

			beanAPI = getAPILibroBean(archivador, lista, libro);
		} catch (Exception e) {
			logger.error("Error al obtener el libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return beanAPI;
	}

	public int crearLibro(LibroBean libroAPI, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresLibroEstadoImpl libroDAO = getSicresLibroEstadoImpl(libroAPI);
			IDocArchHDRImpl archivadorDAO = getIDocArchHDRImpl(libroAPI);
			int idLibro = RPAdminLibroManager.crearLibro(archivadorDAO,
					libroDAO, entidad.getIdentificador());
			return idLibro;
		} catch (Exception e) {
			logger.error("Error al crear el libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public void editarLibro(LibroBean libroAPI, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresLibroEstadoImpl libroDAO = getSicresLibroEstadoImpl(libroAPI);
			IDocArchHDRImpl archivadorDAO = getIDocArchHDRImpl(libroAPI);
			RPAdminLibroManager.editarLibro(libroDAO, archivadorDAO, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al editar el libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public void modificarEstadoLibro(int idBook, String usuario, int idEstado,
			Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresLibroEstadoImpl libro = RPAdminLibroManager.getLibro(idBook,
					entidad.getIdentificador());
			libro.setCloseDate(new Date());
			libro.setState(idEstado);
			libro.setCloseUser(usuario);
			RPAdminLibroManager.editarLibro(libro, null, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al modificar el estado del libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public void eliminarLibro(int idBook, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminLibroManager.eliminarLibro(idBook, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al editar el libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public Organizaciones obtenerOrganizacionesPadre(Entidad entidad)
			throws RPAdminException {
		Organizaciones listaAPI = new Organizaciones();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresTiposOrganizacionesImpl listaDAO = RPAdminOrganizacionManager
					.obtenerTiposOrganizaciones(entidad.getIdentificador());
			listaAPI = getAPIOrganizaciones(listaDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo organizacionesPadre");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public Organizaciones obtenerHijosOrganizacion(int orgId, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		// Si el identificador es el 0, se refiere a tipos de unidades
		// administrativas.
		if (orgId == 0)
			return obtenerOrganizacionesPadre(entidad);

		Organizaciones listaAPI = new Organizaciones();
		try {
			SicresOrganizacionesImpl listaDAO = RPAdminOrganizacionManager
					.obtenerOrganizaciones(orgId, entidad.getIdentificador());
			listaAPI = getAPIOrganizaciones(listaDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo organizacionesPadre");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public OrganizacionBean obtenerOrganizacion(int orgId, Entidad entidad)
			throws RPAdminException {
		OrganizacionBean beanAPI = null;

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			// Si el id es positivo se trata de una organización, sino, se trata
			// de un tipo de organizacion
			if (orgId > 0) {
				SicresOrganizacionImpl organizacion = RPAdminOrganizacionManager
						.getOrganizacion(orgId, entidad.getIdentificador());
				SicresOrganizacionLocalizacionImpl localizacion = RPAdminOrganizacionManager
						.getLocalizacion(orgId, entidad.getIdentificador());
				beanAPI = getAPIOrganizacionBean(organizacion, localizacion);
			} else {
				SicresTipoOrganizacionImpl tipoOrganizacion = RPAdminOrganizacionManager
						.getTipoOrganizacion(orgId, entidad.getIdentificador());
				beanAPI = getAPIOrganizacionBean(tipoOrganizacion);
			}

		} catch (Exception e) {
			logger.error("Error al obtener la organización", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return beanAPI;
	}

	public OrganizacionBean obtenerOrganizacionByCode(String code,
			Entidad entidad) throws RPAdminException {
		OrganizacionBean beanAPI = null;

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOrganizacionImpl organizacion = RPAdminOrganizacionManager
					.getOrganizacionByCode(code, entidad.getIdentificador());
			if (organizacion != null) {
				SicresOrganizacionLocalizacionImpl localizacion = RPAdminOrganizacionManager
						.getLocalizacion(organizacion.getId(), entidad
								.getIdentificador());
				beanAPI = getAPIOrganizacionBean(organizacion, localizacion);
			}

		} catch (Exception e) {
			logger.error("Error al obtener la organización", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return beanAPI;
	}

	public int crearOrganizacion(boolean esPrimerNivel, OrganizacionBean bean,
			Entidad entidad) throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		if (esPrimerNivel) {
			return crearTipoOrganizacion(bean, entidad);
		} else {
			return crearOrganizacion(bean, entidad);
		}
	}

	private int crearOrganizacion(OrganizacionBean bean, Entidad entidad)
			throws RPAdminException {
		int devuelve;

		// Comprobar que el código no exista
		SicresOrganizacionImpl organizacionDAO = null;
		try {
			organizacionDAO = RPAdminOrganizacionManager.getOrganizacionByCode(
					safeUpperCase(bean.getUid()), entidad.getIdentificador());
		} catch (Exception e) {
			// Si hay excepción es porque no existia la oficina.
		}
		if (organizacionDAO != null) {
			logger
					.debug("Se intenta crear una organizacion cuando el código existe");
			throw new RPAdminException(
					RPAdminException.CODIGO_ORGANIZACION_SICRES_EXISTENTE);
		}

		try {
			bean.setFechaAlta(new Date());
			bean.setEnabled(true);
			devuelve = RPAdminOrganizacionManager.crearOrganizacion(
					getSicresOrganizacionImpl(bean),
					getSicresOrganizacionLocalizacionImpl(bean), getSicresOrganizacionIntercambioRegistral(bean), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al crear la organizacion", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return devuelve;
	}

	private int crearTipoOrganizacion(OrganizacionBean bean, Entidad entidad)
			throws RPAdminException {
		int devuelve;

		// Comprobar que el código no exista
		SicresTipoOrganizacionImpl tipoOrganizacionDAO = null;
		try {
			tipoOrganizacionDAO = RPAdminOrganizacionManager
					.getTipoOrganizacionByCode(safeUpperCase(bean.getUid()),
							entidad.getIdentificador());
		} catch (Exception e) {
			// Si hay excepción es porque no existia la oficina.
		}
		if (tipoOrganizacionDAO != null) {
			logger
					.debug("Se intenta crear un tipo de organizacion cuando el código existe");
			throw new RPAdminException(
					RPAdminException.CODIGO_TIPO_ORGANIZACION_SICRES_EXISTENTE);
		}

		try {
			devuelve = RPAdminOrganizacionManager.crearTipoOrganizacion(
					getSicresTipoOrganizacionImpl(bean), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al crear el tipo de organizacion", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return -1 * devuelve;
	}

	public void editarOrganizacion(OrganizacionBean bean, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			if (bean.getId() > 0) {
				RPAdminOrganizacionManager.editarOrganizacion(
						getSicresOrganizacionImpl(bean),
						getSicresOrganizacionLocalizacionImpl(bean), getSicresOrganizacionIntercambioRegistral(bean), entidad
								.getIdentificador());
			} else {
				RPAdminOrganizacionManager.editarTipoOrganizacion(
						getSicresTipoOrganizacionImpl(bean), entidad
								.getIdentificador());
			}
		} catch (Exception e) {
			logger.error("Error al crear la organizacion", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void eliminarOrganizacion(int orgId, Entidad entidad)
			throws RPAdminException {
		// Comprobar que no existen usuarios
		SicresOrganizacionesImpl organizaciones = null;

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		try {
			organizaciones = RPAdminOrganizacionManager.obtenerOrganizaciones(
					orgId, entidad.getIdentificador());
		} catch (Exception e) {
			logger
					.error("Error al comprobar si existen organizaciones hijas en la organizacion a borrar");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		if (organizaciones == null || organizaciones.count() > 0) {
			if (logger.isDebugEnabled()) {
				logger
						.error("Se ha intentado eliminar una organizaciones que tenía organizaciones hijas");
			}
			throw new RPAdminException(RPAdminException.ORGANIZACION_CON_HIJAS);
		}

		try {
			if (orgId > 0) {
				RPAdminOrganizacionManager.eliminarOrganizacion(orgId, entidad
						.getIdentificador());
			} else {
				RPAdminOrganizacionManager.eliminarTipoOrganizacion(-1 * orgId,
						entidad.getIdentificador());
			}
		} catch (Exception e) {
			logger.error("Error al eliminar la organizacion", e);
			throw new RPAdminException(
					RPAdminException.ORGANIZACION_CON_REFERENCIAS, e);
		}
		return;
	}

	public Distribuciones obtenerDistribuciones(int orgId, Entidad entidad)
			throws RPAdminException {

		Distribuciones listaAPI = new Distribuciones();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresListaDistribucionesImpl listaDAO = RPAdminDistribucionManager
					.obtenerDistribuciones(orgId, entidad.getIdentificador());
			listaAPI = getAPIDistribuciones(listaDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo distribuciones");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public void crearDistribuciones(int idOrg, int idTipo, int[] ids,
			Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminDistribucionManager.crearDistribuciones(
					getSicresListaDistribucionesImpl(idOrg, idTipo, ids),
					entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al crear distribuciones", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void eliminarDistribucion(int id, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminDistribucionManager.eliminarDistribucion(id, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar una distribucion", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public OptionsBean obtenerDepartamentosRaiz(Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return obtenerDepartamentosHijos(0, entidad);
	}

	public OptionsBean obtenerGruposRaiz(Entidad entidad)
			throws RPAdminException {
		OptionsBean listaAPI = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			Grupos listaDAO = RPAdminUserManager.obtenerGrupos(entidad
					.getIdentificador());
			listaAPI = getAPIOptionsBean(listaDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo grupos");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public OptionsBean obtenerGruposHijos(int idGrupo, Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		if (idGrupo == 0) {
			return obtenerGruposRaiz(entidad);
		} else {
			// Los grupos no tienen hijos
			return new OptionsBean();
		}
	}

	public OptionsBean obtenerDepartamentosHijos(int idDepartamento,
			Entidad entidad) throws RPAdminException {
		OptionsBean listaAPI = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			Departamentos listaDAO = RPAdminOficinaManager
					.obtenerDepartamentosHijos(idDepartamento, entidad
							.getIdentificador());
			listaAPI = getAPIOptionsBean(listaDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo departamentos hijos");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public OptionsBean obtenerUsuarios(int id, int tipo, Entidad entidad)
			throws RPAdminException {
		OptionsBean listaAPI = null;
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			Usuarios listaDAO = null;
			if (tipo == Distribucion.TIPO_DEPARTAMENTO) {
				listaDAO = RPAdminUserManager.obtenerUsuariosDepartamento(id,
						entidad.getIdentificador());
			} else {
				listaDAO = RPAdminUserManager.obtenerUsuariosGrupo(id, entidad
						.getIdentificador());
			}
			listaAPI = getAPIOptionsBean(listaDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return listaAPI;
	}

	private UsuariosRegistradores getAPIUsuariosLdap(UsuariosLdap listaDAO, boolean agregado) {
		UsuariosRegistradores listaAPI = new UsuariosRegistradores();
		if (listaDAO != null) {
			for (int i = 0; i < listaDAO.count(); i++) {
				UsuarioRegistrador beanAPI = null;
				UsuarioLdap beanDAO = listaDAO.get(i);
				beanAPI = getAPIUsuarioLdap(beanDAO, agregado);
				listaAPI.add(beanAPI);
			}
		}
		return listaAPI;
	}

	private UsuariosRegistradores getAPIUsuarios(ListaUsuariosImpl listaDAO, boolean agregado) {
		UsuariosRegistradores listaAPI = new UsuariosRegistradores();
		for (int i = 0; i < listaDAO.count(); i++) {
			UsuarioRegistrador beanAPI = null;
			ListaUsuarioImpl beanDAO = listaDAO.get(i);
			beanAPI = getAPIUsuarioListado(beanDAO, agregado);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private UsuarioRegistrador getAPIUsuarioLdap(UsuarioLdap beanDAO, boolean agregado) {
		SicresUsuarioAgregadoImpl beanAPI = new SicresUsuarioAgregadoImpl();
		beanAPI.setId(beanDAO.get_id());
		beanAPI.setNombre(beanDAO.get_ldapfullname());

		if (isNuloOVacio(beanDAO.get_surName())
				&& isNuloOVacio(beanDAO.get_firstName())
				&& isNuloOVacio(beanDAO.get_secondName())) {
			beanAPI.setNombreCompleto(beanDAO.get_ldapfullname());
		} else {
			StringBuffer sb = new StringBuffer();
			if (!isNuloOVacio(beanDAO.get_surName())) {
				sb.append(beanDAO.get_surName()).append(" ");
			}
			if (!isNuloOVacio(beanDAO.get_firstName())) {
				sb.append(beanDAO.get_firstName()).append(" ");
			}
			if (!isNuloOVacio(beanDAO.get_secondName())) {
				sb.append(beanDAO.get_secondName());
			}
			beanAPI.setNombreCompleto(sb.toString());
		}

		// beanAPI.setNombreCompleto(beanDAO.get_ldapfullname());

		beanAPI.setOficinaRegistro(null);
		int perfil = 0;
		if (beanDAO.get_Perfil(ManagerUtils.ISICRES_PROD_ID) != null) {
			perfil = beanDAO.get_Perfil(ManagerUtils.ISICRES_PROD_ID)
					.get_profile();
		}
		beanAPI.setPerfil(obtenerDescripcionPerfil(perfil));
		beanAPI.setAgregado(agregado);
		return beanAPI;
	}

	private UsuarioRegistrador getAPIUsuarioListado(ListaUsuarioImpl beanDAO, boolean agregado) {
		SicresUsuarioAgregadoImpl beanAPI = new SicresUsuarioAgregadoImpl();
		beanAPI.setId(beanDAO.getId());
		beanAPI.setNombre(beanDAO.getNombre());
		if (isNuloOVacio(beanDAO.getSurName())
				&& isNuloOVacio(beanDAO.getFirstName())
				&& isNuloOVacio(beanDAO.getSecondName())) {
			beanAPI.setNombreCompleto(beanDAO.getNombre());
		} else {
			StringBuffer sb = new StringBuffer();
			if (!isNuloOVacio(beanDAO.getSurName())) {
				sb.append(beanDAO.getSurName()).append(" ");
			}
			if (!isNuloOVacio(beanDAO.getFirstName())) {
				sb.append(beanDAO.getFirstName()).append(" ");
			}
			if (!isNuloOVacio(beanDAO.getSecondName())) {
				sb.append(beanDAO.getSecondName());
			}
			beanAPI.setNombreCompleto(sb.toString());
		}
		beanAPI.setOficinaRegistro(beanDAO.getCode());
		beanAPI.setPerfil(obtenerDescripcionPerfil(beanDAO.getType()));
		beanAPI.setAgregado(agregado);
		return beanAPI;
	}

	private Oficinas getAPIOficinas(SicresOficinasImpl listaDAO) {
		Oficinas listaAPI = new Oficinas();
		if (listaDAO != null && listaDAO.count() > 0) {
			for (int i = 0; i < listaDAO.count(); i++) {
				Oficina beanAPI = null;
				SicresOficinaImpl beanDAO = listaDAO.get(i);
				beanAPI = getAPIOficinaListado(beanDAO);
				listaAPI.add(beanAPI);
			}
		}
		return listaAPI;
	}

	private Oficina getAPIOficinaListado(SicresOficinaImpl beanDAO) {
		Oficina beanAPI = new Oficina();
		beanAPI.setId(beanDAO.getId());
		beanAPI.setNombre(beanDAO.getName());
		beanAPI.setAbreviatura(beanDAO.getAcron());
		beanAPI.setCodigo(beanDAO.getCode());
		beanAPI.setEntidadRegistral(beanDAO.getName());
		return beanAPI;
	}

	private Libros getAPILibros(IDocArchsHDRImpl listaDAO) {
		Libros listaAPI = new Libros();
		for (int i = 0; i < listaDAO.count(); i++) {
			Libro beanAPI = null;
			IDocArchHDRImpl beanDAO = listaDAO.get(i);
			beanAPI = getAPILibro(beanDAO);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private Libro getAPILibro(IDocArchHDRImpl beanDAO) {
		Libro beanAPI = new Libro();
		beanAPI.setId(beanDAO.getId());
		beanAPI.setNombre(beanDAO.getName());
		beanAPI.setTipo(beanDAO.getType());
		beanAPI.setEstado(beanDAO.getEstadoLibro());
		return beanAPI;
	}

	private LibroBean getAPILibroBean(IDocArchHDRImpl archivador,
			IVolArchListImpl lista, SicresLibroEstadoImpl libro) {
		LibroBean beanAPI = new LibroBean();
		beanAPI.setAutenticacion(libro.getImageAuth());
		beanAPI.setEstado(libro.getState());
		beanAPI.setFechaCierre(libro.getCloseDate());
		beanAPI.setId(archivador.getId());
		beanAPI.setIdLista(lista.getListId());
//		beanAPI.setNombreLista(archivador.getName());
		beanAPI.setNombre(archivador.getName());
		beanAPI.setNumeracion(libro.getNumerationType());
		beanAPI.setTipo(archivador.getType());
		beanAPI.setUsuarioCierre(libro.getCloseUser());
		return beanAPI;
	}

	private Organizaciones getAPIOrganizaciones(
			SicresTiposOrganizacionesImpl listaDAO) {
		Organizaciones listaAPI = new Organizaciones();
		for (int i = 0; i < listaDAO.count(); i++) {
			Organizacion beanAPI = null;
			SicresTipoOrganizacionImpl beanDAO = listaDAO.get(i);
			beanAPI = getAPIOrganizacion(beanDAO);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private Organizacion getAPIOrganizacion(SicresTipoOrganizacionImpl beanDAO) {
		Organizacion beanAPI = new Organizacion();
		// Como en el arbol de unidades administrativas hay tipos y unidades,
		// los tipos tendrán
		// id negativo y padre 0.
		beanAPI.setId(-1 * beanDAO.getId());
		beanAPI.setUid(beanDAO.getCode());
		beanAPI.setNombre(beanDAO.getDescription());
		beanAPI.setTipo(beanDAO.getId());
		beanAPI.setIdPadre(0);
		return beanAPI;
	}

	private Organizaciones getAPIOrganizaciones(
			SicresOrganizacionesImpl listaDAO) {
		Organizaciones listaAPI = new Organizaciones();
		for (int i = 0; i < listaDAO.count(); i++) {
			Organizacion beanAPI = null;
			SicresOrganizacionImpl beanDAO = listaDAO.get(i);
			beanAPI = getAPIOrganizacion(beanDAO);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private Organizacion getAPIOrganizacion(SicresOrganizacionImpl beanDAO) {
		Organizacion beanAPI = new Organizacion();
		beanAPI.setAbreviatura(beanDAO.getAcron());
		beanAPI.setFechaAlta(beanDAO.getCreationDate());
		beanAPI.setFechaBaja(beanDAO.getDisableDate());
		beanAPI.setId(beanDAO.getId());
		// Si no había padre es porque realmente su padre es un tipo de unidad
		// administrativa, por lo que le pondremos su tipo en negativo
		if (beanDAO.getIdFather() > 0) {
			beanAPI.setIdPadre(beanDAO.getIdFather());
		} else {
			beanAPI.setIdPadre(-1 * beanDAO.getType());
		}
		beanAPI.setNombre(beanDAO.getName());
		beanAPI.setUid(beanDAO.getCode());
		beanAPI.setTipo(beanDAO.getType());
		return beanAPI;
	}

	private PermisosSicres getAPIPermisos(SicresLibrosOficinasImpl oficinasDAO,
			IUserObjsPermsImpl permisosDAO) {
		PermisosSicres listaAPI = new PermisosSicres();
		for (int i = 0; i < oficinasDAO.count(); i++) {
			PermisoSicres beanAPI = null;
			SicresLibroOficinaImpl oficinaDAO = oficinasDAO.get(i);
			beanAPI = getAPIPermiso(oficinaDAO, permisosDAO);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private PermisoSicres getAPIPermiso(SicresLibroOficinaImpl oficinaDAO,
			IUserObjsPermsImpl permisosDAO) {
		PermisoSicres beanAPI = new PermisoSicres();
		beanAPI.setId(oficinaDAO.getIdOfic());
		beanAPI.setNombre(oficinaDAO.getNameOfic());
		beanAPI.setTipo(PermisoSicres.TIPO_OFICINA);
		beanAPI.setIdBook(oficinaDAO.getIdBook());
		beanAPI.setNumeracion(oficinaDAO.getNumeration());
		for (int i = 0; i < permisosDAO.count(); i++) {
			IUserObjPermImpl permisoDAO = permisosDAO.get(i);
			if (oficinaDAO.getDeptId() == permisoDAO.getDstId()
					&& permisoDAO.getDstType() == PermisoSicres.TIPO_OFICINA) {
				if (permisoDAO.getAPerm() == IUserObjPermImpl.PERMISO_SICRES_CONSULTA) {
					beanAPI.setConsultar(true);
				} else if (permisoDAO.getAPerm() == IUserObjPermImpl.PERMISO_SICRES_ACTUALIZACION) {
					beanAPI.setModificar(true);
				} else if (permisoDAO.getAPerm() == IUserObjPermImpl.PERMISO_SICRES_CREACION) {
					beanAPI.setCrear(true);
				}
			}
		}
		return beanAPI;
	}

	private PermisosSicres getAPIPermisos(int bookId,
			ListaUsuariosImpl usuariosDAO, IUserObjsPermsImpl permisosDAO) {
		PermisosSicres listaAPI = new PermisosSicres();
		for (int i = 0; i < usuariosDAO.count(); i++) {
			PermisoSicres beanAPI = null;
			ListaUsuarioImpl usuarioDAO = usuariosDAO.get(i);
			beanAPI = getAPIPermiso(bookId, usuarioDAO, permisosDAO);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private PermisoSicres getAPIPermiso(int bookId,
			ListaUsuarioImpl usuarioDAO, IUserObjsPermsImpl permisosDAO) {
		PermisoSicres beanAPI = new PermisoSicres();
		beanAPI.setId(usuarioDAO.getId());
		beanAPI.setNombre(usuarioDAO.getNombre());
		beanAPI.setTipo(PermisoSicres.TIPO_USUARIO);
		beanAPI.setIdBook(bookId);
		for (int i = 0; i < permisosDAO.count(); i++) {
			IUserObjPermImpl permisoDAO = permisosDAO.get(i);
			if (usuarioDAO.getId() == permisoDAO.getDstId()
					&& permisoDAO.getDstType() == PermisoSicres.TIPO_USUARIO) {
				if (permisoDAO.getAPerm() == IUserObjPermImpl.PERMISO_SICRES_CONSULTA) {
					beanAPI.setConsultar(true);
				} else if (permisoDAO.getAPerm() == IUserObjPermImpl.PERMISO_SICRES_ACTUALIZACION) {
					beanAPI.setModificar(true);
				} else if (permisoDAO.getAPerm() == IUserObjPermImpl.PERMISO_SICRES_CREACION) {
					beanAPI.setCrear(true);
				}
			}
		}
		return beanAPI;
	}

	private UsuarioRegistradorBean getAPIUsuarioBean(Usuario userDAO,
			SicresUserPermisosImpl permisos,
			SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion) {
		UsuarioRegistradorBean beanAPI = new UsuarioRegistradorBean();

		if (userDAO != null) {
			beanAPI.setId(userDAO.get_id());
			beanAPI.setNombre(userDAO.get_name());
			beanAPI.setIdPerfil(userDAO.get_Perfil(ManagerUtils.ISICRES_PROD_ID).get_profile());
		}

		if (permisos != null) {
			CalculadorPermisos.descifraPermisos(beanAPI, permisos);
		}

		if (identificacion != null) {
			beanAPI.setNombreIdentificacion(identificacion.getSurname());
			beanAPI.setPrimerApellido(identificacion.getFirstName());
			beanAPI.setSegundoApellido(identificacion.getSecondName());
		}

		if (localizacion != null) {
			beanAPI.setCiudad(localizacion.getCity());
			beanAPI.setCodigoPostal(localizacion.getZip());
			beanAPI.setDireccion(localizacion.getAddress());
			beanAPI.setEmail(localizacion.getEmail());
			beanAPI.setFax(localizacion.getFax());
			beanAPI.setProvincia(localizacion.getCountry());
			beanAPI.setTelefono(localizacion.getTelephone());
		}

		return beanAPI;
	}

	private UsuarioRegistradorBean getAPIUsuarioBeanLdap(UsuarioLdap userLdapDAO,
			SicresUserPermisosImpl permisos,
			SicresUserIdentificacionImpl identificacion,
			SicresUserLocalizacionImpl localizacion) {
		UsuarioRegistradorBean beanAPI = new UsuarioRegistradorBean();

		if (userLdapDAO != null) {
			beanAPI.setId(userLdapDAO.get_id());
			beanAPI.setNombre(userLdapDAO.get_ldapfullname());
			if (userLdapDAO.get_Perfil(ManagerUtils.ISICRES_PROD_ID) != null) {
				beanAPI.setIdPerfil(userLdapDAO.get_Perfil(
						ManagerUtils.ISICRES_PROD_ID).get_profile());
			}
			beanAPI.setLdapGuid(userLdapDAO.get_ldapguid());
			beanAPI.setLdapFullName(userLdapDAO.get_ldapfullname());
		}

		if (permisos != null) {
			CalculadorPermisos.descifraPermisos(beanAPI, permisos);
		}

		if (identificacion != null) {
			beanAPI.setNombreIdentificacion(identificacion.getSurname());
			beanAPI.setPrimerApellido(identificacion.getFirstName());
			beanAPI.setSegundoApellido(identificacion.getSecondName());
		}

		if (localizacion != null) {
			beanAPI.setCiudad(localizacion.getCity());
			beanAPI.setCodigoPostal(localizacion.getZip());
			beanAPI.setDireccion(localizacion.getAddress());
			beanAPI.setEmail(localizacion.getEmail());
			beanAPI.setFax(localizacion.getFax());
			beanAPI.setProvincia(localizacion.getCountry());
			beanAPI.setTelefono(localizacion.getTelephone());
		}

		return beanAPI;
	}

	private OrganizacionBean getAPIOrganizacionBean(
			SicresOrganizacionImpl organizacion,
			SicresOrganizacionLocalizacionImpl localizacion) {
		OrganizacionBean beanAPI = new OrganizacionBean();

		if (organizacion != null) {
			beanAPI.setId(organizacion.getId());
			beanAPI.setNombre(organizacion.getName());
			beanAPI.setAbreviatura(organizacion.getAcron());
			beanAPI.setCif(organizacion.getCif());
			beanAPI.setFechaAlta(organizacion.getCreationDate());
			beanAPI.setFechaBaja(organizacion.getDisableDate());
			beanAPI.setTipo(organizacion.getType());
			beanAPI.setUid(organizacion.getCode());
			if (organizacion.getIdFather() == DbDataType.NULL_LONG_INTEGER) {
				beanAPI.setIdPadre(-1 * organizacion.getType());
			} else {
				beanAPI.setIdPadre(organizacion.getIdFather());
			}

		}

		if (localizacion != null) {
			beanAPI.setCiudad(localizacion.getCity());
			beanAPI.setCodigoPostal(localizacion.getZip());
			beanAPI.setDireccion(localizacion.getAddress());
			beanAPI.setUri(localizacion.getEmail());
			beanAPI.setFax(localizacion.getFax());
			beanAPI.setProvincia(localizacion.getCountry());
			beanAPI.setTelefono(localizacion.getTelephone());
		}

		return beanAPI;
	}

	private OrganizacionBean getAPIOrganizacionBean(
			SicresTipoOrganizacionImpl tipoOrganizacion) {
		OrganizacionBean beanAPI = new OrganizacionBean();

		if (tipoOrganizacion != null) {
			beanAPI.setId(-1 * tipoOrganizacion.getId());
			beanAPI.setNombre(tipoOrganizacion.getDescription());
			beanAPI.setUid(tipoOrganizacion.getCode());
		}

		return beanAPI;
	}

	private OficinaBean getAPIOficinaBean(SicresOficinaImpl oficinaDAO,
			SicresOficinaLocalizacionImpl localizacion) {
		OficinaBean beanAPI = new OficinaBean();

		if (oficinaDAO != null) {
			beanAPI.setId(oficinaDAO.getId());
			beanAPI.setNombre(oficinaDAO.getName());
			beanAPI.setAbreviatura(oficinaDAO.getAcron());
			beanAPI.setCodigo(oficinaDAO.getCode());
			beanAPI.setFecha(oficinaDAO.getCreationDate());
			beanAPI.setFechaBaja(oficinaDAO.getDisableDate());
			beanAPI.setIdEntidadRegistral(oficinaDAO.getIdOrgs());
			beanAPI.setIdTipoOficina(oficinaDAO.getType());
			beanAPI.setSello(oficinaDAO.getStamp());
			beanAPI.setDeptId(oficinaDAO.getDeptId());
		}

		if (localizacion != null) {
			beanAPI.setCiudad(localizacion.getCity());
			beanAPI.setCodigoPostal(localizacion.getZip());
			beanAPI.setDireccion(localizacion.getAddress());
			beanAPI.setUri(localizacion.getEmail());
			beanAPI.setFax(localizacion.getFax());
			beanAPI.setProvincia(localizacion.getCountry());
			beanAPI.setTelefono(localizacion.getTelephone());
			beanAPI.setRepresentante(localizacion.getSigner());
		}

		return beanAPI;
	}

	private SicresLibroEstadoImpl getSicresLibroEstadoImpl(LibroBean libroAPI) {
		SicresLibroEstadoImpl libroDAO = new SicresLibroEstadoImpl();

		libroDAO.setCloseDate(libroAPI.getFechaCierre());
		libroDAO.setCloseUser(libroAPI.getUsuarioCierre());
		libroDAO.setIdArchReg(libroAPI.getId());
		libroDAO.setImageAuth(libroAPI.getAutenticacion());
		libroDAO.setNumerationType(libroAPI.getNumeracion());
		libroDAO.setState(libroAPI.getEstado());

		return libroDAO;
	}

	private IDocArchHDRImpl getIDocArchHDRImpl(LibroBean archivadorAPI) {
		IDocArchHDRImpl archivadorDAO = new IDocArchHDRImpl();

		archivadorDAO.setId(archivadorAPI.getId());
		archivadorDAO.setName(archivadorAPI.getNombre());
		archivadorDAO.setType(archivadorAPI.getTipo());
		return archivadorDAO;
	}

	private Distribuciones getAPIDistribuciones(
			SicresListaDistribucionesImpl listaDAO) {
		Distribuciones listaAPI = new Distribuciones();
		for (int i = 0; i < listaDAO.count(); i++) {
			Distribucion beanAPI = null;
			SicresListaDistribucionImpl beanDAO = listaDAO.get(i);
			beanAPI = getAPIDistribucion(beanDAO);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private Distribucion getAPIDistribucion(SicresListaDistribucionImpl beanDAO) {
		Distribucion beanAPI = new Distribucion();
		beanAPI.setId(beanDAO.getId());
		beanAPI.setIdDestino(beanDAO.getIdDest());
		beanAPI.setIdOrganizacion(beanDAO.getIdOrgs());
		beanAPI.setNombre(beanDAO.getName());
		beanAPI.setTipoDestino(beanDAO.getTypeDest());
		return beanAPI;
	}

	private OptionsBean getAPIOptionsBean(SicresOficinasImpl oficinasDAO) {
		OptionsBean optionsAPI = new OptionsBean();
		for (int i = 0; i < oficinasDAO.count(); i++) {
			OptionBean optionAPI = null;
			SicresOficinaImpl oficinaDAO = oficinasDAO.get(i);
			optionAPI = getAPIOptionBean(oficinaDAO);
			optionsAPI.add(optionAPI);
		}
		return optionsAPI;
	}

	private Oficinas getOficinasFromDAO(SicresOficinasImpl oficinasDAO) {
		Oficinas oficinas = new Oficinas();
		for (int i = 0; i < oficinasDAO.count(); i++) {
			Oficina oficina = new Oficina();
			SicresOficinaImpl oficinaDAO = oficinasDAO.get(i);

			oficina.setId(oficinaDAO.getId());
			oficina.setCodigo(oficinaDAO.getCode());
			oficina.setNombre(oficinaDAO.getName());
			oficina.setAbreviatura(oficinaDAO.getAcron());

			oficinas.add(oficina);
		}
		return oficinas;
	}

	private Oficinas getOficinasLdapDAO(SicresOficinasImpl oficinasDAO) {
		Oficinas oficinas = new Oficinas();
		for (int i = 0; i < oficinasDAO.count(); i++) {
			Oficina oficina = new Oficina();
			SicresOficinaImpl oficinaDAO = oficinasDAO.get(i);

			oficina.setId(oficinaDAO.getId());
			oficina.setCodigo(oficinaDAO.getCode());
			oficina.setNombre(oficinaDAO.getName());
			oficina.setAbreviatura(oficinaDAO.getAcron());

			oficinas.add(oficina);
		}
		return oficinas;
	}

	private OptionBean getAPIOptionBean(SicresOficinaImpl oficinaDAO) {
		OptionBean optionAPI = new OptionBean();
		optionAPI.setCodigo(Integer.toString(oficinaDAO.getDeptId()));
		StringBuffer sb = new StringBuffer();
		sb.append(oficinaDAO.getCode()).append(" - ").append(
				oficinaDAO.getName());
		optionAPI.setDescripcion(sb.toString());
		return optionAPI;
	}

	private OptionsBean getAPIOptionsBean(Listas listasDAO) {
		OptionsBean optionsAPI = new OptionsBean();
		for (int i = 0; i < listasDAO.count(); i++) {
			OptionBean optionAPI = null;
			Lista listaDAO = listasDAO.get(i);
			optionAPI = getAPIOptionBean(listaDAO);
			optionsAPI.add(optionAPI);
		}
		return optionsAPI;
	}

	private OptionBean getAPIOptionBean(Lista listaDAO) {
		OptionBean optionAPI = new OptionBean();
		optionAPI.setCodigo(Integer.toString(listaDAO.getId()));
		optionAPI.setDescripcion(listaDAO.getName());
		return optionAPI;
	}

	private OptionsBean getAPIOptionsBean(SicresOrganizacionesImpl oficinasDAO) {
		OptionsBean optionsAPI = new OptionsBean();
		for (int i = 0; i < oficinasDAO.count(); i++) {
			OptionBean optionAPI = null;
			SicresOrganizacionImpl organizacionDAO = oficinasDAO.get(i);
			optionAPI = getAPIOptionBean(organizacionDAO);
			optionsAPI.add(optionAPI);
		}
		return optionsAPI;
	}

	private OptionBean getAPIOptionBean(SicresOrganizacionImpl organizacionDAO) {
		OptionBean optionAPI = new OptionBean();
		optionAPI.setCodigo(Integer.toString(organizacionDAO.getId()));
		optionAPI.setDescripcion(organizacionDAO.getName());
		return optionAPI;
	}

	private OptionsBean getAPIOptionsBean(SicresTiposOficinaImpl tiposDAO) {
		OptionsBean optionsAPI = new OptionsBean();
		for (int i = 0; i < tiposDAO.count(); i++) {
			OptionBean optionAPI = null;
			SicresTipoOficinaImpl oficinaDAO = tiposDAO.get(i);
			optionAPI = getAPIOptionBean(oficinaDAO);
			optionsAPI.add(optionAPI);
		}
		return optionsAPI;
	}

	private OptionBean getAPIOptionBean(SicresTipoOficinaImpl tipoDAO) {
		OptionBean optionAPI = new OptionBean();
		optionAPI.setCodigo(Integer.toString(tipoDAO.getId()));
		optionAPI.setDescripcion(tipoDAO.getDescription());
		return optionAPI;
	}

	private OptionsBean getAPIOptionsBean(Departamentos departamentosDAO) {
		OptionsBean optionsAPI = new OptionsBean();
		for (int i = 0; i < departamentosDAO.getDepartamentosLista().count(); i++) {
			OptionBean optionAPI = null;
			Departamento oficinaDAO = departamentosDAO.getDepartamentosLista()
					.get(i);
			optionAPI = getAPIOptionBean(oficinaDAO);
			optionsAPI.add(optionAPI);
		}
		return optionsAPI;
	}

	private OptionBean getAPIOptionBean(Departamento departamentoDAO) {
		OptionBean optionAPI = new OptionBean();
		optionAPI.setCodigo(Integer.toString(departamentoDAO.get_id()));
		optionAPI.setDescripcion(departamentoDAO.get_name());
		return optionAPI;
	}

	private OptionsBean getAPIOptionsBean(Usuarios listaDAO) {
		OptionsBean optionsAPI = new OptionsBean();
		for (int i = 0; i < listaDAO.count(); i++) {
			OptionBean optionAPI = null;
			Usuario beanDAO = (Usuario) listaDAO.get(i);
			optionAPI = getAPIOptionBean(beanDAO);
			optionsAPI.add(optionAPI);
		}
		return optionsAPI;
	}

	private OptionBean getAPIOptionBean(Usuario beanDAO) {
		OptionBean optionAPI = new OptionBean();
		optionAPI.setCodigo(Integer.toString(beanDAO.get_id()));
		optionAPI.setDescripcion(beanDAO.get_name());
		return optionAPI;
	}

	private OptionsBean getAPIOptionsBean(Grupos listaDAO) {
		OptionsBean optionsAPI = new OptionsBean();
		for (int i = 0; i < listaDAO.getGruposLista().count(); i++) {
			OptionBean optionAPI = null;
			Grupo beanDAO = listaDAO.getGruposLista().get(i);
			optionAPI = getAPIOptionBean(beanDAO);
			optionsAPI.add(optionAPI);
		}
		return optionsAPI;
	}

	private OptionBean getAPIOptionBean(Grupo beanDAO) {
		OptionBean optionAPI = new OptionBean();
		optionAPI.setCodigo(Integer.toString(beanDAO.get_id()));
		optionAPI.setDescripcion(beanDAO.get_name());
		return optionAPI;
	}

	private Contadores getAPIContadores(int anyo, int tipo,
			SicresOficinasImpl oficinasDAO,
			SicresContadoresOficinasImpl contadoresDAO) {
		Contadores listaAPI = new Contadores();
		for (int i = 0; i < oficinasDAO.count(); i++) {
			Contador beanAPI = new Contador();
			SicresOficinaImpl oficinaDAO = oficinasDAO.get(i);
			beanAPI.setId(oficinaDAO.getId());
			beanAPI.setNombre(oficinaDAO.getName());
			beanAPI.setContador(contadoresDAO.contador(oficinaDAO.getId()));
			beanAPI.setAnyo(anyo);
			listaAPI.add(beanAPI);
		}
		return listaAPI;
	}

	private SicresUserPermisosImpl getSicresUserPermisosImpl(
			UsuarioRegistradorBean beanAPI) {
		SicresUserPermisosImpl beanDAO = new SicresUserPermisosImpl();
		beanDAO.setIdUsr(beanAPI.getId());
		beanDAO.setTmstamp(new Date());
		CalculadorPermisos.cifraPermisos(beanDAO, beanAPI);
		if (beanDAO.getPerms() == 0)
			return null;
		else
			return beanDAO;
	}

	private IUserObjsPermsImpl getToDeleteIUserObjsPermsImpl(
			PermisosSicres listaAPI) {
		IUserObjsPermsImpl listaDAO = new IUserObjsPermsImpl();
		for (int i = 0; i < listaAPI.count(); i++) {
			PermisoSicres beanAPI = listaAPI.getPermisos(i);
			IUserObjPermImpl beanDAO = new IUserObjPermImpl();
			beanDAO.setAPerm(0);
			beanDAO.setDstId(beanAPI.getId());
			beanDAO.setDstType(beanAPI.getTipo());
			beanDAO.setObjId(beanAPI.getIdBook());
			listaDAO.add(beanDAO);
		}

		return listaDAO;
	}

	private IUserObjsPermsImpl getIUserObjsPermsImpl(PermisosSicres listaAPI) {
		IUserObjsPermsImpl listaDAO = new IUserObjsPermsImpl();

		for (int i = 0; i < listaAPI.count(); i++) {
			PermisoSicres beanAPI = listaAPI.getPermisos(i);
			IUserObjsPermsImpl subListaDAO = getIUserObjsPermsImpl(beanAPI);
			listaDAO.getLista().addAll(subListaDAO.getLista());
		}

		return listaDAO;
	}

	private IUserObjsPermsImpl getIUserObjsPermsImpl(PermisoSicres beanAPI) {
		IUserObjsPermsImpl listaDAO = new IUserObjsPermsImpl();

		if (beanAPI.getConsultar()) {
			listaDAO.add(getIUserObjPermImpl(beanAPI.getTipo(),
					beanAPI.getId(), beanAPI.getIdBook(),
					IUserObjPermImpl.PERMISO_SICRES_CONSULTA));
			listaDAO.add(getIUserObjPermImpl(beanAPI.getTipo(),
					beanAPI.getId(), beanAPI.getIdBook(),
					IUserObjPermImpl.PERMISO_SICRES_CONSULTA_1));
			listaDAO.add(getIUserObjPermImpl(beanAPI.getTipo(),
					beanAPI.getId(), beanAPI.getIdBook(),
					IUserObjPermImpl.PERMISO_SICRES_CONSULTA_2));
		}

		if (beanAPI.getModificar()) {
			listaDAO.add(getIUserObjPermImpl(beanAPI.getTipo(),
					beanAPI.getId(), beanAPI.getIdBook(),
					IUserObjPermImpl.PERMISO_SICRES_ACTUALIZACION));
			listaDAO.add(getIUserObjPermImpl(beanAPI.getTipo(),
					beanAPI.getId(), beanAPI.getIdBook(),
					IUserObjPermImpl.PERMISO_SICRES_ACTUALIZACION_1));
		}

		if (beanAPI.getCrear()) {
			listaDAO.add(getIUserObjPermImpl(beanAPI.getTipo(),
					beanAPI.getId(), beanAPI.getIdBook(),
					IUserObjPermImpl.PERMISO_SICRES_CREACION));
			listaDAO.add(getIUserObjPermImpl(beanAPI.getTipo(),
					beanAPI.getId(), beanAPI.getIdBook(),
					IUserObjPermImpl.PERMISO_SICRES_CREACION_1));
		}
		return listaDAO;
	}

	private IUserObjPermImpl getIUserObjPermImpl(int tipo, int id, int bookId,
			int permiso) {
		IUserObjPermImpl beanDAO = new IUserObjPermImpl();
		beanDAO.setDstType(tipo);
		beanDAO.setDstId(id);
		beanDAO.setObjId(bookId);
		beanDAO.setAPerm(permiso);
		return beanDAO;
	}

	private SicresUserIdentificacionImpl getSicresUserIdentificacionImpl(
			UsuarioRegistradorBean beanAPI) {
		SicresUserIdentificacionImpl beanDAO = new SicresUserIdentificacionImpl();
		/*if (isNuloOVacio(beanAPI.getNombreIdentificacion())
				|| isNuloOVacio(beanAPI.getPrimerApellido())) {
			return null;
		}*/
		beanDAO.setSurname(safeUpperCase(beanAPI.getNombreIdentificacion()));
		beanDAO.setFirstName(safeUpperCase(beanAPI.getPrimerApellido()));
		beanDAO.setSecondName(safeUpperCase(beanAPI.getSegundoApellido()));
		beanDAO.setTmstamp(new Date());
		beanDAO.setUserId(beanAPI.getId());
		return beanDAO;
	}

	private SicresUserLocalizacionImpl getSicresUserLocalizacionImpl(
			UsuarioRegistradorBean beanAPI) {
		SicresUserLocalizacionImpl beanDAO = new SicresUserLocalizacionImpl();
		/*if (isNuloOVacio(beanAPI.getDireccion())
				|| isNuloOVacio(beanAPI.getCiudad())) {
			return null;
		}*/
		beanDAO.setAddress(safeUpperCase(beanAPI.getDireccion()));
		beanDAO.setCity(safeUpperCase(beanAPI.getCiudad()));
		beanDAO.setCountry(safeUpperCase(beanAPI.getProvincia()));
		beanDAO.setEmail(beanAPI.getEmail());
		beanDAO.setFax(beanAPI.getFax());
		beanDAO.setTelephone(beanAPI.getTelefono());
		beanDAO.setTmstamp(new Date());
		beanDAO.setUserId(beanAPI.getId());
		beanDAO.setZip(beanAPI.getCodigoPostal());
		return beanDAO;
	}

	private SicresOficinaImpl getSicresOficinaImpl(OficinaBean beanAPI) {
		SicresOficinaImpl beanDAO = new SicresOficinaImpl();

		beanDAO.setId(beanAPI.getId());
		beanDAO.setAcron(safeUpperCase(beanAPI.getAbreviatura()));
		beanDAO.setCode(safeUpperCase(beanAPI.getCodigo()));
		beanDAO.setCreationDate(beanAPI.getFecha());
		beanDAO.setDisableDate(beanAPI.getFechaBaja());
		beanDAO.setDeptId(beanAPI.getDeptId());
		beanDAO.setIdOrgs(beanAPI.getIdEntidadRegistral());
		beanDAO.setName(safeUpperCase(beanAPI.getNombre()));
		beanDAO.setStamp(beanAPI.getSello());
		beanDAO.setType(beanAPI.getIdTipoOficina());
		beanDAO.setTypeDept(beanAPI.getTipoDepartamento());
		beanDAO.setLdapGuid(beanAPI.getLdapGuid());
		beanDAO.setLdapDn(beanAPI.getLdapDn());

		return beanDAO;
	}

	private SicresOficinaLocalizacionImpl getSicresOficinaLocalizacionImpl(
			OficinaBean beanAPI) {
		SicresOficinaLocalizacionImpl beanDAO = new SicresOficinaLocalizacionImpl();
		/*if (isNuloOVacio(beanAPI.getDireccion())
				|| isNuloOVacio(beanAPI.getCiudad())) {
			return null;
		}*/
		beanDAO.setAddress(safeUpperCase(beanAPI.getDireccion()));
		beanDAO.setCity(safeUpperCase(beanAPI.getCiudad()));
		beanDAO.setCountry(safeUpperCase(beanAPI.getProvincia()));
		beanDAO.setEmail(beanAPI.getUri());
		beanDAO.setFax(beanAPI.getFax());
		beanDAO.setSigner(beanAPI.getRepresentante());
		beanDAO.setTelephone(beanAPI.getTelefono());
		beanDAO.setZip(beanAPI.getCodigoPostal());
		return beanDAO;
	}

	private EntidadRegistralVO getEntidadRegistralOficina(OficinaBean beanAPI){
		EntidadRegistralVO beanDAO = null;

		// comprobamos que los datos de la entidad Registral esten completos o
		// tenga un identificador válido
		if ((beanAPI.getIdEntidadRegistral() != 0)
			|| (StringUtils.isNotBlank(beanAPI.getCodEntidadReg())
			&& (StringUtils.isNotBlank(beanAPI.getNameEntidadReg())))) {
			beanDAO = new EntidadRegistralVO();
			beanDAO.setIdOfic(beanAPI.getId());
			beanDAO.setCode(beanAPI.getCodEntidadReg());
			beanDAO.setId(beanAPI.getIdEntidadRegistral());
			beanDAO.setName(beanAPI.getNameEntidadReg());
		}
		//retornamos el objeto
		return beanDAO;
	}

	private SicresOrganizacionLocalizacionImpl getSicresOrganizacionLocalizacionImpl(
			OrganizacionBean beanAPI) {
		SicresOrganizacionLocalizacionImpl beanDAO = new SicresOrganizacionLocalizacionImpl();
		if (isNuloOVacio(beanAPI.getDireccion())
				|| isNuloOVacio(beanAPI.getCiudad())) {
			return null;
		}
		beanDAO.setAddress(safeUpperCase(beanAPI.getDireccion()));
		beanDAO.setCity(safeUpperCase(beanAPI.getCiudad()));
		beanDAO.setCountry(safeUpperCase(beanAPI.getProvincia()));
		beanDAO.setEmail(beanAPI.getUri());
		beanDAO.setFax(beanAPI.getFax());
		beanDAO.setIdOrgs(beanAPI.getId());
		beanDAO.setTelephone(beanAPI.getTelefono());
		beanDAO.setZip(beanAPI.getCodigoPostal());
		return beanDAO;
	}

	private UnidadRegistralVO getSicresOrganizacionIntercambioRegistral(OrganizacionBean beanAPI){
		UnidadRegistralVO result = new UnidadRegistralVO();

		result.setId(beanAPI.getIdUnidadTramit());
		if(!StringUtils.isEmpty(beanAPI.getCodEntidadReg())){
			result.setCode_entity(safeUpperCase(beanAPI.getCodEntidadReg()));
		}

		if(!StringUtils.isEmpty(beanAPI.getNameEntidadReg())){
			result.setName_entity(safeUpperCase(beanAPI.getNameEntidadReg()));
		}

		if(!StringUtils.isEmpty(beanAPI.getCodeUnidadTramit())){
			result.setCode_tramunit(safeUpperCase(beanAPI.getCodeUnidadTramit()));
		}

		if(!StringUtils.isEmpty(beanAPI.getNameUnidadTramit())){
			result.setName_tramunit(safeUpperCase(beanAPI.getNameUnidadTramit()));
		}

		result.setId_orgs(beanAPI.getId());

		return result;
	}

	private SicresOrganizacionImpl getSicresOrganizacionImpl(
			OrganizacionBean beanAPI) {
		SicresOrganizacionImpl beanDAO = new SicresOrganizacionImpl();

		beanDAO.setAcron(safeUpperCase(beanAPI.getAbreviatura()));
		beanDAO.setCode(safeUpperCase(beanAPI.getUid()));
		beanDAO.setCreationDate(beanAPI.getFechaAlta());
		beanDAO.setCif(safeUpperCase(beanAPI.getCif()));
		beanDAO.setDisableDate(beanAPI.getFechaBaja());
		if(beanAPI.isEnabled())
			beanDAO.setEnabled(1);
		else
			beanDAO.setEnabled(0);
		beanDAO.setId(beanAPI.getId());
		beanDAO.setName(safeUpperCase(beanAPI.getNombre()));
		beanDAO.setType(beanAPI.getTipo());

		if (beanAPI.getIdPadre() > 0) {
			beanDAO.setIdFather(beanAPI.getIdPadre());
		} else if (beanAPI.getIdPadre() < 0) {
			beanDAO.setIdFather(DbDataType.NULL_LONG_INTEGER);
		}

		return beanDAO;
	}

	private SicresTipoOrganizacionImpl getSicresTipoOrganizacionImpl(
			OrganizacionBean beanAPI) {
		SicresTipoOrganizacionImpl beanDAO = new SicresTipoOrganizacionImpl();

		beanDAO.setCode(safeUpperCase(beanAPI.getUid()));
		beanDAO.setId(-1 * beanAPI.getId());
		beanDAO.setDescription(safeUpperCase(beanAPI.getNombre()));

		return beanDAO;
	}

	private SicresLibrosOficinasImpl getSicresLibrosOficinasImpl(int bookId,
			int[] destIds, Entidad entidad) throws Exception {
		return getSicresLibrosOficinasImpl(bookId, destIds, SicresLibroOficinaImpl.NUMERACION_OFICINA, entidad);
	}

	private SicresLibrosOficinasImpl getSicresLibrosOficinasImpl(int bookId,
			int[] destIds, int numeration, Entidad entidad) throws Exception {
		SicresLibrosOficinasImpl listaDAO = new SicresLibrosOficinasImpl();

		for (int i = 0; i < destIds.length; i++) {
			SicresLibroOficinaImpl beanDAO = new SicresLibroOficinaImpl();
			OficinaBean oficina = obtenerOficina(destIds[i], entidad);

			beanDAO.setIdBook(bookId);
			beanDAO.setIdOfic(oficina.getId());
			beanDAO.setDeptId(oficina.getDeptId());
			beanDAO.setNumeration(numeration);
			listaDAO.add(beanDAO);
		}

		return listaDAO;
	}

	private SicresListaDistribucionesImpl getSicresListaDistribucionesImpl(
			int idOrg, int type, int[] destIds) {
		SicresListaDistribucionesImpl listaDAO = new SicresListaDistribucionesImpl();

		for (int i = 0; i < destIds.length; i++) {
			SicresListaDistribucionImpl beanDAO = new SicresListaDistribucionImpl();
			beanDAO.setIdDest(destIds[i]);
			beanDAO.setTypeDest(type);
			beanDAO.setIdOrgs(idOrg);
			listaDAO.add(beanDAO);
		}

		return listaDAO;
	}

	private SicresContadoresOficinasImpl getSicresContadoresOficinasImpl(
			int tipo, Contadores listaAPI) {
		SicresContadoresOficinasImpl listaDAO = new SicresContadoresOficinasImpl();

		for (int i = 0; i < listaAPI.count(); i++) {
			Contador beanAPI = listaAPI.get(i);
			SicresContadorOficinaImpl beanDAO = getSicresContadorOficinaImpl(
					tipo, beanAPI);
			listaDAO.add(beanDAO);
		}

		return listaDAO;
	}

	private SicresContadorOficinaImpl getSicresContadorOficinaImpl(int tipo,
			Contador beanAPI) {
		SicresContadorOficinaImpl beanDAO = new SicresContadorOficinaImpl();

		beanDAO.setIdArch(tipo);
		beanDAO.setAn(beanAPI.getAnyo());
		beanDAO.setNumReg(beanAPI.getContador());
		beanDAO.setOficina(beanAPI.getId());

		return beanDAO;
	}

	private FiltrosImpl getFiltrosImpl(Filtros listaAPI) {
		FiltrosImpl listaDAO = new FiltrosImpl();

		for (int i = 0; i < listaAPI.count(); i++) {
			Filtro beanAPI = listaAPI.get(i);
			FiltroImpl beanDAO = getFiltroImpl(beanAPI);
			listaDAO.add(beanDAO);
		}

		return listaDAO;
	}

	private FiltroImpl getFiltroImpl(Filtro beanAPI) {
		FiltroImpl beanDAO = new FiltroImpl();

		beanDAO.setCampo(Integer.parseInt(beanAPI.getCampo()));
		beanDAO.setNexo(beanAPI.getNexo());
		beanDAO.setOperador(Integer.parseInt(beanAPI.getOperador()));
		beanDAO.setValor(beanAPI.getValor());

		return beanDAO;
	}

	private Filtros getAPIFiltros(FiltrosImpl listaDAO) {
		Filtros listaAPI = new Filtros();

		for (int i = 0; i < listaDAO.count(); i++) {
			FiltroImpl beanDAO = listaDAO.get(i);
			Filtro beanAPI = getAPIFiltro(beanDAO);
			listaAPI.add(beanAPI);
		}

		return listaAPI;
	}

	private Filtro getAPIFiltro(FiltroImpl beanDAO) {
		Filtro beanAPI = new Filtro();

		beanAPI.setCampo(new Integer(beanDAO.getCampo()).toString());
		beanAPI.setNexo(beanDAO.getNexo());
		beanAPI.setOperador(new Integer(beanDAO.getOperador()).toString());
		beanAPI.setValor(beanDAO.getValor());

		return beanAPI;
	}

	private SicresContadorCentralImpl getSicresContadorCentralImpl(int anyo,
			int tipo, int contador) {
		SicresContadorCentralImpl beanDAO = new SicresContadorCentralImpl();

		beanDAO.setAn(anyo);
		beanDAO.setIdArch(tipo);
		beanDAO.setNumReg(contador);

		return beanDAO;
	}

	private String obtenerDescripcionPerfil(int perfil) {
		String sPerfil = new Integer(perfil).toString();
		if (perfiles.get(sPerfil) != null) {
			sPerfil = perfiles.get(sPerfil).toString();
		}
		return sPerfil;
	}

	public static boolean isNuloOVacio(Object cadena) {
		if ((cadena == null) || ("".equals(cadena)) || ("null".equals(cadena))) {
			return true;
		}
		return false;
	}

	public static String safeUpperCase(String cadena) {
		if (cadena != null) {
			return cadena.toUpperCase();
		} else {
			return null;
		}
	}

	public void editarContadorCentral(int anyo, int tipo, int contador,
			Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminLibroManager.editarContadorCentral(
					getSicresContadorCentralImpl(anyo, tipo, contador), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error editando el contador central", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void editarContadoresOficinas(int tipo, Contadores contadores,
			Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminLibroManager.editarContadoresOficinas(
					getSicresContadoresOficinasImpl(tipo, contadores), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error editando el contador central", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public int obtenerContadorCentral(int anyo, int tipo, Entidad entidad)
			throws RPAdminException {
		SicresContadorCentralImpl contador = new SicresContadorCentralImpl();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			contador = RPAdminLibroManager.obtenerContadorCentral(anyo, tipo,
					entidad.getIdentificador());

		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo contador central");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return contador.getNumReg();
	}

	public Contadores obtenerContadoresOficinas(int anyo, int tipo,
			Entidad entidad) throws RPAdminException {
		Contadores contadoresAPI = new Contadores();

		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager
					.obtenerOficinas(entidad.getIdentificador());
			SicresContadoresOficinasImpl contadoresDAO = RPAdminLibroManager
					.obtenerContadoresOficinas(anyo, tipo, entidad
							.getIdentificador());

			contadoresAPI = getAPIContadores(anyo, tipo, oficinasDAO,
					contadoresDAO);
		} catch (RPAdminDAOException e) {
			logger.error("Error obteniendo contadores de oficinas");
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return contadoresAPI;
	}

	public Campos obtenerCampos(int tipoFiltro, int tipoLibro, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		Archive archivador = AdapterVOSigem.adapterSIGEMArchive(RPAdminLibroManager
				.getDefinicionLibroRegistro(tipoLibro).getBookDefinition(""));

		ArchiveFlds camposDAO = archivador.getFldsDef();
		Campos campos = new Campos();
		for (int i = 0; i < camposDAO.count(); i++) {
			Campo campo = new Campo();
			ArchiveFld campoDAO = camposDAO.get(i);
			// if((tipoLibro!=1 && campoDAO.getId()!=13 && campoDAO.getId()!=16)
			// &&
			// (tipoLibro!=2 && campoDAO.getId()!=10)) {

			//se excluyen del listado los campos extendidos
			if(campoDAO.getType()!=2){
				campo.setId(campoDAO.getId());
				campo.setTipo(DefinicionFilterField.idToType(tipoLibro,
						campoDAO.getId()));
				campo.setDescripcion(campoDAO.getName());
				campos.add(campo);
			}
			// }
		}

		return campos;
	}

	public OptionsBean obtenerOperadores(String tipoCampo, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		OptionsBean options = new OptionsBean();

		int[] operadoresDAO = RPAdminLibroManager.getOperadores(tipoCampo,
				entidad.getIdentificador());

		for (int i = 0; i < operadoresDAO.length; i++) {
			OptionBean option = new OptionBean();
			option.setCodigo(new Integer(operadoresDAO[i]).toString());
			options.add(option);
		}

		return options;
	}

	public void configurarFiltro(int tipoFiltro, int tipoLibro, int idLibro,
			int idUserOfic, Filtros filtros, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminLibroManager.configureFiltros(tipoFiltro, tipoLibro,
					idLibro, idUserOfic, getFiltrosImpl(filtros), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error almacenando los filtros", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public Filtros obtenerFiltros(int tipoFiltro, int tipoLibro, int idLibro,
			int idUserOfic, Entidad entidad) throws RPAdminException {

		Filtros filtros = new Filtros();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			FiltrosImpl filtrosDAO = RPAdminLibroManager.obtenerFiltros(
					tipoFiltro, tipoLibro, idLibro, idUserOfic, entidad
							.getIdentificador());
			filtros = getAPIFiltros(filtrosDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo los filtros", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return filtros;
	}

	public void asociarOficinasAUsuario(int idUser, int idOfic, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminUserManager.asociarOficinasAUsuario(idUser, idOfic, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asocia una oficina a un usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void desasociarOficinaAUsuario(int idUser, int idOfic,
			Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminUserManager.desasociarOficinasAUsuario(idUser, idOfic, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al desasociar una oficina a un usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void desasociarOficinaAUsuarioLDAP(int idUser, int idOfic,
			Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminUserManager.desasociarOficinasAUsuarioLDAP(idUser, idOfic, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al desasociar una oficina a un usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public Oficinas obtenerOficinasAsociadasAUsuario(int idUser, Entidad entidad)
			throws RPAdminException {

		Oficinas oficinas = new Oficinas();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager
					.obtenerOficinasAgregadasAUsuario(idUser, entidad
							.getIdentificador());
			oficinas = getAPIOficinas(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas asociadas a usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return oficinas;
	}

	public Oficinas obtenerOficinasDesasociadasAUsuario(int idUser,
			Entidad entidad) throws RPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager
					.obtenerOficinasDesagregadasAUsuario(idUser, entidad
							.getIdentificador());
			oficinas = getAPIOficinas(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas desasociadas a usuario", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}

	public Oficina obtenerOficinaAsociadaADeptoUsuario(int idUser, Entidad entidad) throws RPAdminException{
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return RPAdminUserManager.getOficinaDeptoUsuario(idUser, entidad.getIdentificador());
	}

	public void asociarOficinaPreferenteAUsuario(int userId, int idOficPref,
			Entidad entidad) throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminUserManager.asociarOficinaPreferenteAUsuario(userId, idOficPref, entidad.getIdentificador());
	}

	public Integer obtenerIdOficinaPreferenteUsuario(int userId, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return RPAdminUserManager.getIdOficinaPreferenteUsuario(userId, entidad.getIdentificador());
	}

	public int crearTransporte(Transporte transporte, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTransporteManager.crearTransporte(transporte, entidad.getIdentificador());
		return transporte.getId();
	}

	public void editarTransporte(Transporte transporte, Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTransporteManager.editarTransporte(transporte, entidad.getIdentificador());
	}

	public void eliminarTransporte(int id, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTransporteManager.eliminarTransporte(id, entidad.getIdentificador());
	}

	public Transporte obtenerTransporte(int id, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			return RPAdminTransporteManager.obtenerTransporte(id, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al Obtener el Transporte");
		}
		return null;
	}

	public Transportes obtenerTransportes(Entidad entidad) throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return RPAdminTransporteManager.obtenerTransportes(entidad.getIdentificador());
	}

	public int crearTipoAsunto(TipoAsuntoBean tipoAsuntoBean, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.crearTipoAsunto(tipoAsuntoBean, entidad.getIdentificador());
		return tipoAsuntoBean.getId();
	}

	public void editarTipoAsunto(TipoAsuntoBean tipoAsuntoBean, Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.editarTipoAsunto(tipoAsuntoBean, entidad.getIdentificador());

	}

	public void eliminarTipoAsunto(int id, Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.eliminarTipoAsunto(id, entidad.getIdentificador());
	}

	public TipoAsuntoBean obtenerTipoAsunto(int id, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			return RPAdminTipoAsuntoManager.obtenerTipoAsunto(id, entidad.getIdentificador());
		} catch (Exception e) {
			throw new RPAdminException(e);
		}
	}

	public TiposAsuntoBean obtenerTiposAsunto(Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return RPAdminTipoAsuntoManager.obtenerTiposAsunto(entidad.getIdentificador());
	}

	public void asociarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.asociarDocumentoTipoAsunto(documento, entidad.getIdentificador());
	}

	public void asociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.asociarOficinaTipoAsunto(oficina, entidad.getIdentificador());

	}

	public void desasociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, Entidad entidad)
			throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.desasociarDocumentoTipoAsunto(documento, entidad.getIdentificador());
	}

	public void desasociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws RPAdminException {

		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.desasociarOficinaTipoAsunto(oficina, entidad.getIdentificador());
	}

	public void editarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminTipoAsuntoManager.editarDocumentoTipoAsunto(documento, entidad.getIdentificador());
	}

	public void actualizarNumeracionOficinaAsociadaALibro(int idBook, int idOfic, Entidad entidad)
		throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			LibroBean libro = obtenerLibroBean(idBook, entidad);
			RPAdminLibroManager.actualizarNumeracionOficinaAsociadaALibro(idBook, idOfic,
					libro.getNumeracion(), entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al actualizar numeracion de oficinas asociada a libro", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,	e);
		}
	}

	public void importarDepartamentos(int deptId, boolean isSelected, String idUnidad, Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminOrganizacionManager.importarDepartamentos(deptId, isSelected, idUnidad, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al importar departamentos de la estructura organizativa", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,e);
		}
	}

	public void importarGruposLdap(String nodeDn, int maxChildrenLdap, int treeType, boolean isSelected, String idUnidad, Entidad entidad) throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			RPAdminOrganizacionManager.importarGruposLdap(nodeDn, maxChildrenLdap, treeType, isSelected, idUnidad, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al importar grupos LDAP", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION,e);
		}
	}

	public Oficinas obtenerOficinasUsuarioLdap(String ldapguid, Entidad entidad) throws RPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOrganizacionManager.
											getOficinasUsuarioLdap(ldapguid, entidad.getIdentificador());
			oficinas = getOficinasLdapDAO(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas asociadas a usuario LDAP", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	public Oficinas obtenerOficinasDesasociadasAUsuarioLdap(String ldapguid, Entidad entidad) throws RPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			SicresOficinasImpl oficinasDAO = RPAdminOficinaManager.
											getOficinasDesasociadasAUsuarioLdap(ldapguid, entidad.getIdentificador());
			oficinas = getAPIOficinas(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo posibles oficinas que se pueden asociar a un usuario LDAP", e);
			throw new RPAdminException(RPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	/**
	 * INFORMES
	 */

	public InformesBean obtenerInformes(Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		return RPAdminInformeManager.obtenerInformes(entidad.getIdentificador());
	}

	public InformeBean obtenerInforme(int id, Entidad entidad, OptionsBean perfiles)
	throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			return RPAdminInformeManager.obtenerInforme(id, entidad.getIdentificador(), perfiles);
		} catch (Exception e) {
			throw new RPAdminException(e);
		}
	}

	public void editarInforme(InformeBean informe, Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminInformeManager.editarInforme(informe, entidad.getIdentificador());
	}

	public int crearInforme(InformeBean informeBean, Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminInformeManager.crearInforme(informeBean, entidad.getIdentificador());
		return informeBean.getId();
	}

	public InformeBean descargarInforme(int id, Entidad entidad)
			throws RPAdminException {
		try {
			//seteamos al thread local
			MultiEntityContextHolder.setEntity(entidad.getIdentificador());

			return RPAdminInformeManager.descargarInforme(id, entidad.getIdentificador());
		} catch (Exception e) {
			throw new RPAdminException(e);
		}
	}

	public void eliminarInforme(int id, Entidad entidad)
			throws RPAdminException {
		//seteamos al thread local
		MultiEntityContextHolder.setEntity(entidad.getIdentificador());

		RPAdminInformeManager.eliminarInforme(id, entidad.getIdentificador());

	}
}