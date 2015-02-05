package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * DAO para la gestión de la tabla de trámites de procedimientos.
 *
 */
public class PTramiteDAO extends ObjectDAO {

	/**
	 * Nombre de la tabla.
	 */
	static final String TABLENAME = "SPAC_P_TRAMITES";

	/**
	 * Nombre de la secuencia.
	 */
	static final String IDSEQUENCE = "SPAC_SQ_ID_PTRAMITES";

	/**
	 * Nombre de la columna del identificador único.
	 */
	static final String IDKEY = "ID";
	
	/**
	 * Constructor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public PTramiteDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PTramiteDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * Constructor
	 * @param cnt Conexión a la base de datos.
	 * @param id Identificador único del trámite.
	 * @throws ISPACException si ocurre algún error.
	 */
	public PTramiteDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	/**
	 * Obtiene el nombre de la tabla.
	 * @return Nombre de la tabla.
	 */
	public String getTableName() {
		return TABLENAME;
	}

	/**
	 * Construye un nuevo registro.
	 * @param cnt Conexión a la base de datos.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected void newObject(DbCnt cnt) throws ISPACException {
		set(IDKEY, IdSequenceMgr.getIdSequence(cnt, IDSEQUENCE));
	}

	/**
	 * Obtiene la sentencia SQL por defecto.
	 * @param nActionDAO Código de de acción.
	 * @return Sentencia SQL. 
	 * @throws ISPACException si ocurre algún error. 
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	/**
	 * Obtiene el nombre de la columna del identificador único.
	 * @return Nombre de la columna del identificador único.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getKeyName() throws ISPACException {
		return IDKEY;
	}

	public static IItem getProcedureTaskCTL(DbCnt cnt, int taskCtlId)
			throws ISPACException {
		String sql = "WHERE ID_CTTRAMITE = " + taskCtlId;

		CollectionDAO collection = new CollectionDAO(PTramiteDAO.class);
		collection.query(cnt, sql);
		if (!collection.next()) {
			throw new ISPACNullObject();
		}

		return collection.value();
	}

	public CollectionDAO getTaskApps(DbCnt cnt) throws ISPACException {
		String sql = "WHERE ID_TRAMITE = " + getInt(IDKEY);
		CollectionDAO objlist = new CollectionDAO(PFrmTramiteDAO.class);
		objlist.query(cnt, sql);
		return objlist;
	}

	public static boolean checkTaskInstances(DbCnt cnt, int ptaskId)
			throws ISPACException {
		String sql = "WHERE ID_TRAMITE = " + ptaskId;

		CollectionDAO collection = new CollectionDAO(TXTramiteDAO.class);
		return (collection.count(cnt, sql) > 0);
	}

	public static boolean checkTaskInstances(DbCnt cnt, int pcdId, int pstageId, int cttaskId)
			throws ISPACException {
		String sql = "WHERE ID_PCD=" + pcdId
			+ " AND ID_FASE=" + pstageId
			+ " AND ID_CTTRAMITE=" + cttaskId;

		CollectionDAO collection = new CollectionDAO(PTramiteDAO.class);
		return (collection.count(cnt, sql) > 0);
	}

	/**
	 * Actualiza el identificador del subproceso de los trámites.
	 * @param cnt Conexión a la base de datos.
	 * @param oldSubPcdId Identificador anterior del subproceso.
	 * @param newSubPcdId Nuevo identificador del subproceso.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static void updateSubProcedureId(DbCnt cnt, int oldSubPcdId, 
			int newSubPcdId) throws ISPACException {
		
		String sql = new StringBuffer()
			.append("UPDATE ")
			.append(TABLENAME)
			.append(" SET ID_PCD_SUB=")
			.append(newSubPcdId)
			.append(" WHERE ID_PCD_SUB=")
			.append(oldSubPcdId)
			.toString();
		
		cnt.directExec(sql);
	}

}
