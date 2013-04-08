package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacmgr.action.form.CustomBatchForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.context.NextActivity;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author RAULHC
 *
 */
public class CrearSolicitudSubsanacionAction extends BaseAction {
        
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

        //-----------------------------------------------------------------------
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);

		
		// Estado de tramitación
    	IState state = managerAPI.currentState(getStateticket(request));        
        //-----------------------------------------------------------------------
        
        //Documentos Seleccionados
        String[] docSeleccionados = ((CustomBatchForm)form).getMultibox();
        
        //Comprobar que se han seleccionado Documentos
        if (StringUtils.isEmpty(request.getParameter("return")) && docSeleccionados.length == 0) {
	    	ActionMessages actionMessages = new ActionMessages();
	    	actionMessages.add(ActionMessages.GLOBAL_MESSAGE, 
	    	        new ActionMessage("custom.message","Debe marcar los documentos que desea subsanar."));
	    	saveErrors(request,actionMessages);
            return mapping.findForward("error");
        }
        
        //Documento XML con la lista completa de documentos
        String tmpXML = ((CustomBatchForm)form).getXml();
        //Obtener el XML con la lista completa y marcados los incompletos.
        String documentos = getDocumentosXML(docSeleccionados, tmpXML);
        
        //Crear Trámite de Solicitud de Subsanación
        String idTaskCTVar = ConfigurationMgr.getVarGlobal(cct, 
                ConfigurationMgr.ID_TASK_SOLICITUD_SUBSANACION);
        int idTaskCT = Integer.parseInt(idTaskCTVar);
        int idTaskPcd = getIdTaskPcd(idTaskCT, invesFlowAPI, state);
        
        int taskId = Integer.parseInt(request.getParameter("taskId")); 
        
        if (taskId == 0)
            taskId = createTaskSolicitudSubsanacion(idTaskPcd, invesFlowAPI, state);
            
        
        //Cambiar el Estado Administrativo del Expediente a DOCUMENTACION INCOMPLETA
        setEstadoExpediente(ConfigurationMgr.ESTADOADM_DOC_INCOMPLETA, cct, entitiesAPI, state);
            
        //Guardar la información adicional para el trámite (La lista de Documentos)
        saveInfoTramite(taskId, idTaskCT, documentos, invesFlowAPI, entitiesAPI, cct, state);
            
        if (StringUtils.isNotEmpty(request.getParameter("return"))){
            ActionForward forwardAux = mapping.findForward("comprobarDocumentacion");
    		return new ActionForward(forwardAux.getName(),forwardAux.getPath()+"?idsStage="+state.getStageId(),true);    	
        }
        
        //Movermos a la vista del Tramite
        int nProcess = state.getProcessId();
        if (nProcess > 0)
        	return NextActivity.afterCreateTask(nProcess, state.getStageId(), taskId, invesFlowAPI, mapping);

        return mapping.findForward("success");
    }

    /**
     * Cambia el Estado Administrativo del Expediente
     * @param estado Nuevo Estado
     * @param cct
     * @param entitiesAPI
     * @param state
     * @throws ISPACException
     */
    private void setEstadoExpediente(String estado, ClientContext cct, 
    		IEntitiesAPI entitiesAPI, IState state) throws ISPACException {
        IItem expediente = entitiesAPI.getExpedient(state.getNumexp());
        expediente.set("ESTADOADM", ConfigurationMgr.getVarGlobal(cct,estado));
        expediente.store(cct);
    }
    
    /**
     * Modifica el XML de documentos estableciendo los seleccionados para crear la solicitud de subsanación.
     * @param seleccionados Identificadores de los Documentos Seleccionados
     * @param xml Documento XML con la lista de Documentos completa
     * @return
     * @throws ISPACException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    private String getDocumentosXML(String[] seleccionados, String xml) 
    		throws TransformerFactoryConfigurationError {
        
        XmlFacade xmlFacade = new XmlFacade(xml);
        
        List pendientes = xmlFacade.getList("/documentos/documento/pendiente");
        
        //Establecer el estado de Pendiente a NO
        for (int i = 0; i < pendientes.size(); i++) {
            pendientes.set(i, "NO");
        }
        xmlFacade.setList("/documentos/documento/pendiente", pendientes);
        
        //Marcar los Pendientes   
        for (int i = 0; i < seleccionados.length; i++) {
            xmlFacade.set("/documentos/documento[@id='" + seleccionados[i] + "']/pendiente", "SI");
        }
                        
        return xmlFacade.toString();
    
    }
        
    /**
     * Guarda la información adicional del trámite con la lista de documentos seleccionados para la subsanación.
     * @param idTask Identificador del nuevo trámite de Solicitud Subsanación
     * @param idTaskCT Identificador del tramite de Solicitud de Subsanación en el Catálogo
     * @param infoXML Documento XML con la lista de documentos seleccionados para la subsanación.
     * @param invesFlowAPI
     * @param entitiesAPI
     * @param cct
     * @param state
     * @throws ISPACException
     */
    private void saveInfoTramite(int idTask, int idTaskCT, String infoXML, 
    		IInvesflowAPI invesFlowAPI, IEntitiesAPI entitiesAPI, ClientContext cct, IState state) throws ISPACException {
        
        IItem infoTramite = entitiesAPI.createEntity(SpacEntities.SPAC_INFOTRAMITE);
        infoTramite.set("NUMEXP", state.getNumexp());
        infoTramite.set("ID_TRAMITE", idTask);
        infoTramite.set("ID_CT_TRAMITE", idTaskCT);
        infoTramite.set("ID_PCD", state.getPcdId());
        infoTramite.set("ID_P_FASE", state.getStagePcdId());
        infoTramite.set("INFO", infoXML);
        infoTramite.store(cct); 
    }
    
    /**
     * Obtiene el Identificador de un Tramite para el Procedimiento/Fase dado el Identificador del Tramite del Catálogo
     * @param idTaskCT Identificador del Trámite en el Catálogo
     * @param invesFlowAPI
     * @param state
     * @return Identificador del Trámite para el Procedimiento/Fase del Expediente Actual.
     * @throws Exception
     */
    private int getIdTaskPcd(int idTaskCT, IInvesflowAPI invesFlowAPI, IState state) throws Exception {
        IProcedure procedure = invesFlowAPI.getProcedure(state.getPcdId());
        IItemCollection stagetasks = procedure.getTasks(state.getStagePcdId());
        Iterator it = stagetasks.iterator();
        int idTaskPcd = 0;
        
        //Obtener el Identificador del Tramite para el Procedimiento
        while (it.hasNext()) {
            IItem task = (IItem)it.next();
            if (task.getInt("ID_CTTRAMITE") == idTaskCT) {
                idTaskPcd = task.getInt("ID");
                break;
            }
        }
        //Si no se ha encontrado provocar una excepcion
        if (idTaskPcd == 0) {
            throw new Exception("No se ha encontrado el Trámite para Solicitud de Subsanación en el procedimiento actual.");
        }
        
        return idTaskPcd;
    }
    
    /**
     * Crea un Trámite de solictud de Subsanación para el Expediente Actual
     * @param managerAPI
     * @param invesFlowAPI
     * @param state
     * @return Identificador del Trámite creado.
     * @throws Exception
     */
    private int createTaskSolicitudSubsanacion(int idTaskPcd, IInvesflowAPI invesFlowAPI,
            IState state) throws Exception {
                        
        //Crear el tramite de solicitud de subsanación
        ITXTransaction tx = invesFlowAPI.getTransactionAPI();
        int nNewTask = tx.createTask(state.getStageId(), idTaskPcd);
        
        
        return nNewTask;
    }
}
