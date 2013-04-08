package ieci.tdw.ispac.ispacpublicador.business.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispacpublicador.business.vo.ConditionVO;

/**
 * DAO de acceso a las condiciones.
 *
 */
public class ConditionDAO extends BaseDAO {
	
    public static ConditionVO getCondition(DbCnt cnt, int conditionId) 
    		throws ISPACException {
        
    	final String sql = "SELECT * FROM PUB_CONDICIONES WHERE ID = ?";

    	return (ConditionVO) getVO(cnt, sql, new Object [] {
    			new Integer(conditionId)} , ConditionVO.class);
    }

}
