package ieci.tdw.ispac.ispacpublicador.business.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActionVO;


/**
 * DAO de acceso a la información de las acciones.
 *
 */
public class ActionDAO extends BaseDAO{

    public static ActionVO getAction(DbCnt cnt, int actionId) 
    		throws ISPACException {
    	
	    final String sql = "SELECT * FROM PUB_ACCIONES WHERE ID=?";

	    return (ActionVO) getVO(cnt, sql, new Object[] {new Integer(actionId)}, 
	    		ActionVO.class);
    }

}
