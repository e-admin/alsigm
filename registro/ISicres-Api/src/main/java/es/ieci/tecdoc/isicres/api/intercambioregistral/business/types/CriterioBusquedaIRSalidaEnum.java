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
public class CriterioBusquedaIRSalidaEnum extends StringValuedEnum {

	private static final long serialVersionUID = -5596439714016691121L;

	public static final String TABLE_INTERCAMBIOS_ENTRADA = "SCR_EXREG";


	/*
	 * Constantes para la información de los asientos registrales
	 */
	public static final CriterioBusquedaIRSalidaEnum ID_ARCHIVADOR = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_arch", "id_arch");
	public static final CriterioBusquedaIRSalidaEnum ID_FOLDER = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_fdr", "id_fdr");
	public static final CriterioBusquedaIRSalidaEnum ID_OFICINA = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_ofic", "id_ofic");
	public static final CriterioBusquedaIRSalidaEnum EXCHANGE_DATE = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "exchange_date", "exchange_date");
	public static final CriterioBusquedaIRSalidaEnum STATE = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "state", "state");
	public static final CriterioBusquedaIRSalidaEnum STATE_DATE = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "state_date", "state_date");
	public static final CriterioBusquedaIRSalidaEnum ID_EXCHANGE_SIR = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_exchange_sir", "id_exchange_sir");
	public static final CriterioBusquedaIRSalidaEnum ID_EXCHANGE = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "id_exchange", "id_exchange");
	public static final CriterioBusquedaIRSalidaEnum USERNAME = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "username", "username");
	public static final CriterioBusquedaIRSalidaEnum CODE_TRAMUNIT = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "code_tramunit", "code_tramunit");
	public static final CriterioBusquedaIRSalidaEnum NAME_TRAMUNIT = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "name_tramunit", "name_tramunit");
	public static final CriterioBusquedaIRSalidaEnum CODE_ENTITY = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "code_entity", "code_entity");
	public static final CriterioBusquedaIRSalidaEnum NAME_ENTITY = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "name_entity", "name_entity");
	public static final CriterioBusquedaIRSalidaEnum NUM_REG_ORIGINAL = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "num_reg_orig", "num_reg_orig");
	public static final CriterioBusquedaIRSalidaEnum CONTACTO_ORIGINAL = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "contacto_orig", "contacto_orig");
	public static final CriterioBusquedaIRSalidaEnum COMMENTS = new CriterioBusquedaIRSalidaEnum(TABLE_INTERCAMBIOS_ENTRADA, "comments", "comments");
	public static final CriterioBusquedaIREntradaEnum TYPE_ORIGINAL = new CriterioBusquedaIREntradaEnum(TABLE_INTERCAMBIOS_ENTRADA, "type_orig", "type_orig");


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
	protected CriterioBusquedaIRSalidaEnum(String table, String name, String value) {
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
	public static CriterioBusquedaIRSalidaEnum getCriterio(String value) {
		return (CriterioBusquedaIRSalidaEnum) StringValuedEnum.getEnum(CriterioBusquedaIRSalidaEnum.class, value);
	}
}
