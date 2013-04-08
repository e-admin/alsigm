package ieci.tdw.ispac.ispacpublicador.business.condition;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.ClassLoaderUtil;
import ieci.tdw.ispac.ispacpublicador.business.bi.IPublisherBI;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ConditionException;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.RenderException;
import ieci.tdw.ispac.ispacpublicador.business.service.ServiceLocator;
import ieci.tdw.ispac.ispacpublicador.business.vo.ConditionVO;

import org.apache.log4j.Logger;


/**
 * Clase encargada de instanciar Condiciones. 
 *
 */
public class ConditionFactory {

    /**
     * Logger de la clase.
     */
    protected static final Logger logger = 
    	Logger.getLogger(ConditionFactory.class);
    
	/**
	 * Consturye un nuevo ActionFactory.
	 */
    public ConditionFactory() {}

    /**
	 * @param conditionId Identifcador de una Condición
	 * @return Condición instanciada identificada por <code>'conditionId'</code>
	 * @throws ConditionException
	 */
	public ICondition instanceCondition(int conditionId)
			throws ConditionException{
		
        //Obtener el Servicio del Publicador
        IPublisherBI publisherService = ServiceLocator.lookupPublisherBI();
        ConditionVO conditionVO;
        try {
            conditionVO = publisherService.getCondition(conditionId);
        } catch (ISPACException e) {
            RenderException.show(logger, e);
            throw new ConditionException(e);
        }
        return instanceCondition(conditionVO.getClase());
	}
	
	/**
	 * @param className Nombre de la clase de la Condición
	 * @return Condición instanciada cuya clase es <code>'className'</code>
	 * @throws ActionException
	 */
	public ICondition instanceCondition(String className) 
			throws ConditionException {
		try {
            return (ICondition)ClassLoaderUtil.getInstance(className);
        } catch (ISPACException e) {
            RenderException.show(logger, e);
            throw new ConditionException(e);
        }
	}    
}
