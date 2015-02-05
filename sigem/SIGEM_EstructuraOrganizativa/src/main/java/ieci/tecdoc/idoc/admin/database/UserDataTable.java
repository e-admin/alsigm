package ieci.tecdoc.idoc.admin.database;

public class UserDataTable {

	private static final String TN = "iuserdata";
	private static final String CN_ID = "id";
	private static final String CN_CARGO = "cargo";
	private static final String CN_EMAIL = "email";
	private static final String CN_TFNO_MOVIL = "tfno_movil";
	private static final String CN_ID_CERTIFICADO = "id_certificado";
	private static final String CN_NOMBRE = "nombre";
	private static final String CN_APELLIDOS = "apellidos";

	public static final String GET_UPDATE_COLUMN_NAMES_METHOD = "getUpdateBaseColumnNames";
	public static final String GET_INSERT_COLUMN_NAMES_METHOD = "getInsertBaseColumnNames";

	public static final String GET_TABLE_NAME_METHOD = "getBaseTableName";

	public String getBaseTableName() {
		return TN;
	}

	/**
	 * Devuelve los nombres completos de todas las columnas mapeadas de la
	 * tabla, separados por comas.
	 * 
	 * @return El nombre de las columnas.
	 */
	public String getAllColumnNames() {
		String val = CN_ID + ", " + CN_CARGO + ", " + CN_EMAIL + ", " + CN_TFNO_MOVIL + ", "
				+ CN_ID_CERTIFICADO + ", " + CN_NOMBRE + ", " + CN_APELLIDOS;
		return val;
	}

	public String getInsertBaseColumnNames() {
		return getAllColumnNames();
	}

	/**
	 * Devuelve los nombres completos de las columnas mapeadas de la tabla para
	 * modificacion, separados por comas.
	 * 
	 * @return El nombre de las columnas.
	 */
	public String getUpdateBaseColumnNames() {
		String val = CN_CARGO + ", " + CN_EMAIL + ", " + CN_TFNO_MOVIL + ", " + CN_ID_CERTIFICADO
				+ ", " + CN_NOMBRE + ", " + CN_APELLIDOS;
		return val;
	}

	/**
	 * Devuelve la condicion para cargar el usuario por el identificador
	 * 
	 * @param idUsuario
	 * @return Condicion WHERE para cargar el usuario por el identificador
	 */
	public String getLoadIdBaseQual(int idUsuario) {
		String condicion = "where " + CN_ID + " = " + idUsuario;
		return condicion;
	}

	/**
	 * Devuelve la condicion para cargar el usuario por el identificador del
	 * certificado digital
	 * 
	 * @param idCert
	 *            Identificador del certificado digital
	 * @return Condicion WHERE para cargar el usuario por el identificador del
	 *         certificado digital
	 */
	public String getLoadIdCertBaseQual(String idCert) {
		String condicion = "where " + CN_ID_CERTIFICADO + " = '" + idCert + "'";
		return condicion;
	}

	/**
	 * Construye una expresion de busqueda para eliminar el grupo de la tabla
	 * principal.
	 * 
	 * @param id
	 *            El identificador de grupo.
	 * @return La expresion mencionada.
	 */

	public String getDeleteBaseQual(int id) {
		return getLoadIdBaseQual(id);
	}

}
