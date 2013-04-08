package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class IDocFmtTabla {

	private static final Logger logger = Logger.getLogger(IDocFmtTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "idocfmt";
	private static final String CN_ID = "id";
	private static final String CN_NAME = "name";
	private static final String CN_ARCHID = "archid";
	private static final String CN_TYPE = "type";
	private static final String CN_SUBTYPE = "subtype";
	private static final String CN_DATA = "data";
	private static final String CN_REMARKS = "remarks";
	private static final String CN_ACCESSTYPE = "accesstype";
	private static final String CN_ACSID = "acsid";
	private static final String CN_CRTR_ID = "crtrid";
	private static final String CN_CRTN_DATE = "crtndate";
	
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_NAME + SEPARATOR + CN_ARCHID + SEPARATOR + CN_TYPE + SEPARATOR + 
										CN_SUBTYPE + SEPARATOR + CN_DATA + SEPARATOR + CN_REMARKS + SEPARATOR + CN_ACCESSTYPE + SEPARATOR + 
										CN_ACSID + SEPARATOR + CN_CRTR_ID + SEPARATOR + CN_CRTN_DATE;
	
	public IDocFmtTabla() {
	}

	public String getTableName() {
	   return TABLE_NAME;
	}
	
	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}
	
}
