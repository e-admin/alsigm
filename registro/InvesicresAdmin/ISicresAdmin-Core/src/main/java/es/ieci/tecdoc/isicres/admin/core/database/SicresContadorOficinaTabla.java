package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresContadorOficinaTabla {

	private static final Logger logger = Logger.getLogger(SicresContadorOficinaTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_cntoficina";
	private static final String CN_AN = "an";
	private static final String CN_OFICINA = "oficina";
	private static final String CN_NUM_REG = "num_reg";
	private static final String CN_ID_ARCH = "id_arch";

	private static final String ALL_COLUMN_NAMES = CN_AN + SEPARATOR + CN_OFICINA + SEPARATOR + CN_NUM_REG + SEPARATOR + CN_ID_ARCH;
	private static final String UPDATE_COLUMN_NAMES = CN_NUM_REG;
	
	public SicresContadorOficinaTabla() {
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
	
	public String getByTypeYearAndOfic(int type, int year, int oficina) {
		StringBuffer sbAux = new StringBuffer(" WHERE ").append(CN_ID_ARCH).append(" = ").append(type).append(" AND ")
			.append(CN_AN).append(" = ").append(year).append(" AND ")
			.append(CN_OFICINA).append(" = ").append(oficina);
		return sbAux.toString();
	}
	
	public String getByTypeAndYear(int type, int year) {
		StringBuffer sbAux = new StringBuffer(" WHERE ").append(CN_ID_ARCH).append(" = ").append(type).append(" AND ")
			.append(CN_AN).append(" = ").append(year).append(" ");
		return sbAux.toString();
	}

}
