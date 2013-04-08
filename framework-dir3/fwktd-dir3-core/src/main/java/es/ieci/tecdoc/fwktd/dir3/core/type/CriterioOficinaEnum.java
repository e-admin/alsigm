package es.ieci.tecdoc.fwktd.dir3.core.type;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los nombres de los criterios
 * de las búsquedas de oficinas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CriterioOficinaEnum extends CriterioEnum {

	private static final long serialVersionUID = 2532489523387703362L;

	public static final String TABLE_DIR_OFICINA = "OFI";
	public static final String TABLE_CAT_NIVEL_ADMINISTRACION = "CNA";
	public static final String TABLE_CAT_ESTADO_ENTIDAD = "CEA";
	public static final String TABLE_DIR_UNIDAD_ORGANICA = "UO";


	public static final CriterioOficinaEnum OFICINA_ID = new CriterioOficinaEnum(TABLE_DIR_OFICINA, "Identificador de la oficina", "CODIGO_OFICINA");
	public static final CriterioOficinaEnum OFICINA_NOMBRE = new CriterioOficinaEnum(TABLE_DIR_OFICINA, "Nombre de la oficina", "DENOMINACION_OFICINA");

	public static final CriterioOficinaEnum OFICINA_ID_UNIDAD_RESPONSABLE = new CriterioOficinaEnum(TABLE_DIR_OFICINA, "Unidad responsable de la oficina", "CODIGO_UNIDAD_ORGANICA");

	public static final CriterioOficinaEnum OFICINA_ID_EXTERNO_FUENTE = new CriterioOficinaEnum(TABLE_DIR_OFICINA, "Código externo de la Entidad Pública", "EXTERNO_FUENTE");

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
	protected CriterioOficinaEnum(String table, String name, String value) {
		super(table, name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 *
	 * @param value
	 *            Valor de la constante
	 * @return Constante.
	 */
	public static CriterioOficinaEnum getCriterio(String value) {
		return (CriterioOficinaEnum) StringValuedEnum.getEnum(CriterioOficinaEnum.class,
				value);
	}
}
