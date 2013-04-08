package ieci.tdw.ispac.ispacpublicador.business.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispacpublicador.business.vo.ApplicationVO;

import java.util.List;

/**
 * DAO de acceso a la información de las aplicaciones.
 *
 */
public class ApplicationDAO extends BaseDAO {

	/**
	 * Obtiene la lista de aplicaciones activas.
	 * @param cnt Conexión a la base de datos.
	 * @return lista de aplicaciones activas.
	 * @throws ISPACException si ocurre algún error.
	 */
    public static List getActiveApplications(DbCnt cnt) throws ISPACException {

    	final String sql = "SELECT * FROM PUB_APLICACIONES_HITOS WHERE ACTIVA = 1 AND ID IN (SELECT DISTINCT(ID_APLICACION) FROM PUB_REGLAS)";
	
	    return getVOs(cnt, sql, ApplicationVO.class);
    }

	/**
	 * Obtiene la lista de aplicaciones relacionadas con un evento.
	 * @param cnt Conexión a la base de datos.
	 * @param eventId Identificador del evento.
	 * @return lista de aplicaciones.
	 * @throws ISPACException si ocurre algún error.
	 */
    public static List getActions(DbCnt cnt, int eventId) 
    		throws ISPACException {
    	
    	final String sql = "SELECT * FROM PUB_APLICACIONES_HITOS WHERE ACTIVA=1 AND ID_EVENTO=?";
    	
	    return getVOs(cnt, sql, new Object[] {new Integer(eventId)}, 
	    		ApplicationVO.class);
    }
}
