package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresUserFilterTabla {

	private static final Logger logger = Logger.getLogger(SicresUserFilterTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_userfilter";
	private static final String CN_IDARCH = "idarch";
	private static final String CN_IDUSER = "iduser";
	private static final String CN_FILTER_DEF = "filterdef";
	private static final String CN_TYPE_OBJ = "type_obj";
	private static final String ALL_COLUMN_NAMES = CN_IDARCH + SEPARATOR + CN_IDUSER + SEPARATOR + CN_FILTER_DEF + SEPARATOR + CN_TYPE_OBJ;
	private static final String UPDATE_COLUMN_NAMES = CN_FILTER_DEF;
	
	public SicresUserFilterTabla() {
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
	
	public String getByIds(int idArch, int idUser, int typeObj) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_IDARCH).append(" = ").append(idArch).append(" AND ")
				.append(CN_IDUSER).append(" = ").append(idUser).append(" AND ")
				.append(CN_TYPE_OBJ).append(" = ").append(typeObj);
		return sbAux.toString();
	}

}
