package ieci.tdw.ispac.ispacpublicador.business.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbPreparedStatement;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActiveMilestoneVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ErrorVO;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO de acceso a los errores.
 *
 */
public class ErrorDAO extends BaseDAO {

    public static int insertError(DbCnt cnt, ErrorVO errorVO) 
    		throws ISPACException {
    	
    	final String sql = "INSERT INTO PUB_ERRORES (ID_HITO, ID_APLICACION, ID_SISTEMA, ID_OBJETO, DESCRIPCION, ID_ERROR) VALUES (? ,? ,? ,? ,? ,? )";
    	
    	return update(cnt, sql, new Object[] {
    			new Integer(errorVO.getIdHito()), 
    			new Integer(errorVO.getIdAplicacion()), 
    			errorVO.getIdSistema(), 
    			errorVO.getIdObjeto(), 
    			errorVO.getDescripcion(), 
    			new Integer(errorVO.getIdError())    			
    	});
    }

    public static List IdObjectErrorsList(DbCnt cnt) throws ISPACException {
    	final String sql = "SELECT DISTINCT ID_OBJETO FROM PUB_ERRORES";
    	
		List idList = new ArrayList();
		DbPreparedStatement ps = null;
		DbQuery dbq = null;
		
		try {
			ps = cnt.prepareDBStatement(sql);
			dbq = ps.executeQuery();
			while (dbq.next()) {
				idList.add(dbq.getString("ID_OBJETO"));
		    }
		} finally {
			if (dbq != null) {
				dbq.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		
		return idList;
    }

    public static boolean isErrorMilestone(DbCnt cnt, String idObjeto) 
    		throws ISPACException {
    	final String sql = "SELECT COUNT(ID_OBJETO) FROM PUB_HITOS_ACTIVOS WHERE ID_OBJETO=? AND ESTADO=?";
    	
		Integer res = getInteger(cnt, sql, new Object [] {
				idObjeto, new Integer(ActiveMilestoneVO.ESTADO_ERROR) });
		
		return ((res != null) && (res.intValue() > 0));
    }
}
