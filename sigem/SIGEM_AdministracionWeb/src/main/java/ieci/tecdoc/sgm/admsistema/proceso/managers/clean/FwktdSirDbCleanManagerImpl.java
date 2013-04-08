package ieci.tecdoc.sgm.admsistema.proceso.managers.clean;

import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class FwktdSirDbCleanManagerImpl extends DefaultDbCleanManagerImpl {

	private static final String DB_NAME = "fwktd-sir";

	private static final Map SQL_SENTENCES_MAP = new HashMap();
	static {
		SQL_SENTENCES_MAP.put("*", new String[] {

				"DELETE FROM documentos",
				"DELETE FROM sir_anexos",
				"DELETE FROM sir_asientos_registrales",
				"DELETE FROM sir_contadores",
				"DELETE FROM sir_interesados"
		});
	}

	private static final String[][] DYNAMIC_TABLES_FOR_CLEANING = new String[0][0];

	private static final String[] DYNAMIC_TABLES_FOR_CLEANING_CONDITION = new String [0];


	/**
	 * Constructor.
	 */
	public FwktdSirDbCleanManagerImpl() {
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
		return Defs.BD_USUARIO_SIR_IMP;
	}

	public String getCleanUserPwdKey() {
		return Defs.BD_PASS_SIR_IMP;
	}
}
