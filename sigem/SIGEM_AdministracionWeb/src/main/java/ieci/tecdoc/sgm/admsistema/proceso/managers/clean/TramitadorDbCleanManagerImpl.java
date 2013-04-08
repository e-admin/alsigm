package ieci.tecdoc.sgm.admsistema.proceso.managers.clean;

import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class TramitadorDbCleanManagerImpl extends DefaultDbCleanManagerImpl {

	private static final String DB_NAME = "tramitador";
	
	private static final Map SQL_SENTENCES_MAP = new HashMap();
	static {
		SQL_SENTENCES_MAP.put("*", new String[] {
				
				"DELETE FROM pub_errores",
				"DELETE FROM pub_hitos_activos",
				"DELETE FROM pub_ultimo_hito_tratado",
				"DELETE FROM spac_anotaciones",
				"DELETE FROM spac_avisos_electronicos",
				"DELETE FROM spac_docobj",
				"DELETE FROM spac_dt_documentos",
				"DELETE FROM spac_dt_intervinientes",
				"DELETE FROM spac_dt_tramites",
				"DELETE FROM spac_exp_relacionados",
				"DELETE FROM spac_expedientes",
				"DELETE FROM spac_fases",
				"DELETE FROM spac_hitos",
				"DELETE FROM spac_infotramite",
				"DELETE FROM spac_procesos",
				"DELETE FROM spac_s_bloqueos",
				"DELETE FROM spac_s_sesiones",
				"DELETE FROM spac_s_vars",
				"DELETE FROM spac_sincnodo",
				"DELETE FROM spac_tramitaciones_agrupadas",
				"DELETE FROM spac_tramitcs_agrupadas_exps",
				"DELETE FROM spac_tramites",
				
				"UPDATE spac_numexp_contador SET contador=1",
				"UPDATE spac_vars SET valor='14' WHERE nombre='ID_TASK_SOLICITUD_SUBSANACION'",
				"UPDATE spac_vars SET valor='DC' WHERE nombre='ESTADOADM_DOC_COMPLETA'",
				"UPDATE spac_vars SET valor='DI' WHERE nombre='ESTADOADM_DOC_INCOMPLETA'",
				"UPDATE spac_vars SET valor='PR' WHERE nombre='ESTADO_ADM_INICIAL'",
				"UPDATE spac_vars SET valor='1' WHERE nombre='DEFAULT_CALENDAR_ID'"
		});
	}
	
	private static final String[][] DYNAMIC_TABLES_FOR_CLEANING = new String[][] {
		{"##", null}
    }; 
	
	private static final String[] DYNAMIC_TABLES_FOR_CLEANING_CONDITION = new String[] {
		"nombre", "spac_ct_entidades", "tipo=1"
	};
	
	
	/**
	 * Constructor.
	 */
	public TramitadorDbCleanManagerImpl() {
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
		return Defs.BD_USUARIO_GE_IMP;
	}

	public String getCleanUserPwdKey() {
		return Defs.BD_PASS_GE_IMP;
	}
}
