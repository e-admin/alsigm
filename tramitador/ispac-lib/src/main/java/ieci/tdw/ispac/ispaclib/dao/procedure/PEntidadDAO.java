package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Lema
 *
 */
public class PEntidadDAO extends ObjectDAO
{

	static final String TABLENAME 	= "SPAC_P_ENTIDADES";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PENTIDADES";
	static final String IDKEY 		= "ID";

	/**
	 *
	 * @throws ISPACException
	 */
	public PEntidadDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PEntidadDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PEntidadDAO(DbCnt cnt, int id) throws ISPACException	{
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

	protected String getDefaultSQL(int nActionDAO)
	throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName()
	throws ISPACException
	{
		return IDKEY;
	}

	/**
	 * Obtiene la colección de las entidades relacionadas
	 * con un procedimiento.
	 * @param cnt manejador de la conexión
	 * @param procedureId identificador del procedimiento
	 * @return una colección de objetos EntityProcedureDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getProcedureEntities(DbCnt cnt,int procedureId)
	throws ISPACException
	{
		String sql = "WHERE ID_PCD = " + procedureId + " ORDER BY ORDEN";

		CollectionDAO collection = new CollectionDAO(PEntidadDAO.class);
		collection.query(cnt,sql);
		return collection;
	}

	/**
	 * Obtiene la colección de las entidades relacionadas
	 * con un procedimiento.
	 * @param cnt manejador de la conexión
	 * @param procedureId identificador del procedimiento
	 * @return una colección de objetos EntityProcedureDAO
	 * @throws ISPACException
	 */
	public static CollectionDAO getProcedureEntities(DbCnt cnt,int procedureId, int stagePcdId, int taskPcdId)
	throws ISPACException
	{
		String sqlVisibleEntities = "";
		String sqlEntitiesWithForm = "";

		String sql = "WHERE ID_PCD = " + procedureId;

		// Entidades visibles
		// las que no están marcadas como no visibles en el contexto actual de trámite, fase o procedimiento
		// y las que estando marcadas como no visibles en contextos inferiores de fase o procedimiento
		// tienen un formulario o regla asignados en contextos superiores de trámite o fase

		if (taskPcdId > 0) {

			// La entidad no está entre las no visibles para el trámite del procedimiento
			sqlVisibleEntities = " ID_ENT NOT IN ( SELECT ID_ENT FROM SPAC_P_FRMTRAMITES WHERE ID_TRAMITE = " + taskPcdId + " AND FRM_EDIT = " + ISPACEntities.ENTITY_FORM_NO_VISIBLE + " ) "
							   + " AND "
			// y la entidad no está entre las no visibles para la fase del procedimiento siempre que el trámite no tenga formulario o regla asignados
							   + " ID_ENT NOT IN ( SELECT ID_ENT FROM SPAC_P_FRMFASES SPACPFRMFASES "
							   				   + " WHERE ID_FASE = " + stagePcdId + " AND FRM_EDIT = " + ISPACEntities.ENTITY_FORM_NO_VISIBLE
							   				   + " AND ( SELECT COUNT(*) FROM SPAC_P_FRMTRAMITES WHERE ID_TRAMITE = " + taskPcdId + " AND ID_ENT = SPACPFRMFASES.ID_ENT AND (FRM_EDIT > 0 OR ID_RULE_FRM > 0 OR ID_RULE_VISIBLE > 0) ) = 0 ) ";

			// Entidades con formulario o regla asignados en el trámite o en la fase del trámite
			sqlEntitiesWithForm = " ( ID_ENT IN ( SELECT ID_ENT FROM SPAC_P_FRMTRAMITES WHERE ID_TRAMITE = " + taskPcdId + " AND (FRM_EDIT > 0 OR ID_RULE_FRM > 0 OR ID_RULE_VISIBLE > 0) ) ) "
								+ " OR "
								+ " ( ID_ENT IN ( SELECT ID_ENT FROM SPAC_P_FRMFASES WHERE ID_FASE = " + stagePcdId + " AND (FRM_EDIT > 0 OR ID_RULE_FRM > 0 OR ID_RULE_VISIBLE > 0) ) ) ";
		}
		else if (stagePcdId > 0) {

			// La entidad no está entre las no visibles para la fase del procedimiento
			sqlVisibleEntities = " ID_ENT NOT IN ( SELECT ID_ENT FROM SPAC_P_FRMFASES WHERE ID_FASE = " + stagePcdId + " AND FRM_EDIT = " + ISPACEntities.ENTITY_FORM_NO_VISIBLE + " ) ";

			// Entidades con formulario o regla asignados en la fase
			sqlEntitiesWithForm = " ID_ENT IN ( SELECT ID_ENT FROM SPAC_P_FRMFASES WHERE ID_FASE = " + stagePcdId + " AND (FRM_EDIT > 0 OR ID_RULE_FRM > 0 OR ID_RULE_VISIBLE > 0) ) ";
		}

		// Entidades visibles
		if (StringUtils.isNotEmpty(sqlVisibleEntities)) {
			sqlVisibleEntities = " AND ( " + sqlVisibleEntities + " ) ";
		}

		// Entidades con formulario o regla asignados
		if (StringUtils.isNotEmpty(sqlEntitiesWithForm)) {
			sqlEntitiesWithForm = " OR ( " + sqlEntitiesWithForm + " )";
		}

		// y no están marcadas como no visibles
		// o estando marcadas como no visibles para el procedimiento tienen un formulario o regla asignados a nivel de fase o trámite
		sql += sqlVisibleEntities + " AND ( ( FRM_EDIT IS NULL OR FRM_EDIT <> 0 )" + sqlEntitiesWithForm + " ) ORDER BY ORDEN";

		CollectionDAO collection = new CollectionDAO(PEntidadDAO.class);
		collection.query(cnt,sql);
		return collection;
	}

	/**
	 * Obtiene la aplicación por defecto asociada a la entidad
	 * del procedimiento.
	 * @param cnt manejador de la conexión
	 * @return el objeto aplicación
	 * @throws ISPACException
	 */
	public CTApplicationDAO getApplication(DbCnt cnt)
	throws ISPACException
	{
		CTApplicationDAO ctApplication = null;

		if (isNull("FRM_EDIT")) {

			return null;
			//throw new ISPACNullObject("FRM_EDIT es nulo en SPAC_P_ENTIDADES");
		}

		// Formulario asignado a nivel de procedimiento
		int application = getInt( "FRM_EDIT");
		ctApplication = new CTApplicationDAO(cnt,application);
		//SOBRA--> el load se hace dentro del ctappliationdao ctApplication.load(cnt);

		return ctApplication;
	}

	/**
	 * Obtiene la aplicación asociada a la entidad
	 * de una fase de un procedimiento.
	 * @param cnt manejador de la conexión
	 * @param stageId identificador de la tarea
	 * @return el objeto aplicación
	 * @throws ISPACException
	 */
	public CTApplicationDAO getStageApplication( DbCnt cnt, int stageId)
	throws ISPACException
	{
		String strSQL = "where ID_ENT = "
									+ getKeyInt()
									+ "AND ID_FASE = "
									+ stageId;
		PFrmFaseDAO frmStage = new PFrmFaseDAO(cnt);
		frmStage.load(cnt, strSQL);
		return frmStage.getApplication(cnt);
	}

	/**
	 * Obtiene la aplicación asociada a la entidad
	 * de una tarea de un procedimiento.
	 * @param cnt manejador de la conexión
	 * @param taskId identificador de la tarea
	 * @return el objeto aplicación
	 * @throws ISPACException
	 */
	public CTApplicationDAO getTaskApplication( DbCnt cnt, int taskId)
	throws ISPACException
	{
		String strSQL = "where ID_ENT = "
									+ getKeyInt()
									+ "AND ID_TRAMITE = "
									+ taskId;
		PFrmTramiteDAO taskStage = new PFrmTramiteDAO(cnt);
		taskStage.load(cnt,strSQL);
		return taskStage.getApplication(cnt);
	}

	/**
	 * Obtiene la entidad de un procedimiento
	 * @param cnt manejador de la conexión
	 * @param procedureId identificador del procedimiento
	 * @param entityId identificador de la entidad
	 * @throws ISPACException
	 */
	public void load(DbCnt cnt, int procedureId, int entityId)
	throws ISPACException
	{
		String sWhere = "WHERE ID_PCD = "
									+ procedureId
									+ " AND ID_ENT = "
									+ entityId;
		load(cnt,sWhere);
	}


	public PEntidadDAO getPredecessor(DbCnt cnt,int procedureId)
	throws ISPACException
	{
		int order = Integer.MIN_VALUE;
	    if (!isNull("ORDEN")) {

	    	order = getInt("ORDEN");
	    }

	    String sql = "WHERE ID_PCD = " + procedureId
		   		   // Ya que la entidad del trámite no se muestra en los tabs, no interviene en el orden
		   		   + " AND ID_ENT <> " + SpacEntities.SPAC_DT_TRAMITES
		   		   + " AND ORDEN > " + order + " ORDER BY ORDEN";

	    CollectionDAO objlist = new CollectionDAO(PEntidadDAO.class);
	    objlist.query(cnt,sql);

	    // Retornar el primer elemento que es la entidad predecesora
	    if (objlist.next())
	        return (PEntidadDAO)objlist.value();

        return null;
	}

	public PEntidadDAO getAntecessor(DbCnt cnt,int procedureId)
	throws ISPACException
	{
	    int order = Integer.MAX_VALUE;
	    if (!isNull("ORDEN")) {

	    	order = getInt("ORDEN");
	    }

	    String sql = "WHERE ID_PCD = " + procedureId
	    		   // Ya que la entidad del trámite no se muestra en los tabs, no interviene en el orden
	    		   + " AND ID_ENT <> " + SpacEntities.SPAC_DT_TRAMITES
	    		   + " AND ORDEN < " + order + " ORDER BY ORDEN DESC";

	    CollectionDAO objlist = new CollectionDAO(PEntidadDAO.class);
	    objlist.query(cnt,sql);

	    // Retornar el primer elemento que es la entidad antecesora
	    if (objlist.next())
	        return (PEntidadDAO)objlist.value();

        return null;
	}

	public void delete(DbCnt cnt)
	throws ISPACException
	{
		// Eliminar los eventos asociados a la entidad en el procedimiento
		CollectionDAO entityEventsCollection = PEventoDAO.getEvents(cnt, EventsDefines.EVENT_OBJ_ENTITY, getKeyInt());
		while (entityEventsCollection.next()) {

			PEventoDAO pEventoDAO = (PEventoDAO) entityEventsCollection.value();
			pEventoDAO.delete(cnt);
		}

		// Eliminar la entidad en el procedimiento
		super.delete(cnt);
	}

	/**
	 *
	 * @param cnt
	 * @param procedureId
	 * @param mapEntityIds
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getFormsWithEntityVisibleRule(DbCnt cnt, int procedureId, Map mapEntityIds)
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

		String sql = "WHERE FORM.ID_RULE_VISIBLE = RULE.ID AND FORM.ID_PCD = " + procedureId + " AND FORM.ID_ENT IN (" + ids + ")";

	    return factory.queryTableJoin(cnt, sql);
	}

	/**
	 *
	 * @param cnt
	 * @param procedureId
	 * @param mapEntityIds
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getFormsAssignedOrWithEntityVisibleRule(DbCnt cnt, int procedureId, Map mapEntityIds)
	throws ISPACException
	{
		String ids = "";
		Iterator it = mapEntityIds.keySet().iterator();
		while (it.hasNext()) {

			ids += it.next().toString() + ", ";
		}
		ids = ids.substring(0, ids.length() - 2);

		String sql = "WHERE ID_PCD = " + procedureId + " AND ID_ENT IN (" + ids + ")"
				   + " AND (FRM_EDIT > 0 OR ID_RULE_FRM > 0 OR ID_RULE_VISIBLE > 0)";

		CollectionDAO collection = new CollectionDAO(PEntidadDAO.class);
		collection.query(cnt,sql);

		return collection;
	}
}
