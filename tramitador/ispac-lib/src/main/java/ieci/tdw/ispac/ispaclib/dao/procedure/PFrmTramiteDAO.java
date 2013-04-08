/*
 * Created on 27-jul-2004
 *
 */
package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Lema
 *
 */
public class PFrmTramiteDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_FRMTRAMITES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PFRMTRAMITES";
	static final String IDKEY 		= "ID";

	/**
	 * @throws ISPACException
	 */
	public PFrmTramiteDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PFrmTramiteDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PFrmTramiteDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected void newObject(DbCnt cnt)
		throws ISPACException
	{
		set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}

	public void load(DbCnt cnt, int taskId, int entityId)
	throws ISPACException
	{
		String sSQL = "where ID_ENT = "
								+ entityId
								+ " AND ID_TRAMITE = "
								+ taskId;
		load(cnt,sSQL);
	}

	public void loadDefault(DbCnt cnt, int entityId)
	throws ISPACException
	{
		String sSQL = "WHERE ID_ENT = "
					+ entityId
					+ " AND ID_TRAMITE = 0";

		load(cnt,sSQL);
	}

	public CTApplicationDAO getApplication( DbCnt cnt)
	throws ISPACException
	{
		CTApplicationDAO ctApplication = null;

		int application = getInt( "FRM_EDIT");

		ctApplication = new CTApplicationDAO(cnt,application);
		//SOBRA--> el load se hace dentro del ctappliationdao ctApplication.load(cnt);

		return ctApplication;
	}

	/**
	 * Obtiene la colección de las entidades relacionadas
	 * con una tarea de un procedimiento.
	 * @param cnt manejador de la conexión
	 * @param taskPcdId identificador del trámite
	 * @return una colección de objetos PFrmTramiteDAO
	 * @throws ISPACException
	 */
	public static IItemCollection getTaskEntities(DbCnt cnt,int taskPcdId)
	throws ISPACException
	{
		String sql = "WHERE ID_TRAMITE = " + taskPcdId;

		CollectionDAO collection = new CollectionDAO(PFrmTramiteDAO.class);
		collection.query(cnt,sql);

		return collection.disconnect();
	}

	/**
	 * Obtiene la colección de los formularios de una entidad
	 * asociados a los trámites de un procedimiento.
	 * @param cnt manejador de la conexión
	 * @param pcdId identificador del procedimiento
	 * @param entityId identificador de la entidad
	 * @return una colección de objetos PFrmTramiteDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getFormsByEntityInPcd(DbCnt cnt, int pcdId, int entityId)
	throws ISPACException
	{
		String sql = " WHERE ID_ENT = " + entityId
				   + " AND ID_TRAMITE IN ( SELECT ID FROM SPAC_P_TRAMITES WHERE ID_PCD = " + pcdId + " ) ";

		CollectionDAO collection = new CollectionDAO(PFrmTramiteDAO.class);
		collection.query(cnt,sql);

		return collection;
	}

	/**
	 *
	 * @param cnt
	 * @param taskPcdId
	 * @param mapEntityIds
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getFormsWithEntityVisibleRule(DbCnt cnt, int taskPcdId, Map mapEntityIds)
	throws ISPACException
	{
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TABLENAME, "FORM");
		factory.addTable(CTRuleDAO.TABLENAME, "RULE");

		String ids = "";
		Iterator it = mapEntityIds.keySet().iterator();
		while (it.hasNext()) {

			ids += it.next().toString() + ", ";
		}
		ids = ids.substring(0, ids.length() - 2);

		String sql = "WHERE FORM.ID_RULE_VISIBLE = RULE.ID AND FORM.ID_TRAMITE = " + taskPcdId + " AND FORM.ID_ENT IN (" + ids + ")";

	    return factory.queryTableJoin(cnt, sql);
	}

	/**
	 *
	 * @param cnt
	 * @param taskPcdId
	 * @param mapEntityIds
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getFormsAssignedOrWithEntityVisibleRule(DbCnt cnt, int taskPcdId, Map mapEntityIds)
	throws ISPACException
	{
		String ids = "";
		Iterator it = mapEntityIds.keySet().iterator();
		while (it.hasNext()) {

			ids += it.next().toString() + ", ";
		}
		ids = ids.substring(0, ids.length() - 2);

		String sql = "WHERE ID_TRAMITE = " + taskPcdId + " AND ID_ENT IN (" + ids + ")"
				   + " AND (FRM_EDIT > 0 OR ID_RULE_FRM > 0 OR ID_RULE_VISIBLE > 0)";

		CollectionDAO collection = new CollectionDAO(PFrmTramiteDAO.class);
		collection.query(cnt,sql);

		return collection;
	}
}