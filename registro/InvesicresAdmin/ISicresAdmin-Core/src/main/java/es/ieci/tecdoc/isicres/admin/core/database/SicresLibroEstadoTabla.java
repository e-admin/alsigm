package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresLibroEstadoTabla {

	private static final Logger logger = Logger.getLogger(SicresLibroEstadoTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_regstate";
	private static final String CN_ID = "id";
	private static final String CN_IDARCHREG = "idarchreg";
	private static final String CN_STATE = "state";
	private static final String CN_CLOSE_DATE = "closedate";
	private static final String CN_CLOSE_USER = "closeuser";
	private static final String CN_NUMERATION_TYPE = "numeration_type";
	private static final String CN_IMAGE_AUTH = "image_auth";

	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_IDARCHREG + SEPARATOR + CN_STATE + SEPARATOR + CN_CLOSE_DATE + SEPARATOR + CN_CLOSE_USER + SEPARATOR + CN_NUMERATION_TYPE + SEPARATOR + CN_IMAGE_AUTH;
	private static final String UPDATE_COLUMN_NAMES = CN_STATE + SEPARATOR + CN_CLOSE_DATE + SEPARATOR + CN_CLOSE_USER + SEPARATOR + CN_NUMERATION_TYPE + SEPARATOR + CN_IMAGE_AUTH;
	
	public SicresLibroEstadoTabla() {
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
	
	public String getById(int id) {
		StringBuffer sbAux = new StringBuffer(" WHERE ").append(CN_IDARCHREG).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}
}
