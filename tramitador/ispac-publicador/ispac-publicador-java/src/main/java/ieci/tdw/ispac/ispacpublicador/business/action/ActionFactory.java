package ieci.tdw.ispac.ispacpublicador.business.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.ClassLoaderUtil;
import ieci.tdw.ispac.ispacpublicador.business.bi.IPublisherBI;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.ActionException;
import ieci.tdw.ispac.ispacpublicador.business.exceptions.RenderException;
import ieci.tdw.ispac.ispacpublicador.business.service.ServiceLocator;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActionVO;

import org.apache.log4j.Logger;

/**
 * Clase encargada de instanciar Acciones.
 * 
 */
public class ActionFactory {
	
    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(ActionFactory.class);
     
	/**
	 * Construye un nuevo ActionFactory.
	 */
	public ActionFactory() {}
	
	/**
	 * @param actionId Identifcador de una Acción
	 * @return Acción instanciada identificada por <code>'actionId'</code>
	 * @throws ActionException
	 */
	public IAction instanceAction(int actionId) throws ActionException{
	    
        //Obtener el Servicio del Publicador
        IPublisherBI publisherService = ServiceLocator.lookupPublisherBI();
        ActionVO actionVO;
        try {
            actionVO = publisherService.getAction(actionId);
        } catch (ISPACException e) {
            RenderException.show(logger, e);
            throw new ActionException(e);
        }
	    return instanceAction(actionVO.getClase());
	}
	
	/**
	 * @param className Nombre de la clase de la Acción
	 * @return Acción instanciada cuya clase es <code>'className'</code>
	 * @throws ActionException
	 */
	public IAction instanceAction(String className) throws ActionException{
		try {
		    return (IAction)ClassLoaderUtil.getInstance(className); 
        } catch (ISPACException e) {
            RenderException.show(logger, e);
            throw new ActionException(e);
        }
	}
}
