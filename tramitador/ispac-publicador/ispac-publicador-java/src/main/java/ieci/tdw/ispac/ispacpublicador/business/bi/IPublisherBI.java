package ieci.tdw.ispac.ispacpublicador.business.bi;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActionVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ActiveMilestoneVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ConditionVO;
import ieci.tdw.ispac.ispacpublicador.business.vo.ErrorVO;

import java.util.List;


/**
 * Business Interface del Publicador.
 * Conjunto de servicios que se ofrecen.		
 * @author Ildefonso Noreña
 */
public interface IPublisherBI {

    
    /**
     * @param id Identificador del Hito
     * @return un Value Object con la información del Hito identificado por <code>'id'</code>
     * @throws ISPACException
     */
    public ActiveMilestoneVO getMilestone(int id) throws ISPACException;
    
    /**
     * @return un Identificador que representa un límite inferior de los hitos activos,
     * es decir, por debajo de ese valor ya se han tratado todos los hitos. De esta 
     * manera cuando se pida al Lector los nuevos hitos, se restringirá que tengan un
     * id mayor al devuelto por esta función. Se obtendrá el valor de retorno
     * para el identificador del sistema indicado en <code>'externalSystemId'</code>
     * @param externalSystemId Identificador del Sistema externo sobre el que se quiere obtener el valor.
     * @throws ISPACException
     */
    public int getInfLimitMilestoneId(String externalSystemId) throws ISPACException;

    /**
     * @return una lista con las aplicaciones que estén activas y que tengan alguna regla
     * dada de alta.
     * @throws ISPACException
     */
    public List getActiveApplications() throws ISPACException;

    /**
     * Este método insertará en la tabla de hitos activos un registro por cada
     * hito que haya en <code>milestoneList</code> y por cada aplicación existente
     * en <code>activeAppList</code>
     * 
     * @param milestoneList Lista de Nuevos hitos a insertar.
     * @param activeAppList Lista de Aplicaciones activas.
     * @param extSystemId Identificador del sistema del que procede la lista de hitos
     * @return número de Hitos Activos insertados
     * @throws ISPACException
     */
    public int insertActiveMilestone(List milestoneList, List activeAppList, 
    		String extSystemId) throws ISPACException;
    
    
    /**
     * @param appName Nombre de la aplicación
     * @return una Lista con los Hitos Activos que se corresponden con la aplicación
     * cuyo nombre es <code>'appName'</code> en estado inicial.
     * @throws ISPACException
     */
    public List getActiveMilestoneAppList(String appName) throws ISPACException;

    /**
     * @param eventId Identificador del evento.
     * @return una lista con las acciones que unifican con los datos del contexto del Hito Activo.
     * @throws ISPACException
     */
    public List getActions(int eventId) throws ISPACException;

    /**
     * @param idPcd Identificador del Procedimiento que generó el hito
     * @param idFase Identificador de la Fase que generó el hito
     * @param idTramite Identificador del Trámite que generó el hito
     * @param tipoDoc Tipo de Documento que generó el hito
     * @param idEvento Identificador del Evento que generó el hito
     * @param idInfo Identificador de la información auxiliar
     * @return una lista con las reglas cuyo evento sea el mismo que el evento que generó el hito.
     * @throws ISPACException
     */
    public List getRuleList(int idPcd, int idFase, int idTramite, int tipoDoc, 
    		int idEvento, int idInfo) throws ISPACException;

    /**
     * @param vo Value Object con los datos del Hito a bloquear.
     * @return true si se ha podido bloquear el hito, false en caso contrario.
     * @throws ISPACException
     */
    public boolean lockActiveMilestone(ActiveMilestoneVO vo) 
    	throws ISPACException;  
    

    /**
     * @param vo Value Object con los datos del Hito a desbloquear.
     * @return true si se ha podido desbloquear el hito, false en caso contrario.
     * @throws ISPACException
     */
    public boolean unlockActiveMilestone(ActiveMilestoneVO vo) 
    	throws ISPACException;  
        
    /**
     * @param vo Value Object con los datos del Hito.
     * @return true si se ha podido poner el hito en estado OK, false en caso contrario.
     * @throws ISPACException
     */
    public boolean okActiveMilestone(ActiveMilestoneVO vo) 
    	throws ISPACException;    

    
    /**
     * @param vo Value Object con los datos del Hito.
     * @return true si se ha podido poner el hito en estado ERROR, false en caso contrario.
     * @throws ISPACException
     */    
    public boolean errorActiveMilestone(ActiveMilestoneVO vo) 
    	throws ISPACException;

    /**
     * @param conditionId Identificador de la condición a obtener.
     * @return un Value Object que contiene la información de la Condición identificada
     * por <code>'conditionId'</code>.
     * @throws ISPACException
     */
    public ConditionVO getCondition(int conditionId) throws ISPACException;

    /**
     * @param actionId Identificador de la Acción a obtener.
     * @return un Value Object que contiene la información de la Acción identificada
     * por <code>'actionId'</code>.
     * @throws ISPACException
     */
    public ActionVO getAction(int actionId) throws ISPACException;       
    
    
    /**
     * Este método se encarga de liberar aquellos hitos activos
     * para los cuales se ha completado su tratamiento, es decir,
     * todas las aplicaciones activas han tratado ese hito activo.
     * @return Número de hitos liberados.	
     * @throws ISPACException
     */
    public int cleanTreatedMilestones() throws ISPACException;

    /**
     * Método que establece un límite inferior a la hora de insertar
     * nuevos Hitos. Para obtener nuevos Hitos Activos estos deberán
     * tener un identificador superior a <code>'idHito'</code>.
     * @param externalSystemId Identificador del Sistema Externo campo por el que se identifica el registro a actualizar 
     * @param idHito Identificador de Hito activo que se establecerá 
     * como límite inferior para el filtro.
     * @return true si se ya podido actualizar este valor, false en caso contrario.
     * @throws ISPACException
     */
    public boolean updateInfLimit(String externalSystemId, int idHito) 
    	throws ISPACException;


    /**
     * @param errorVO Value Object con los datos del Error a insertar.
     * @return true si se ha insertado el error correctamente, false en caso contrario
     * @throws ISPACException
     */
    public boolean insertError(ErrorVO errorVO) throws ISPACException;

    /**
     * @return una lista de identificadores de objeto (ID_OBJETO) que aparecen
     * en la tabla de errores.
     * @throws ISPACException
     */
    public List getIdObjectErrorsList()throws ISPACException;

    /**
     * Indica si hay algún hito en estado de error con ese identificador de
     * objeto.
     * @param idObjeto Identificador de objeto.
     * @return true si hay algún hito.
     * @throws ISPACException si ocurre algún error.
     */
    public boolean isErrorMilestone(String idObjeto) throws ISPACException;

    /**
     * @param appName Nombre de la aplicación de la cual se quieren obtener los hitos que está bloqueando.
     * @return una lista con los Hitos Activos que se encuentre en 
     * el estado BLOQUEADO por la aplicación cuyo nombre es <code>'appName'</code>.
     * @throws ISPACException
     */
    public List getLockedActiveMilestones(String appName)throws ISPACException;

    /**
     * Método que cambia a estado de ERROR al Hito contenido en <code>'activeMilestoneVO'</code>,
     * así como crear un nuego registro con los datos del error producido cuyos datos
     * se encuentran en <code>'errorVO'</code>
     * @param activeMilestoneVO Value Object que representa al Hito Activo a situar en el estado de error
     * @param errorVO Value Object con los datos del error a insertar
     * @return true si el cambio de estado e inserción de un nuevo error han sido correctos,
     * false en caso contrario
     * @throws ISPACException
     */
    public boolean setError(ActiveMilestoneVO activeMilestoneVO, 
    		ErrorVO errorVO)throws ISPACException;
}
