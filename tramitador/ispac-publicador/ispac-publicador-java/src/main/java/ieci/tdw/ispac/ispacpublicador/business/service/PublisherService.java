package ieci.tdw.ispac.ispacpublicador.business.service;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispacpublicador.business.bi.IPublisherBI;
import ieci.tdw.ispac.ispacpublicador.business.dao.ActionDAO;
import ieci.tdw.ispac.ispacpublicador.business.dao.ActiveMilestoneDAO;
import ieci.tdw.ispac.ispacpublicador.business.dao.ApplicationDAO;
import ieci.tdw.ispac.ispacpublicador.business.dao.ConditionDAO;
import ieci.tdw.ispac.ispacpublicador.business.dao.ErrorDAO;
import ieci.tdw.ispac.ispacpublicador.business.dao.MilestoneFilterDAO;
import ieci.tdw.ispac.ispacpublicador.business.dao.RuleDAO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActionVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActiveMilestoneVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ConditionVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ErrorVO;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Servicio del Publicador que ofrece la conexión con las funciones
 * ofreceidas por el Business Interface del Publicador. 
 */
public class PublisherService implements IPublisherBI {

	/** Logger de la clase. */
	protected static final Logger logger = 
		Logger.getLogger(PublisherService.class);

	/** Contexto de cliente. */
	private IClientContext context = null;
	
	
	/**
	 * Contructor.
	 * @param context Contexto de cliene.
	 */
	public PublisherService() {
		this.context = new ClientContext();
	}
	
    public ActiveMilestoneVO getMilestone(int id) throws ISPACException  {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.getMilestone(cnt, id);
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public List getActiveApplications() throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ApplicationDAO.getActiveApplications(cnt);
		} finally {
			context.releaseConnection(cnt);
		}
    }

    public int insertActiveMilestone(List milestoneList, List activeAppList, 
    		String extSystemId) throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.insertActiveMilestone(cnt, milestoneList,
					activeAppList,extSystemId);
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public List getActiveMilestoneAppList(String appName) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.getActiveMilestoneAppList(cnt, appName);
		} finally {
			context.releaseConnection(cnt);
		}
    }

    
    public boolean lockActiveMilestone(ActiveMilestoneVO vo) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.lockActiveMilestone(cnt, vo);
		} finally {
			context.releaseConnection(cnt);
		}
    }    
    
    public boolean unlockActiveMilestone(ActiveMilestoneVO vo) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.unlockActiveMilestone(cnt, vo);
		} finally {
			context.releaseConnection(cnt);
		}
    }    

    
    public boolean okActiveMilestone(ActiveMilestoneVO vo) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.okActiveMilestone(cnt, vo);
		} finally {
			context.releaseConnection(cnt);
		}
    }

    public boolean errorActiveMilestone(ActiveMilestoneVO vo) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.errorActiveMilestone(cnt, vo);
		} finally {
			context.releaseConnection(cnt);
		}
    }    

    public List getActions(int eventId) throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ApplicationDAO.getActions(cnt, eventId);
		} finally {
			context.releaseConnection(cnt);
		}
    }

    public List getRuleList(int idPcd, int idFase, int idTramite, int tipoDoc, 
    		int idEvento, int idInfo) throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return RuleDAO.getRuleList(cnt, idPcd, idFase, idTramite, tipoDoc, 
					idEvento, idInfo);
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public ConditionVO getCondition(int conditionId) throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ConditionDAO.getCondition(cnt, conditionId);
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public ActionVO getAction(int actionId) throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActionDAO.getAction(cnt, actionId);
		} finally {
			context.releaseConnection(cnt);
		}
    }

    public int cleanTreatedMilestones() throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.cleanTreatedMilestones(cnt);
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public int getInfLimitMilestoneId(String externalSystemId) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return MilestoneFilterDAO.getInfLimitMilestoneId(cnt, 
					externalSystemId);
		} finally {
			context.releaseConnection(cnt);
		}
	}
    
    private boolean initiateInfLimitMilestoneId(String externalSystemId, 
    		int initialValue)throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return MilestoneFilterDAO.initiateInfLimitMilestoneId(cnt, 
					externalSystemId, initialValue)>0;
		} finally {
			context.releaseConnection(cnt);
		}
	}    
    
    public boolean updateInfLimit(String externalSystemId, int idHito) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			boolean ret = MilestoneFilterDAO.updateInfLimit(cnt, 
					externalSystemId, idHito);
            //si no se ha podido actualizar el valor... (puede ser porque no 
            //existe el registro para el Sistema Externo)
            if (!ret) {
            	//inicializando el valor
                ret = initiateInfLimitMilestoneId(externalSystemId, idHito);
            }
            return ret;
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public boolean insertError(ErrorVO errorVO) throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ErrorDAO.insertError(cnt, errorVO) > 0;
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public List getIdObjectErrorsList() throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ErrorDAO.IdObjectErrorsList(cnt);
		} finally {
			context.releaseConnection(cnt);
		}
    }

    public boolean isErrorMilestone(String idObjeto) throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ErrorDAO.isErrorMilestone(cnt, idObjeto);
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public List getLockedActiveMilestones(String appName) 
    		throws ISPACException {
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			return ActiveMilestoneDAO.getLockedActiveMilestones(cnt, appName);
		} finally {
			context.releaseConnection(cnt);
		}
    }


    public boolean setError(ActiveMilestoneVO activeMilestoneVO, 
    		ErrorVO errorVO) throws ISPACException {
    	boolean update = false;
    	boolean insert = false;
    	
    	try {
    		context.beginTX();
    		
	        //...Pasamos el Hito Activo al estado ERROR, es decir, 
    		//NO se ha termitando su tratamiento de forma correcta
	        update = errorActiveMilestone(activeMilestoneVO); 

	        //Insertamos un Registro en la tabla de errores
	        insert = insertError(errorVO);
	        
	        context.endTX(true);
	        
    	} catch (ISPACException e) {
    		context.endTX(false);
    		throw e;
    	} catch (Throwable e) {
    		context.endTX(false);
    		throw new ISPACException(
    				"Error inesperado al establecer el error", e);
    	}
    	
    	return (insert && update);
    }
    
}
