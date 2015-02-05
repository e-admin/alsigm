package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresOrganizacionTabla {

	private static final Logger logger = Logger.getLogger(SicresOrganizacionTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_orgs";
	private static final String CN_ID = "id";
	private static final String CN_CODE = "code";
	private static final String CN_ID_FATHER = "id_father";
	private static final String CN_ACRON = "acron";
	private static final String CN_NAME = "name";
	private static final String CN_CREATION_DATE = "creation_date";
	private static final String CN_DISABLE_DATE = "disable_date";
	private static final String CN_TYPE = "type";
	private static final String CN_ENABLED = "enabled";
	private static final String CN_CIF = "cif";

	
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_CODE + SEPARATOR + CN_ID_FATHER + SEPARATOR + CN_ACRON + SEPARATOR + CN_NAME + SEPARATOR + CN_CREATION_DATE + SEPARATOR + CN_DISABLE_DATE + SEPARATOR + CN_TYPE + SEPARATOR + CN_ENABLED + SEPARATOR + CN_CIF;
	private static final String UPDATE_COLUMN_NAMES = CN_CODE + SEPARATOR + CN_ID_FATHER + SEPARATOR + CN_ACRON + SEPARATOR + CN_NAME + SEPARATOR + CN_CREATION_DATE + SEPARATOR + CN_DISABLE_DATE + SEPARATOR + CN_TYPE + SEPARATOR + CN_ENABLED + SEPARATOR + CN_CIF;
	
	public SicresOrganizacionTabla() {
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
		StringBuffer sbAux = new StringBuffer(" WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

	public String getByCode(String code) {
		StringBuffer sbAux = new StringBuffer(" WHERE ").append(CN_CODE).append(" = '").append(code).append("' ");
		return sbAux.toString();
	}
	
	public String getByType(int type) {
		StringBuffer sbAux = new StringBuffer(" WHERE ").append(CN_TYPE).append(" = ").append(type).append(" AND ")
			.append(CN_ID_FATHER).append(" IS NULL ");
		return sbAux.toString();
	}
	
	public String getByFather(int idPadre) {
		StringBuffer sbAux = new StringBuffer(" WHERE ").append(CN_ID_FATHER).append(" = ").append(idPadre).append(" ");
		return sbAux.toString();
	}

	public String getOrderByDesc() {
		StringBuffer sbAux = new StringBuffer(" ORDER BY ").append(CN_NAME);
		return sbAux.toString();
	}
}
