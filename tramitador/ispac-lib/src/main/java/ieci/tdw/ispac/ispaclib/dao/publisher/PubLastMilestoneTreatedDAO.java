package ieci.tdw.ispac.ispaclib.dao.publisher;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

/**
 * DAO de acceso a los últimos hitos tratados.
 *
 */
public class PubLastMilestoneTreatedDAO extends ObjectDAO {
	
	static final String TABLENAME	= "PUB_ULTIMO_HITO_TRATADO";

	
	/**
	 * Constructor.
	 * @throws ISPACException si ocurre algún error.
	 */
	public PubLastMilestoneTreatedDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * Constructor.
	 * @param cnt Información de la conexión a base de datos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public PubLastMilestoneTreatedDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * Obtiene el nombre de la tabla.
	 * @return Nombre de la tabla.
	 */
	public String getTableName() {
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return "";
	}

	protected void newObject(DbCnt cnt) throws ISPACException {}

	public String getKeyName() throws ISPACException {
		return "";
	}

    public static int insertLastMilestoneTreated(DbCnt cnt, String systemId, int milestoneId) 
    		throws ISPACException {
    	
    	String sql = "INSERT INTO " + TABLENAME + "(ID_SISTEMA, ID_HITO) VALUES ('" 
    		+ DBUtil.replaceQuotes(systemId) + "', " + milestoneId + ")";
    	
 		return cnt.directExec(sql);
    }

    public static int updateLastMilestoneTreated(DbCnt cnt, String systemId, int milestoneId) 
    		throws ISPACException {

    	String sql = "UPDATE " + TABLENAME + " SET ID_HITO=" + milestoneId 
    		+ " WHERE ID_SISTEMA='" + DBUtil.replaceQuotes(systemId) + "'";
	
		return cnt.directExec(sql);
    }

}