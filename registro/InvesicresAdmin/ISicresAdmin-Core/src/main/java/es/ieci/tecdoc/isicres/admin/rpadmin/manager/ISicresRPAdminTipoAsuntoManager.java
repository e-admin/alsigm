package es.ieci.tecdoc.isicres.admin.rpadmin.manager;

import java.util.Iterator;


import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;
import org.apache.commons.beanutils.BeanUtils;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.DocumentosTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Estados;
import es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinasTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.TipoAsunto;
import es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresDocumentoTipoAsuntoImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresInfoAuxiliarTipoAsuntoImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaTipoAsuntoImpl;
import es.ieci.tecdoc.isicres.admin.core.database.IdsGenerator;
import es.ieci.tecdoc.isicres.admin.core.database.SicresDocumentoTipoAsuntoDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresInfoAuxiliarTipoAsuntoDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOficinaTipoAsuntoDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresOrganizacionDatos;
import es.ieci.tecdoc.isicres.admin.core.database.SicresTipoAsuntoDatos;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminDAOException;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;

public class ISicresRPAdminTipoAsuntoManager {
	public static final Logger logger = Logger
			.getLogger(ISicresRPAdminTipoAsuntoManager.class);

	public static TiposAsuntoBean obtenerTiposAsunto(String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		TiposAsuntoBean tiposAsunto = null;
		try {
			db.open(DBSessionManager.getSession());
			tiposAsunto = SicresTipoAsuntoDatos.getTiposAsunto(db);
		} catch (Exception e) {
			logger.error("Error obteniendo tipos de Asunto");
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
		return tiposAsunto;
	}

	public static void crearTipoAsunto(TipoAsuntoBean tipoAsuntoBean,
			String entidad) throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			TipoAsunto tipoAsunto = (TipoAsunto) tipoAsuntoBean;

			tipoAsunto.setId(IdsGenerator.generateNextId(IdsGenerator.SCR_CA,
					entidad));
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			new SicresTipoAsuntoDatos(tipoAsunto).add(db);

			// Añadir la Información Auxiliar
			addInformacionAuxiliar(db, tipoAsunto.getId(), tipoAsuntoBean
					.getInformacionAuxiliar(), entidad);

			// Añadir las Oficinas
			addOficinas(db, tipoAsunto.getId(), tipoAsuntoBean.getOficinas(),
					entidad);

			// Añadir documentos
			addDocumentos(db, tipoAsunto.getId(), tipoAsuntoBean
					.getDocumentos(), entidad);

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
			logger.error("Error creando el tranporte");
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

	public static void editarTipoAsunto(TipoAsuntoBean tipoAsuntoBean,
			String entidad) throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			db.beginTransaction();
			new SicresTipoAsuntoDatos(tipoAsuntoBean).update(db);

			// Añadir la Información Auxiliar
			addInformacionAuxiliar(db, tipoAsuntoBean.getId(), tipoAsuntoBean
					.getInformacionAuxiliar(), entidad);

			// Añadir las Oficinas
			removeOficinas(db, tipoAsuntoBean.getOficinasEliminadas(), entidad);
			addOficinas(db, tipoAsuntoBean.getId(), tipoAsuntoBean
					.getOficinas(), entidad);

			// Añadir documentos
			removeDocumentos(db, tipoAsuntoBean.getDocumentosEliminados(),
					entidad);
			addDocumentos(db, tipoAsuntoBean.getId(), tipoAsuntoBean
					.getDocumentos(), entidad);

			db.endTransaction(true);
		} catch (ISicresRPAdminDAOException rpaEx) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el tipo asunto");
			throw rpaEx;
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error editando el TipoAsunto");
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

	public static void eliminarTipoAsunto(int id, String entidad)
			throws ISicresRPAdminDAOException {

		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			SicresTipoAsuntoDatos tipoasunto = new SicresTipoAsuntoDatos();
			tipoasunto.load(id, db);

			db.beginTransaction();

			eliminarInfoAuxiliar(id, db);

			eliminarDocumentosByIdMatter(id, db);
			eliminarOficinasByIdMatter(id, db);

			tipoasunto.delete(db);
			db.endTransaction(true);
			if (logger.isDebugEnabled())
				logger.error("TipoAsunto eliminado");
		} catch (ISicresRPAdminDAOException rpaEx) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el tipoasunto");
			throw rpaEx;
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando el tipoasunto");
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

	public static TipoAsuntoBean obtenerTipoAsunto(int id, String entidad)
			throws ISicresRPAdminDAOException, Exception {
		DbConnection db = new DbConnection();

		TipoAsuntoBean tipoAsuntoBean = new TipoAsuntoBean();

		try {
			db.open(DBSessionManager.getSession());
			SicresTipoAsuntoDatos tipoAsunto = new SicresTipoAsuntoDatos();
			tipoAsunto.load(id, db);

			// Establecer los datos del tipo de Asunto
			BeanUtils.copyProperties(tipoAsuntoBean, tipoAsunto);

			// Obtener la Unidad Administrativa
			try {
				if (tipoAsunto.getIdOrg() != 0) {
					SicresOrganizacionDatos organizacionDAO = new SicresOrganizacionDatos();
					organizacionDAO.load(tipoAsunto.getIdOrg(), db);
					tipoAsuntoBean.setCodigoOrg(organizacionDAO.getCode());
					tipoAsuntoBean.setNombreOrg(organizacionDAO.getName());
				}
			} catch (ISicresAdminDAOException e) {
				logger.debug("Error al obtener la unidad administrativa: " + e);
				throw e;
			}

			// Obtener Información Auxiliar
			try{
				SicresInfoAuxiliarTipoAsuntoDatos tipoAsuntoAux = new SicresInfoAuxiliarTipoAsuntoDatos();
				tipoAsuntoAux.loadByIdMatter(id, db);
				tipoAsuntoBean.setInformacionAuxiliar(tipoAsuntoAux
						.getDatosAux());
			} catch (ISicresAdminDAOException e) {
				logger.debug("Error al obtener la información auxiliar:" + e);
				throw e;
			}

			// Obtener las Oficinas
			try{
				SicresOficinaTipoAsuntoDatos oficnasTipoAsuntoDAO = new SicresOficinaTipoAsuntoDatos();
				OficinasTipoAsuntoBean oficinas = oficnasTipoAsuntoDAO
						.getOficinasByIdMatter(id, db);

				tipoAsuntoBean.setOficinas(oficinas);
			} catch (ISicresAdminDAOException e) {
				logger.debug("Error al obtener oficinas:" + e);
				throw e;
			}

			// Obtener los Documentos
			try{
				SicresDocumentoTipoAsuntoDatos documentotipoAsuntoDAO = new SicresDocumentoTipoAsuntoDatos();
				DocumentosTipoAsuntoBean documentos = documentotipoAsuntoDAO
						.getDocumentosByIdMatter(id, db);

				tipoAsuntoBean.setDocumentos(documentos);
			} catch (ISicresAdminDAOException e) {
				logger.debug("No hay oficinas");
				throw e;
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
		return tipoAsuntoBean;
	}

	public static void asociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, String entidad)
			throws ISicresRPAdminDAOException {
		DbConnection db = new DbConnection();
		try {
			SicresDocumentoTipoAsuntoImpl documentoTipoAsunto = new SicresDocumentoTipoAsuntoImpl();
			documentoTipoAsunto.setId(IdsGenerator.generateNextId(
					IdsGenerator.SCR_CADOCS, entidad));
			documentoTipoAsunto.setIdMatter(documento.getIdMatter());
			documentoTipoAsunto.setDescription(documento.getDescription());
			documentoTipoAsunto.setMandatory(documento.getMandatory());
			db.beginTransaction();
			new SicresDocumentoTipoAsuntoDatos(documentoTipoAsunto).add(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando el documento del tipo de asunto");
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

	public static void asociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			String entidad) throws ISicresRPAdminException {
		DbConnection db = new DbConnection();
		try {
			SicresOficinaTipoAsuntoImpl oficinaTipoAsunto = new SicresOficinaTipoAsuntoImpl();
			oficinaTipoAsunto.setId(IdsGenerator.generateNextId(
					IdsGenerator.SCR_CAOFIC, entidad));
			oficinaTipoAsunto.setIdMatter(oficina.getIdTipoAsunto());
			oficinaTipoAsunto.setIdOfic(oficina.getIdOficina());
			db.beginTransaction();
			new SicresOficinaTipoAsuntoDatos(oficinaTipoAsunto).add(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando la oficina del tipo de asunto");
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

	public static void desasociarOficinaTipoAsunto(
			OficinaTipoAsuntoBean oficina, String entidad)
			throws ISicresRPAdminException {
		DbConnection db = new DbConnection();

		try {
			db.open(DBSessionManager.getSession());
			SicresOficinaTipoAsuntoDatos oficinaTipoAsunto = new SicresOficinaTipoAsuntoDatos();
			oficinaTipoAsunto.load(oficina.getId(), db);
			oficinaTipoAsunto.delete(db);
			db.endTransaction(true);
			if (logger.isDebugEnabled())
				logger.error("Oficina de TipoAsunto eliminada");
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando la oficina del tipo asunto");
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

	public static void desasociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, String entidad)
			throws ISicresRPAdminException {
		DbConnection db = new DbConnection();
		try {
			db.open(DBSessionManager.getSession());
			SicresDocumentoTipoAsuntoDatos documentoTipoAsunto = new SicresDocumentoTipoAsuntoDatos();
			documentoTipoAsunto.load(documento.getId(), db);
			documentoTipoAsunto.delete(db);
			db.endTransaction(true);
			if (logger.isDebugEnabled())
				logger.error("Oficina de TipoAsunto eliminada");
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error eliminando la oficina del tipo asunto");
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

	public static void editarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, String entidad)
			throws ISicresRPAdminException {
		DbConnection db = new DbConnection();
		try {
			SicresDocumentoTipoAsuntoDatos documentoTipoAsunto = new SicresDocumentoTipoAsuntoDatos();
			documentoTipoAsunto.load(documento.getId(), db);

			documentoTipoAsunto.setIdMatter(documento.getIdMatter());
			documentoTipoAsunto.setDescription(documento.getDescription());
			documentoTipoAsunto.setMandatory(documento.getMandatory());
			documentoTipoAsunto.update(db);

			db.beginTransaction();
			new SicresDocumentoTipoAsuntoDatos(documentoTipoAsunto).add(db);
			db.endTransaction(true);
		} catch (Exception e) {
			if (db != null && db.inTransaction())
				try {
					db.endTransaction(false);
				} catch (Exception e1) {
					logger.error("Problemas con rollback");
				}
			logger.error("Error creando el documento del tipo de asunto");
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

	public static TipoAsunto getTipoAsuntoByCode(String codigo,
			String entidad) throws Exception {
		DbConnection db = new DbConnection();
		SicresTipoAsuntoDatos tipoAsunto = new SicresTipoAsuntoDatos();
		try {
			db.open(DBSessionManager.getSession());
			tipoAsunto.load(codigo, db);
		} finally {
			try {
				if (db != null && db.existConnection()){
					db.close();
				}
			} catch (Exception e) {
				logger.error("No se ha podido cerrar la conexión a la BBDD");
			}
		}
		return tipoAsunto;
	}

	private static void addInformacionAuxiliar(DbConnection db, int idMatter,
			String informacionAuxiliar, String idEntidad) throws Exception {
		if (!"".equals(informacionAuxiliar)) {
			SicresInfoAuxiliarTipoAsuntoImpl tipoAsuntoAux = new SicresInfoAuxiliarTipoAsuntoImpl();
			tipoAsuntoAux.setId(IdsGenerator.generateNextId(
					IdsGenerator.SCR_CAAUX, idEntidad));
			tipoAsuntoAux.setIdMatter(idMatter);
			tipoAsuntoAux.setDatosAux(informacionAuxiliar);

			new SicresInfoAuxiliarTipoAsuntoDatos(tipoAsuntoAux).add(db);
		}
	}

	private static void removeOficinas(DbConnection db,
			OficinasTipoAsuntoBean oficinasTipoAsuntoBean, String idEntidad)
			throws Exception {
		if (oficinasTipoAsuntoBean != null
				&& oficinasTipoAsuntoBean.getLista() != null) {
			for (Iterator iterator = oficinasTipoAsuntoBean.getLista()
					.iterator(); iterator.hasNext();) {
				OficinaTipoAsuntoBean oficina = (OficinaTipoAsuntoBean) iterator
						.next();

				SicresOficinaTipoAsuntoImpl oficinaTipoAsunto = new SicresOficinaTipoAsuntoImpl();
				oficinaTipoAsunto.setId(oficina.getId());
				new SicresOficinaTipoAsuntoDatos(oficinaTipoAsunto).delete(db);
			}
		}
	}

	private static void addOficinas(DbConnection db, int idMatter,
			OficinasTipoAsuntoBean oficinasTipoAsuntoBean, String idEntidad)
			throws Exception {
		if (oficinasTipoAsuntoBean != null
				&& oficinasTipoAsuntoBean.getLista() != null) {
			for (Iterator iterator = oficinasTipoAsuntoBean.getLista()
					.iterator(); iterator.hasNext();) {
				OficinaTipoAsuntoBean oficina = (OficinaTipoAsuntoBean) iterator
						.next();

				SicresOficinaTipoAsuntoImpl oficinaTipoAsunto = new SicresOficinaTipoAsuntoImpl();

				oficinaTipoAsunto.setIdMatter(idMatter);
				oficinaTipoAsunto.setIdOfic(oficina.getIdOficina());

				switch (oficina.getEstado()) {
				case Estados.NUEVO:
					oficinaTipoAsunto.setId(IdsGenerator.generateNextId(
							IdsGenerator.SCR_CAOFIC, idEntidad));
					new SicresOficinaTipoAsuntoDatos(oficinaTipoAsunto).add(db);
					break;
				case Estados.MODIFICADO:
					new SicresOficinaTipoAsuntoDatos(oficinaTipoAsunto)
							.update(db);
					break;
				case Estados.ELIMINADO:
					new SicresOficinaTipoAsuntoDatos(oficinaTipoAsunto)
							.delete(db);
					break;

				default:
					break;
				}

			}
		}
	}

	private static void addDocumentos(DbConnection db, int idMatter,
			DocumentosTipoAsuntoBean documentos, String idEntidad)
			throws Exception {
		if (documentos != null && documentos.getLista() != null) {
			for (Iterator iterator = documentos.getLista().iterator(); iterator
					.hasNext();) {
				DocumentoTipoAsuntoBean documento = (DocumentoTipoAsuntoBean) iterator
						.next();

				SicresDocumentoTipoAsuntoImpl documentoTipoAsunto = new SicresDocumentoTipoAsuntoImpl();

				documentoTipoAsunto.setIdMatter(idMatter);
				documentoTipoAsunto.setDescription(documento.getDescription());
				documentoTipoAsunto.setMandatory(documento.getMandatory());

				switch (documento.getEstado()) {
				case Estados.NUEVO:
					documentoTipoAsunto.setId(IdsGenerator.generateNextId(
							IdsGenerator.SCR_CADOCS, idEntidad));
					new SicresDocumentoTipoAsuntoDatos(documentoTipoAsunto)
							.add(db);
					break;
				case Estados.MODIFICADO:
					documentoTipoAsunto.setId(documento.getId());
					new SicresDocumentoTipoAsuntoDatos(documentoTipoAsunto)
							.update(db);
					break;
				case Estados.ELIMINADO:
					documentoTipoAsunto.setId(documento.getId());
					new SicresDocumentoTipoAsuntoDatos(documentoTipoAsunto)
							.delete(db);
					break;
				default:
					break;
				}
			}
		}
	}

	private static void removeDocumentos(DbConnection db,
			DocumentosTipoAsuntoBean documentos, String idEntidad)
			throws Exception {
		if (documentos != null && documentos.getLista() != null) {
			for (Iterator iterator = documentos.getLista().iterator(); iterator
					.hasNext();) {
				DocumentoTipoAsuntoBean documento = (DocumentoTipoAsuntoBean) iterator
						.next();

				SicresDocumentoTipoAsuntoImpl documentoTipoAsunto = new SicresDocumentoTipoAsuntoImpl();

				documentoTipoAsunto.setId(documento.getId());
				new SicresDocumentoTipoAsuntoDatos(documentoTipoAsunto)
						.delete(db);
			}
		}
	}

	/**
	 * Elimina las Oficinas asociadas al Tipo de Asunto
	 *
	 * @param idMatter
	 *            Identificador del tipo de Asunto
	 * @param db
	 * @throws Exception
	 */
	private static void eliminarOficinasByIdMatter(int idMatter, DbConnection db)
			throws Exception {
		SicresOficinaTipoAsuntoDatos tipoAsuntoOficinaDatos = new SicresOficinaTipoAsuntoDatos();
		tipoAsuntoOficinaDatos.setIdMatter(idMatter);
		tipoAsuntoOficinaDatos.deleteByIdMatter(db);
	}

	/**
	 * Elimina la Información Auxuliar Asociada al Tipo de Asunto
	 *
	 * @param idMatter
	 *            Identificador del Tipo de Asunto
	 * @param db
	 * @throws Exception
	 */
	private static void eliminarInfoAuxiliar(int idMatter, DbConnection db)
			throws Exception {
		SicresInfoAuxiliarTipoAsuntoDatos tipoAsuntoAux = new SicresInfoAuxiliarTipoAsuntoDatos();
		tipoAsuntoAux.setIdMatter(idMatter);
		tipoAsuntoAux.deleteByIdMatter(db);
	}

	/**
	 * Elimina los Documentos Asociados al Tipo de Asunto
	 *
	 * @param idMatter
	 *            Identificador del Tipo de Asunto
	 * @param db
	 * @throws Exception
	 */
	private static void eliminarDocumentosByIdMatter(int idMatter,
			DbConnection db) throws Exception {
		SicresDocumentoTipoAsuntoDatos tipoAsuntoDocumentosDatos = new SicresDocumentoTipoAsuntoDatos();
		tipoAsuntoDocumentosDatos.setIdMatter(idMatter);
		tipoAsuntoDocumentosDatos.deleteByIdMatter(db);
	}

}

