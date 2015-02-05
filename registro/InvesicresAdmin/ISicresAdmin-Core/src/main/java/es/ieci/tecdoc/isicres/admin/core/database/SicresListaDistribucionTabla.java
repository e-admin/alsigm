package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresListaDistribucionTabla {

	private static final Logger logger = Logger.getLogger(SicresListaDistribucionTabla.class);	
	
	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_distlist";
	private static final String CN_ID = "id";
	private static final String CN_ID_ORGS = "id_orgs";
	private static final String CN_TYPE_DEST = "type_dest";	
	private static final String CN_ID_DEST = "id_dest";
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_ID_ORGS + SEPARATOR + CN_TYPE_DEST + SEPARATOR + CN_ID_DEST;
	
	public SicresListaDistribucionTabla() {
	}

	public String getTableName() {
	   return TABLE_NAME;
	}

	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}

	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id);
		return sbAux.toString();
	}
	
	public String getByIdOrg(int idOrg) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID_ORGS).append(" = ").append(idOrg);
		return sbAux.toString();
	}
}
