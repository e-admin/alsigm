package es.ieci.tecdoc.fwktd.dir3.core.type;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los nombres de los criterios
 * de las búsquedas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioUnidadOrganicaEnum extends CriterioEnum {

	private static final long serialVersionUID = -6615854045641957655L;

	public static final String TABLE_DIR_UNIDAD_ORGANICA = "UO";
	public static final String TABLE_CAT_NIVEL_ADMINISTRACION = "CNA";
	public static final String TABLE_CAT_ESTADO_ENTIDAD = "CEA";

	public static final String TABLE_UNIDAD_ORGANICA_SUPERIOR = "UO_SUP";
	public static final String TABLE_UNIDAD_ORGANICA_PRINCIPAL = "UO_PRI";
	public static final String TABLE_UNIDAD_ORGANICA_EDP = "UO_EDP";

	public static final CriterioUnidadOrganicaEnum UO_ID = new CriterioUnidadOrganicaEnum(TABLE_DIR_UNIDAD_ORGANICA, "Identificador de la unidad orgánica", "CODIGO_UNIDAD_ORGANICA");
	public static final CriterioUnidadOrganicaEnum UO_NOMBRE = new CriterioUnidadOrganicaEnum(TABLE_DIR_UNIDAD_ORGANICA, "Nombre de la unidad orgánica", "NOMBRE_UNIDAD_ORGANICA");
	public static final CriterioUnidadOrganicaEnum UO_ID_EXTERNO_FUENTE = new CriterioUnidadOrganicaEnum(TABLE_DIR_UNIDAD_ORGANICA, "Código externo de la Entidad Pública", "CODIGO_EXTERNO_FUENTE");

	public static final CriterioUnidadOrganicaEnum UO_ID_UNIDAD_ORGANICA_SUPERIOR = new CriterioUnidadOrganicaEnum(TABLE_DIR_UNIDAD_ORGANICA, "Identificador de la unidad orgánica superior", "CODIGO_UNIDAD_SUP_JERARQUICA");
	public static final CriterioUnidadOrganicaEnum UO_NOMBRE_UNIDAD_ORGANICA_SUPERIOR = new CriterioUnidadOrganicaEnum(TABLE_UNIDAD_ORGANICA_SUPERIOR, "Nombre de la unidad orgánica superior", "DENOM_UNIDAD_SUP_JERARQUICA");

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
	protected CriterioUnidadOrganicaEnum(String table, String name, String value) {
		super(table, name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 *
	 * @param value
	 *            Valor de la constante
	 * @return Constante.
	 */
	public static CriterioUnidadOrganicaEnum getCriterio(String value) {
		return (CriterioUnidadOrganicaEnum) StringValuedEnum.getEnum(CriterioUnidadOrganicaEnum.class,
				value);
	}
}
