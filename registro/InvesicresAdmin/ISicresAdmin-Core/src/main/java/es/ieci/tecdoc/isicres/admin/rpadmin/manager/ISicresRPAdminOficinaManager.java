package es.ieci.tecdoc.isicres.admin.rpadmin.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.business.exception.ISicresAdminIntercambioRegistralException;
import es.ieci.tecdoc.isicres.admin.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.admin.business.spring.AppContext;
import es.ieci.tecdoc.isicres.admin.business.spring.AdminIRManagerProvider;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserDeptHdrImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.IUserObjPermImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaLocalizacionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinasImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresTiposOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresUsuarioConfigImpl;
import es.ieci.tecdoc.isicres.admin.core.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.core.database.IUserDeptHdrDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IUserLdapUserHdrDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IUserObjPermDatos;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresLibroOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOficinaLocalizacionDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresTipoOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUsuarioConfigDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresUsuarioOficinaDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapScope;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.core.types.IeciTdType;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresAdminEstructuraAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos;
import es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapGroup;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminDefsKeys;
import es.ieci.tecdoc.isicres.admin.estructura.manager.ISicresAdminEstructuraLdapManager;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasAuthConfig;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtil;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;
import es.ieci.tecdoc.isicres.admin.service.ISicresAdminEstructuraService;
import es.ieci.tecdoc.isicres.admin.database.LdapUsersTable;
import es.ieci.tecdoc.isicres.admin.sbo.util.nextid.NextId;


public class ISicresRPAdminOficinaManager {
	public static final Logger logger = Logger
			.getLogger(ISicresRPAdminOficinaManager.class);

	public static SicresOficinasImpl obtenerOficinasListado(String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresOficinasImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = SicresOficinaDatos.getOficinas(db);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
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
		return oficinas;
	}

	public static SicresOficinasImpl obtenerOficinas(String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresOficinasImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = SicresOficinaDatos.getOficinas(db);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
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
		return oficinas;
	}

	public static void asociarUsuariosAOficina(String[] idsUser, int idOfic,
			String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			for (int i = 0; i < idsUser.length; i++) {
				int idUser = Integer.parseInt(idsUser[i]);
				// SicresOficinaImpl oficina = getOficinaById(idOfic, entidad);
				SicresUsuarioOficinaDatos relacion = new SicresUsuarioOficinaDatos();
				relacion.setId(IdsGenerator.generateNextId(
						IdsGenerator.SCR_USEROFIC, entidad));
				relacion.setIdUser(idUser);
				relacion.setIdOfic(idOfic);
				relacion.add(db);

				// Comprobar si el usuario tiene oficina prferente
				SicresUsuarioConfigImpl usuarioConfig = ISicresRPAdminUserManager
						.getSicresUsuarioConfigImplByUserId(idUser, entidad);

				// Si el usuario no tiene oficina preferente, asociarle esta
				// como preferente
				if (usuarioConfig == null
						|| usuarioConfig.getIdOficPref() == 0
						|| usuarioConfig.getIdOficPref() == IeciTdType.NULL_LONG_INTEGER)
					ISicresRPAdminUserManager.asociarOficinaPreferenteAUsuario(
							idUser, idOfic, entidad);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando la oficina");
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

	public static void crearOficina(SicresOficinaImpl oficina,
			SicresOficinaLocalizacionImpl localizacion, EntidadRegistralVO entidadRegistral, String entidad)
			throws ISicresRPAdminDAOException, ISicresAdminIntercambioRegistralException {

		DbConnection db = new DbConnection();

		try {
			oficina.setId(IdsGenerator.generateNextId(IdsGenerator.SCR_OFIC,
					entidad));

			// El departamento pasa a ser una oficina.
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();

			// Si es un departamento LDAP crearlo
			GrupoLdap grupoLdap = null;
			if (oficina.getTypeDept() == Departamento.LDAP_DEPT_TYPE) {
				try {
					grupoLdap = oServicio.getGrupoLdap(oficina.getLdapGuid(),
							entidad);
				} catch (Exception e) {
				}
				if (grupoLdap == null)
					grupoLdap = oServicio.crearGrupoLdap(oficina.getLdapGuid(),
							oficina.getLdapDn(),
							GrupoLdap.IUSER_GROUP_TYPE_SICRES, entidad);
			}

			db.open(DBSessionManager.getSession());
			db.beginTransaction();

			if (oficina.getTypeDept() == Departamento.LDAP_DEPT_TYPE) {

				// Insertar un nuevo nuevo dept.
				IUserDeptHdrImpl iUserDeptHdrImpl = new IUserDeptHdrImpl();

				//Obtenemos el id del siguiente departamento de la tabla IUSERNEXTID (TYPE = 2)
				iUserDeptHdrImpl.setId(NextId.generateNextId(LdapUsersTable.TN_NEXTID,
                        ISicresAdminDefsKeys.DESTINATION_DEPT, entidad));

				iUserDeptHdrImpl.setName(oficina.getName());
				iUserDeptHdrImpl.setType(Departamento.LDAP_DEPT_TYPE);
				iUserDeptHdrImpl.setCrtrid(grupoLdap.get_id());
				Date actualDate = new Date();
				iUserDeptHdrImpl.setCrtndate(actualDate);
				iUserDeptHdrImpl.setUpdrid(0);
				iUserDeptHdrImpl.setUpddate(actualDate);
				iUserDeptHdrImpl.setParentid(0);
				iUserDeptHdrImpl.setMgrid(0);
				new IUserDeptHdrDatos(iUserDeptHdrImpl).add(db);

				// Guardar en la oficina el id del departamento
				oficina.setDeptId(iUserDeptHdrImpl.getId());

			} else {

				// Cambiar el tipo del existente
				IUserDeptHdrImpl iUserDeptHdrImpl = new IUserDeptHdrImpl();
				iUserDeptHdrImpl.setId(oficina.getDeptId());
				new IUserDeptHdrDatos(iUserDeptHdrImpl).update(db);
			}

			// oServicio.editarDepartamento(departamento, entidad);

			new SicresOficinaDatos(oficina).add(db);
			if (localizacion != null) {
				localizacion.setIdOfic(oficina.getId());
				new SicresOficinaLocalizacionDatos(localizacion).add(db);
			}

			db.endTransaction(true);

			//Intercambio registral
			if (entidadRegistral != null) {
				// añadimos el codigo de la oficina a la entidad
				entidadRegistral.setIdOfic(oficina.getId());
				// creamos la Entidad Registral asociada a la oficina
				IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

				entidadRegistral = intercambioRegistralManager
						.addEntidadRegistral(entidadRegistral);
			}

		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}

			logger.error("Error creando la oficina");

			if(e instanceof ISicresAdminIntercambioRegistralException){
				throw new ISicresAdminIntercambioRegistralException(e);
			}
			else{
				throw new ISicresRPAdminDAOException(
						ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			}
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

	public static void editarOficina(SicresOficinaImpl oficina,
			SicresOficinaLocalizacionImpl localizacion, EntidadRegistralVO entidadRegistral, String entidad)
			throws ISicresRPAdminDAOException, ISicresAdminIntercambioRegistralException {

		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());

			// ya no es necesario Obtener el id real, no el del departamento.
			// SicresOficinaDatos oficinaBBDD = new SicresOficinaDatos();
			// oficinaBBDD.load(oficina.getDeptId(), db);
			// oficina.setId(oficinaBBDD.getId());

			// Posible cambio de nombre de departamento => Eliminado porque no
			// tiene por qué hacerse esta actualización

			db.beginTransaction();

			new SicresOficinaDatos(oficina).update(db);
			// Si viene nulo, borrar fila, si no viene nulo y la fila existe
			// acutalizar, si no existe añadir.
			SicresOficinaLocalizacionDatos localizacionDAO = new SicresOficinaLocalizacionDatos();
			if (localizacion == null) {
				localizacionDAO.setIdOfic(oficina.getId());
				localizacionDAO.delete(db);
			} else {
				localizacion.setIdOfic(oficina.getId());
				try {
					localizacionDAO.load(oficina.getId(), db);
					new SicresOficinaLocalizacionDatos(localizacion).update(db);
				} catch (ISicresAdminDAOException e) {
					new SicresOficinaLocalizacionDatos(localizacion).add(db);
				}
			}
			db.endTransaction(true);


			if(entidadRegistral != null){
				//realizamos la operativa necesaria con la entidad registral
				operativaEntidadRegistralEditarOficina(oficina,
						entidadRegistral);
			}

		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando la oficina");
			if(e instanceof ISicresAdminIntercambioRegistralException){
				throw new ISicresAdminIntercambioRegistralException(e);
			}
			else{
				throw new ISicresRPAdminDAOException(
						ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
			}
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
	 * Método que realiza la operativa necesaria: Crear, Actualizar o Borrar la
	 * entidad registral según los datos recibidos
	 *
	 * @param oficina
	 *            - Datos de la oficina
	 * @param entidadRegistral
	 *            - Datos de la entidad registral
	 *
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	private static void operativaEntidadRegistralEditarOficina(
			SicresOficinaImpl oficina, EntidadRegistralVO entidadRegistral)
			throws ISicresAdminIntercambioRegistralException {
		//Intercambio Registral
		IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();

		if(entidadRegistral.getId() != 0){
			//obtenemos los datos almacenados de la Entidad Registral
			EntidadRegistralVO entidadRegistralAux = intercambioRegistralManager.getEntidadRegistralByIdOficina(oficina.getId());
			// comprobamos si los datos de la entidad Registral que recibimos
			// como parametro son nulos
			if (!entidadRegistral.isBlankEntidadRegistral()) {
				//modificamos los datos de la Entidad Registral
				entidadRegistralAux.setCode(entidadRegistral.getCode());
				entidadRegistralAux.setName(entidadRegistral.getName());
				intercambioRegistralManager.updateEntidadRegistral(entidadRegistralAux);
			}else{
				//borramos la Entidad Registral ya que los datos de codigo y nombre son vacios
				intercambioRegistralManager.deleteEntidadRegistral(entidadRegistralAux);
			}
		}else{
			//creamos entidad Registral
			intercambioRegistralManager.addEntidadRegistral(entidadRegistral);
		}
	}

	public static void eliminarOficina(int id, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			SicresOficinaDatos oficina = new SicresOficinaDatos();
			oficina.load(id, db);

			// El departamento deja de ser una oficina.
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			Departamento departamento = oServicio.getDepartamento(oficina
					.getDeptId(), entidad);
			departamento.set_type(Departamento.INVESDOC_DEPT_TYPE);
			oServicio.editarDepartamento(departamento, entidad);

			db.beginTransaction();
			// Localizacion
			SicresOficinaLocalizacionDatos localizacion = new SicresOficinaLocalizacionDatos();
			localizacion.setIdOfic(oficina.getId());
			localizacion.delete(db);

			//Intercambio Registral
			IntercambioRegistralManager intercambioRegistralManager =AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();
			//buscamos la Entidad Registral asociada a la oficina
			EntidadRegistralVO entidadRegistral = intercambioRegistralManager
					.getEntidadRegistralByIdOficina(oficina.getId());

			if(entidadRegistral != null){
				//borramos la relacion de la Entidad Registral
				intercambioRegistralManager.deleteEntidadRegistral(entidadRegistral);
			}

			// RelacionesOficinaLibro
			SicresLibroOficinaDatos.deleteOficina(oficina.getId(), db);

			// Permisos
			IUserObjPermDatos.deleteDestino(IUserObjPermImpl.TIPO_DEPARTAMENTO,
					oficina.getDeptId(), db);

			// Usuarios agregados
			SicresUsuarioOficinaDatos.deleteOficina(oficina.getId(), db);

			// Desasociar Oficina preferente de los usuarios de dicha oficina a
			// eliminar
			updateOficPrefUsuario(oficina.getDeptId(), entidad);

			oficina.delete(db);

			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando la oficina");
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

	public static void eliminarOficinaLDAP(int idOficina, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			SicresOficinaDatos oficina = new SicresOficinaDatos();
			oficina.load(idOficina, db);

			db.beginTransaction();
			// Localizacion
			SicresOficinaLocalizacionDatos localizacion = new SicresOficinaLocalizacionDatos();
			localizacion.setIdOfic(oficina.getId());
			localizacion.delete(db);

			//Intercambio Registral
			IntercambioRegistralManager intercambioRegistralManager = AdminIRManagerProvider.getInstance().getIntercambioRegistralManager();
			//buscamos la Entidad Registral asociada a la oficina
			EntidadRegistralVO entidadRegistral = intercambioRegistralManager
					.getEntidadRegistralByIdOficina(oficina.getId());

			if(entidadRegistral != null){
				//borramos la relacion de la Entidad Registral
				intercambioRegistralManager.deleteEntidadRegistral(entidadRegistral);
			}

			// RelacionesOficinaLibro
			SicresLibroOficinaDatos.deleteOficina(oficina.getId(), db);

			// Permisos
			IUserObjPermDatos.deleteDestino(IUserObjPermImpl.TIPO_DEPARTAMENTO,
					oficina.getDeptId(), db);

			// Usuarios agregados
			SicresUsuarioOficinaDatos.deleteOficina(oficina.getId(), db);

			// Desasociar Oficina preferente de los usuarios de dicha oficina a
			// eliminar
			updateOficPrefUsuario(oficina.getDeptId(), entidad);

			int idDept = oficina.getDeptId();

			oficina.delete(db);

			// Borramos el departamento
			IUserDeptHdrDatos iuserDeptHdrDatos = new IUserDeptHdrDatos();
			iuserDeptHdrDatos.deleteDepartamentoById(db, idDept);

			// Borramos el grupo ldap de BBDD y sus permisos
			ISicresAdminEstructuraLdapManager manager = new ISicresAdminEstructuraLdapManager();
			LdapGroup ldapgroup = manager.getLdapGroupByDeptId(idDept, entidad);
			ldapgroup.delete(entidad);

			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando la oficina");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		} finally {
			try {
				if (db != null && db.existConnection()) {
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
	}

	public static SicresTiposOficinaImpl obtenerTiposOficina(String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresTiposOficinaImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = SicresTipoOficinaDatos.getTiposOficina(db);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
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
		return oficinas;
	}

	@Deprecated
	public static Departamentos obtenerDepartamentos(boolean oficinas,
			String entidad) throws ISicresRPAdminDAOException {
		Departamentos departamentos = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			departamentos = oServicio.getDepartamentos(entidad);

			// Si no se quieren las oficinas se quitan de la lista
			if (!oficinas) {
				for (int i = departamentos.getDepartamentosLista().count() - 1; i >= 0; i--) {
					Departamento departamento = departamentos
							.getDepartamentosLista().get(i);
					if (departamento.get_type() == Departamento.SICRES_DEPT_TYPE) {
						departamentos.getDepartamentosLista().remove(i);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error obteniendo departamentos");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return departamentos;
	}

	/**
	 * Obtiene el listado de departamentos
	 *
	 * @param entidad
	 * @return Listado de departamentos {@link Departamentos}
	 * @throws ISicresRPAdminDAOException
	 */
	public static Departamentos obtenerDepartamentos(
			String entidad) throws ISicresRPAdminDAOException {
		Departamentos departamentos = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			departamentos = oServicio.getDepartamentos(entidad);

		} catch (Exception e) {
			logger.error("Error obteniendo departamentos");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return departamentos;
	}



	public static Departamentos obtenerDepartamentosHijos(int parentId,
			String entidad) throws ISicresRPAdminDAOException {
		Departamentos departamentos = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			departamentos = oServicio.getDepartamentos(parentId, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo departamentos");
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return departamentos;
	}

	public static SicresOficinaImpl getOficinaByDeptId(int id, String entidad)
			throws Exception {
		DbConnection db = new DbConnection();
		SicresOficinaDatos oficina = new SicresOficinaDatos();
		try {
			db.open(DBSessionManager.getSession());
			oficina.loadDeptId(id, db);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return oficina;
	}

	public static SicresOficinaImpl getOficinaByLdapGroup(String ldapGuid,
			String entidad) throws Exception {
		DbConnection db = new DbConnection();
		SicresOficinaDatos oficina = new SicresOficinaDatos();
		try {
			db.open(DBSessionManager.getSession());
			oficina.loadDeptLdap(ldapGuid, db);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return oficina;
	}

	public static SicresOficinasImpl getOficinasByDeptsId(int ids[],
			String entidad) throws Exception {
		DbConnection db = new DbConnection();
		SicresOficinaDatos ofic = new SicresOficinaDatos();
		SicresOficinasImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = ofic.loadDeptsId(ids, db);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
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
		return oficinas;
	}

	public static SicresOficinaImpl getOficinaById(int id, String entidad)
			throws Exception {
		DbConnection db = new DbConnection();
		SicresOficinaDatos oficina = new SicresOficinaDatos();
		try {
			db.open(DBSessionManager.getSession());
			oficina.load(id, db);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return oficina;
	}

	public static SicresOficinaImpl getOficinaByCode(String codigo,
			String entidad) throws Exception {
		DbConnection db = new DbConnection();
		SicresOficinaDatos oficina = new SicresOficinaDatos();
		try {
			db.open(DBSessionManager.getSession());
			oficina.load(codigo, db);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return oficina;
	}

	public static Departamento getDepartamento(int id, String entidad)
			throws Exception {
		Departamento departamento = null;
		try {
			ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
			departamento = oServicio.getDepartamento(id, entidad);
		} catch (Exception e) {
			logger.error("Error obteniendo un departamento", e);
			throw new ISicresRPAdminDAOException(
					ISicresRPAdminDAOException.EXC_GENERIC_EXCEPCION, e);
		}
		return departamento;
	}

	public static SicresOficinaLocalizacionImpl getLocalizacion(int id,
			String entidad) {
		DbConnection db = new DbConnection();
		SicresOficinaLocalizacionDatos localizacion = new SicresOficinaLocalizacionDatos();
		try {
			db.open(DBSessionManager.getSession());
			localizacion.load(id, db);
		} catch (Exception e) {
			logger
					.error("No se ha podido recuperar la localizacion de la oficina");
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
		return localizacion;
	}

	public static SicresOficinasImpl obtenerOficinasDesasociadasALibro(
			int bookId, String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresOficinasImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = SicresOficinaDatos.getOficinasDesasociadasALibro(bookId,
					db);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
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
		return oficinas;
	}

	public static SicresOficinasImpl obtenerOficinasDesagregadasAUsuario(
			int idUser, String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresOficinasImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = SicresOficinaDatos.getOficinasDesagregadasAUsuario(
					idUser, db);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
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
		return oficinas;
	}

	public static SicresOficinasImpl getOficinasDesasociadasAUsuarioLdap(
			String ldapguid, String entidad) throws ISicresRPAdminDAOException {
		SicresOficinasImpl oficinas = null;
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();

			LdapConnection ldapConn = new LdapConnection();

			// Obtener la información del servidor
			LdapConnCfg connCfg = UasConfigUtilLdap
					.createLdapConnConfig(entidad);
			ldapConn.open(connCfg);
			UasAuthConfig authCfg = UasConfigUtil.createUasAuthConfig(entidad);

			// Obtenemos el DN del usuario a partir de su ldapguid
			String userDn = LdapUasFns.findUserDnByGuid(ldapConn, authCfg
					.getUserStart(), LdapScope.SUBTREE, ldapguid, entidad);
			if(userDn != null){
				// Obtenemos los grupos a los que pertenece el usuario LDAP (por el
				// DN)
				IeciTdShortTextArrayList listaGuids = LdapUasFns
						.findUserGroupGuids(ldapConn, authCfg.getGroupStart(),
								LdapScope.SUBTREE, userDn);

				IUserLdapUserHdrDatos userLdap = new IUserLdapUserHdrDatos();
				userLdap.getUserLdapByGuid(ldapguid, db);

				// Se obtienen los grupos cuyo guid coincide con alguno de la lista
				// de GUIDS (los que esten dados de alta en la aplicacion)
				ISicresAdminEstructuraService oServicio = new ISicresAdminEstructuraAdapter();
				List groupsValids = new ArrayList();
				for (int i = 0; i < listaGuids.count(); i++) {
					try {
						GrupoLdap grupoLdap = oServicio.getGrupoLdap(listaGuids
								.get(i), entidad);
						groupsValids.add(new Integer(grupoLdap.get_id()));
					} catch (Exception f) {
						// Si salta la excepcion es que el grupo no esta dado de
						// alta en la aplicación
					}
				}
				int[] idsGroupValid = new int[groupsValids.size()];
				idsGroupValid = ArrayUtils.toPrimitive((Integer[]) groupsValids
						.toArray(new Integer[groupsValids.size()]));

				oficinas = SicresOficinaDatos
						.getOficinasDesasociadasUsuarioLdap(userLdap.getId(),
								idsGroupValid, db);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			logger
					.error("Error obteniendo posibles oficinas para asociar a un usuario LDAP.");
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
		return oficinas;
	}

	public static SicresOficinasImpl obtenerOficinasAgregadasAUsuario(
			int idUser, String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresOficinasImpl oficinas = null;
		try {
			db.open(DBSessionManager.getSession());
			oficinas = SicresOficinaDatos.getOficinasAgregadasAUsuario(idUser,
					db);
		} catch (Exception e) {
			logger.error("Error obteniendo oficinas");
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
		return oficinas;
	}

	/**
	 * Actualizando oficina preferente asignada a los usuarios asociados a la
	 * oficina pasada como parametro.
	 *
	 * @param idOfic
	 *            Identificador de la oficina
	 * @param entidad
	 *            Entidad
	 * @param db
	 *            Conexión a la Base De Datos
	 * @throws ISicresRPAdminDAOException
	 * @throws Exception
	 */
	public static void updateOficPrefUsuario(int idOfic, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			SicresUsuarioConfigImpl sicresUsuarioConfigImpl = new SicresUsuarioConfigImpl();
			sicresUsuarioConfigImpl.setIdOficPref(IeciTdType.NULL_LONG_INTEGER);
			new SicresUsuarioConfigDatos(sicresUsuarioConfigImpl)
					.updateByIdOficPref(db, idOfic);
		} catch (Exception e) {
			logger
					.error("Error actualizando oficina preferente de los usuarios asociados a la oficina.");
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

}
