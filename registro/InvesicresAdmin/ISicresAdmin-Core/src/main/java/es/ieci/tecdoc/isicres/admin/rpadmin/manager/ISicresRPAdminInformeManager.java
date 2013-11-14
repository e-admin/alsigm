package es.ieci.tecdoc.isicres.admin.rpadmin.manager;


import java.util.Iterator;
import java.util.ResourceBundle;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.beans.Estados;
import es.ieci.tecdoc.isicres.admin.beans.Informe;
import es.ieci.tecdoc.isicres.admin.beans.InformeBean;
import es.ieci.tecdoc.isicres.admin.beans.InformesBean;
import es.ieci.tecdoc.isicres.admin.beans.LibroInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.LibrosInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinasInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.PerfilInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.PerfilesInformeBean;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresLibroInformeImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaInformeImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresPerfilInformeImpl;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresInformeDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresLibroInformesDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOficinaInformesDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresPerfilInformesDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;

public class ISicresRPAdminInformeManager {
	public static final Logger logger = Logger
			.getLogger(ISicresRPAdminInformeManager.class);

	/**
	 * Listado de Informes
	 *
	 * @param entidad
	 * @return
	 * @throws RPAdminDAOException
	 */
	public static InformesBean obtenerInformes(String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();
		InformesBean informesBean = null;
		ResourceBundle rs = ResourceBundle
				.getBundle("es.ieci.tecdoc.isicres.admin.core.manager.tipoInforme");
		try {
			db.open(DBSessionManager.getSession());
			informesBean = SicresInformeDatos.getInformes(db);
			Iterator it = informesBean.getLista().iterator();
			Informe informe;
			// Recorre la lista de elementos para asociar el tipo de informe
			// segun el codigo
			while (it.hasNext()) {
				informe = (Informe) it.next();
				informe.setNameTypeReport(rs.getString(String.valueOf(informe
						.getTypeReport())));
			}
		} catch (Exception e) {
			logger.error("Error obteniendo informes");
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
		return informesBean;
	}

	/**
	 * Recupera un informe
	 *
	 * @param id
	 * @param entidad
	 * @return
	 * @throws ISicresAdminDAOException
	 * @throws Exception
	 */
	public static InformeBean obtenerInforme(int id, String entidad,
			OptionsBean listaPerfiles) throws Exception {
		DbConnection db = new DbConnection();

		InformeBean informeBean = new InformeBean();

		try {
			db.open(DBSessionManager.getSession());
			SicresInformeDatos informeDatos = new SicresInformeDatos();
			informeDatos.load(id, db);

			// Establecer los datos del tipo de Asunto
			BeanUtils.copyProperties(informeBean, informeDatos);

			try{
				SicresOficinaInformesDatos oficnasInformeDAO = new SicresOficinaInformesDatos();
				OficinasInformeBean oficinas = oficnasInformeDAO
						.getOficinasByIdReport(id, db);

				informeBean.setOficinas(oficinas);
			} catch (ISicresAdminDAOException e) {
				logger.debug("Error al obtener oficinas:" + e);
				throw new ISicresRPAdminDAOException(e.getErrorCode());
			}

			try{
				SicresPerfilInformesDatos perfilInformesDAO = new SicresPerfilInformesDatos();
				PerfilesInformeBean perfiles = perfilInformesDAO
						.getPerfilesByIdReport(id, db, listaPerfiles);
				informeBean.setPerfiles(perfiles);
			} catch (ISicresAdminDAOException e) {
				logger.debug("Error al obtener perfiles:" + e);
				throw new ISicresRPAdminDAOException(e.getErrorCode());
			}

			try{
				SicresLibroInformesDatos libroInformesDAO = new SicresLibroInformesDatos();
				LibrosInformeBean librosInformeBean = libroInformesDAO
						.getLibrosByIdReport(id, db);
				informeBean.setLibros(librosInformeBean);
			} catch (ISicresAdminDAOException e) {
				logger.debug("Error al obtener libros:" + e);
				throw new ISicresRPAdminDAOException(e.getErrorCode());
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
		return informeBean;
	}

	/**
	 * Recupera un informe
	 *
	 * @param id
	 * @param entidad
	 * @return
	 * @throws ISicresRPAdminDAOException
	 * @throws Exception
	 */
	public static InformeBean descargarInforme(int id, String entidad)
			throws ISicresRPAdminDAOException, Exception {
		DbConnection db = new DbConnection();

		InformeBean informeBean = new InformeBean();

		try {
			db.open(DBSessionManager.getSession());
			SicresInformeDatos informeDatos = new SicresInformeDatos();
			informeDatos.download(id, db);

			// Establecer los datos del Informe
			BeanUtils.copyProperties(informeBean, informeDatos);

		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return informeBean;
	}

	public static void editarInforme(InformeBean informeBean, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			new SicresInformeDatos(informeBean).update(db);

			// Eliminar las Oficinas, Perfiles y Libros
			removeOficinas(db, informeBean.getOficinasEliminadas(), entidad);
			removePerfiles(db, informeBean.getPerfilesEliminados(), entidad);
			removeLibros(db, informeBean.getLibrosEliminados(), entidad);

			// Añadir las Oficinas, Perfiles y Libros
			addOficinas(db, informeBean.getId(), informeBean.getOficinas(),
					entidad);
			addPerfiles(db, informeBean.getId(), informeBean.getPerfiles(),
					entidad);
			addLibros(db, informeBean.getId(), informeBean.getLibros(), entidad);

			db.endTransaction(true);
		} catch (ISicresRPAdminDAOException rpaEx) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el Informe");
			throw rpaEx;
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el Informe");
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
	 *
	 * @param db
	 * @param oficinasInformesBean
	 * @param idEntidad
	 * @throws Exception
	 */
	public static void removeOficinas(DbConnection db,
			OficinasInformeBean oficinasInformesBean, String idEntidad)
			throws Exception {
		if (oficinasInformesBean != null
				&& oficinasInformesBean.getLista() != null) {
			for (Iterator iterator = oficinasInformesBean.getLista().iterator(); iterator
					.hasNext();) {
				OficinaInformeBean informeBean = (OficinaInformeBean) iterator
						.next();
				SicresOficinaInformeImpl informe = new SicresOficinaInformeImpl();
				informe.setId(informeBean.getId());
				new SicresOficinaInformesDatos(informe).delete(db);
			}
		}
	}

	/**
	 *
	 * @param db
	 * @param perfilesInformesBean
	 * @param idEntidad
	 * @throws Exception
	 */
	public static void removePerfiles(DbConnection db,
			PerfilesInformeBean perfilesInformesBean, String idEntidad)
			throws Exception {
		if (perfilesInformesBean != null
				&& perfilesInformesBean.getLista() != null) {
			for (Iterator iterator = perfilesInformesBean.getLista().iterator(); iterator
					.hasNext();) {
				PerfilInformeBean perfilBean = (PerfilInformeBean) iterator
						.next();
				SicresPerfilInformeImpl perfil = new SicresPerfilInformeImpl();
				perfil.setId(perfilBean.getId());
				new SicresPerfilInformesDatos(perfil).delete(db);
			}
		}
	}

	/**
	 *
	 * @param db
	 * @param librosInformesBean
	 * @param idEntidad
	 * @throws Exception
	 */
	public static void removeLibros(DbConnection db,
			LibrosInformeBean librosInformesBean, String idEntidad)
			throws Exception {
		if (librosInformesBean != null && librosInformesBean.getLista() != null) {
			for (Iterator iterator = librosInformesBean.getLista().iterator(); iterator
					.hasNext();) {
				LibroInformeBean libroBean = (LibroInformeBean) iterator.next();
				SicresLibroInformeImpl libro = new SicresLibroInformeImpl();
				libro.setId(libroBean.getId());
				new SicresLibroInformesDatos(libro).delete(db);
			}
		}
	}

	/**
	 *
	 * @param db
	 * @param idReport
	 * @param oficinasInformeBean
	 * @param idEntidad
	 * @throws Exception
	 */
	public static void addOficinas(DbConnection db, int idReport,
			OficinasInformeBean oficinasInformeBean, String idEntidad)
			throws Exception {
		if (oficinasInformeBean != null
				&& oficinasInformeBean.getLista() != null) {
			for (Iterator iterator = oficinasInformeBean.getLista().iterator(); iterator
					.hasNext();) {

				OficinaInformeBean informe = (OficinaInformeBean) iterator
						.next();

				SicresOficinaInformeImpl oficinaInforme = new SicresOficinaInformeImpl();

				oficinaInforme.setIdReport(idReport);
				oficinaInforme.setIdOfic(informe.getIdOficina());

				switch (informe.getEstado()) {
				case Estados.NUEVO:
					oficinaInforme.setId(IdsGenerator.generateNextId(
							IdsGenerator.SCR_REPORTOFIC, idEntidad));
					new SicresOficinaInformesDatos(oficinaInforme).add(db);
					break;
				case Estados.MODIFICADO:
					new SicresOficinaInformesDatos(oficinaInforme).update(db);
					break;
				case Estados.ELIMINADO:
					new SicresOficinaInformesDatos(oficinaInforme).delete(db);
					break;

				default:
					break;
				}

			}
		}
	}

	/**
	 *
	 * @param db
	 * @param idReport
	 * @param perfilesInformeBean
	 * @param idEntidad
	 * @throws Exception
	 */
	public static void addPerfiles(DbConnection db, int idReport,
			PerfilesInformeBean perfilesInformeBean, String idEntidad)
			throws Exception {
		if (perfilesInformeBean != null
				&& perfilesInformeBean.getLista() != null) {
			for (Iterator iterator = perfilesInformeBean.getLista().iterator(); iterator
					.hasNext();) {

				PerfilInformeBean perfil = (PerfilInformeBean) iterator.next();

				SicresPerfilInformeImpl perfilInforme = new SicresPerfilInformeImpl();

				perfilInforme.setIdReport(idReport);
				perfilInforme.setIdPerf(perfil.getIdPerfil());

				switch (perfil.getEstado()) {
				case Estados.NUEVO:
					perfilInforme.setId(IdsGenerator.generateNextId(
							IdsGenerator.SCR_REPORTPERF, idEntidad));
					new SicresPerfilInformesDatos(perfilInforme).add(db);
					break;
				case Estados.MODIFICADO:
					new SicresPerfilInformesDatos(perfilInforme).update(db);
					break;
				case Estados.ELIMINADO:
					new SicresPerfilInformesDatos(perfilInforme).delete(db);
					break;

				default:
					break;
				}

			}
		}
	}

	/**
	 *
	 * @param db
	 * @param idReport
	 * @param librosInformeBean
	 * @param idEntidad
	 * @throws Exception
	 */
	public static void addLibros(DbConnection db, int idReport,
			LibrosInformeBean librosInformeBean, String idEntidad)
			throws Exception {
		if (librosInformeBean != null && librosInformeBean.getLista() != null) {
			for (Iterator iterator = librosInformeBean.getLista().iterator(); iterator
					.hasNext();) {

				LibroInformeBean libro = (LibroInformeBean) iterator.next();

				SicresLibroInformeImpl libroInforme = new SicresLibroInformeImpl();

				libroInforme.setIdReport(idReport);
				libroInforme.setIdArch(Integer.parseInt(libro
						.getCodigoArchivo()));

				switch (libro.getEstado()) {
				case Estados.NUEVO:
					libroInforme.setId(IdsGenerator.generateNextId(
							IdsGenerator.SCR_REPORTARCH, idEntidad));
					new SicresLibroInformesDatos(libroInforme).add(db);
					break;
				case Estados.MODIFICADO:
					new SicresLibroInformesDatos(libroInforme).update(db);
					break;
				case Estados.ELIMINADO:
					new SicresLibroInformesDatos(libroInforme).delete(db);
					break;

				default:
					break;
				}

			}
		}
	}

	public static void crearInforme(InformeBean informeBean, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			Informe informe = (Informe) informeBean;

			informe.setId(IdsGenerator.generateNextId(IdsGenerator.SCR_REPORT,
					entidad));
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			new SicresInformeDatos(informe).add(db);

			// Añadir las Oficinas, Perfiles y Libros
			addOficinas(db, informeBean.getId(), informeBean.getOficinas(),
					entidad);
			addPerfiles(db, informeBean.getId(), informeBean.getPerfiles(),
					entidad);
			addLibros(db, informeBean.getId(), informeBean.getLibros(), entidad);

			db.endTransaction(true);
		} catch (ISicresRPAdminDAOException rpaEx) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando el tipoasunto");
			throw rpaEx;

		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando el informe");
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

	public static void eliminarInforme(int id, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			SicresInformeDatos informe = new SicresInformeDatos();
			informe.load(id, db);
			db.beginTransaction();

			eliminarOficinasByIdReport(informe.getId(), db);
			eliminarLibrosByIdReport(informe.getId(), db);
			eliminarPerfilesByIdReport(informe.getId(), db);

			informe.delete(db);
			db.endTransaction(true);
			if (logger.isDebugEnabled())
				logger.error("Informe eliminado");
		} catch (ISicresRPAdminDAOException rpaEx) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el Informe");
			throw rpaEx;
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el Informe");
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
	 * Elimina las Oficinas asociadas al Tipo de Asunto
	 * @param idMatter Identificador del tipo de Asunto
	 * @param db
	 * @throws Exception
	 */
	public static void eliminarOficinasByIdReport(int idReport, DbConnection db) throws Exception{
		SicresOficinaInformesDatos informeOficinaDatos = new SicresOficinaInformesDatos();
		informeOficinaDatos.setIdReport(idReport);
		informeOficinaDatos.deleteByIdReport(db);
	}

	/**
	 * Elimina las Oficinas asociadas al Tipo de Asunto
	 * @param idMatter Identificador del tipo de Asunto
	 * @param db
	 * @throws Exception
	 */
	public static void eliminarLibrosByIdReport(int idReport, DbConnection db) throws Exception{
		SicresLibroInformesDatos informeLibroDatos = new SicresLibroInformesDatos();
		informeLibroDatos.setIdReport(idReport);
		informeLibroDatos.deleteByIdReport(db);
	}

	/**
	 * Elimina las Oficinas asociadas al Tipo de Asunto
	 * @param idMatter Identificador del tipo de Asunto
	 * @param db
	 * @throws Exception
	 */
	public static void eliminarPerfilesByIdReport(int idReport, DbConnection db) throws Exception{
		SicresPerfilInformesDatos informePerfilDatos = new SicresPerfilInformesDatos();
		informePerfilDatos.setIdReport(idReport);
		informePerfilDatos.deleteByIdReport(db);
	}
}
