package ieci.tdw.ispac.ispacpublicador.business.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.InetUtils;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.RenderException;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActiveMilestoneVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ApplicationVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.MilestoneVO;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * DAO de acceso a la información de hitos activos.
 *
 */
public class ActiveMilestoneDAO extends BaseDAO {

	/** Logger de la clase. */
    private static final Logger logger = 
    	Logger.getLogger(ActiveMilestoneDAO.class);


    public static ActiveMilestoneVO getMilestone(DbCnt cnt, int id) 
    		throws ISPACException {

	    final String sql = "SELECT * FROM PUB_HITOS_ACTIVOS WHERE ID_HITO=?";
	
	    return (ActiveMilestoneVO) getVO(cnt, sql, new Object[] {
	    		new Integer(id)}, ActiveMilestoneVO.class);
    }

    
    
    public static int insertActiveMilestone(DbCnt cnt, List milestoneList, 
    		List activeAppList, String extSystemId) throws ISPACException {
        int result = 0;
        
        final String sql = "INSERT INTO PUB_HITOS_ACTIVOS (ID_HITO, ID_PCD, ID_FASE, ID_TRAMITE, TIPO_DOC, ID_OBJETO, ID_INFO, INFO_AUX, ID_EVENTO, ESTADO, ID_APLICACION, IP_MAQUINA, FECHA, ID_SISTEMA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int res;
        
        for (Iterator iter = milestoneList.iterator(); iter.hasNext();) {
            MilestoneVO milestoneVO = (MilestoneVO) iter.next();
            for (Iterator iterator = activeAppList.iterator(); iterator.hasNext();) {
                ApplicationVO appVO = (ApplicationVO) iterator.next();
                
                int idHito = milestoneVO.getIdHito();
                int idPcd = milestoneVO.getIdProcedimiento();
                int idFase = milestoneVO.getIdFase();
                int idTramite = milestoneVO.getIdTramite();
                int tipoDoc = milestoneVO.getTipoDoc();
                String idObjeto = milestoneVO.getIdObjeto();
                int idInfo = milestoneVO.getIdInfo();
                String info = milestoneVO.getInfo();
	            
	            int idEvento = milestoneVO.getIdEvento();
                
                int idAplicacion = appVO.getId();
                
                //Obtenemos la direccion ip de la maquina
                String ipMaquina = getLocalHostAddress();

                
                res = update(cnt, sql, new Object [] {
                		new Integer(idHito),
			    	    new Integer(idPcd),
			    	    new Integer(idFase),
			    	    new Integer(idTramite),
			    	    new Integer(tipoDoc),
			    	    idObjeto,
			    	    new Integer(idInfo),
			    	    info,
			    	    new Integer(idEvento),
			    	    new Integer(ActiveMilestoneVO.ESTADO_INICIAL),
			    	    new Integer(idAplicacion),
			    	    ipMaquina,
			    	    new Timestamp(System.currentTimeMillis()),
			    	    extSystemId });
                
	    	    if (res > 0) {
	    	    	result += res;
	    	    }
            }
        }
        return result;
    }

    public static List getActiveMilestoneAppList(DbCnt cnt, String appName) 
    		throws ISPACException {

	    final String sql = "SELECT * FROM PUB_HITOS_ACTIVOS WHERE ESTADO=? AND ID_APLICACION IN (SELECT ID FROM PUB_APLICACIONES_HITOS WHERE NOMBRE=?) ORDER BY ID_HITO";
	    
	    return getVOs(cnt, sql, new Object[] {
	    		new Integer(ActiveMilestoneVO.ESTADO_INICIAL), appName }, 
	    		ActiveMilestoneVO.class);
    }
    
    
    public static boolean lockActiveMilestone(DbCnt cnt, ActiveMilestoneVO vo) 
    		throws ISPACException {
    	return updateActiveMilestoneStatus(cnt, vo.getIdHito(), 
    			vo.getIdAplicacion(), ActiveMilestoneVO.ESTADO_BLOQUEADO);
    }

    
    public static boolean unlockActiveMilestone(DbCnt cnt, ActiveMilestoneVO vo) 
    		throws ISPACException {
    	return updateActiveMilestoneStatus(cnt, vo.getIdHito(), 
    			vo.getIdAplicacion(), ActiveMilestoneVO.ESTADO_INICIAL);
   }    
    
    public static boolean okActiveMilestone(DbCnt cnt, ActiveMilestoneVO vo) 
    		throws ISPACException {
    	return updateActiveMilestoneStatus(cnt, vo.getIdHito(), 
    			vo.getIdAplicacion(), ActiveMilestoneVO.ESTADO_CORRECTO);
    }
    

    public static boolean errorActiveMilestone(DbCnt cnt, ActiveMilestoneVO vo) 
    		throws ISPACException {
    	return updateActiveMilestoneStatus(cnt, vo.getIdHito(), 
    			vo.getIdAplicacion(), ActiveMilestoneVO.ESTADO_ERROR);
    }

    private static boolean updateActiveMilestoneStatus(DbCnt cnt, 
    		int milestoneId, int appId, int status) throws ISPACException {
	    final String sql = "UPDATE PUB_HITOS_ACTIVOS SET ESTADO=?,FECHA=?,IP_MAQUINA=? WHERE ID_HITO=? AND ID_APLICACION=?";
	    
	    int res = update(cnt, sql, new Object[] {
	    		new Integer(status),
	    		new Timestamp(System.currentTimeMillis()),
	    		getLocalHostAddress(),
	    		new Integer(milestoneId),
	    		new Integer(appId) });
	    
	    return (res > 0);
    }

    public static int cleanTreatedMilestones(DbCnt cnt) throws ISPACException {
    	
	    final String sql = "DELETE FROM PUB_HITOS_ACTIVOS WHERE ESTADO = ?";

	    return update(cnt, sql, new Object[] {
	    		new Integer(ActiveMilestoneVO.ESTADO_CORRECTO) });
    }
    
    public static List getLockedActiveMilestones(DbCnt cnt, String appName) 
    		throws ISPACException {

    	final String sql = "SELECT * FROM PUB_HITOS_ACTIVOS WHERE ESTADO=? AND ID_APLICACION IN (SELECT ID FROM PUB_APLICACIONES_HITOS WHERE NOMBRE=?)";
	    
    	return getVOs(cnt, sql, new Object[] {
    			new Integer(ActiveMilestoneVO.ESTADO_BLOQUEADO), appName}, 
    			ActiveMilestoneVO.class);
    }    
    
    /**
     * @return una cadena con la dirección IP de la máquina local,
     * si se produce un error al obtenerla devolverá la cadena 'undefined' 
     */
    private static String getLocalHostAddress(){
        String ipMaquina;
        try {
            ipMaquina = InetUtils.getLocalHostAddress();
        } catch (ISPACException e) {
            ipMaquina = "undefined";
            RenderException.show(logger, e);
        }
        return ipMaquina;
    }
}
