package es.ieci.tecdoc.isicres.admin.rpadmin.manager;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresListaDistribucionesImpl;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresListaDistribucionDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresAdminEstructuraAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo;
import es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.login.LoginManager;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.login.LoginMethod;
import es.ieci.tecdoc.isicres.admin.service.ISicresAdminEstructuraService;

/*$Id*/

public class ISicresRPAdminDistribucionManager {
	private static final Logger logger = Logger
			.getLogger(ISicresRPAdminDistribucionManager.class);

	public static SicresListaDistribucionesImpl obtenerDistribuciones(
			int idOrg, String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		SicresListaDistribucionesImpl lista = null;
		try {
			db.open(DBSessionManager.getSession());
			lista = SicresListaDistribucionDatos.getDistribucionesOrganizacion(
					idOrg, db);

			int loginMethod = LoginMethod.STANDARD;
			LoginManager loginManager = new LoginManager();
			loginMethod = loginManager.getLoginMethod(db);

			if (LoginMethod.STANDARD == loginMethod) {
				lista = obtenerDistribucionesStandar(entidad, lista);
			} else {
				lista = obtenerDistribucionesLdap(entidad, lista);
			}
		} catch (Exception e) {
			logger.error("Error obteniendo distribuciones");
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
		return lista;
	}

	public static void crearDistribuciones(SicresListaDistribucionesImpl lista,
			String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			for (int i = 0; i < lista.count(); i++) {
				SicresListaDistribucionImpl bean = lista.get(i);
				bean.setId(IdsGenerator.generateNextId(
						IdsGenerator.SCR_DISTLIST, entidad));
				new SicresListaDistribucionDatos(bean).add(db);
			}
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando distribuciones");
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

	public static void eliminarDistribucion(int id, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			SicresListaDistribucionDatos bean = new SicresListaDistribucionDatos();
			bean.setId(id);
			bean.delete(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando distribuciones");
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

	public static SicresListaDistribucionesImpl obtenerDistribucionesStandar(
			String entidad, SicresListaDistribucionesImpl lista)
			throws Exception {

		// Completar Nombres
		for (int i = 0; i < lista.count(); i++) {
			SicresListaDistribucionImpl bean = lista.get(i);
			if (bean.getTypeDest() == SicresListaDistribucionImpl.TIPO_USUARIO) {
				Usuario user = ISicresRPAdminUserManager.getUser(bean.getIdDest(),
						entidad);

				if(user!=null){
					bean.setName(user.get_name());
				}
				else{
					// borramos de la lista el elemento que esta incompleto
					lista.getLista().remove(i);
					// restamos la posicion del elemento borrado al indice
					i--;
				}

			} else if (bean.getTypeDest() == SicresListaDistribucionImpl.TIPO_DEPARTAMENTO) {
				Departamento dept = ISicresRPAdminOficinaManager.getDepartamento(bean
						.getIdDest(), entidad);
				bean.setName(dept.get_name());
			} else if (bean.getTypeDest() == SicresListaDistribucionImpl.TIPO_GRUPO) {
				Grupo group = ISicresRPAdminUserManager.getGrupo(bean.getIdDest(),
						entidad);
				bean.setName(group.get_name());
			}
		}

		return lista;
	}

	public static SicresListaDistribucionesImpl obtenerDistribucionesLdap(
			String entidad, SicresListaDistribucionesImpl lista)
			throws Exception {
			ISicresAdminEstructuraService servicioEstructuraOrganizativa = new ISicresAdminEstructuraAdapter();


		// Completar Nombres
		for (int i = 0; i < lista.count(); i++) {
			SicresListaDistribucionImpl bean = lista.get(i);
			if (bean.getTypeDest() == SicresListaDistribucionImpl.TIPO_USUARIO) {
				UsuarioLdap userLdap = ISicresRPAdminUserManager.getUserLdap(bean
						.getIdDest(), entidad);

				if(userLdap!=null){
					bean.setName(userLdap.get_ldapfullname());
				}
				else{
					// borramos de la lista el elemento que esta incompleto
					lista.getLista().remove(i);
					// restamos la posicion del elemento borrado al indice
					i--;
				}

			} else if (bean.getTypeDest() == SicresListaDistribucionImpl.TIPO_DEPARTAMENTO) {
				GrupoLdap groupLdap = servicioEstructuraOrganizativa
						.getGrupoLdapByDeptId(bean.getIdDest(), entidad);
				bean.setName(groupLdap.get_fullname());
			} else if (bean.getTypeDest() == SicresListaDistribucionImpl.TIPO_GRUPO) {
				GrupoLdap groupLdap = servicioEstructuraOrganizativa
						.getGrupoLdapById(bean.getIdDest(), entidad);
				bean.setName(groupLdap.get_fullname());
			}
		}

		return lista;
	}

}
