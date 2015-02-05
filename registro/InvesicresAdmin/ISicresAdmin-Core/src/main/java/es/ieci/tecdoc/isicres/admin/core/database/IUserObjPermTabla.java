package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class IUserObjPermTabla {

	private static final Logger logger = Logger.getLogger(IUserObjPermTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "iuserobjperm";
	private static final String CN_DSTTYPE = "dsttype";
	private static final String CN_DSTID = "dstid";
	private static final String CN_OBJID = "objid";
	private static final String CN_APERM = "aperm";
	
	private static final String ALL_COLUMN_NAMES = CN_DSTTYPE + SEPARATOR + CN_DSTID + SEPARATOR + CN_OBJID + SEPARATOR + CN_APERM;
	private static final String UPDATE_COLUMN_NAMES = CN_APERM;
	
	public IUserObjPermTabla() {
	}

	public String getUpdateColumnNames() {
		return UPDATE_COLUMN_NAMES;
	}
	   
	public String getTableName() {
	   return TABLE_NAME;
	}

	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}
	
	public String getOneByIds(int dstType, int dstId, int objId, int aperm) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_DSTTYPE).append(" = ").append(dstType).append(" AND ")
		.append(CN_DSTID).append(" = ").append(dstId).append(" AND ")
		.append(CN_APERM).append(" = ").append(aperm).append(" AND ")
		.append(CN_OBJID).append(" = ").append(objId);
		return sbAux.toString();
	}
	
	public String getAllByIds(int dstType, int dstId, int objId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_DSTTYPE).append(" = ").append(dstType).append(" AND ")
		.append(CN_DSTID).append(" = ").append(dstId).append(" AND ").append(CN_OBJID).append(" = ").append(objId);
		return sbAux.toString();
	}

	public String getByBookAndType(int objId, int dstType) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_DSTTYPE).append(" = ").append(dstType)
		.append(" AND ").append(CN_OBJID).append(" = ").append(objId);
		return sbAux.toString();
	}
	
	public String getByDestAndType(int dstId, int dstType) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_DSTTYPE).append(" = ").append(dstType)
		.append(" AND ").append(CN_DSTID).append(" = ").append(dstId);
		return sbAux.toString();
	}
	
	public String getByObjAndType(int objId) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_OBJID).append(" = ").append(objId);
		return sbAux.toString();
	}

}
