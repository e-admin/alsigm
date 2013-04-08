package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class IUserLdapUserHdrTabla {

	private static final Logger logger = Logger.getLogger(IUserLdapUserHdrTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "iuserldapuserhdr";
	private static final String CN_ID = "id";
	private static final String CN_LDAPGUID = "ldapguid";
	private static final String CN_LDAPFULLNAME = "ldapfullname";	
		
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_LDAPGUID + SEPARATOR + CN_LDAPFULLNAME;	
	
	public IUserLdapUserHdrTabla() {
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
	
	public String getByGuid(String ldapguid) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_LDAPGUID).append(" = '").append(ldapguid).append("'");
		return sbAux.toString();
	}
}
