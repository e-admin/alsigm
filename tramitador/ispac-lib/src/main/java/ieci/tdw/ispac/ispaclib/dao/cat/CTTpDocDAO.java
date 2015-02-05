
package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;


public class CTTpDocDAO extends ObjectDAO {

	static final String TBNAME	= "SPAC_CT_TPDOC";
	static final String SQNAME	= "SPAC_SQ_ID_CTTPDOC";
	static final String PKNAME	= "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public CTTpDocDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTTpDocDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTTpDocDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TBNAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + PKNAME + " = " + getInt(PKNAME);
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		set(PKNAME, IdSequenceMgr.getIdSequence(cnt, SQNAME));
	}

	public String getKeyName() throws ISPACException {
		return PKNAME;
	}

	/**
	 * Obtiene la colección de tipos de documento generables desde una fase
	 * @param cnt manejador de la conexión
	 * @param stageId Identificador de la fase  en SPAC_P_FASES
	 * @return una colección de objetos CTTpDocDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getDocTypesFromStage(DbCnt cnt,int stageId)
	throws ISPACException
	{
		String sql = "WHERE ID IN (SELECT ID_TPDOC FROM SPAC_P_FSTD WHERE ID_FASE = " + stageId + ") ORDER BY NOMBRE ";

		CollectionDAO collection = new CollectionDAO(CTTpDocDAO.class);
		collection.query(cnt,sql);
		return collection;
	}

	/**
	 * Obtiene la colección de tipos de documento generables desde un trámite
	 * del procedimiento.
	 * @param cnt manejador de la conexión
	 * @param taskId Identificador de la fase  en SPAC_P_TRAMITES
	 * @return una colección de objetos CTTpDocDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getDocTypesFromTaskPCD(DbCnt cnt,int taskPcdId)
	throws ISPACException
	{
		// la referencia fácil de obtener desde el tramitador es el id. del trámite en el procedimiento,
		// pero el tipo de documento está asociado al trámite en el catálogo --> se busca en un join
		String sql = "SELECT CT.ID_TPDOC FROM SPAC_CT_TRTD CT, SPAC_P_TRAMITES TASK ";
		sql += " WHERE CT.ID_TRAMITE = TASK.ID_CTTRAMITE ";
		sql += " AND TASK.ID = "+ taskPcdId;
		sql = "WHERE ID IN (" + sql + ") ORDER BY NOMBRE ";

		CollectionDAO collection = new CollectionDAO(CTTpDocDAO.class);
		collection.query(cnt,sql);
		return collection;
	}

	/**
	 * Obtiene la colección de tipos de documento generables desde un trámite
	 * del catálogo.
	 * @param cnt manejador de la conexión
	 * @param taskId Identificador de la fase  en SPAC_P_TRAMITES
	 * @return una colección de objetos CTTpDocDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getDocTypesFromTaskCTL(DbCnt cnt,int taskCtlId)
	throws ISPACException
	{
		String sql = "SELECT TRTD.ID_TPDOC FROM SPAC_CT_TRTD TRTD, SPAC_P_TRAMITES TASK"
		           + " WHERE TRTD.ID_TRAMITE = TASK.ID_CTTRAMITE"
		           + " AND TASK.ID_CTTRAMITE = " + taskCtlId;
		sql = "WHERE ID IN (" + sql + ") ORDER BY NOMBRE ";
		
		CollectionDAO collection = new CollectionDAO(CTTpDocDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
	
	/**
	 * Obtiene un tipo de documento a partir de su código.
	 * @param cnt manejador de la conexión
	 * @param typeCode Código del tipo de documento
	 * @return una colección de objetos CTTpDocDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getDocTypeByCode(DbCnt cnt, String typeCode)
	throws ISPACException
	{
		String sql = "WHERE COD_TPDOC = '" + DBUtil.replaceQuotes(typeCode) + "'";

		CollectionDAO collection = new CollectionDAO(CTTpDocDAO.class);
		collection.query(cnt, sql);
		return collection;
	}
	
}