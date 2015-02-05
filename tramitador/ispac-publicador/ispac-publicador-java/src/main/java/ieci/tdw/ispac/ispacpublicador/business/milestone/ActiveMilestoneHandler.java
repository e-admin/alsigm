/*
 * Created on 11-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.milestone;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispacpublicador.business.bi.IPublisherBI;
import ieci.tdw.ispac.ispacpublicador.business.context.ContextBuilder;
import ieci.tdw.ispac.ispacpublicador.business.context.ContextProperties;
import ieci.tdw.ispac.ispacpublicador.business.context.MilestoneContext;
import ieci.tdw.ispac.ispacpublicador.business.context.RuleContext;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleError;
import ieci.tdw.ispac.ispacpublicador.business.rule.RuleHandler;
import ieci.tdw.ispac.ispacpublicador.business.service.ServiceLocator;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActiveMilestoneVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ErrorVO;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * Clase que se encarga de procesar un Hito Activo.
 * @author Ildefonso Noreña
 * 
 */
public class ActiveMilestoneHandler {

    /**
     * Logger
     */
    protected Logger logger = Logger.getLogger(ActiveMilestoneHandler.class);
    
    protected Logger ruleLogger = Logger.getLogger("MILESTONE_LOGGER");
    
    protected Logger processMilisteneLogger = Logger.getLogger("MILESTONE_PROCESS");
    
    private ActiveMilestoneVO activeMilestoneVO;
    
    /**
     * Construye un ActiveMilestoneHandler inicializando el Hito Activo que maneja
     * con el párametro <code>'activeMilestone'</code> pasado. 
     * @param activeMilestone
     */
    public ActiveMilestoneHandler(ActiveMilestoneVO activeMilestone){
        this.activeMilestoneVO = activeMilestone;
    }
    
    /**
     * Construye un ActiveMilestoneHandler inicializando el Hito Activo. 
     */
    public ActiveMilestoneHandler(){
        this(null);
    }

    
    /**
     * Procesa los Hitos Activos que se corresponden con la aplicación
     * cuyo nombre es <code>'appName'</code>.
     * @param appName Nombre de la aplicación.
     * @throws ISPACException
     */
    public void processActiveMilestones(String appName) throws ISPACException {
        
        //Obtenemos los hitos activos para la aplicación
        IPublisherBI publisherService = ServiceLocator.lookupPublisherBI();
        List list = publisherService.getActiveMilestoneAppList(appName);
        
		//Filtramos los Hitos Activos en los que el ID_OBJETO tenga un error asociado, ya que no se puede
        //procesar un hito cuyo campo ID_OBJETO este relacionado con un error, es decir, que haya
        //un registro en la tabla de errores cuyo ID_OBJETO coincida con el de un Hito Activo.
        filterMiletones(list);
        
        for (Iterator iter = list.iterator(); iter.hasNext();){
            activeMilestoneVO = (ActiveMilestoneVO) iter.next();
            processActiveMilestone();
        }
        
    }
    
    /**
     * Filtra los Hitos Activos. Elimina de la lista <code>'milestoneList'</code>
     * aquellos hitos cuyo ID_OBJETO esté relacionado con un error, es decir,
     * que existe un registro en la tabla que mantiene los errores que haga
     * referencia al ID_OBJETO de un nuevo hito incluido <code>'milestoneList'</code>.
     * @throws ISPACException
     */
    private static void filterMiletones(List milestoneList) 
    		throws ISPACException {
    	
    	if (!CollectionUtils.isEmpty(milestoneList)) {
    		
	        //Obtenemos el servicio del publicador
	        IPublisherBI publisherService = ServiceLocator.lookupPublisherBI();
	
	        //Lista que contiene los campos ID_OBJETO que aparecen en los 
	        //registros de error
	        List list = publisherService.getIdObjectErrorsList();
	        
	        // Eliminar los hitos relacionados con registros de error
	        if (!CollectionUtils.isEmpty(list)) {
	        	Iterator it = milestoneList.iterator();
	        	ActiveMilestoneVO vo;
		        while (it.hasNext()) {
		            vo = (ActiveMilestoneVO) it.next();
		            if (list.contains(vo.getIdObjeto())){
		              it.remove();
		            }            
		        }
	        }
    	}
    }
    
    
    /**
     * Procesa el Hito Activo almacenado en el atributo <code>'activeMilestoneVO'</code>.
     * @throws ISPACException
     */
    public void processActiveMilestone() throws ISPACException {
    	
        //Obtener el Servicio del Publicador
        IPublisherBI publisherService = ServiceLocator.lookupPublisherBI();
        
        // Comprobar que el hito no esté relacionado (idObjeto) con otro 
        // en estado de error.
        if (publisherService.isErrorMilestone(activeMilestoneVO.getIdObjeto())) {
         	return;
        }
        
        //Bloqueamos el Hito
        if (!publisherService.lockActiveMilestone(activeMilestoneVO)) {
            throw new ISPACException(
            		"No se ha podido bloquear el hito cuyos datos son: "
            			+ activeMilestoneVO.toString() + ".");
        }
        
        //Obtenemos las reglas asociadas al evento que originó el Hito
        List list = publisherService.getRuleList(
        		activeMilestoneVO.getIdProcedimiento(), 
        		activeMilestoneVO.getIdFase(), 
        		activeMilestoneVO.getIdTramite(), 
        		activeMilestoneVO.getTipoDoc(),  
        		activeMilestoneVO.getIdEvento(),
        		activeMilestoneVO.getIdInfo());

        //Si exsten reglas asociadas al Hito Activo...
        if (list!= null && list.size()>0){ 
            //...evaluamos las reglas
	        //Creamos el contexto de la Regla
            String infoAux = activeMilestoneVO.getInfoAux();
            MilestoneContext ctx = new MilestoneContext(infoAux, ContextProperties.CTX_INFO_AUX);
            Map map = ctx.getProperties();
            
            ContextBuilder ctxBuilder = new ContextBuilder();
            ctxBuilder.newContext();
            ctxBuilder.addContext(map);
            ctxBuilder.addContext(activeMilestoneVO, false);
            RuleContext rctx = new RuleContext(ctxBuilder.getContext().toString());
	        
	        //Manejador de la regla
	        RuleHandler ruleHandler = new RuleHandler(rctx);
	        
	        //Procesamos la lista de reglas
            ruleHandler.processsRuleList(list);
            

            //Comprobamos si se han producido errores en el procesamiento de la regla
            if (rctx.getMessages().size() >0){
                //TODO Falta determinar cuando se considera que ha habido error en el procesamiento del Hito:
                //		- Si se ha producido una Excepcion en la evaluacion de una Regla???
                //		- Si se ha producido una Excepcion en la ejecucion de una Accion
                //		- Si se ha producido une Error en la ejecucion de una Accion, que sera cuando la ejecucion retorna false
              ruleLogger.info(rctx.toString());
            }
	        //Si se ha producido un error en el procesamiento de las reglas...
	        RuleError error = rctx.getError(); 
            if (error != null){
	            
	            //TODO Terminar: Obtener el tipo de error
	            ErrorVO errorVO = new ErrorVO(activeMilestoneVO.getIdHito(), activeMilestoneVO.getIdAplicacion(), activeMilestoneVO.getIdSistema(), activeMilestoneVO.getIdObjeto(), error.getMessage(), error.getCodigoError());
	            
	            //Cambios al estado de ERROR al hito y se crea un nuevo registro de error
	            boolean result = publisherService.setError(activeMilestoneVO, errorVO);
	            
	            if (!result)
	                throw new ISPACException("No se ha podido actualizar al estado ERROR el hito cuyos datos son: "+activeMilestoneVO.toString()+".");
	            return;
	        }
            if (processMilisteneLogger.isInfoEnabled())
                processMilisteneLogger.info("Hito '"+activeMilestoneVO.toString()+"' procesado.");
            
        }
        //Si no hay reglas asociadas al Hito Activo directamente se pasara al estado de CORRECTO, tambien si se ha procesado correctamente el Hito Activo tras procesar las reglas asociadas
        //Pasamos el Hito Activo al estado CORRECTO, es decir, se ha termitando su tratamiento de forma correcta 
        if (!publisherService.okActiveMilestone(activeMilestoneVO))
            throw new ISPACException("No se ha podido actualizar al estado CORRECTO el hito cuyos datos son: "+activeMilestoneVO.toString()+".");
    }
    
}
