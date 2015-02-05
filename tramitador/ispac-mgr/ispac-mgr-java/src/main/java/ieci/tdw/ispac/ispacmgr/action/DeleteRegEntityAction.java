package ieci.tdw.ispac.ispacmgr.action;

import java.util.Date;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteModificacionVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Clase que se encarga de eliminar un Registro de una entidad cuyo 
 * identificador de entidad será recogido de un parámetro, así como el identificador del registro dentro de la entidad.
 */
public class DeleteRegEntityAction extends BaseAction {
	
	IspacAuditoriaManager auditoriaManager = new IspacAuditoriaManagerImpl();

    public ActionForward executeAction(ActionMapping mapping,
    								   ActionForm form,
    								   HttpServletRequest request,
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {
    	
    	ClientContext cct = session.getClientContext();
    	// Estado del contexto de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
    	IState currentstate = managerAPI.currentState(getStateticket(request));
		
    	int entityId = currentstate.getEntityId();

		// Ejecución en un contexto transaccional
		boolean bCommit = false;
	
		try {

			// Iniciar transacción
			cct.beginTX();	
			int regId=cct.getStateContext().getKey();
			//Tenemos que eliminar tb de la entidad spac_dt_documentos los docs que pudieran estar asociados a regId y entityId
			
			//Obtener todos los items de tipo documento que estan asociados a la entidad y al registro de la entidad
			IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
			IItemCollection itemcol= entitiesAPI.queryEntities(SpacEntities.SPAC_DT_DOCUMENTOS, " where id_entidad="+entityId +" and id_reg_entidad="+regId);
			//Comprobamos que no exista ningún documento bloqueado, en caso de que exista no permitiremos eliminar el registro de la entidad
		
			while (itemcol.next()) {
				
				entitiesAPI.deleteDocumentFromRegEntity(itemcol.value(), false);
				
			}

			EntityForm defaultForm = (EntityForm) form;
			EntityApp entityapp = defaultForm.getEntityApp();
			entityapp.delete(cct);
			
			//Auditoría: Añadir en el ThreadLocal el objeto AuditContext.
			AuditContext auditContext = new AuditContext();
			auditContext.setUserHost(request.getRemoteHost());
			auditContext.setUserIP(request.getRemoteAddr());						
			AuditContextHolder.setAuditContext(auditContext);
			
			//TODO: Auditar la eliminación de la entidad
			String numExp = currentstate.getNumexp();
			auditDeleteEntidad(cct, regId, entityapp,numExp);

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
			
		}
		finally{
			cct.endTX(bCommit);
		}
		
   		String displayTagOrderParams = getDisplayTagOrderParams(request);
   		if (!StringUtils.isEmpty(displayTagOrderParams)) {
   			displayTagOrderParams = "&" + displayTagOrderParams;
   		}
		
		// Establecer el retorno
		String action = "/showExpedient.do";
		String params = "?entity=" + entityId;
		
		if (currentstate.getState() == ManagerState.TASK) {
			
			action = "/showTask.do";
			
			if (request.getParameter("taskId") != null) {
				params += "&taskId=" + request.getParameter("taskId");
			}
			if (request.getParameter("numexp") != null) {
				params += "&numexp=" + request.getParameter("numexp");
			}
		}
		else if (currentstate.getState() == ManagerState.SUBPROCESS) {
			
			action = "/showSubProcess.do";
			
			if (request.getParameter("taskId") != null) {
				params += "&taskId=" + request.getParameter("taskId");
			}
			if (request.getParameter("activityId") != null) {
				params += "&activityId=" + request.getParameter("activityId");
			}
		}
		else {
			if (request.getParameter("stageId") != null) {
				params += "&stageId=" + request.getParameter("stageId");
			}
		}
		
		if (request.getParameter("form") != null) {
			params += "&form=" + request.getParameter("form");
		}
		params += displayTagOrderParams;

		ActionForward actionForward = new ActionForward();
		actionForward.setPath(action + params);
		actionForward.setRedirect(true);

		
		
		return actionForward;
    }

	/**
	 * @param cct
	 * @param regId
	 * @param entityapp
	 */
	private void auditDeleteEntidad(ClientContext cct, int regId, EntityApp entityapp, String numExp) {
		IspacAuditEventEntidadBajaVO evento = new IspacAuditEventEntidadBajaVO();
		AuditContext auditContext = AuditContextHolder.getAuditContext();
		
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		
		evento.setIdUser(cct.getUser().getUID());
		evento.setUser(cct.getUser().getName());
		
		evento.setEntidadAppName(entityapp.getAppName());
		evento.setEntidadAppId(String.valueOf(entityapp.getAppId()));
		
		//TODO: ¿Añadir también el número de expediente del trámite?
		evento.setNumExpediente(numExp);
		evento.setId(String.valueOf(regId));
				
		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la modificación de la entidad");
		auditoriaManager.audit(evento);
	}

}