package es.ieci.tecdoc.fwktd.dir3.core.type;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los nombres de los criterios
 * de las búsquedas de las relaciones entre oficinas y unid. orgánicas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioRelacionUnidOrgOficinaEnum extends CriterioEnum {

	/**
	 *
	 */
	private static final long serialVersionUID = -7550794989214066675L;

	public static final String TABLE_DIR_RELACION_UNID_ORG_OFICINA = "DIR_RELAC_UNID_ORG_OFIC";

	public static final CriterioRelacionUnidOrgOficinaEnum OFICINA_ID = new CriterioRelacionUnidOrgOficinaEnum(TABLE_DIR_RELACION_UNID_ORG_OFICINA, "Identificador de la oficina", "CODIGO_OFICINA");
	public static final CriterioRelacionUnidOrgOficinaEnum OFICINA_NOMBRE = new CriterioRelacionUnidOrgOficinaEnum(TABLE_DIR_RELACION_UNID_ORG_OFICINA, "Nombre de la oficina", "DENOMINACION_OFICINA");

	public static final CriterioRelacionUnidOrgOficinaEnum UO_ID = new CriterioRelacionUnidOrgOficinaEnum(TABLE_DIR_RELACION_UNID_ORG_OFICINA, "Identificador de la unidad orgánica", "CODIGO_UNIDAD_ORGANICA");
	public static final CriterioRelacionUnidOrgOficinaEnum UO_NOMBRE = new CriterioRelacionUnidOrgOficinaEnum(TABLE_DIR_RELACION_UNID_ORG_OFICINA, "Nombre de la unidad orgánica", "NOMBRE_UNIDAD_ORGANICA");

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
	protected CriterioRelacionUnidOrgOficinaEnum(String table, String name,
			String value) {
		super(table, name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 *
	 * @param value
	 *            Valor de la constante
	 * @return Constante.
	 */
	public static CriterioRelacionUnidOrgOficinaEnum getCriterio(String value) {
		return (CriterioRelacionUnidOrgOficinaEnum) StringValuedEnum.getEnum(CriterioRelacionUnidOrgOficinaEnum.class,
				value);
	}
}
