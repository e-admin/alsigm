package ieci.tecdoc.sgm.admsistema.proceso.managers.clean;

import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class EtramitacionDbCleanManagerImpl extends DefaultDbCleanManagerImpl {

	private static final String DB_NAME = "eTramitacion";

	private static final Map SQL_SENTENCES_MAP = new HashMap();
	static {
		SQL_SENTENCES_MAP.put("*", new String[] {

				"DELETE FROM csv_documentos",
				"DELETE FROM sgm_au_usuarios",
				"DELETE FROM sgm_cnxusr",
				"DELETE FROM sgm_ctfichhito",
				"DELETE FROM sgm_cthitoestexp",
				"DELETE FROM sgm_cthitohistexp",
				"DELETE FROM sgm_ctinfoexp",
				"DELETE FROM sgm_ctintexp",
				"DELETE FROM sgm_ntfichnotif",
				"DELETE FROM sgm_ntinfonotif",
				"DELETE FROM sgm_pe_liquidaciones",
		    	/*"DELETE FROM sgm_pe_tasas",*/ //No se si se debería borrar
				"DELETE FROM sgmafsesion_info",
				"DELETE FROM sgmcertificacion",
				"DELETE FROM sgmctnotificacion",
				"DELETE FROM sgmctpago",
				"DELETE FROM sgmctsubsanacion",
				"DELETE FROM sgmntinfo_documento",
				"DELETE FROM sgmntinfo_estado_noti",
				"DELETE FROM sgmntinfo_notificacion",
				"DELETE FROM sgmrdedocumentos",
				"DELETE FROM sgmrtregistro",
				"DELETE FROM sgmrtregistro_documentos",
				"DELETE FROM sgmrtregistro_docs_csv",
				"DELETE FROM sgmrtregistro_secuencia", //Da igual (en teoría) borrarla que reiniciarla
				"DELETE FROM sgmrttmp_documentos",

				"UPDATE sgm_pe_al12nsec SET numsec=1",
				"UPDATE sgm_pe_al3nsec SET numsec=1"
		});
	}

	private static final String[][] DYNAMIC_TABLES_FOR_CLEANING = new String[0][0];

	private static final String[] DYNAMIC_TABLES_FOR_CLEANING_CONDITION = new String [0];


	/**
	 * Constructor.
	 */
	public EtramitacionDbCleanManagerImpl() {
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
		return Defs.BD_USUARIO_TE_IMP;
	}

	public String getCleanUserPwdKey() {
		return Defs.BD_PASS_TE_IMP;
	}
}
