package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * DAO para la gestión de la tabla de trámites.
 *
 */
public class DTTramiteDAO extends ObjectDAO {

	private static final long serialVersionUID = 1L;

	/**
	 * Nombre de la tabla.
	 */
	static final String TABLENAME = "SPAC_DT_TRAMITES";

	/**
	 * Nombre de la secuencia.
	 */
	static final String IDSEQUENCE = "SPAC_SQ_ID_DTTRAMITES";

	/**
	 * Nombre de la columna del identificador único.
	 */
	static final String IDKEY = "ID";
	
	/**
	 * Constructor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public DTTramiteDAO() throws ISPACException {
		//super(null);
		super(null, true);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public DTTramiteDAO(DbCnt cnt) throws ISPACException {
		//super(cnt, null);
		super(cnt, null, true);
	}
	
	/**
	 * Constructor
	 * @param cnt Conexión a la base de datos.
	 * @param id Identificador único del trámite.
	 * @throws ISPACException si ocurre algún error.
	 */
	public DTTramiteDAO(DbCnt cnt, int id) throws ISPACException {
		//super(cnt, id, null);
		super(cnt, id, null, true);
	}

	/**
	 * Obtiene el nombre de la tabla.
	 * @return Nombre de la tabla.
	 */
	public String getTableName() {
		return TABLENAME;
	}

	/**
	 * Construye un nuevo registro.
	 * @param cnt Conexión a la base de datos.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected void newObject(DbCnt cnt) throws ISPACException {
		set(IDKEY, IdSequenceMgr.getIdSequence(cnt, IDSEQUENCE));
	}

	/**
	 * Obtiene la sentencia SQL por defecto.
	 * @param nActionDAO Código de de acción.
	 * @return Sentencia SQL. 
	 * @throws ISPACException si ocurre algún error. 
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	/**
	 * Obtiene el nombre de la columna del identificador único.
	 * @return Nombre de la columna del identificador único.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getKeyName() throws ISPACException {
		return IDKEY;
	}
}