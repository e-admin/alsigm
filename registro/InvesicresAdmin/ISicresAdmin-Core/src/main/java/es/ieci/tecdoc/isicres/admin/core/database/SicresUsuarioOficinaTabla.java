package es.ieci.tecdoc.isicres.admin.core.database;
/*$Id*/

import org.apache.log4j.Logger;

public class SicresUsuarioOficinaTabla {

	private static final Logger logger = Logger.getLogger(SicresUsuarioOficinaTabla.class);	

	private static final String SEPARATOR = ", ";
	public static final String TABLE_NAME = "scr_usrofic";
	public static final String CN_ID = "id";
	public static final String CN_IDUSER = "iduser";
	public static final String CN_IDOFIC = "idofic";
	
	private static final String ALL_COLUMN_NAMES = CN_ID + SEPARATOR + CN_IDUSER + SEPARATOR + CN_IDOFIC;
	
	public SicresUsuarioOficinaTabla() {
	}

	public String getTableName() {
	   return TABLE_NAME;
	}

	public String getAllColumnNames() {
	   return ALL_COLUMN_NAMES;
	}
	
	public String getByIds(int iduser, int idofic) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_IDUSER).append(" = ").append(iduser).append(" AND ")
		.append(CN_IDOFIC).append(" = ").append(idofic);
		return sbAux.toString();
	}
	
	public String getByUser(int iduser) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_IDUSER).append(" = ").append(iduser);
		return sbAux.toString();
	}
	
	public String getByOficina(int idofic) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_IDOFIC).append(" = ").append(idofic);
		return sbAux.toString();
	}
	
	public String getByIdsOficAndNotIdsUser(int idsOfic[], int idsUser[]) {
		StringBuffer sbAux = new StringBuffer("WHERE ").append(CN_IDOFIC).append(" IN(");
		for(int i=0; i<idsOfic.length; i++){
			sbAux.append(idsOfic[i]);
			if(i != idsOfic.length-1)
				sbAux.append(",");
		}
		sbAux.append(")");
		if(idsUser != null){
			sbAux.append(" AND ").append(CN_IDUSER).append(" NOT IN(");
			for(int i=0; i<idsUser.length; i++){
				sbAux.append(idsUser[i]);
				if(i != idsUser.length-1)
					sbAux.append(",");
			}
			sbAux.append(")");
		}
		return sbAux.toString();
	}
}