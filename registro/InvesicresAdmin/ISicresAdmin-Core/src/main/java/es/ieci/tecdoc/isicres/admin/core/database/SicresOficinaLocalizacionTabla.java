package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresOficinaLocalizacionTabla {

	private static final Logger logger = Logger.getLogger(SicresOficinaLocalizacionTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_dirofic";
	private static final String CN_ID = "id_ofic";
	private static final String CN_ADDRESS = "address";
	private static final String CN_CITY = "city";
	private static final String CN_ZIP = "zip";
	private static final String CN_COUNTRY = "country";
	private static final String CN_TELEPHONE = "telephone";
	private static final String CN_FAX = "fax";
	private static final String CN_EMAIL = "email";
	private static final String CN_SIGNER = "signer";
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_ADDRESS + SEPARATOR + CN_CITY + SEPARATOR + CN_ZIP + SEPARATOR + CN_COUNTRY + SEPARATOR + CN_TELEPHONE + SEPARATOR + CN_FAX + SEPARATOR + CN_EMAIL + SEPARATOR + CN_SIGNER;
	private static final String UPDATE_COLUMN_NAMES = CN_ADDRESS + SEPARATOR + CN_CITY + SEPARATOR + CN_ZIP + SEPARATOR + CN_COUNTRY + SEPARATOR + CN_TELEPHONE + SEPARATOR + CN_FAX + SEPARATOR + CN_EMAIL + SEPARATOR + CN_SIGNER;
	
	public SicresOficinaLocalizacionTabla() {
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
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_ID).append(" = ").append(id).append(" ");
		return sbAux.toString();
	}

}
