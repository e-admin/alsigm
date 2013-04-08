package ieci.tdw.ispac.ispacpublicador.business.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispacpublicador.business.vo.RuleVO;

import java.util.List;

/**
 * DAO de acceso a las reglas.
 *
 */
public class RuleDAO extends BaseDAO {

    public static List getRuleList(DbCnt cnt, int idPcd, int idFase, 
    			int idTramite, int tipoDoc, int idEvento, int idInfo) 
    		throws ISPACException {
    	
    	final String sql = "SELECT * FROM PUB_REGLAS WHERE (ID_PCD = ? OR ID_PCD < 0) AND (ID_FASE = ? OR ID_FASE < 0) AND (ID_TRAMITE = ? OR ID_TRAMITE < 0) AND (TIPO_DOC = ? OR TIPO_DOC < 0) AND (ID_EVENTO = ? OR ID_EVENTO < 0) AND (ID_INFO = ? OR ID_INFO < 0) ORDER BY ORDEN ASC";

    	return getVOs(cnt, sql, new Object[] {
    			new Integer(idPcd), 
    			new Integer(idFase),
    			new Integer(idTramite), 
    			new Integer(tipoDoc),
    			new Integer(idEvento),
    			new Integer(idInfo) }, RuleVO.class);
	}
}
