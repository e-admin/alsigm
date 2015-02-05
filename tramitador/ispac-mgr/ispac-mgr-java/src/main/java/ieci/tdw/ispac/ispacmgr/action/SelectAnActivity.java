package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispactx.TXConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Redirige la selección sobre una lista de resultados de búsqueda a la presentación del expediente.
 * 
 */
public class SelectAnActivity extends BaseAction
{
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		String numexp = request.getParameter("numexp");
		
   		String sParameter = null;
   		ActionForward action = null;

   		try {
   			IProcess process = invesflowAPI.getProcess(numexp);
			int processId = process.getInt ("ID");
			
//			if (process.getInt ("ESTADO") != 1) {
//				throw new ISPACNullObject();
//			}
			
		Responsible user = session.getClientContext().getUser();
		ISecurityAPI securityAPI = invesflowAPI.getSecurityAPI();
		// Controlar que el proceso cerrado o enviado a la papelera pueda no tener responsable asignado
		boolean isResponsible = false;
		//Si el expediente se encuentra enviado a la papelera no se podrá visualizar	
			if (process.getInt("ESTADO") == TXConstants.STATUS_DELETED) {
				if (!securityAPI.isFunction(user.getUID(),
						ISecurityAPI.FUNC_TOTALSUPERVISOR)
						&& !securityAPI.isFunction(user.getUID(),
								ISecurityAPI.FUNC_MONITORINGSUPERVISOR))
					throw new ISPACInfo("exception.expedient.trash",
							new Object[] { numexp });
				else {
					String stageId = request.getParameter("stageId");
					if (stageId == null) {
						IItemCollection collection = null;

						// Obtiene la primera fase del expediente y de mi
						// responsabilidad
						collection = invesflowAPI.getWorkListAPI()
								.findActiveStagesInTrash(processId);
						if (collection.next()) {
							stageId = collection.value().getString("ID_STAGE");
							sParameter = "?stageId=" + stageId;
							action = mapping.findForward("showexp");
						}
						// Siempre debería existir una fase abierta en un
						// expediente enviado a la papelera, ya
						// que no se permiten enviar a la papelera expedientes
						// cerrados, no obstante en caso
						// de que suceda no vamos al showexp sino al showdata
						else {

							sParameter = "?numexp=" + numexp;
							action = mapping.findForward("showdata");
						}
					}
				}
			}
		//Si el expediente no se encuentra abierto
			else if (process.getInt ("ESTADO") != TXConstants.STATUS_OPEN ) {
			String sUID = process.getString("ID_RESP");
			if (StringUtils.isNotBlank(sUID)) {
				isResponsible = invesflowAPI.getWorkListAPI().isInResponsibleList(sUID, ISecurityAPI.SUPERV_ANY, process);
			} else {
				if(!isResponsible){
				// Usuario supervisor en Modo Modificación
				if (securityAPI.isFunction(user.getUID(), ISecurityAPI.FUNC_TOTALSUPERVISOR)) {
					isResponsible = true;
				} else 
					// Usuario supervisor en Modo seguimiento
					if (securityAPI.isFunction(user.getUID(), ISecurityAPI.FUNC_MONITORINGSUPERVISOR)) {
					isResponsible = true;
				}
				}
			}
			
			if (!isResponsible) {

				// Comprobar si existe algún permiso para la responsabilidad
				// del usuario conectado
				String resp = invesflowAPI.getWorkListAPI().getRespString();
				if (!invesflowAPI.getSecurityAPI().existPermissions(process, resp, null)) {

					throw new ISPACInfo("exception.expedients.responsability", new Object[]{numexp});
				}
			}

			sParameter = "?numexp=" + numexp;
			action = mapping.findForward("showdata");	
		}else{

			String stageId = request.getParameter("stageId");
			if (stageId==null){
				IItemCollection collection = null;
				
				//	Obtiene la primera fase del expediente y de mi responsabilidad
				collection = invesflowAPI.getWorkListAPI().findActiveStages(processId);
		    	if (collection.next()) {
		    		stageId = collection.value().getString("ID_STAGE");		
//		    		sParameter = "?stageId="+iStageId;
//					action = mapping.findForward("showexp");
		    	} else {
	//		    	// Obtiene la primera fase abierta
	//	    		collection = process.getOpenedStages();
	//	    		
	//				if ( collection.next()) {
	//					IStage stage = (IStage) collection.value();
	//					sParameter = "?stageId="+ stage.getKeyInt();
	//					action = mapping.findForward("showexp");
	//				} else {
	//					throw new ISPACNullObject();
	//				}
		    		throw new ISPACInfo("exception.expedients.responsability", new Object[]{numexp});
		    	}
			}
			sParameter = "?stageId="+stageId;
			action = mapping.findForward("showexp");
		}
		}catch (ISPACException e)
		{
			// No existe el proceso, pero como puede tratarse de un expediente cerrado...
			if (  (e instanceof ISPACNullObject)
			   || (e.getCause() instanceof ISPACNullObject))
			{
				//...redirigimos para mostrar los datos del expediente en solo lectura (caso de expediente cerrado)
				sParameter = "?numexp=" + numexp;
				action = mapping.findForward("showdata");
			    //throw new ISPACInfo("Expediente '"+numexp+"' no encontrado");
			}
			else
			{
				//throw e;
				request.setAttribute(Globals.MESSAGE_KEY,e);
				request.getSession().setAttribute("infoAlert", e);
				return mapping.findForward("showmainwithwarning");
			}
		}
		
		// Ahora el formulario de búsqueda está en sesión y se mantienen los parámetros de la última búsqueda realizada
	   	// return new ActionForward(action.getName(), action.getPath()+ sParameter+"&"+ActionsConstants.FROM_SEARCH_RESULTS+"=true", true);
		return new ActionForward(action.getName(), action.getPath()+ sParameter, true);
	}
}
