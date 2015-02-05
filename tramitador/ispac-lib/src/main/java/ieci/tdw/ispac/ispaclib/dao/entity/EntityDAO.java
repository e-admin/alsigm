package ieci.tdw.ispac.ispaclib.dao.entity;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.errors.ISPACPropertyUnknown;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.rule.EventManager;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.api.rule.RuleProperties;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.MemberDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBColumn;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBEntityDesc;
import ieci.tdw.ispac.ispaclib.dao.dbmgr.DBTableMgrFactory;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.entity.EntityTableManager;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO genérico para las entidades.
 */
public class EntityDAO extends ObjectDAO
{
	private final int mid;
	private final int mtype;
	private final String mtablename;
	private final String mfieldpk;
	private final String mfieldnumexp;
	private final String msequence;
	
	public EntityDAO(DbCnt cnt,
			 		 String tablename, 
			 		 String fieldpk,  
			 		 String sequence) throws ISPACException {

		super();
		setPosibleProcessEntity(true);

		mid = ISPACEntities.ENTITY_NULLREGKEYID;
		mtype = ISPACEntities.ENTITY_NULLREGKEYID;
		mtablename = tablename;
		mfieldpk = fieldpk;
		mfieldnumexp = "";
		msequence = sequence;

		// Obtener la descripción de la tabla
		//Debido a que se hacen consultas de tablas como si fueran entidades 
		//se realiza la comprobacion para iniciar su descripcion en funcion de si es o no una entidad
		CTEntityDAO ctEntityDAO = EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt, mtablename);
		if (ctEntityDAO != null && ctEntityDAO.getType() == EntityType.PROCESS_ENTITY_TYPE.getId())
			initEntityDesc(cnt, mtablename, null, null);
		else
			initTableDesc(cnt, mtablename, null, null);
	}
	
	
	
	
	protected void initEntityDesc(DbCnt cnt, String tablename, Property[] tableColumns, Date timestamp) throws ISPACException
	{
		mMembersMap = new LinkedHashMap();
		mbNewObject = false;

		mTableDesc = DBTableMgrFactory.getInstance().getEntityDesc(cnt, tablename, null, tableColumns, timestamp);

		Iterator it = mTableDesc.iterator();
		while (it.hasNext())
		{
			newMemberDAO(mTableDesc.getColumn(it));
		}
	}	
	
	
	public void load(IClientContext context) throws ISPACException
	{
		if (mtype != EntityType.PROCESS_ENTITY_TYPE.getId()){
			super.load(context);
			return;
		}
		DbCnt cnt = context.getConnection();
		try
		{
			load(cnt);
			loadMultivalues(cnt);
			executeLoadRules(context);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
	
	
	
	
	
	




	protected void storeDataMultivalue(DbCnt cnt, String sqlWhere) throws ISPACException {
		//Se eliminar los registros de campos multivalor existentes y se insertan los nuevos
		MemberDAO member;

		Iterator it = iterator();

		while (it.hasNext())
		{
			member = getMemberDAO(it);
			//if (member.getColumn().isMultivalue() && member.isDirty())
			if (member.getColumn().isMultivalue())
			{
				try{
					//Se eliminan los campos multivalor asociados a este registro
					String multivalueTableName = MultivalueTable.getInstance().composeMultivalueTableName(mtablename , member.getColumn().getType());
					String stmt = "DELETE FROM " + multivalueTableName + " WHERE " + MultivalueTable.getInstance().FIELD_FIELD + " = '" + member.getRawName() +  "' AND " + MultivalueTable.getInstance().FIELD_REG + " = " + this.getKeyInt();
					cnt.execute(stmt);
	
					if (member.getValue() != null && ((Object[])member.getValue()).length != 0){
						Object[] values =(Object[])member.getValue();
						
						for (int i = 0; i < values.length; i++) {
		
							String sequenceName = EntityTableManager.composeSequenceName(multivalueTableName);
							int id = IdSequenceMgr.nextVal(cnt, sequenceName);
							stmt = "INSERT INTO "
								 + multivalueTableName
								 + " ("
								 + getMultivalueTableCols()
								 + ") VALUES ( "
								 + id
								 + ", '" + member.getRawName() + "', " + this.getKeyInt() 
								 + ", ?)";
		
							PreparedStatement ps = cnt.prepareStatement(stmt);
							
							//Si los valores vienen sin parsear (p.ej: al clonar un expediente), se parsean
							if (values[i] instanceof String)
								values[i] = TypeConverter.parse(member.getColumn().getType(), (String)values[i]);
							
							member.getColumn().setObject(ps, 1, values[i]);
							
							ps.executeUpdate();
							ps.close();
						}
					}
				}
				catch (Exception e)
				{
					throw new ISPACException(e);
				}
			}
		}
	}

	public void loadMultivalues(DbCnt cnt) throws ISPACException {
		loadMultivalues(cnt, null);
	}
	
	public void loadMultivalues(DbCnt cnt, Map columns) throws ISPACException {
		DbResultSet dbrs = null;
		String stmt = null; 
		List multivalueFields = null;
		
		//Si la descripcion de la entidad ha sido cargada como tabla, forzamos la recarga como entidad
		if (!(mTableDesc instanceof DBEntityDesc)){
			mTableDesc = DBTableMgrFactory.getInstance().reloadEntityDesc(cnt, mtablename);
		}
		multivalueFields = ((DBEntityDesc)mTableDesc).getEntityDefinition().getMultivalueFields();
		
		for (Iterator iter = multivalueFields.iterator(); iter.hasNext();) {
			EntityField field = (EntityField) iter.next();
			if (columns == null || columns.get(field.getPhysicalName().toUpperCase()) == null){
				stmt = "SELECT " + getMultivalueTableCols() + 
				      " FROM " + getMultivalueTableName(field.getType().getJdbcType()) + 
				      " WHERE "+MultivalueTable.FIELD_FIELD+" = '" + field.getPhysicalName().toUpperCase() + "' " +
		      		  " AND " + MultivalueTable.FIELD_REG + " = " + this.getKeyInt() ;
				
				try {
					dbrs = cnt.executeQuery(stmt);
					processMultivalueRowData(field.getPhysicalName().toUpperCase(), dbrs.getResultSet(), true);
				} finally {
					if (dbrs != null) {
						dbrs.close();
					}
				}
			}
		}
		
	}

	private void processMultivalueRowData(String physicalName, ResultSet rs, boolean bClean) throws ISPACException {
		DBColumn col = mTableDesc.getColumn(physicalName);
		MemberDAO member = (MemberDAO) mMembersMap.get(physicalName);
		if (member == null)
			throw new ISPACPropertyUnknown("No existe la propiedad [" + physicalName + "] en el objeto "+ getTableName() +".");

		List list = new ArrayList();
		try {
			while(rs.next()){
				switch(col.getType()){
					case Types.VARCHAR:
					case Types.LONGVARCHAR:
					case Types.SMALLINT:
					case Types.INTEGER:
					case Types.REAL:
					case Types.FLOAT:
					{
						list.add(rs.getString(MultivalueTable.FIELD_VALUE));
						break;
					}
					case Types.DATE:
					{
						Date date = rs.getDate(MultivalueTable.FIELD_VALUE);
						list.add(DateUtil.format(date, "dd/MM/yyyy"));
						break;
					}
					case Types.TIMESTAMP:
					{
						Timestamp ts = rs.getTimestamp(MultivalueTable.FIELD_VALUE);
						list.add(DateUtil.format(ts, "dd/MM/yyyy HH:mm:ss"));
						break;
					}
					case Types.LONGVARBINARY:
					{
						list.add(rs.getObject(MultivalueTable.FIELD_VALUE));
						break;
					}
					default:
					{
						list.add(rs.getObject(MultivalueTable.FIELD_VALUE));
						break;
					}
				}
			}
			member.setValue(list.toArray());
		}catch(SQLException e){
			throw new ISPACException(e);
		}
		if (bClean)
			member.cleanDirty();
	}



	private String getMultivalueTableName(int type) {
		return MultivalueTable.getInstance().composeMultivalueTableName(getTableName(), type);
	}

	private String getMultivalueTableCols() {
		String[] columns = MultivalueTable.getInstance().getColumns();
		return StringUtils.join(columns, ',');
	}




	public EntityDAO(DbCnt cnt, 
					 int id, 
					 int type, 
					 String tablename, 
					 String fieldpk, 
					 String fieldnumexp, 
					 String sequence) throws ISPACException {
		
		super();
		setPosibleProcessEntity(true);
		mid = id;
		mtype = type;
		mtablename = tablename;
		mfieldpk = fieldpk;
		mfieldnumexp = fieldnumexp;
		msequence = sequence;
		
		// Obtener la descripción de la tabla
		//initTableDesc(cnt, mtablename, null, null);
		initEntityDesc(cnt, mtablename, null, null);
	}
	
	public EntityDAO(DbCnt cnt, 
			 		 Integer id, 
			 		 Integer type, 
			 		 String tablename, 
			 		 String fieldpk, 
			 		 String fieldnumexp, 
			 		 String sequence) throws ISPACException {

		super();
		setPosibleProcessEntity(true);
		mid = id.intValue();
		mtype = type.intValue();
		mtablename = tablename;
		mfieldpk = fieldpk;
		mfieldnumexp = fieldnumexp;
		msequence = sequence;

		// Obtener la descripción de la tabla
		//initTableDesc(cnt, mtablename, null, null);
		if (type.intValue() == EntityType.PROCESS_ENTITY_TYPE.getId())
			initEntityDesc(cnt, mtablename, null, null);
		else
			initTableDesc(cnt, mtablename, null, null);
	}
	
	public EntityDAO(String tablename,
			 		 String fieldpk,
			 		 String sequence) throws ISPACException {

		this(null, tablename, fieldpk, sequence);
}
	
	public EntityDAO(int id,
					 int type,
					 String tablename,
					 String fieldpk,
					 String fieldnumexp,
					 String sequence) throws ISPACException {
		
		this(null, id, type, tablename, fieldpk, fieldnumexp, sequence);
	}
	
	public EntityDAO(Integer id,
			 		 Integer type,
			 		 String tablename,
			 		 String fieldpk,
			 		 String fieldnumexp,
			 		 String sequence) throws ISPACException {

		this(null, id, type, tablename, fieldpk, fieldnumexp, sequence);
	}
	
//	public EntityDAO(DbCnt cnt,
//	 		 		 String tablename, 
//	 		 		 String fieldpk, 
//	 		 		 String sequence,
//	 		 		 Date timestamp) throws ISPACException {
//
//		super();
//
//		mid = ISPACEntities.ENTITY_NULLREGKEYID;
//		mtype = ISPACEntities.ENTITY_NULLREGKEYID;
//		mtablename = tablename;
//		mfieldpk = fieldpk;
//		mfieldnumexp = "";
//		msequence = sequence;
//
//		// Obtener la descripción de la tabla
//		//initTableDesc(cnt, mtablename, null, timestamp);
//		initEntityDesc(cnt, mtablename, null, timestamp);
//	}
	
	public EntityDAO(DbCnt cnt, 
			 		 int id, 
			 		 int type, 
			 		 String tablename, 
			 		 String fieldpk, 
			 		 String fieldnumexp, 
			 		 String sequence,
			 		 Date timestamp) throws ISPACException {
		
		super();
		setPosibleProcessEntity(true);
		
		mid = id;
		mtype = type;
		mtablename = tablename;
		mfieldpk = fieldpk;
		mfieldnumexp = fieldnumexp;
		msequence = sequence;
		
		// Obtener la descripción de la tabla
		//initTableDesc(cnt, mtablename, null, timestamp);
		if (type == EntityType.PROCESS_ENTITY_TYPE.getId())
			initEntityDesc(cnt, mtablename, null, timestamp);
		else
			initTableDesc(cnt, mtablename, null, timestamp);
	}
	
	public EntityDAO(DbCnt cnt, 
	 		 		 Integer id, 
	 		 		 Integer type, 
	 		 		 String tablename, 
	 		 		 String fieldpk, 
	 		 		 String fieldnumexp, 
	 		 		 String sequence,
	 		 		 Date timestamp) throws ISPACException {

		super();
		setPosibleProcessEntity(true);

		mid = id.intValue();
		mtype = type.intValue();
		mtablename = tablename;
		mfieldpk = fieldpk;
		mfieldnumexp = fieldnumexp;
		msequence = sequence;

		// Obtener la descripción de la tabla
		//initTableDesc(cnt, mtablename, null, timestamp);
		if (type.intValue() == EntityType.PROCESS_ENTITY_TYPE.getId())
			initEntityDesc(cnt, mtablename, null, timestamp);
		else
			initTableDesc(cnt, mtablename, null, timestamp);
	}
	
//	public EntityDAO(DbCnt cnt,
//					 String tablename, 
//					 String fieldpk, 
//					 String sequence,
//					 Timestamp timestamp) throws ISPACException {
//
//		this(cnt, tablename, fieldpk, sequence, (Date) timestamp);
//	}
	
	public EntityDAO(DbCnt cnt, 
	 		 		 int id, 
	 		 		 int type, 
	 		 		 String tablename, 
	 		 		 String fieldpk, 
	 		 		 String fieldnumexp, 
	 		 		 String sequence,
	 		 		 Timestamp timestamp) throws ISPACException {
		
		this(cnt, id, type, tablename, fieldpk, fieldnumexp, sequence, (Date) timestamp);
	}
	
	public EntityDAO(DbCnt cnt, 
	 		 		 Integer id, 
	 		 		 Integer type, 
	 		 		 String tablename, 
	 		 		 String fieldpk, 
	 		 		 String fieldnumexp, 
	 		 		 String sequence,
	 		 		 Timestamp timestamp) throws ISPACException {

		this(cnt, id, type, tablename, fieldpk, fieldnumexp, sequence, (Date) timestamp);
	}
	
//	public EntityDAO(String tablename, 
//	 		 		 String fieldpk, 
//	 		 		 String sequence,
//	 		 		 Date timestamp) throws ISPACException {
//
//		this(null, tablename, fieldpk, sequence, timestamp);
//	}
	
	public EntityDAO(int id, 
	 		 		 int type, 
	 		 		 String tablename, 
	 		 		 String fieldpk, 
	 		 		 String fieldnumexp, 
	 		 		 String sequence,
	 		 		 Date timestamp) throws ISPACException {
		
		this(null, id, type, tablename, fieldpk, fieldnumexp, sequence, timestamp);
	}
	
	public EntityDAO(Integer id, 
	 		 		 Integer type, 
	 		 		 String tablename, 
	 		 		 String fieldpk, 
	 		 		 String fieldnumexp, 
	 		 		 String sequence,
	 		 		 Date timestamp) throws ISPACException {

		this(null, id, type, tablename, fieldpk, fieldnumexp, sequence, timestamp);
	}
	
//	public EntityDAO(String tablename, 
//	 		 		 String fieldpk, 
//	 		 		 String sequence,
//	 		 		 Timestamp timestamp) throws ISPACException {
//
//		this(null, tablename, fieldpk, sequence, timestamp);
//	}
	
	public EntityDAO(int id, 
	 		 		 int type, 
	 		 		 String tablename, 
	 		 		 String fieldpk, 
	 		 		 String fieldnumexp, 
	 		 		 String sequence,
	 		 		 Timestamp timestamp) throws ISPACException {
		
		this(null, id, type, tablename, fieldpk, fieldnumexp, sequence, timestamp);
	}
	
	public EntityDAO(Integer id, 
	 		 		 Integer type, 
	 		 		 String tablename, 
	 		 		 String fieldpk, 
	 		 		 String fieldnumexp, 
	 		 		 String sequence,
	 		 		 Timestamp timestamp) throws ISPACException {

		this(null, id, type, tablename, fieldpk, fieldnumexp, sequence, timestamp);
	}		
	
	public int getCTEntityId() {
		
		return mid;
	}
	
	public int getCTEntityType() {
		
		return mtype;
	}
	
	public String getTableName() {
		
		return mtablename;
	}
	
	public String getKeyName() throws ISPACException {
		
		return mfieldpk;
	}
	
	public String getFieldNumExp() {
		
		return mfieldnumexp;
	}
	
	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		
//		return " WHERE " + mfieldpk + " = " + getString(mfieldpk);
		if (isString( mfieldpk)) {
			return " WHERE " + mfieldpk + " = '" + getString(mfieldpk) + "'";
		} else {
			return " WHERE " + mfieldpk + " = " + getString(mfieldpk);
		}
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		
		// Establecer en el campo PK el siguiente número de la secuencia de la tabla
		set(mfieldpk, IdSequenceMgr.getIdSequence(cnt, msequence));
	}

	/** 
	 * Crea un nuevo objeto del mismo tipo que contiene las mismas propiedades
	 * y valores excepto la propiedad clave.
	 * 
	 * @param cnt Conexión.
	 * @return Objeto duplicado pero con distinta clave.
	 * @throws ISPACException
	 */
	public ObjectDAO duplicate(DbCnt cnt) throws ISPACException	{
		
	    Class classobjdao = this.getClass();
	    
	    try {
	        Class argstypes[] = {Integer.class, Integer.class, String.class, String.class, String.class, String.class};
	        Integer id = new Integer(mid);
	        Integer type = new Integer(mtype);
	        Object argsvalues[] = {id, type, mtablename, mfieldpk, mfieldnumexp, msequence};

	        Constructor constrdao = classobjdao.getConstructor(argstypes);
	        EntityDAO obj = (EntityDAO) constrdao.newInstance(argsvalues);

			obj.createNew(cnt);
			obj.copy(this, false);
			
			return obj;
		}
		catch (Exception e) {
			
			throw new ISPACException("Error en EntityDAO:duplicate() ", e);
		}
	}
	
	/*
	private int getCatalogEntityId(IClientContext contex) throws ISPACException {
		
		// Obtener el identificador de la entidad en el catálogo
		IEntitiesAPI entitiesAPI = contex.getAPI().getEntitiesAPI();
		IItemCollection catalogEntities = entitiesAPI.getCatalogEntities("WHERE NOMBRE = '" + mtablename + "'");
		
		if (catalogEntities.next()) {
		
			IItem catalogEntity = catalogEntities.value();
			return catalogEntity.getInt("ID");
		}
		else {
			return ISPACEntities.ENTITY_NULLREGKEYID;
		}
	}
	*/
	
	protected void executeStoreRules(IClientContext context) throws ISPACException {
		
		/*
		EventManager meventmgr = new EventManager(context);
		meventmgr.getRuleContextBuilder().setItem(this);
		
		// Obtener el identificador de la entidad en el catálogo
		int entityId = getCatalogEntityId(context);
		
		// Ejecutar reglas al MODIFICAR entidad.
		if (entityId != ISPACEntities.ENTITY_NULLREGKEYID) {
		
			IInvesflowAPI invesflowAPI = context.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			 
			try {
				// Componer el contexto de regla
				IItem item = entitiesAPI.getCatalogEntity(entityId);
				String fieldNumexp = item.getString("CAMPO_NUMEXP");
				if (StringUtils.isNotEmpty(fieldNumexp)) {
				
					String numExp = this.getString(fieldNumexp);
					if(!StringUtils.isEmpty(numExp)) {
					
						IItem itemExp = entitiesAPI.getExpedient(numExp);
						
						meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_NUMEXP, numExp);
						meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCEDURE, itemExp.getString("ID_PCD"));
					}
				}
			}
			catch(ISPACException e) {
				// Si se produce alguna excepción al componer el contexto, se continúa igual
			}
			meventmgr.processEvents(EventsDefines.EVENT_OBJ_ENTITY, entityId, EventsDefines.EVENT_ENTITY_MODIFY);
		}
		*/
		
		executeRules(context, EventsDefines.EVENT_ENTITY_MODIFY);
	}
	
	protected void executeNewRules(IClientContext context) throws ISPACException {
		
		/*
		EventManager meventmgr = new EventManager(context);
		meventmgr.getRuleContextBuilder().setItem(this);
		
		// Obtener el identificador de la entidad en el catálogo
		int entityId = getCatalogEntityId(context);
		
		// Ejecutar reglas al CREAR entidad
		if (entityId != ISPACEntities.ENTITY_NULLREGKEYID) {
		
			IInvesflowAPI invesflowAPI = context.getAPI();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			 
			try {
				// Componer el contexto de regla
				IItem item = entitiesAPI.getCatalogEntity(entityId);
				String fieldNumexp = item.getString("CAMPO_NUMEXP");
				if (StringUtils.isNotEmpty(fieldNumexp)) {
				
					String numExp = this.getString(fieldNumexp);
					if(StringUtils.isNotEmpty(numExp)) {
					
						IItem itemExp = entitiesAPI.getExpedient(numExp);
						
						meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_NUMEXP, numExp);
						meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCEDURE, itemExp.getString("ID_PCD"));
					}
				}
			}
			catch(ISPACException e) {
				// Si se produce alguna excepción al componer el contexto, se continúa igual
			}
			
			meventmgr.processEvents(EventsDefines.EVENT_OBJ_ENTITY, entityId, EventsDefines.EVENT_ENTITY_CREATE);
		}
		*/
		
		executeRules(context, EventsDefines.EVENT_ENTITY_CREATE);
	}
	
	protected void executeLoadRules(IClientContext context) throws ISPACException {
		
		/*
		EventManager meventmgr = new EventManager(context);
		meventmgr.getRuleContextBuilder().setItem(this);
		
		// Obtener el identificador de la entidad en el catálogo
		int entityId = getCatalogEntityId(context);
		
		// Ejecutar reglas al CARGAR entidad
		if (entityId != ISPACEntities.ENTITY_NULLREGKEYID) {
			meventmgr.processEvents(EventsDefines.EVENT_OBJ_ENTITY, entityId, EventsDefines.EVENT_ENTITY_VIEW);
		}
		*/
		
		executeRules(context, EventsDefines.EVENT_ENTITY_VIEW);
	}
	
	protected void executeDeleteRules(IClientContext context) throws ISPACException {
		
		/*
		EventManager meventmgr = new EventManager(context);
		meventmgr.getRuleContextBuilder().setItem(this);
		
		// Obtener el identificador de la entidad en el catálogo
		int entityId = getCatalogEntityId(context);
		
		// Ejecutar reglas al BORRAR entidad
		if (entityId != ISPACEntities.ENTITY_NULLREGKEYID) {
			meventmgr.processEvents(EventsDefines.EVENT_OBJ_ENTITY, entityId, EventsDefines.EVENT_ENTITY_DELETE);
		}
		*/
		
		executeRules(context, EventsDefines.EVENT_ENTITY_DELETE);
	}
	
	private void executeRules(IClientContext context, int eventCode) throws ISPACException {
		
		EventManager meventmgr = new EventManager(context);
		meventmgr.getRuleContextBuilder().setItem(this);
				
		// Identificador de la entidad en el catálogo
		int ctEntityId = mid;
		if (ctEntityId > 0) {
			
			int pcdId = 0;
			
			// Obtener el estado actual de tramitación
			StateContext stateContext = context.getStateContext();
			if (stateContext != null) {
				
				pcdId = stateContext.getPcdId();
				
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCEDURE, String.valueOf(pcdId));
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCESS, String.valueOf(stateContext.getProcessId()));
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_NUMEXP, stateContext.getNumexp());
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGE, String.valueOf(stateContext.getStageId()));
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_STAGEPCD, String.valueOf(stateContext.getStagePcdId()));
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASK, String.valueOf(stateContext.getTaskId()));
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_TASKPCD, String.valueOf(stateContext.getTaskPcdId()));
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_REGENTITYID, String.valueOf(stateContext.getKey()));
			} else {
				
				if (StringUtils.isNotBlank(getFieldNumExp())) {
					String numExp = getString(getFieldNumExp());
					if (StringUtils.isNotBlank(numExp)) {
						IProcess process = context.getAPI().getProcess(numExp);
						pcdId = process.getInt("ID_PCD");
						
						meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCEDURE, String.valueOf(pcdId));
						meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_PROCESS, String.valueOf(process.getKey()));
						meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_NUMEXP, numExp);
					}
				}
				
				meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_REGENTITYID, String.valueOf(getKey()));
			}
			
			meventmgr.getRuleContextBuilder().addContext(RuleProperties.RCTX_ENTITYID, String.valueOf(ctEntityId));
			
			if (pcdId > 0) {
				
				// Ejecución de las reglas en un contexto transaccional
				boolean ongoingTX = context.ongoingTX();
				boolean bCommit = false;
				
				try {
					if (!ongoingTX) {
						context.beginTX();
					}
			    	DbCnt cnt = context.getConnection();
			    	
			    	// Obtener el identificador de la entidad en el procedimiento
					PEntidadDAO pEntidadDAO = new PEntidadDAO(cnt);
					
					try {
						pEntidadDAO.load(cnt, pcdId, ctEntityId);
					}
					catch (ISPACNullObject e) {
						pEntidadDAO = null;
					}
					
					if (pEntidadDAO != null) {
						
						// Procesar las reglas asociadas al evento en la entidad del procedimiento
						meventmgr.processEvents(EventsDefines.EVENT_OBJ_ENTITY, pEntidadDAO.getKeyInt(), eventCode);
					}
					
					// Si todo ha sido correcto se hace commit de la transacción
					bCommit = true;
				}
				finally {
					if (!ongoingTX) {
						context.endTX(bCommit);
					}
				}
			}
		}
	}
	
}