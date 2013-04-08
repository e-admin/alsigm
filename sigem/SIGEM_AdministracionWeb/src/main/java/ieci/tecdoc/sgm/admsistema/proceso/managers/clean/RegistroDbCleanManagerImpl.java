package ieci.tecdoc.sgm.admsistema.proceso.managers.clean;

import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class RegistroDbCleanManagerImpl extends DefaultDbCleanManagerImpl {

	private static final String DB_NAME = "registro";
	
	private static final Map SQL_SENTENCES_MAP = new HashMap();
	static {
		SQL_SENTENCES_MAP.put("*", new String[] {
				
				"DELETE FROM scr_regint",
		    	"DELETE FROM scr_regasoc",
		    	"DELETE FROM scr_regasocex",
		    	"DELETE FROM scr_regpdocs",
		    	"DELETE FROM scr_sharedfiles",
		    	"DELETE FROM scr_lastregister",
		    	"DELETE FROM scr_modifreg",
		    	"DELETE FROM scr_valstr",
		    	"DELETE FROM scr_valnum",
		    	"DELETE FROM scr_valdate",
		    	"DELETE FROM scr_distreg",
		    	"DELETE FROM scr_distregstate",
		    	"DELETE FROM scr_procreg",
		    	"DELETE FROM scr_distaccept",
		    	"DELETE FROM scr_relations",
		    	"DELETE FROM scr_lockrelations",
		    	"DELETE FROM scr_ws",
		    	"DELETE FROM scr_pfis",
		    	"DELETE FROM scr_pjur",
		    	"DELETE FROM scr_pinfo",
		    	"DELETE FROM scr_address",
		    	"DELETE FROM scr_dom",
		    	"DELETE FROM scr_addrtel",
		    	"DELETE FROM scr_cntcentral",
		    	"DELETE FROM scr_cntlocal",
		    	"DELETE FROM scr_cntoficina",
		    	"DELETE FROM scr_lastconnection",
		    	"DELETE FROM scr_repository",
		    	"DELETE FROM scr_bookrepository",
		    	"DELETE FROM scr_pagerepository",
		    	"DELETE FROM scr_pageinfo",
		    	
		    	"UPDATE scr_contador SET contador=0 WHERE tablaid = 'SCR_WS'",
		    	"UPDATE scr_contador SET contador=0 WHERE tablaid = 'SCR_ADDRESS'"
		});
	}
	
	private static final String[][] DYNAMIC_TABLES_FOR_CLEANING = new String[][] {
		{"a##clfhx", null},
		{"a##clfhx", null},
		{"a##doch", null},
		{"a##dochx", null},
		{"a##fdrh", null},
		{"a##pageh", null},
		{"a##pagehx", null},
		{"a##sf", null},
		{"a##xf", null},
		{"a##xnid", null}
    };

	private static final String[] DYNAMIC_TABLES_FOR_CLEANING_CONDITION = new String[] {
		"id", "idocarchhdr", ""
	};
	
	
	/**
	 * Constructor.
	 */
	public RegistroDbCleanManagerImpl() {
		super();
	}

	public String getDbName() {
		return DB_NAME;
	}

	public Map getSQLSentences() {
		return SQL_SENTENCES_MAP;
	}

	public String[][] getDynamicTablesForCleaning() {
		return DYNAMIC_TABLES_FOR_CLEANING;
	}

	public String[] getDynamicTablesForCleaningCondition() {
		return DYNAMIC_TABLES_FOR_CLEANING_CONDITION;
	}

	public String getCleanUserKey() {
		return Defs.BD_USUARIO_RP_IMP;
	}

	public String getCleanUserPwdKey() {
		return Defs.BD_PASS_RP_IMP;
	}
}
