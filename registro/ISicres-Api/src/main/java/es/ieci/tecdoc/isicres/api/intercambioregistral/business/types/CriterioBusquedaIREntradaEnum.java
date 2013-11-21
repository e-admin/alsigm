package es.ieci.tecdoc.isicres.api.intercambioregistral.business.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los nombres de los criterios
 * de las búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioBusquedaIREntradaEnum extends StringValuedEnum {

	private static final long serialVersionUID = -5596439714016691121L;

	public static final String TABLE_INTERCAMBIOS_ENTRADA = "SCR_EXREG_IN";


	/*
	 * Constantes para la información de los asientos registrales
	 */
	public static final CriterioBusquedaIREntradaEnum ID_ARCHIVADOR = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_arch", "id_arch");
	public static final CriterioBusquedaIREntradaEnum ID_FOLDER = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_fdr", "id_fdr");
	public static final CriterioBusquedaIREntradaEnum ID_OFICINA = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_ofic", "id_ofic");
	public static final CriterioBusquedaIREntradaEnum EXCHANGE_DATE = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "exchange_date", "exchange_date");
	public static final CriterioBusquedaIREntradaEnum STATE = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "state", "state");
	public static final CriterioBusquedaIREntradaEnum STATE_DATE = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "state_date", "state_date");
	public static final CriterioBusquedaIREntradaEnum ID_EXCHANGE_SIR = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_exchange_sir", "id_exchange_sir");
	public static final CriterioBusquedaIREntradaEnum ID_EXCHANGE = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_exchange", "id_exchange");
	public static final CriterioBusquedaIREntradaEnum USERNAME = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "username", "username");
	public static final CriterioBusquedaIREntradaEnum CODE_TRAMUNIT = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "code_tramunit", "code_tramunit");
	public static final CriterioBusquedaIREntradaEnum NAME_TRAMUNIT = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "name_tramunit", "name_tramunit");
	public static final CriterioBusquedaIREntradaEnum CODE_ENTITY = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "code_entity", "code_entity");
	public static final CriterioBusquedaIREntradaEnum NAME_ENTITY = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "name_entity", "name_entity");
	public static final CriterioBusquedaIREntradaEnum NUM_REG_ORIGINAL = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "num_reg_orig", "num_reg_orig");
	public static final CriterioBusquedaIREntradaEnum CONTACTO_ORIGINAL = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "contacto_orig", "contacto_orig");
	public static final CriterioBusquedaIREntradaEnum COMMENTS = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "comments", "comments");

	/**
	 * Nombre de la tabla.
	 */
	private String table = null;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            Nombre de la tabla.
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected CriterioBusquedaIREntradaEnum(String table, String name, String value) {
		super(name, value);
		setTable(table);
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static CriterioBusquedaIREntradaEnum getCriterio(String value) {
		return (CriterioBusquedaIREntradaEnum) StringValuedEnum.getEnum(CriterioBusquedaIREntradaEnum.class, value);
	}
}
