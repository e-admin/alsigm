/**
 * 
 */
package ieci.tecdoc.sgm.registro.util.database;

/**
 * @author IECISA
 * 
 *         Mapea la tabla SGMRTREGISTRO_DOCS_CSV
 * 
 */
public class RegistroDocumentoCSVTabla {

	private static final String TN = "SGMRTREGISTRO_DOCS_CSV";

	private static final String CN_GUID = "GUID";
	private static final String CN_CSV = "CSV";

	/**
	 * Devuelve el nombre completo de la tabla mapeada sin cualificar.
	 * 
	 * @return El nombre de la tabla.
	 */
	public String getTableName() {
		return TN;
	}

	public String getGuidColumnName() {
		return CN_GUID;
	}

	public String getCsvColumnName() {
		return CN_CSV;
	}

	/**
	 * Devuelve los nombres completos de todas las columnas de la tabla mapeada,
	 * separados por comas.
	 * 
	 * @return Los nombres de las columnas mencionadas.
	 */
	public String getAllColumnNames() {
		return CN_GUID + ", " + CN_CSV;
	}

	/**
	 * Devuelve la cláusula de consulta por el CSV como parámetro.
	 * 
	 * @param csv
	 *            CSV.
	 * @return La cláusula.
	 */
	public String getByCsvQual(String csv) {
		return " WHERE " + CN_CSV + " = '" + csv + "'";
	}

}
