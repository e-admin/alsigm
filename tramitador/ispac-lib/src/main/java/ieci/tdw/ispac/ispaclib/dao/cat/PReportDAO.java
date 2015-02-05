package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class PReportDAO extends ObjectDAO {

	public static final String TABLENAME = "SPAC_P_INFORMES";
	public static final String IDOBJ = "ID_OBJ";
	public static final String TPOBJ = "TP_OBJ";
	public static final String IDREPORT = "ID_INFORME";

	/**
	 * Constructor vacio
	 * @throws ISPACException
	 */
	public PReportDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PReportDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * Devuelve la query por defecto
	 * @return query
	 * @throws ISPACException 
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + IDREPORT + " = " + getInt(IDREPORT) 
			+ " AND " + TPOBJ + "=" + getInt(TPOBJ) 
			+ " AND " + IDOBJ + "=" + getInt(IDOBJ);
	}

	/**
	 * Devuelve el nombre de la tabla
	 * @return Nombre de la tabla
	 * @throws ISPACException 
	 */
	public String getTableName() {
		return TABLENAME;
	}

	/**
	 * Devuelve el nombre del campo clave primaria
	 * 
	 * @return nombre de la clave primaria
	 * @throws ISPACException
	 */
	public String getKeyName() throws ISPACException {
		return null;
	}

	/**
	 * Para crear un nuevo registro
	 * 
	 * @param cnt
	 *            conexion
	 * @throws ISPACException
	 */
	public void newObject(DbCnt cnt) throws ISPACException {
	}

	/**
	 * Elimina las relaciones del informe con el objeto indicado.
	 * @param cnt Conexión a base de datos.
	 * @param objectType Tipo de objeto.
	 * @param objectId Identificador del objeto.
	 * @param reportId Identificador del informe. 
	 * @throws ISPACException si ocurre algún error.
	 */
	public static void delete(DbCnt cnt, int objectType, int objectId, int reportId) 
			throws ISPACException {
		String sQuery = "DELETE FROM " + TABLENAME + " WHERE " + IDREPORT + "="
				+ reportId + " AND " + TPOBJ + "=" + objectType + " AND "
				+ IDOBJ + "=" + objectId;
		cnt.directExec(sQuery);
	}

	/**
	 * Elimina todas las relaciones del informe.
	 * @param cnt Conexión a base de datos.
	 * @param reportId Identificador del informe. 
	 * @throws ISPACException si ocurre algún error.
	 */
	public static void delete(DbCnt cnt, int reportId) throws ISPACException {
		String sQuery = "DELETE FROM " + TABLENAME + " WHERE " + IDREPORT + "=" + reportId ;
		cnt.directExec(sQuery);
	}
}
