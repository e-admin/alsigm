package ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.dao;

import java.util.List;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.ExpedienteVO;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.InteresadoExpedienteVO;
import ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.vo.InteresadoVO;
import ieci.tdw.ispac.ispacpublicador.business.dao.BaseDAO;

/**
 * DAO de acceso a los expedientes
 *
 */
public class ExpedienteDAO extends BaseDAO {

	/**
	 * Obtiene la información del expediente.
	 * @param cnt Conexión a la base de datos.
	 * @param numExp Número del expediente.
	 * @return Información del expediente.
	 * @throws ISPACException si ocurre algún error.
	 */
    public static ExpedienteVO getExpediente(DbCnt cnt, String numExp) 
    		throws ISPACException {
    	
	    final String sql = "SELECT * FROM SPAC_EXPEDIENTES WHERE NUMEXP=?";

	    return (ExpedienteVO) getVO(cnt, sql, new Object[] { numExp }, 
	    		ExpedienteVO.class);
    }
    
	/**
	 * Obtiene el interesado principal del expediente.
	 * @param cnt Conexión a la base de datos.
	 * @param numExp Número del expediente.
	 * @return Información del expediente.
	 * @throws ISPACException si ocurre algún error.
	 */
    public static InteresadoExpedienteVO getInteresadoExpediente(DbCnt cnt, 
    		String numExp) throws ISPACException {

    	InteresadoExpedienteVO interesado = null;
    	
	    final String sql = "SELECT * FROM SPAC_EXPEDIENTES WHERE NUMEXP=?";
	    interesado = (InteresadoExpedienteVO) getVO(cnt, sql, 
	    		new Object[] { numExp }, InteresadoExpedienteVO.class);
	    
	    if ((interesado != null) && StringUtils.isBlank(interesado.getCnif())) {
	    	interesado = null;
	    }
	    
	    return interesado;
	}

	/**
	 * Obtiene la lista de interesados del expediente.
	 * @param cnt Conexión a la base de datos.
	 * @param numExp Número del expediente.
	 * @return Lista de interesados del expediente.
	 * @throws ISPACException si ocurre algún error.
	 */
    public static List getInteresados(DbCnt cnt, String numExp)
			throws ISPACException {

		final String sql = "SELECT * FROM SPAC_DT_INTERVINIENTES WHERE NUMEXP=?";

		return getVOs(cnt, sql, new Object[] { numExp }, InteresadoVO.class);
	}
    
}
