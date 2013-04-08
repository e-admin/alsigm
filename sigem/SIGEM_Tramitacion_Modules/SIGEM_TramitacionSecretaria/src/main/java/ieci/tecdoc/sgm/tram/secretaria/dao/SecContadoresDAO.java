package ieci.tecdoc.sgm.tram.secretaria.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;

public class SecContadoresDAO extends ObjectDAO {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(SecContadoresDAO.class);

	static final String TABLENAME = SecretariaConstants.TBL_CONTADOR;
	static final String IDSEQUENCE = SecretariaConstants.SEQ_TBL_CONTADOR;
	static final String IDKEY = "ID";

	/**
	 *
	 * @throws ISPACException
	 */
	public SecContadoresDAO() throws ISPACException {
		super(null);
	}

	/**
	 *
	 * @param cnt
	 * @throws ISPACException
	 */
	public SecContadoresDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
		load(cnt);
	}

	/**
	 *
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public SecContadoresDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TABLENAME;
	}

	/**
	 * Devuelve la query por defecto
	 *
	 * @return query
	 * @throws ISPACException
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		int id = getInt(IDKEY);
		if (id != Integer.MIN_VALUE) {
			return " WHERE " + IDKEY + " = " + id;
		}
		return "";
	}

	/**
	 * Devuelve el nombre del campo clave primaria
	 *
	 * @return nombre de la clave primaria
	 * @throws ISPACException
	 */
	public String getKeyName() throws ISPACException {
		return IDKEY;
	}

	/**
	 * Para crear un nuevo registro con identificador proporcionado por la
	 * secuencia correspondiente
	 *
	 * @param cnt
	 *            conexion
	 * @throws ISPACException
	 */
	public void newObject(DbCnt cnt) throws ISPACException {
		set(IDKEY, IdSequenceMgr.getIdSequence(cnt, IDSEQUENCE));
		if (logger.isDebugEnabled()) {
			logger.debug("ConcContadoresDAO: newObject--> IDKEY obtenido: "
					+ IDKEY);
		}
	}

	/**
	 * Elimina el informe cuyo id es el mismo que el que recibe como parametro
	 *
	 * @param cnt
	 *            Conexion con la bbdd utilizada
	 * @param id
	 *            Identificador del informe
	 * @throws ISPACException
	 */

	public static void delete(DbCnt cnt, int id) throws ISPACException {
		String sQuery = "DELETE FROM " + TABLENAME + " WHERE id = " + id;
		if (logger.isDebugEnabled()) {
			logger.debug("ConcContadoresDAO: delete--> SQL to execute: "
					+ sQuery);
		}
		cnt.directExec(sQuery);
	}

	/**
	 * Obtiene el primer contador que satisfaga la búsqueda
	 *
	 * @param cnt
	 * @param query
	 * @return
	 */
	public static SecContadoresDAO getContador(DbCnt cnt, String query) {
		try {

			CollectionDAO collection = new CollectionDAO(
					SecContadoresDAO.class);

			if (logger.isDebugEnabled()) {
				logger
						.debug("SecContadoresDAO: getContador--> query de la SQL: "
								+ query);
			}
			collection.query(cnt, query);
			if(collection.next()){
				return (SecContadoresDAO) collection.value();
			}
		} catch (ISPACException e) {
			logger.error("SecContadoresDAO: getContador", e);

		}
		return null;

	}

}