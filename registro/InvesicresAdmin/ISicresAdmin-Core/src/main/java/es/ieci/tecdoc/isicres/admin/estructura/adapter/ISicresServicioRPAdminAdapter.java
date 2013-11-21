package es.ieci.tecdoc.isicres.admin.estructura.adapter;



import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.isicres.admin.base.db.DbDataType;
import es.ieci.tecdoc.isicres.admin.beans.Campo;
import es.ieci.tecdoc.isicres.admin.beans.Campos;
import es.ieci.tecdoc.isicres.admin.beans.Contador;
import es.ieci.tecdoc.isicres.admin.beans.Contadores;
import es.ieci.tecdoc.isicres.admin.beans.Distribucion;
import es.ieci.tecdoc.isicres.admin.beans.Distribuciones;
import es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Filtro;
import es.ieci.tecdoc.isicres.admin.beans.Filtros;
import es.ieci.tecdoc.isicres.admin.beans.InformeBean;
import es.ieci.tecdoc.isicres.admin.beans.InformesBean;
import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.beans.Libros;
import es.ieci.tecdoc.isicres.admin.beans.Oficina;
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.Organizacion;
import es.ieci.tecdoc.isicres.admin.beans.OrganizacionBean;
import es.ieci.tecdoc.isicres.admin.beans.Organizaciones;
import es.ieci.tecdoc.isicres.admin.beans.PermisoSicres;
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Transporte;
import es.ieci.tecdoc.isicres.admin.beans.Transportes;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistrador;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.beans.UsuariosRegistradores;
import es.ieci.tecdoc.isicres.admin.business.exception.ISicresAdminIntercambioRegistralException;
import es.ieci.tecdoc.isicres.admin.business.manager.GestionDCOManager;
import es.ieci.tecdoc.isicres.admin.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.admin.business.spring.AppContext;
import es.ieci.tecdoc.isicres.admin.business.spring.AdminIRManagerProvider;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosOficinaDCVO;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosUnidadOrganicaDCVO;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.core.CalculadorPermisos;
import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.FiltrosImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IDocArchHDRImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IDocArchsHDRImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjsPermsImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IVolArchListImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuarioImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.ListaUsuariosImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorCentralImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadorOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresContadoresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroEstadoImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibrosOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOrganizacionesImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTipoOrganizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOrganizacionesImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserIdentificacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUserPermisosImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioAgregadoImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionFilterField;
import es.ieci.tecdoc.isicres.admin.core.manager.ManagerUtils;
import es.ieci.tecdoc.isicres.admin.core.services.GestionURLsAdministracion;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Archive;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFlds;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Grupos;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Lista;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Listas;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuarios;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuariosLdap;
import es.ieci.tecdoc.isicres.admin.estructura.enums.PerfilesReport;
import es.ieci.tecdoc.isicres.admin.estructura.enums.PerfilesUsuario;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminConfigLdapManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminDistribucionManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminInformeManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminOficinaManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminOrganizacionManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminTipoAsuntoManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminTransporteManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminUserManager;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

/*
 * Consideraciones generales, la oficina está asociada inicialmente a un
 * departamento, además al usar tablas invesdoc y tablas isicres usa indistintamente
 * el id de departamento como el el isicres, a la parte web he decidido
 * pasarle siempre el de departamento.
 * Esto podría dificultar la administración de varias oficinas para un departamento
 * por lo que convendría pensarlo con más tranquilidad tras la entrega de Abril del 2008
 */

public class ISicresServicioRPAdminAdapter implements ISicresServicioRPAdmin {

	private static Logger logger = Logger
			.getLogger(ISicresServicioRPAdminAdapter.class);

	public UsuariosRegistradores obtenerUsuarios(Entidad entidad)
			throws ISicresRPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			ListaUsuariosImpl usuariosDAO = ISicresRPAdminUserManager
					.obtenerUsuariosListado(entidad.getIdentificador());
			usuariosAPI = getAPIUsuarios(usuariosDAO, ManagerUtils.SIN_AGREGADOS);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosLdap(Entidad entidad)
			throws ISicresRPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			UsuariosLdap usuariosDAO = ISicresRPAdminUserManager.obtenerListadoUsuariosLdap(entidad.getIdentificador());
			usuariosAPI = getAPIUsuariosLdap(usuariosDAO, ManagerUtils.SIN_AGREGADOS);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios de LDAP");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosAsociacion(int idOfic, Entidad entidad) throws ISicresRPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			OficinaBean oficina = obtenerOficina(idOfic, entidad);
			ListaUsuariosImpl usuariosDAO = ISicresRPAdminUserManager
					.obtenerUsuariosAsociacion(idOfic, oficina.getDeptId(),
							entidad.getIdentificador());
			usuariosAPI = getAPIUsuarios(usuariosDAO, ManagerUtils.CON_AGREGADOS);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo posibles usuarios para asociar a una oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosLdapAsociacion(int idOfic, Entidad entidad) throws ISicresRPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			UsuariosLdap usuariosDAO = ISicresRPAdminUserManager.obtenerUsuariosLdapAsociacion(idOfic, entidad.getIdentificador());
			usuariosAPI = getAPIUsuariosLdap(usuariosDAO, ManagerUtils.CON_AGREGADOS);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios LDAP para asociar a una oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return usuariosAPI;
	}

	public UsuarioRegistradorBean obtenerUsuario(int id, Entidad entidad)
			throws ISicresRPAdminException {
		UsuarioRegistradorBean usuarioAPI = null;
		try {
			Usuario userDAO = ISicresRPAdminUserManager.getUser(id, entidad
					.getIdentificador());
			SicresUserPermisosImpl permisos = ISicresRPAdminUserManager.getPermisos(
					id, entidad.getIdentificador());
			SicresUserIdentificacionImpl identificacion = ISicresRPAdminUserManager
					.getIdentificacion(id, entidad.getIdentificador());
			SicresUserLocalizacionImpl localizacion = ISicresRPAdminUserManager
					.getLocalizacion(id, entidad.getIdentificador());
			usuarioAPI = getAPIUsuarioBean(userDAO, permisos,
					identificacion, localizacion);
		} catch (Exception e) {
			logger.error("Error al obtener el usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuarioAPI;
	}

	public UsuarioRegistradorBean obtenerUsuarioLdap(int id, Entidad entidad)
			throws ISicresRPAdminException {
		UsuarioRegistradorBean usuarioAPI = null;
		UsuarioLdap userLdapDAO = null;
		try {
			userLdapDAO = ISicresRPAdminUserManager.getUserLdap(id, entidad.getIdentificador());
			SicresUserPermisosImpl permisos = ISicresRPAdminUserManager.getPermisos(
					id, entidad.getIdentificador());
			SicresUserIdentificacionImpl identificacion = ISicresRPAdminUserManager
					.getIdentificacion(id, entidad.getIdentificador());
			SicresUserLocalizacionImpl localizacion = ISicresRPAdminUserManager
					.getLocalizacion(id, entidad.getIdentificador());
			usuarioAPI = getAPIUsuarioBeanLdap(userLdapDAO, permisos, identificacion, localizacion);
		} catch (Exception e) {
			logger.error("Error al obtener el usuario LDAP", e);
			throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}

		//validamos la existencia del usuario en la estructura LDAP
		if(userLdapDAO != null){
			if(!ISicresRPAdminUserManager.validateUserInStructureLDAP(userLdapDAO.get_ldapguid(), entidad.getIdentificador())){
				logger.error("El usuario ["+ userLdapDAO.get_ldapfullname() + "] no ha sido encontrado dentro del sistema LDAP");
				throw new ISicresRPAdminDAOException(ISicresRPAdminDAOException.IUSER_NOT_FOUND_IN_LDAP);
			}
		}

		return usuarioAPI;
	}

	public UsuariosRegistradores obtenerUsuariosOficina(int idOfic, boolean usuarios,
			boolean agregados, Entidad entidad)	throws ISicresRPAdminException {

		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
		try {
			OficinaBean oficina = obtenerOficina(idOfic, entidad);
			if(usuarios){
				usuariosDAO = ISicresRPAdminUserManager.getUsuarios(new int[]{oficina.getDeptId()}, entidad.getIdentificador());
				usuariosDAO.getLista().addAll(ISicresRPAdminUserManager.getSuperusuarios(oficina.getDeptId(), entidad.getIdentificador()).getLista());
				usuariosAPI.getLista().addAll(getAPIUsuarios(usuariosDAO, ManagerUtils.SIN_AGREGADOS).getLista());
			}
			if(agregados){
				usuariosDAO = ISicresRPAdminUserManager.getUsuariosAgregados(idOfic, entidad.getIdentificador());
				usuariosAPI.getLista().addAll(getAPIUsuarios(usuariosDAO, ManagerUtils.CON_AGREGADOS).getLista());
			}
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios de la oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosAPI;
	}

	public UsuariosRegistradores obtenerUsuariosOficinaLdap(int idOfic, boolean usuarios,
			boolean agregados, Entidad entidad)	throws ISicresRPAdminException {
		UsuariosRegistradores usuariosAPI = new UsuariosRegistradores();
		try {
			OficinaBean oficina = obtenerOficina(idOfic, entidad);
			if(usuarios){
				UsuariosLdap usuariosDAO = ISicresRPAdminUserManager.getUsuariosOficinaLdap(oficina.getDeptId(), entidad.getIdentificador());
				usuariosAPI = getAPIUsuariosLdap(usuariosDAO, ManagerUtils.SIN_AGREGADOS);
			}
			if(agregados){
				UsuariosLdap usuariosAgregadosDAO = ISicresRPAdminUserManager.getUsuariosAgregadosLdap(idOfic, entidad.getIdentificador());
				usuariosAPI.getLista().addAll(getAPIUsuariosLdap(usuariosAgregadosDAO, ManagerUtils.CON_AGREGADOS).getLista());
			}
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios LDAP pertenecientes a una determinada oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return usuariosAPI;
	}

	public int crearUsuario(UsuarioRegistradorBean usuario, Entidad entidad)
			throws ISicresRPAdminException {

		Usuario user = null;
		// Comprobar que existe el usuario
		try {
			user = ISicresRPAdminUserManager.getUser(usuario.getNombre(), entidad
					.getIdentificador());
			if (user == null) {
				throw new ISicresRPAdminException(ISicresRPAdminException.USUARIO_INEXISTENTE);
			}
		} catch (ISicresRPAdminDAOException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Se ha intentado asociar un usuario sin conseguirlo");
			}
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
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
			throw new ISicresRPAdminException(ISicresRPAdminException.OFICINA_INEXISTENTE, e);
		}
		*/

		// Comprobar que no esté asociado ya
		try {
			Usuario userDAO = ISicresRPAdminUserManager.getUser(user.get_id(),
					entidad.getIdentificador());
			if (userDAO.get_Perfil(ManagerUtils.ISICRES_PROD_ID).get_profile() != 0) {
				throw new ISicresRPAdminException(
						ISicresRPAdminException.USUARIO_SICRES_EXISTENTE);
			}
		} catch (ISicresRPAdminDAOException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Se ha intentado asociar un usuario sin conseguirlo");
			}
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		try {
			usuario.setId(user.get_id());
			ISicresRPAdminUserManager.editarUsuario(usuario.getId(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario),
					getSicresUserIdentificacionImpl(usuario),
					getSicresUserLocalizacionImpl(usuario),entidad
							.getIdentificador(), true);
		} catch (Exception e) {
			logger.error("Error al crear el usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}


		return user.get_id();
	}

	public int crearUsuarioLdap(UsuarioRegistradorBean usuario, Entidad entidad)
			throws ISicresRPAdminException {

		UsuarioLdap user = null;

		// Comprobar que existe el usuario
		try {
			user = ISicresRPAdminUserManager.getUserLdapByGuid(usuario.getLdapGuid(), entidad
					.getIdentificador());
			if (user != null) {
				throw new ISicresRPAdminException(ISicresRPAdminException.USUARIO_SICRES_EXISTENTE);
			}
		} catch (ISicresRPAdminDAOException e) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Se ha intentado asociar un usuario sin conseguirlo");
			}
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		int id = -1;

		try {

			id = ISicresRPAdminUserManager.crearUsuarioLdap(usuario.getLdapGuid(), usuario.getLdapFullName(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario), getSicresUserIdentificacionImpl(usuario), getSicresUserLocalizacionImpl(usuario), entidad.getIdentificador(), false);

		} catch (Exception e) {
			logger.error("Error al crear el usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return id;
	}

	public void editarUsuario(UsuarioRegistradorBean usuario, Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminUserManager.editarUsuario(usuario.getId(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario),
					getSicresUserIdentificacionImpl(usuario),
					getSicresUserLocalizacionImpl(usuario), entidad.getIdentificador(), false);
		} catch (Exception e) {
			logger.error("Error al editar el usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void editarUsuarioLdap(UsuarioRegistradorBean usuario, Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminUserManager.editarUsuarioLdap(usuario.getId(), usuario.getIdPerfil(),
					getSicresUserPermisosImpl(usuario),
					getSicresUserIdentificacionImpl(usuario),
					getSicresUserLocalizacionImpl(usuario), entidad.getIdentificador(), false);
		} catch (Exception e) {
			logger.error("Error al editar el usuario de LDAP", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarUsuario(int id, Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminUserManager.eliminarUsuario(id, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar el usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarUsuarioLdap(int id, Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminUserManager.eliminarUsuarioLdap(id, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar el usuario LDAP", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public OptionsBean obtenerOficinasCombo(Entidad entidad) throws ISicresRPAdminException {
		OptionsBean optionsAPI = new OptionsBean();
		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager.obtenerOficinas(entidad.getIdentificador());
			optionsAPI = getAPIOptionsBean(oficinasDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo usuarios");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,e);
		}
		return optionsAPI;
	}


	public Oficinas obtenerOficinasParaLista(Entidad entidad) throws ISicresRPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager.obtenerOficinas(entidad.getIdentificador());
			oficinas = getOficinasFromDAO(oficinasDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo las oficinas");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	/**
	 * Obtiene los tipos de perfiles de informes y los devuelve en un bean
	 * @param entidad
	 * @return
	 * @throws ISicresRPAdminException
	 */
	public OptionsBean obtenerTiposInformesCombo(Entidad entidad)throws ISicresRPAdminException{
		OptionsBean options = new OptionsBean();
		ResourceBundle rs = ResourceBundle.getBundle("es.ieci.tecdoc.isicres.admin.core.manager.tipoInforme");
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

	public OptionsBean obtenerPerfilesCombo(Entidad entidad) throws ISicresRPAdminException {
		OptionsBean options = new OptionsBean();

		for (int i=0; i<PerfilesUsuario.getEnumList().size(); i++){
			PerfilesUsuario perfil = (PerfilesUsuario) PerfilesUsuario.getEnumList().get(i);
			OptionBean option = new OptionBean();
			option.setCodigo(Integer.toString(perfil.getValue()));
			option.setDescripcion(perfil.getName());
			options.add(option);
		}

		return options;
	}

	public OptionsBean obtenerPerfilesReportCombo(Entidad entidad) throws ISicresRPAdminException {
		OptionsBean options = new OptionsBean();

		for (int i=0; i<PerfilesReport.getEnumList().size(); i++){
			PerfilesReport perfil = (PerfilesReport) PerfilesReport.getEnumList().get(i);
			OptionBean option = new OptionBean();
			option.setCodigo(Integer.toString(perfil.getValue()));
			option.setDescripcion(perfil.getName());
			options.add(option);
		}
		return options;
	}

	public Oficinas obtenerOficinas(Entidad entidad) throws ISicresRPAdminException {
		Oficinas oficinasAPI = new Oficinas();
		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager
					.obtenerOficinasListado(entidad.getIdentificador());
			oficinasAPI = getAPIOficinas(oficinasDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinasAPI;
	}

	public OficinaBean obtenerOficina(int id, Entidad entidad) throws ISicresRPAdminException {
		OficinaBean oficinaAPI = null;
		try {
			SicresOficinaImpl oficina = ISicresRPAdminOficinaManager
					.getOficinaById(id, entidad.getIdentificador());
			SicresOficinaLocalizacionImpl localizacion = ISicresRPAdminOficinaManager
					.getLocalizacion(oficina.getId(), entidad.getIdentificador());

			// creamos la Entidad Registral asociada a la oficina
			IntercambioRegistralManager intercambioRegistralManager = (IntercambioRegistralManager) AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

			EntidadRegistralVO entidadRegistral = intercambioRegistralManager.getEntidadRegistralByIdOficina(id);

			oficinaAPI = getAPIOficinaBean(oficina, localizacion, entidadRegistral);

		} catch (Exception e) {
			logger.error("Error al obtener la oficina", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinaAPI;
	}

	public int crearOficina(OficinaBean oficina, Entidad entidad)
			throws ISicresRPAdminException {

		SicresOficinaImpl oficinaDAO = null;

		if ((oficina!=null) && (oficina.getTipoDepartamento() == Departamento.LDAP_DEPT_TYPE)){
			// Comprobar que no esté asociado ya para Grupos LDAP
			try {
				oficinaDAO = ISicresRPAdminOficinaManager.getOficinaByLdapGroup(oficina.getLdapGuid(), entidad.getIdentificador());
			} catch (Exception e) {
				// Si hay excepción es porque no existia la oficina.
			}
			if (oficinaDAO != null) {
				logger.debug("Se intenta crear una oficina cuando su grupo LDAP ya está asociado a otra");

				// TODO NACHO Cambiar esta excepción
				throw new ISicresRPAdminException(
						ISicresRPAdminException.OFICINA_SICRES_EXISTENTE);
			}
		} else {
			// Comprobar que no esté asociado ya para departamentos Invesdoc
			try {
				oficinaDAO = ISicresRPAdminOficinaManager.getOficinaByDeptId(oficina
						.getDeptId(), entidad.getIdentificador());
			} catch (Exception e) {
				// Si hay excepción es porque no existia la oficina.
			}
			if (oficinaDAO != null) {
				logger.debug("Se intenta crear una oficina cuando esta ya existe");
				throw new ISicresRPAdminException(
						ISicresRPAdminException.OFICINA_SICRES_EXISTENTE);
			}
		}

		// Comprobar que el código no exista
		oficinaDAO = null;
		try {
			oficinaDAO = ISicresRPAdminOficinaManager.getOficinaByCode(
					safeUpperCase(oficina.getCodigo()), entidad
							.getIdentificador());
		} catch (Exception e) {
			// Si hay excepción es porque no existia la oficina.
		}
		if (oficinaDAO != null) {
			logger
					.debug("Se intenta crear una oficina cuando el código existe");
			throw new ISicresRPAdminException(
					ISicresRPAdminException.CODIGO_OFICINA_SICRES_EXISTENTE);
		}

		try {
			oficina.setFecha(new Date());

			ISicresRPAdminOficinaManager.crearOficina(getSicresOficinaImpl(oficina),
					getSicresOficinaLocalizacionImpl(oficina), getEntidadRegistralOficina(oficina), entidad
							.getIdentificador());

		} catch (Exception e) {
			logger.error("Error al crear el oficina", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return oficina.getId();
	}

	public void editarOficina(OficinaBean oficina, Entidad entidad)
			throws ISicresRPAdminException {

		try {
			ISicresRPAdminOficinaManager.editarOficina(getSicresOficinaImpl(oficina),
					getSicresOficinaLocalizacionImpl(oficina), getEntidadRegistralOficina(oficina), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al editar la oficina", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void eliminarOficina(int id, Entidad entidad)
			throws ISicresRPAdminException {

		// Comprobar que no existen usuarios
		ListaUsuariosImpl usuarios = null;
		try {
			OficinaBean oficina = obtenerOficina(id, entidad);
			usuarios = ISicresRPAdminUserManager.obtenerUsuariosOficinaDept(oficina
					.getDeptId(), ManagerUtils.CON_SUPERUSUARIOS,
					ManagerUtils.SIN_AGREGADOS, entidad.getIdentificador());
		} catch (Exception e) {
			logger
					.error("Error al comprobar si existen usuarios en la oficina a borrar");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		if (usuarios == null || usuarios.count() > 0) {
			if (logger.isDebugEnabled()) {
				logger
						.error("Se ha intentado eliminar una oficina con usuarios asociados");
			}
			throw new ISicresRPAdminException(ISicresRPAdminException.OFICINA_CON_USUARIOS);
		}

		try {
			ISicresRPAdminOficinaManager.eliminarOficina(id, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar la oficina", e);
			throw new ISicresRPAdminException(
					ISicresRPAdminException.OFICINA_CON_REFERENCIAS, e);
		}
		return;
	}

	public void eliminarOficinaLDAP(int idOficina, Entidad entidad)
			throws ISicresRPAdminException {

		// Comprobar que no existen usuarios
		ListaUsuariosImpl usuarios = null;
		OficinaBean oficina = null;
		try {
			oficina = obtenerOficina(idOficina, entidad);
			usuarios = ISicresRPAdminUserManager.obtenerUsuariosOficinaDept(
					oficina.getDeptId(), ManagerUtils.CON_SUPERUSUARIOS,
					ManagerUtils.SIN_AGREGADOS, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al comprobar si existen usuarios en la oficina a borrar");
			throw new ISicresRPAdminException(
					ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		if (usuarios == null || usuarios.count() > 0) {
			if (logger.isDebugEnabled()) {
				logger.error("Se ha intentado eliminar una oficina con usuarios asociados");
			}
			throw new ISicresRPAdminException(
					ISicresRPAdminException.OFICINA_CON_USUARIOS);
		}

		try {
			ISicresRPAdminOficinaManager.eliminarOficinaLDAP(idOficina,
					entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar la oficina", e);
			throw new ISicresRPAdminException(
					ISicresRPAdminException.OFICINA_CON_REFERENCIAS, e);
		}
		return;
	}

	@Deprecated
	public OptionsBean obtenerDepartamentosCombo(boolean oficinas,
			Entidad entidad) throws ISicresRPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			Departamentos departamentosDAO = ISicresRPAdminOficinaManager
					.obtenerDepartamentos(oficinas, entidad.getIdentificador());
			optionsAPI = getAPIOptionsBean(departamentosDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public OptionsBean obtenerDepartamentosCombo(Entidad entidad)
			throws ISicresRPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			Departamentos departamentosDAO = ISicresRPAdminOficinaManager
					.obtenerDepartamentos(entidad.getIdentificador());

			optionsAPI = getAPIOptionsBean(departamentosDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new ISicresRPAdminException(
					ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return optionsAPI;
	}

	public OptionsBean obtenerEntidadesRegistralesCombo(Entidad entidad)
			throws ISicresRPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			SicresOrganizacionesImpl organizacionesDAO = ISicresRPAdminOrganizacionManager
					.obtenerOrganizacionesPorTipo(
							ManagerUtils.ID_TYPE_ENTIDAD_REGISTRAL, entidad
									.getIdentificador());
			optionsAPI = getAPIOptionsBean(organizacionesDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public OptionsBean obtenerTipoOficinasCombo(Entidad entidad)
			throws ISicresRPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			SicresTiposOficinaImpl tiposDAO = ISicresRPAdminOficinaManager
					.obtenerTiposOficina(entidad.getIdentificador());
			optionsAPI = getAPIOptionsBean(tiposDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public Libros obtenerLibros(int tipoLibro, Entidad entidad)
			throws ISicresRPAdminException {
		Libros librosAPI = new Libros();
		try {
			IDocArchsHDRImpl librosDAO = ISicresRPAdminLibroManager.obtenerLibros(
					tipoLibro, entidad.getIdentificador());
			librosAPI = getAPILibros(librosDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo libros");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return librosAPI;
	}

	public PermisosSicres obtenerPermisosOficinasLibro(int bookId,
			Entidad entidad) throws ISicresRPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();

		try {
			IUserObjsPermsImpl permisosDAO = ISicresRPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_OFICINA,
							entidad.getIdentificador());

			SicresLibrosOficinasImpl oficinasDAO = ISicresRPAdminLibroManager
					.obtenerOficinas(bookId, entidad.getIdentificador());

			permisosAPI = getAPIPermisos(oficinasDAO, permisosDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo permisos");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return permisosAPI;
	}

	public Oficinas obtenerOficinasDesasociadasALibro(int bookId,
			Entidad entidad) throws ISicresRPAdminException {
		Oficinas optionsAPI = new Oficinas();

		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager
					.obtenerOficinasDesasociadasALibro(bookId, entidad
							.getIdentificador());

			optionsAPI = getAPIOficinas(oficinasDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo oficinas desasociadas");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public void asociarOficinaALibro(int bookId, int[] destIds, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			SicresLibroEstadoImpl libro = ISicresRPAdminLibroManager.getLibro(bookId, entidad.getIdentificador());
			ISicresRPAdminLibroManager.asociarOficinasALibro(
					getSicresLibrosOficinasImpl(bookId, destIds, libro
							.getNumerationType(), entidad), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asociar oficinas a un libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void desasociarOficinaALibro(int bookId, int destId, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			ISicresRPAdminLibroManager.desasociarOficinasALibro(
					getSicresLibrosOficinasImpl(bookId, new int[] { destId }, entidad),
					entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asociar oficinas a un libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void modificarPermisos(PermisosSicres permisos, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			ISicresRPAdminLibroManager
					.modificarPermisos(getToDeleteIUserObjsPermsImpl(permisos),
							getIUserObjsPermsImpl(permisos), entidad
									.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asociar oficinas a un libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public PermisosSicres obtenerPermisosUsuarios(int bookId, Entidad entidad)
			throws ISicresRPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			IUserObjsPermsImpl permisosDAO = ISicresRPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO,
							entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = ISicresRPAdminUserManager.getUsuarios(entidad
					.getIdentificador());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return permisosAPI;
	}

	public PermisosSicres obtenerPermisosUsuariosOficinas(int bookId, int idsDept[], Entidad entidad) throws ISicresRPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			IUserObjsPermsImpl permisosDAO = ISicresRPAdminLibroManager.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO, entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = ISicresRPAdminUserManager.getUsuarios(idsDept, entidad.getIdentificador());
			int idsUser[] = null;
			if(usuariosDAO.count() > 0){
				idsUser = new int[usuariosDAO.count()];
				for(int i=0; i < usuariosDAO.count(); i++){
					ListaUsuarioImpl user = usuariosDAO.get(i);
					idsUser[i] = user.getId();
				}
			}
			SicresOficinasImpl oficinas = ISicresRPAdminOficinaManager.getOficinasByDeptsId(idsDept, entidad.getIdentificador());
			int idsOfic[] = new int[idsDept.length];
			for(int i=0; i<oficinas.count(); i++)
				idsOfic[i] = oficinas.get(i).getId();

			usuariosDAO.getLista().addAll(ISicresRPAdminUserManager.getPermisosUsuariosAgregados(idsOfic, idsUser, entidad.getIdentificador()).getLista());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return permisosAPI;
	}

	public PermisosSicres obtenerPermisosUsuariosLdap(int bookId,
			Entidad entidad) throws ISicresRPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			IUserObjsPermsImpl permisosDAO = ISicresRPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO,
							entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = ISicresRPAdminUserManager.getUsuariosLdap(entidad
					.getIdentificador());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);

		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return permisosAPI;
	}

	public PermisosSicres obtenerPermisosUsuariosLdapOficinas(int bookId,
			int[] idsDept, Entidad entidad) throws ISicresRPAdminException {
		PermisosSicres permisosAPI = new PermisosSicres();
		try {
			IUserObjsPermsImpl permisosDAO = ISicresRPAdminLibroManager
					.obtenerPermisos(bookId, PermisoSicres.TIPO_USUARIO,
							entidad.getIdentificador());
			ListaUsuariosImpl usuariosDAO = new ListaUsuariosImpl();
			usuariosDAO = ISicresRPAdminUserManager.getUsuariosLdap(idsDept, entidad
					.getIdentificador());
			int idsUser[] = null;
			if (usuariosDAO.count() > 0) {
				idsUser = new int[usuariosDAO.count()];
				for (int i = 0; i < usuariosDAO.count(); i++) {
					ListaUsuarioImpl user = usuariosDAO.get(i);
					idsUser[i] = user.getId();
				}
			}

			SicresOficinasImpl oficinas = ISicresRPAdminOficinaManager
					.getOficinasByDeptsId(idsDept, entidad.getIdentificador());
			int idsOfic[] = new int[idsDept.length];
			for (int i = 0; i < oficinas.count(); i++) {
				idsOfic[i] = oficinas.get(i).getId();
			}

			usuariosDAO.getLista().addAll(
					ISicresRPAdminUserManager.getPermisosUsuariosLdapAgregados(
							idsOfic, idsUser, entidad.getIdentificador())
							.getLista());
			permisosAPI = getAPIPermisos(bookId, usuariosDAO, permisosDAO);

		} catch (Exception e) {
			logger.error("Error obteniendo permisos");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return permisosAPI;
	}


	public void asociarListaALibro(int idBook, int idLista, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			IVolArchListImpl lista = new IVolArchListImpl();
			lista.setArchId(idBook);
			lista.setListId(idLista);
			ISicresRPAdminLibroManager.asociarListaALibro(lista, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asocial la lista al libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

	}

	public void asociarUsuarioAOficinas(String[] idsUser, int idOfic, String entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminOficinaManager.asociarUsuariosAOficina(idsUser, idOfic, entidad);
		} catch (Exception e) {
			logger.error("Error al asociar usuarios a una oficina", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public OptionsBean obtenerListas(Entidad entidad) throws ISicresRPAdminException {
		OptionsBean optionsAPI = new OptionsBean();

		try {
			Listas listasDAO = ISicresRPAdminLibroManager.obtenerListas(entidad
					.getIdentificador());
			optionsAPI = getAPIOptionsBean(listasDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo tipos de oficina");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return optionsAPI;
	}

	public LibroBean obtenerLibroBean(int idBook, Entidad entidad)
			throws ISicresRPAdminException {
		LibroBean beanAPI = null;
		try {
			IDocArchHDRImpl archivador = ISicresRPAdminLibroManager.getArchivador(
					idBook, entidad.getIdentificador());
			IVolArchListImpl lista = ISicresRPAdminLibroManager.getLista(idBook,
					entidad.getIdentificador());
			SicresLibroEstadoImpl libro = ISicresRPAdminLibroManager.getLibro(idBook,
					entidad.getIdentificador());

			beanAPI = getAPILibroBean(archivador, lista, libro);
		} catch (Exception e) {
			logger.error("Error al obtener el libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return beanAPI;
	}

	public int crearLibro(LibroBean libroAPI, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			SicresLibroEstadoImpl libroDAO = getSicresLibroEstadoImpl(libroAPI);
			IDocArchHDRImpl archivadorDAO = getIDocArchHDRImpl(libroAPI);
			int idLibro = ISicresRPAdminLibroManager.crearLibro(archivadorDAO,
					libroDAO, entidad.getIdentificador());
			return idLibro;
		} catch (Exception e) {
			logger.error("Error al crear el libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public void editarLibro(LibroBean libroAPI, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			SicresLibroEstadoImpl libroDAO = getSicresLibroEstadoImpl(libroAPI);
			IDocArchHDRImpl archivadorDAO = getIDocArchHDRImpl(libroAPI);
			ISicresRPAdminLibroManager.editarLibro(libroDAO, archivadorDAO, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al editar el libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public void modificarEstadoLibro(int idBook, String usuario, int idEstado,
			Entidad entidad) throws ISicresRPAdminException {
		try {
			SicresLibroEstadoImpl libro = ISicresRPAdminLibroManager.getLibro(idBook,
					entidad.getIdentificador());
			libro.setCloseDate(new Date());
			libro.setState(idEstado);
			libro.setCloseUser(usuario);
			ISicresRPAdminLibroManager.editarLibro(libro, null, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al modificar el estado del libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public void eliminarLibro(int idBook, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			ISicresRPAdminLibroManager.eliminarLibro(idBook, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al editar el libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	public Organizaciones obtenerOrganizacionesPadre(Entidad entidad)
			throws ISicresRPAdminException {
		Organizaciones listaAPI = new Organizaciones();
		try {
			SicresTiposOrganizacionesImpl listaDAO = ISicresRPAdminOrganizacionManager
					.obtenerTiposOrganizaciones(entidad.getIdentificador());
			listaAPI = getAPIOrganizaciones(listaDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo organizacionesPadre");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public Organizaciones obtenerHijosOrganizacion(int orgId, Entidad entidad)
			throws ISicresRPAdminException {

		// Si el identificador es el 0, se refiere a tipos de unidades
		// administrativas.
		if (orgId == 0)
			return obtenerOrganizacionesPadre(entidad);

		Organizaciones listaAPI = new Organizaciones();
		try {
			SicresOrganizacionesImpl listaDAO = ISicresRPAdminOrganizacionManager
					.obtenerOrganizaciones(orgId, entidad.getIdentificador());
			listaAPI = getAPIOrganizaciones(listaDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo organizacionesPadre");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public OrganizacionBean obtenerOrganizacion(int orgId, Entidad entidad)
			throws ISicresRPAdminException {
		OrganizacionBean beanAPI = null;

		try {
			// Si el id es positivo se trata de una organización, sino, se trata
			// de un tipo de organizacion
			if (orgId > 0) {
				SicresOrganizacionImpl organizacion = ISicresRPAdminOrganizacionManager
						.getOrganizacion(orgId, entidad.getIdentificador());
				SicresOrganizacionLocalizacionImpl localizacion = ISicresRPAdminOrganizacionManager
						.getLocalizacion(orgId, entidad.getIdentificador());

				//Intercambio Registral
				IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

				UnidadRegistralVO unidadRegistral = intercambioRegistralManager
						.getUnidadRegistralByIdOrgs(organizacion.getId());

				beanAPI = getAPIOrganizacionBean(organizacion, localizacion, unidadRegistral);
			} else {
				SicresTipoOrganizacionImpl tipoOrganizacion = ISicresRPAdminOrganizacionManager
						.getTipoOrganizacion(orgId, entidad.getIdentificador());
				beanAPI = getAPIOrganizacionBean(tipoOrganizacion);
			}

		} catch (Exception e) {
			logger.error("Error al obtener la organización", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return beanAPI;
	}

	public OrganizacionBean obtenerOrganizacionByCode(String code,
			Entidad entidad) throws ISicresRPAdminException {
		OrganizacionBean beanAPI = null;

		try {
			SicresOrganizacionImpl organizacion = ISicresRPAdminOrganizacionManager
					.getOrganizacionByCode(code, entidad.getIdentificador());
			if (organizacion != null) {
				SicresOrganizacionLocalizacionImpl localizacion = ISicresRPAdminOrganizacionManager
						.getLocalizacion(organizacion.getId(), entidad
								.getIdentificador());

				//Intercambio Registral
				IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

				UnidadRegistralVO unidadRegistral = intercambioRegistralManager
						.getUnidadRegistral(organizacion.getId());

				beanAPI = getAPIOrganizacionBean(organizacion, localizacion, unidadRegistral);
			}
		} catch (Exception e) {
			logger.error("Error al obtener la organización", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return beanAPI;
	}

	public int crearOrganizacion(boolean esPrimerNivel, OrganizacionBean bean,
			Entidad entidad) throws ISicresRPAdminException {
		if (esPrimerNivel) {
			return crearTipoOrganizacion(bean, entidad);
		} else {
			return crearOrganizacion(bean, entidad);
		}
	}

	private int crearOrganizacion(OrganizacionBean bean, Entidad entidad)
			throws ISicresRPAdminException {
		int devuelve;

		// Comprobar que el código no exista
		SicresOrganizacionImpl organizacionDAO = null;
		try {
			organizacionDAO = ISicresRPAdminOrganizacionManager.getOrganizacionByCode(
					safeUpperCase(bean.getUid()), entidad.getIdentificador());
		} catch (Exception e) {
			// Si hay excepción es porque no existia la oficina.
		}
		if (organizacionDAO != null) {
			logger
					.debug("Se intenta crear una organizacion cuando el código existe");
			throw new ISicresRPAdminException(
					ISicresRPAdminException.CODIGO_ORGANIZACION_SICRES_EXISTENTE);
		}

		try {
			bean.setFechaAlta(new Date());
			bean.setEnabled(true);
			devuelve = ISicresRPAdminOrganizacionManager.crearOrganizacion(
					getSicresOrganizacionImpl(bean),
					getSicresOrganizacionLocalizacionImpl(bean), getSicresOrganizacionIntercambioRegistral(bean), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al crear la organizacion", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.ERROR_CREATE_ORGANIZACION,
					e);
		}
		return devuelve;
	}

	private int crearTipoOrganizacion(OrganizacionBean bean, Entidad entidad)
			throws ISicresRPAdminException {
		int devuelve;

		// Comprobar que el código no exista
		SicresTipoOrganizacionImpl tipoOrganizacionDAO = null;
		try {
			tipoOrganizacionDAO = ISicresRPAdminOrganizacionManager
					.getTipoOrganizacionByCode(safeUpperCase(bean.getUid()),
							entidad.getIdentificador());
		} catch (Exception e) {
			// Si hay excepción es porque no existia la oficina.
		}
		if (tipoOrganizacionDAO != null) {
			logger
					.debug("Se intenta crear un tipo de organizacion cuando el código existe");
			throw new ISicresRPAdminException(
					ISicresRPAdminException.CODIGO_TIPO_ORGANIZACION_SICRES_EXISTENTE);
		}

		try {
			devuelve = ISicresRPAdminOrganizacionManager.crearTipoOrganizacion(
					getSicresTipoOrganizacionImpl(bean), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al crear el tipo de organizacion", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.ERROR_CREATE_ORGANIZACION,
					e);
		}
		return -1 * devuelve;
	}

	public void editarOrganizacion(OrganizacionBean bean, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			if (bean.getId() > 0) {
				ISicresRPAdminOrganizacionManager.editarOrganizacion(
						getSicresOrganizacionImpl(bean),
						getSicresOrganizacionLocalizacionImpl(bean), getSicresOrganizacionIntercambioRegistral(bean), entidad
								.getIdentificador());
			} else {
				ISicresRPAdminOrganizacionManager.editarTipoOrganizacion(
						getSicresTipoOrganizacionImpl(bean), entidad
								.getIdentificador());
			}
		} catch (Exception e) {
			logger.error("Error al editar la organizacion", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.ERROR_UPDATE_ORGANIZACION,
					e);
		}
		return;
	}

	public void eliminarOrganizacion(int orgId, Entidad entidad)
			throws ISicresRPAdminException {
		// Comprobar que no existen usuarios
		SicresOrganizacionesImpl organizaciones = null;
		try {
			organizaciones = ISicresRPAdminOrganizacionManager.obtenerOrganizaciones(
					orgId, entidad.getIdentificador());
		} catch (Exception e) {
			logger
					.error("Error al comprobar si existen organizaciones hijas en la organizacion a borrar");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		if (organizaciones == null || organizaciones.count() > 0) {
			if (logger.isDebugEnabled()) {
				logger
						.error("Se ha intentado eliminar una organizaciones que tenía organizaciones hijas");
			}
			throw new ISicresRPAdminException(ISicresRPAdminException.ORGANIZACION_CON_HIJAS);
		}

		try {
			if (orgId > 0) {
				ISicresRPAdminOrganizacionManager.eliminarOrganizacion(orgId, entidad
						.getIdentificador());
			} else {
				ISicresRPAdminOrganizacionManager.eliminarTipoOrganizacion(-1 * orgId,
						entidad.getIdentificador());
			}
		} catch (Exception e) {
			logger.error("Error al eliminar la organizacion", e);
			throw new ISicresRPAdminException(
					ISicresRPAdminException.ORGANIZACION_CON_REFERENCIAS, e);
		}
		return;
	}

	public Distribuciones obtenerDistribuciones(int orgId, Entidad entidad)
			throws ISicresRPAdminException {

		Distribuciones listaAPI = new Distribuciones();
		try {
			SicresListaDistribucionesImpl listaDAO = ISicresRPAdminDistribucionManager
					.obtenerDistribuciones(orgId, entidad.getIdentificador());
			listaAPI = getAPIDistribuciones(listaDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo distribuciones");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public void crearDistribuciones(int idOrg, int idTipo, int[] ids,
			Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminDistribucionManager.crearDistribuciones(
					getSicresListaDistribucionesImpl(idOrg, idTipo, ids),
					entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al crear distribuciones", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void eliminarDistribucion(int id, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			ISicresRPAdminDistribucionManager.eliminarDistribucion(id, entidad
					.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al eliminar una distribucion", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public OptionsBean obtenerDepartamentosRaiz(Entidad entidad)
			throws ISicresRPAdminException {
		return obtenerDepartamentosHijos(0, entidad);
	}

	public OptionsBean obtenerGruposRaiz(Entidad entidad)
			throws ISicresRPAdminException {
		OptionsBean listaAPI = null;
		try {
			Grupos listaDAO = ISicresRPAdminUserManager.obtenerGrupos(entidad
					.getIdentificador());
			listaAPI = getAPIOptionsBean(listaDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo grupos");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public OptionsBean obtenerGruposHijos(int idGrupo, Entidad entidad)
			throws ISicresRPAdminException {
		if (idGrupo == 0) {
			return obtenerGruposRaiz(entidad);
		} else {
			// Los grupos no tienen hijos
			return new OptionsBean();
		}
	}

	public OptionsBean obtenerDepartamentosHijos(int idDepartamento,
			Entidad entidad) throws ISicresRPAdminException {
		OptionsBean listaAPI = null;
		try {
			Departamentos listaDAO = ISicresRPAdminOficinaManager
					.obtenerDepartamentosHijos(idDepartamento, entidad
							.getIdentificador());
			listaAPI = getAPIOptionsBean(listaDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo departamentos hijos");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return listaAPI;
	}

	public OptionsBean obtenerUsuarios(int id, int tipo, Entidad entidad)
			throws ISicresRPAdminException {
		OptionsBean listaAPI = null;
		try {
			Usuarios listaDAO = null;
			if (tipo == Distribucion.TIPO_DEPARTAMENTO) {
				listaDAO = ISicresRPAdminUserManager.obtenerUsuariosDepartamento(id,
						entidad.getIdentificador());
			} else {
				listaDAO = ISicresRPAdminUserManager.obtenerUsuariosGrupo(id, entidad
						.getIdentificador());
			}
			listaAPI = getAPIOptionsBean(listaDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo usuarios");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
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
			SicresOrganizacionLocalizacionImpl localizacion, UnidadRegistralVO unidadRegistral) {
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

		//Intercambio Registral
		if(unidadRegistral != null){
			beanAPI.setIdUnidadTramit(unidadRegistral.getId());
			beanAPI.setCodeUnidadTramit(unidadRegistral.getCode_tramunit());
			beanAPI.setNameUnidadTramit(unidadRegistral.getName_tramunit());
			beanAPI.setCodEntidadReg(unidadRegistral.getCode_entity());
			beanAPI.setNameEntidadReg(unidadRegistral.getName_entity());
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
			SicresOficinaLocalizacionImpl localizacion, EntidadRegistralVO entidadRegistral) {
		OficinaBean beanAPI = new OficinaBean();

		if (oficinaDAO != null) {
			beanAPI.setId(oficinaDAO.getId());
			beanAPI.setNombre(oficinaDAO.getName());
			beanAPI.setAbreviatura(oficinaDAO.getAcron());
			beanAPI.setCodigo(oficinaDAO.getCode());
			beanAPI.setFecha(oficinaDAO.getCreationDate());
			beanAPI.setFechaBaja(oficinaDAO.getDisableDate());
			beanAPI.setIdOrgs(oficinaDAO.getIdOrgs());
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

		if(entidadRegistral != null){
			beanAPI.setIdEntidadRegistral(entidadRegistral.getId());
			beanAPI.setCodEntidadReg(entidadRegistral.getCode());
			beanAPI.setNameEntidadReg(entidadRegistral.getName());
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
		beanDAO.setIdOrgs(beanAPI.getIdOrgs());
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
		if (PerfilesUsuario.getEnum(perfil) != null) {
			sPerfil = PerfilesUsuario.getEnum(perfil).getName();
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
			Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminLibroManager.editarContadorCentral(
					getSicresContadorCentralImpl(anyo, tipo, contador), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error editando el contador central", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void editarContadoresOficinas(int tipo, Contadores contadores,
			Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminLibroManager.editarContadoresOficinas(
					getSicresContadoresOficinasImpl(tipo, contadores), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error editando el contador central", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public int obtenerContadorCentral(int anyo, int tipo, Entidad entidad)
			throws ISicresRPAdminException {
		SicresContadorCentralImpl contador = new SicresContadorCentralImpl();

		try {

			contador = ISicresRPAdminLibroManager.obtenerContadorCentral(anyo, tipo,
					entidad.getIdentificador());

		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo contador central");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return contador.getNumReg();
	}

	public Contadores obtenerContadoresOficinas(int anyo, int tipo,
			Entidad entidad) throws ISicresRPAdminException {
		Contadores contadoresAPI = new Contadores();

		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager
					.obtenerOficinas(entidad.getIdentificador());
			SicresContadoresOficinasImpl contadoresDAO = ISicresRPAdminLibroManager
					.obtenerContadoresOficinas(anyo, tipo, entidad
							.getIdentificador());

			contadoresAPI = getAPIContadores(anyo, tipo, oficinasDAO,
					contadoresDAO);
		} catch (ISicresRPAdminDAOException e) {
			logger.error("Error obteniendo contadores de oficinas");
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return contadoresAPI;
	}

	public Campos obtenerCampos(int tipoFiltro, int tipoLibro, Entidad entidad)
			throws ISicresRPAdminException {

		Archive archivador = ISicresRPAdminLibroManager.getDefinicionLibroRegistro(
				tipoLibro).getBookDefinition("");
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
				campo.setTipo(DefinicionFilterField.idToType(tipoLibro, campoDAO
						.getId()));
				campo.setDescripcion(campoDAO.getName());
				campos.add(campo);
			}
			// }
		}

		return campos;
	}

	public OptionsBean obtenerOperadores(String tipoCampo, Entidad entidad)
			throws ISicresRPAdminException {

		OptionsBean options = new OptionsBean();

		int[] operadoresDAO = ISicresRPAdminLibroManager.getOperadores(tipoCampo,
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
			throws ISicresRPAdminException {
		try {
			ISicresRPAdminLibroManager.configureFiltros(tipoFiltro, tipoLibro,
					idLibro, idUserOfic, getFiltrosImpl(filtros), entidad
							.getIdentificador());
		} catch (Exception e) {
			logger.error("Error almacenando los filtros", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public Filtros obtenerFiltros(int tipoFiltro, int tipoLibro, int idLibro,
			int idUserOfic, Entidad entidad) throws ISicresRPAdminException {

		Filtros filtros = new Filtros();
		try {
			FiltrosImpl filtrosDAO = ISicresRPAdminLibroManager.obtenerFiltros(
					tipoFiltro, tipoLibro, idLibro, idUserOfic, entidad
							.getIdentificador());
			filtros = getAPIFiltros(filtrosDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo los filtros", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return filtros;
	}

	public void asociarOficinasAUsuario(int idUser, int idOfic, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			ISicresRPAdminUserManager.asociarOficinasAUsuario(idUser, idOfic, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al asocia una oficina a un usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void desasociarOficinaAUsuario(int idUser, int idOfic,
			Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminUserManager.desasociarOficinasAUsuario(idUser, idOfic, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al desasociar una oficina a un usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public void desasociarOficinaAUsuarioLDAP(int idUser, int idOfic,
			Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminUserManager.desasociarOficinasAUsuarioLDAP(idUser, idOfic, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al desasociar una oficina a un usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
		return;
	}

	public Oficinas obtenerOficinasAsociadasAUsuario(int idUser, Entidad entidad)
			throws ISicresRPAdminException {

		Oficinas oficinas = new Oficinas();
		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager
					.obtenerOficinasAgregadasAUsuario(idUser, entidad
							.getIdentificador());
			oficinas = getAPIOficinas(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas asociadas a usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}

		return oficinas;
	}

	public Oficinas obtenerOficinasDesasociadasAUsuario(int idUser,
			Entidad entidad) throws ISicresRPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager
					.obtenerOficinasDesagregadasAUsuario(idUser, entidad
							.getIdentificador());
			oficinas = getAPIOficinas(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas desasociadas a usuario", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}

		return oficinas;
	}

	public Oficina obtenerOficinaAsociadaADeptoUsuario(int idUser, Entidad entidad) throws ISicresRPAdminException{
		return ISicresRPAdminUserManager.getOficinaDeptoUsuario(idUser, entidad.getIdentificador());
	}

	public void asociarOficinaPreferenteAUsuario(int userId, int idOficPref,
			Entidad entidad) throws ISicresRPAdminException {
		ISicresRPAdminUserManager.asociarOficinaPreferenteAUsuario(userId, idOficPref, entidad.getIdentificador());
	}

	public Integer obtenerIdOficinaPreferenteUsuario(int userId, Entidad entidad)
			throws ISicresRPAdminException {

		return ISicresRPAdminUserManager.getIdOficinaPreferenteUsuario(userId, entidad.getIdentificador());
	}

		public int crearTransporte(Transporte transporte, Entidad entidad)
			throws ISicresRPAdminException {
			ISicresRPAdminTransporteManager.crearTransporte(transporte, entidad.getIdentificador());
		return transporte.getId();
	}

	public void editarTransporte(Transporte transporte, Entidad entidad)
			throws ISicresRPAdminException {
		ISicresRPAdminTransporteManager.editarTransporte(transporte, entidad.getIdentificador());
	}

	public void eliminarTransporte(int id, Entidad entidad)
			throws ISicresRPAdminException {
		ISicresRPAdminTransporteManager.eliminarTransporte(id, entidad.getIdentificador());
	}

	public Transporte obtenerTransporte(int id, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			return ISicresRPAdminTransporteManager.obtenerTransporte(id, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al Obtener el Transporte");
		}
		return null;
	}

	public Transportes obtenerTransportes(Entidad entidad) throws ISicresRPAdminException {
		return ISicresRPAdminTransporteManager.obtenerTransportes(entidad.getIdentificador());
	}

	public int crearTipoAsunto(TipoAsuntoBean tipoAsuntoBean, Entidad entidad)
			throws ISicresRPAdminException {

		ISicresRPAdminTipoAsuntoManager.crearTipoAsunto(tipoAsuntoBean, entidad.getIdentificador());
		return tipoAsuntoBean.getId();
	}

	public void editarTipoAsunto(TipoAsuntoBean tipoAsuntoBean, Entidad entidad)
			throws ISicresRPAdminException {
		ISicresRPAdminTipoAsuntoManager.editarTipoAsunto(tipoAsuntoBean, entidad.getIdentificador());

	}

	public void eliminarTipoAsunto(int id, Entidad entidad)
			throws ISicresRPAdminException {
		ISicresRPAdminTipoAsuntoManager.eliminarTipoAsunto(id, entidad.getIdentificador());
	}

	public TipoAsuntoBean obtenerTipoAsunto(int id, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			return ISicresRPAdminTipoAsuntoManager.obtenerTipoAsunto(id, entidad.getIdentificador());
		} catch (Exception e) {
			throw new ISicresRPAdminException(e);
		}
	}

	public TiposAsuntoBean obtenerTiposAsunto(Entidad entidad)
			throws ISicresRPAdminException {
		return ISicresRPAdminTipoAsuntoManager.obtenerTiposAsunto(entidad.getIdentificador());
	}

	public void asociarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws ISicresRPAdminException {
		ISicresRPAdminTipoAsuntoManager.asociarDocumentoTipoAsunto(documento, entidad.getIdentificador());
	}

	public void asociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws ISicresRPAdminException {
		ISicresRPAdminTipoAsuntoManager.asociarOficinaTipoAsunto(oficina, entidad.getIdentificador());

	}

	public void desasociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, Entidad entidad)
			throws ISicresRPAdminException {

		ISicresRPAdminTipoAsuntoManager.desasociarDocumentoTipoAsunto(documento, entidad.getIdentificador());
	}

	public void desasociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws ISicresRPAdminException {
		ISicresRPAdminTipoAsuntoManager.desasociarOficinaTipoAsunto(oficina, entidad.getIdentificador());

	}

	public void editarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws ISicresRPAdminException {
		ISicresRPAdminTipoAsuntoManager.editarDocumentoTipoAsunto(documento, entidad.getIdentificador());
	}

	public void actualizarNumeracionOficinaAsociadaALibro(int idBook, int idOfic, Entidad entidad)
		throws ISicresRPAdminException {
		try {
			LibroBean libro = obtenerLibroBean(idBook, entidad);
			ISicresRPAdminLibroManager.actualizarNumeracionOficinaAsociadaALibro(idBook, idOfic,
					libro.getNumeracion(), entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al actualizar numeracion de oficinas asociada a libro", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,	e);
		}
	}

	public void importarDepartamentos(int deptId, boolean isSelected, String idUnidad, Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminOrganizacionManager.importarDepartamentos(deptId, isSelected, idUnidad, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al importar departamentos de la estructura organizativa", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,e);
		}
	}

	public void importarGruposLdap(String nodeDn, int maxChildrenLdap, int treeType, boolean isSelected, String idUnidad, Entidad entidad) throws ISicresRPAdminException {
		try {
			ISicresRPAdminOrganizacionManager.importarGruposLdap(nodeDn, maxChildrenLdap, treeType, isSelected, idUnidad, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al importar grupos LDAP", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,e);
		}
	}

	public Oficinas obtenerOficinasUsuarioLdap(String ldapguid, Entidad entidad) throws ISicresRPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOrganizacionManager.
											getOficinasUsuarioLdap(ldapguid, entidad.getIdentificador());
			oficinas = getOficinasLdapDAO(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas asociadas a usuario LDAP", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	public Oficinas obtenerOficinasDesasociadasAUsuarioLdap(String ldapguid, Entidad entidad) throws ISicresRPAdminException {
		Oficinas oficinas = new Oficinas();
		try {
			SicresOficinasImpl oficinasDAO = ISicresRPAdminOficinaManager.
											getOficinasDesasociadasAUsuarioLdap(ldapguid, entidad.getIdentificador());
			oficinas = getAPIOficinas(oficinasDAO);
		} catch (Exception e) {
			logger.error("Error obteniendo posibles oficinas que se pueden asociar a un usuario LDAP", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION, e);
		}
		return oficinas;
	}

	/**
	 * INFORMES
	 */

	public InformesBean obtenerInformes(Entidad entidad)
			throws ISicresRPAdminException {
		return ISicresRPAdminInformeManager.obtenerInformes(entidad.getIdentificador());
	}

	public InformeBean obtenerInforme(int id, Entidad entidad, OptionsBean perfiles)
	throws ISicresRPAdminException {
		try {
			return ISicresRPAdminInformeManager.obtenerInforme(id, entidad.getIdentificador(), perfiles);
		} catch (ISicresRPAdminDAOException rPAE){
			throw rPAE;
		} catch (Exception e) {
			logger.error(e);
			throw new ISicresRPAdminException(e);
		}
	}

	public void editarInforme(InformeBean informe, Entidad entidad)
			throws ISicresRPAdminException {
		// TODO Auto-generated method stub
		ISicresRPAdminInformeManager.editarInforme(informe, entidad.getIdentificador());
	}

	public int crearInforme(InformeBean informeBean, Entidad entidad)
			throws ISicresRPAdminException {
		ISicresRPAdminInformeManager.crearInforme(informeBean, entidad.getIdentificador());
		return informeBean.getId();
	}

	public InformeBean descargarInforme(int id, Entidad entidad)
			throws ISicresRPAdminException {
		try {
			return ISicresRPAdminInformeManager.descargarInforme(id, entidad.getIdentificador());
		} catch (Exception e) {
			throw new ISicresRPAdminException(e);
		}
	}

	public void eliminarInforme(int id, Entidad entidad)
			throws ISicresRPAdminException {
		ISicresRPAdminInformeManager.eliminarInforme(id, entidad.getIdentificador());

	}
	public String obtenerDireccionLogado()  throws ISicresRPAdminException {
		return GestionURLsAdministracion.getUrlLogin();
	}
	public String obtenerDireccionDeslogado()  throws ISicresRPAdminException{
		return GestionURLsAdministracion.getUrlLogout();
	}

	public List obtenerEntidadesRegistrales(Entidad entidad)
			throws ISicresAdminIntercambioRegistralException {

			List listaEntidadesRegistrales = null;

			IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

			listaEntidadesRegistrales = intercambioRegistralManager.getEntidadesRegistrales();

			return listaEntidadesRegistrales;

	}

//	public List obtenerUnidadesRegistralesHijas(int entidadRegistralId,
//			Entidad entidad) throws ISicresAdminIntercambioRegistralException {
//
//
//			IntercambioRegistralManager intercambioRegistralManager = (IntercambioRegistralManager)AppContext.getApplicationContext().getBean("intercambioRegistralManager");
//			List listaUnidadesRegistrales = null;
//			listaUnidadesRegistrales = intercambioRegistralManager.getUnidadesRegistralesHijas(entidadRegistralId);
//			return listaUnidadesRegistrales;
//
//	}

	public int crearEntidadRegistral(EntidadRegistralVO entidadRegistral,
			Entidad entidad) throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		entidadRegistral = intercambioRegistralManager.addEntidadRegistral(entidadRegistral);

		return entidadRegistral.getId();

	}

	public int crearUnidadRegistral(UnidadRegistralVO unidad, Entidad entidad)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		unidad = intercambioRegistralManager.addUnidadRegistral(unidad);
		return unidad.getId();

	}

	public void eliminarEntidadRegistral(int id, Entidad entidad)
			throws ISicresAdminIntercambioRegistralException {

		EntidadRegistralVO entidadRegistral = new EntidadRegistralVO();
		entidadRegistral.setId(id);

		IntercambioRegistralManager intercambioRegistralManager =AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();
		intercambioRegistralManager.deleteEntidadRegistral(entidadRegistral);

	}

	public void eliminarUnidadRegistral(int id, Entidad entidad)
			throws ISicresAdminIntercambioRegistralException {

		UnidadRegistralVO unidadRegistral = new UnidadRegistralVO();
		unidadRegistral.setId(id);

		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		intercambioRegistralManager.deleteUnidadRegistral(unidadRegistral);

	}

	public EntidadRegistralVO getEntidadRegistral(int id)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager =AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		EntidadRegistralVO entidadRegistral = intercambioRegistralManager
				.getEntidadRegistral(id);

		return entidadRegistral;

	}

	public UnidadRegistralVO getUnidadRegistral(int id)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager =AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		UnidadRegistralVO unidadRegistral = intercambioRegistralManager
				.getUnidadRegistral(id);

		return unidadRegistral;

	}

	public EntidadRegistralVO updateEntidadRegistral(
			EntidadRegistralVO entidadRegistral)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		EntidadRegistralVO entidadRegistralResult = intercambioRegistralManager
				.updateEntidadRegistral(entidadRegistral);

		return entidadRegistralResult;

	}

	public UnidadRegistralVO updateUnidadRegistral(
			UnidadRegistralVO unidadRegistral)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		UnidadRegistralVO unidadRegistralResult = intercambioRegistralManager
				.updateUnidadRegistral(unidadRegistral);

		return unidadRegistralResult;

	}

	public List<DatosBasicosOficinaDCVO> findOficinasDirectorioComun(
			Criterios<CriterioOficinaEnum> criteriosBusqueda)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager =AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		List<DatosBasicosOficinaDCVO> oficinas = intercambioRegistralManager
				.findOficinasDirectorioComun(criteriosBusqueda);

		return oficinas;

	}

	public List<DatosBasicosUnidadOrganicaDCVO> findUnidadesOrganicasDirectorioComun(
			Criterios<CriterioUnidadOrganicaEnum> criteriosBusqueda)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		List<DatosBasicosUnidadOrganicaDCVO> unidadesOrganicas = intercambioRegistralManager
				.findUnidadesOrganicasDirectorioComun(criteriosBusqueda);
		return unidadesOrganicas;

	}

	public List<DatosBasicosUnidadOrganicaDCVO> findUnidadesOrganicasDirectorioComunByCodEntidad(
			String codEntidad, String codUnidad, String nombreUnidad)
			throws ISicresAdminIntercambioRegistralException {

		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		List<DatosBasicosUnidadOrganicaDCVO> unidadesOrganicas = intercambioRegistralManager
				.findUnidadesOrganicasDirectorioComunByCodEntidad(codEntidad, codUnidad, nombreUnidad);
		return unidadesOrganicas;

	}

	/**
	 * Método que invoca a la actualizacion del DCO
	 */
	public void actualizarDCO() {
		GestionDCOManager gestionDCOManager = AdminIRManagerProvider.getInstance().getGestionDCOManager();

		gestionDCOManager.actualizarDCO();

	}
	/**
	 * Método que invoca a la inicializacion del DCO
	 */
	public void inicializarDCO() {
		GestionDCOManager gestionDCOManager = AdminIRManagerProvider.getInstance().getGestionDCOManager();

		gestionDCOManager.inicializarDCO();

	}


	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin#getLdapConfig(java.lang.String)
	 */
	public CfgLdapConfig getLdapConfig(String entidad)
			throws ISicresRPAdminException {


		try {
			return ISicresRPAdminConfigLdapManager.getLdapConfig(entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo la configuración de Ldap", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}


	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin#updateLdapConfig(java.lang.String, es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig)
	 */
	public void updateLdapConfig(String entidad, CfgLdapConfig ldapConfig)
			throws ISicresRPAdminException {
		try {
			ISicresRPAdminConfigLdapManager.updateLdapConfig(entidad, ldapConfig);
		} catch (Exception e) {
			logger.error("Error al actualizar la configuración de Ldap", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin#testLdapConfig(es.ieci.tecdoc.isicres.admin.sbo.config.CfgLdapConfig)
	 */

	public boolean testLdapConfig(CfgLdapConfig ldapConfig)
			throws ISicresRPAdminException {
		try {
			return ISicresRPAdminConfigLdapManager.testLdapConfig(ldapConfig);
		} catch (Exception e) {
			logger.error("Error al realizar el test de conexión de LDAP", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.EXC_GENERIC_EXCEPCION,
					e);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin#actualizarLibroASicres3(int, es.ieci.tecdoc.isicres.admin.beans.Entidad)
	 */
	public void actualizarLibroASicres3(int idLibro, Entidad entidad) throws ISicresRPAdminException {

		try {
			ISicresRPAdminLibroManager.actualizarLibroASicres3(idLibro, entidad.getIdentificador());
		} catch (Exception e) {
			logger.error("Error al actualizar el libro a SICRES3", e);
			throw new ISicresRPAdminException(ISicresRPAdminException.ERROR_UPDATE_LIBROS,
					e);
		}
	}
}