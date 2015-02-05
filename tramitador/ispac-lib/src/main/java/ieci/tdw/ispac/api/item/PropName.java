/*
 * Created on Oct 5, 2004
 */
package ieci.tdw.ispac.api.item;

/**
 * Nombres de propiedades predeterminadas de las entidades de tramitación
 * 
 */
public class PropName {

	/**
	 * Entidad EXPEDIENTES
	 */
	public static final String EXP_ID 			= "ID";
	public static final String EXP_ID_PCD		= "ID_PCD";
	public static final String EXP_NUMEXP		= "NUMEXP";
	public static final String EXP_ASUNTO		= "ASUNTO";
	public static final String EXP_NREG			= "NREG";
	public static final String EXP_FREG			= "FREG";
	public static final String EXP_ESTADO_INF	= "ESTADO_INF";
	public static final String EXP_FESTADO		= "FESTADO";
	public static final String EXP_FINICIO		= "FINICIO";
	public static final String EXP_FCIERRE		= "FCIERRE";
	
	/**
	 * Entidad DOCUMENTOS
	 */
	public static final String DOC_ID			= "ID";
	public static final String DOC_NUMEXP		= "NUMEXP";
	//public static final String DOC_NDOC			= "NDOC";
	public static final String DOC_FDOC			= "FDOC";
	public static final String DOC_NOMBRE		= "NOMBRE";
	public static final String DOC_AUTOR		= "AUTOR";
	public static final String DOC_ID_FASE		= "ID_FASE";
	public static final String DOC_ID_TRAMITE	= "ID_TRAMITE";
	public static final String DOC_ID_TPDOC		= "ID_TPDOC";
	public static final String DOC_TP_REG		= "TP_REG";
	public static final String DOC_NREG			= "NREG";
	public static final String DOC_FREG			= "FREG";
	public static final String DOC_INFOPAG		= "INFOPAG";

	/**
	 * Entidad TRAMITES
	 */
	public static final String TRM_ID		= "ID";
	public static final String TRM_NUMEXP	= "NUMEXP";
	public static final String TRM_NOMBRE	= "NOMBRE";
	public static final String TRM_ESTADO	= "ESTADO";
	public static final String TRM_ID_TPCD	= "ID_TRAM_PCD";
	public static final String TRM_ID_FPCD	= "ID_FASE_PCD";
	public static final String TRM_ID_TEXP	= "ID_TRAM_EXP";
	public static final String TRM_NACTO	= "NUM_ACTO";
	public static final String TRM_ESTINF	= "ESTADO_INF";
	public static final String TRM_FINICIO	= "FECHA_INICIO";
	public static final String TRM_FCIERRE	= "FECHA_CIERRE";
	public static final String TRM_FINIP	= "FECHA_INICIO_PLAZO";
	public static final String TRM_FLIMITE	= "FECHA_LIMITE";
	public static final String TRM_URESP	= "UND_RESP";
	public static final String TRM_FPCD		= "FASE_PCD";
	public static final String TRM_OBS		= "OBSERVACIONES";

	/**
	 * Entidad INTERVINIENTES
	 */
	public static final String INT_ID= "ID";
	public static final String INT_ID_EXT= "ID_EXT";
	public static final String INT_NUMEXP= "NUMEXP";
	public static final String INT_ROL= "ROL";
	public static final String INT_TIPO= "TIPO";
	public static final String INT_NDOC= "NDOC";
	public static final String INT_NOMBRE= "NOMBRE";
	public static final String INT_DIRNOT= "DIRNOT";
	public static final String INT_EMAIL= "EMAIL";
	public static final String INT_LOCALIDAD= "LOCALIDAD";
	public static final String INT_CAUT	= "CAUT";
	public static final String INT_CPOSTAL= "C_POSTAL";
}
