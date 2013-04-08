package ieci.tdw.ispac.ispaclib.dao.cat;

import java.util.Iterator;
import java.util.List;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class HierarchyValueDAO extends ObjectDAO {

	static String tableName = null;
	static final String IDKEY 		= "ID";	
	
	public HierarchyValueDAO() throws ISPACException {
		super(null);
	}
	
	
	public HierarchyValueDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	public HierarchyValueDAO(DbCnt cnt, Integer id) throws ISPACException {
		super(cnt, id, null);
	}

    public HierarchyValueDAO(DbCnt cnt, int id) throws ISPACException {
    	super(cnt, id, null);
    }	
	
	protected String getDefaultSQL(int actionDAO) throws ISPACException {
		return null;
	}

	public String getKeyName() throws ISPACException {
		return null;
	}


	protected void newObject(DbCnt cnt)
		throws ISPACException{
		String sequenceName = IdSequenceMgr.SEQUENCE_NAME_PREFIX + Math.abs(getTableName().hashCode());
		set(IDKEY,IdSequenceMgr.getIdSequence(cnt,sequenceName));
	}	
	
	public void setTableName(String tableName) {
		HierarchyValueDAO.tableName = tableName;
	}

	
	public String getTableName() {
		return tableName;
	}


	public static CollectionDAO getValues(DbCnt cnt, int hierarchicalId, String query) throws ISPACException{

		CollectionDAO collection = new CollectionDAO(CTHierarchyDAO.class);
		collection.query(cnt, "WHERE ID = "+hierarchicalId);
		IItemCollection itemcol = collection.disconnect();
		if (!itemcol.next())
			throw new ISPACException("Definición de tabla jerarquica (id = '"+hierarchicalId+"')no encontrada");
		IItem item = itemcol.value();
		int entityAnc = item.getInt("ID_ENTIDAD_PADRE");
		int entityDesc = item.getInt("ID_ENTIDAD_HIJA");
		
		tableName = ICatalogAPI.HIERARCHICAL_TABLE_NAME + hierarchicalId;
		TableJoinFactoryDAO joinfactory=new TableJoinFactoryDAO();
		String prefixJ = "J";
		String prefixAnc = "A";
		String prefixDesc = "D";
		joinfactory.addTable(tableName, prefixJ);
		joinfactory.addEntity(cnt, entityAnc, prefixAnc);
		joinfactory.addEntity(cnt, entityDesc, prefixDesc);
		if (StringUtils.isEmpty(query)){
			query = "WHERE ";	
		}else{
			query += " AND ";
		}
		//query += tableName +".ID_PADRE = " + prefixAnc + ".ID AND " + tableName + ".ID_HIJA = " + prefixDesc + ".ID";
		query +=  prefixJ + ".ID_PADRE = " + prefixAnc + ".ID AND " + prefixJ + ".ID_HIJA = " + prefixDesc + ".ID";
		
		return joinfactory.queryTableJoin(cnt, query);
	}
	
	public boolean deleteValues(DbCnt cnt, String tableName, String[] values) throws ISPACException{
		CollectionDAO collection = new CollectionDAO(HierarchyValueDAO.class);
		String sqlWhere = "WHERE ID IN (" + StringUtils.join(values, ',') + ")"; 
		return collection.delete(cnt, sqlWhere) == values.length;
	}


	public boolean deleteValues(DbCnt cnt, String tableName, List parentIds,
			List descendantIds) throws ISPACException {
		String[] stPIds = new String[parentIds.size()];
		String[] stDIds = new String[descendantIds.size()];

		int i = 0;
		for (Iterator iterator = parentIds.iterator(); iterator.hasNext();) {
			Integer id = (Integer) iterator.next();
			stPIds[i++] = id.toString();
		}
		i = 0;
		for (Iterator iterator = descendantIds.iterator(); iterator.hasNext();) {
			Integer id = (Integer) iterator.next();
			stDIds[i++] = id.toString();
		}
		CollectionDAO collection = new CollectionDAO(HierarchyValueDAO.class);
		String sqlWhere = "WHERE ID_PADRE IN (" + StringUtils.join(stPIds, ',') + ") AND ID_HIJA IN (" + StringUtils.join(stDIds, ',') + ")"; 
		return collection.delete(cnt, sqlWhere) != 0;
	}
	
//	public boolean insertValues(DbCnt cnt, String tableName, int parentValue, int descendantValue) throws ISPACException{
//		
//	}
}
