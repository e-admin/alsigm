package ieci.tdw.ispac.ispacpublicador.business.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbPreparedStatement;
import ieci.tdw.ispac.ispaclib.db.DbQuery;

/**
 * DAO de acceso a los últimos hitos tratados.
 *
 */
public class MilestoneFilterDAO extends BaseDAO {

    public static int getInfLimitMilestoneId(DbCnt cnt, String externalSystemId) 
    		throws ISPACException {
    	
    	final String sql = "SELECT ID_HITO FROM PUB_ULTIMO_HITO_TRATADO WHERE ID_SISTEMA = ?";

		DbPreparedStatement ps = null;
		DbQuery dbq = null;
		int infLimiteId = -1;
		
		try {
			ps = cnt.prepareDBStatement(sql);
			ps.setString(1, externalSystemId);

			dbq = ps.executeQuery();
			if (dbq.next()) {
				infLimiteId = dbq.getInt("ID_HITO");
			}
		} finally {
			if (dbq != null) {
				dbq.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		
        return infLimiteId;
    }

    public static boolean updateInfLimit(DbCnt cnt, String externalSystemId, 
    		int idHito) throws ISPACException {
    	final String sql = "UPDATE PUB_ULTIMO_HITO_TRATADO SET ID_HITO = ? WHERE ID_SISTEMA = ?";
    	return update(cnt, sql, new Object[] {
    			new Integer(idHito), externalSystemId }) > 0;
    }

    public static int initiateInfLimitMilestoneId(DbCnt cnt, 
    		String externalSystemId, int initialValue) throws ISPACException {
    	final String sql = "INSERT INTO PUB_ULTIMO_HITO_TRATADO (ID_SISTEMA, ID_HITO) VALUES (?, ?)";
    	return update(cnt, sql, new Object[] {
    			externalSystemId, new Integer(initialValue) });
    }
}