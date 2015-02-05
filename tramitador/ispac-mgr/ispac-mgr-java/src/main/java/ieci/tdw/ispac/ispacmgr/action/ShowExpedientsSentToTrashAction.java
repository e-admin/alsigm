package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ShowExpedientsSentToTrashAction extends BaseDispatchAction {
	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(ShowExpedientsSentToTrashAction.class);
	/**
	 * Método que obtiene la lista de expedients enviados a la papelera
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesflowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesflowAPI.getCatalogAPI();
		///////////////////////////////////////////////
		// Estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		Map params = request.getParameterMap();
		IState state = managerAPI.enterState(getStateticket(request),
				ManagerState.EXPSTRASHLIST,params); 
		storeStateticket(state,response);

		// Menú
		request.setAttribute("menus", MenuFactory.getSingleMenu(cct, getResources(request)));

		///////////////////////////////////////////////
		// Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/expstrashlistformatter.xml"));
		request.setAttribute("Formatter", formatter);

		//Obtenemos la lista de procedimientos
		List procedimientos=CollectionBean.getBeanList(catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_PROCEDURE, "WHERE TIPO="+TXProcesoDAO.PROCESS_TYPE));
		addProcedureVersion(procedimientos,"NOMBRE" ,"NVERSION");

		request.setAttribute("procedimientos", procedimientos);

		String numReg=request.getParameter("numreg");
		EntityForm entityForm=(EntityForm)form;
		//Recoger los posibles parámetros que nos pueden llegar para realizar la consulta 
		//FECHAINICIO
		//FECHAFIN
		//PROCEDIMIENTO
		String fechaFin= entityForm.getProperty("FECHAFIN");
		String fechaInicio=entityForm.getProperty("FECHAINICIO");
		if(StringUtils.isNotEmpty(fechaFin)){
			fechaFin+=" 23:59:59";
		}
		if(StringUtils.isNotEmpty(fechaInicio)){
			fechaInicio+=" 23:59:59";
		}
		Date dtFechaInicio = (fechaInicio != null && !fechaInicio.equals("")) ? DateUtil.parseDate(fechaInicio)  : null ;
		Date dtFechaEliminacion = (fechaFin != null && !fechaFin.equals("")) ? DateUtil.parseDate(fechaFin)  : null ;

		if (dtFechaEliminacion!=null && dtFechaInicio!=null && dtFechaEliminacion.before(dtFechaInicio)){
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.message.date.interval"));
			saveErrors(request,messages);
			return mapping.findForward("view_list");
		}
		int idproc=-1;
		String idpcd=entityForm.getProperty("PROCESOS:ID_PCD");
		if(StringUtils.isNotBlank(idpcd)){
			idproc=Integer.parseInt(idpcd);
		}


		ISecurityAPI securityAPI = invesflowAPI.getSecurityAPI();
		Responsible user = session.getClientContext().getUser();
		IItemCollection itemcol= invesflowAPI.getExpedientsSentToTrash(fechaInicio,fechaFin, idproc);
		if(itemcol.next()){
			List list = CollectionBean.getBeanList(itemcol);
			addProcedureVersion(list,"PPROCEDIMIENTOS:NOMBRE" , "PPROCEDIMIENTOS:NVERSION");
			request.setAttribute(ActionsConstants.EXPS_TRASH_LIST, list);
			if(StringUtils.isNotBlank(numReg)){
				int numreg=Integer.parseInt(numReg);
				if(numreg > list.size()){
					request.setAttribute("maxResultados", String.valueOf(list.size())); 
					request.setAttribute("numTotalRegistros", String.valueOf(numReg));
				}
			}
			if(logger.isInfoEnabled()){
				logger.info("Se han obtenido "+list.size()+" expedientes enviados a la papelera");
			}
		}
		else{
			if(logger.isInfoEnabled()){
				logger.info("No hay ningún expediente enviado a la papelera");
			}
		}
		if(!securityAPI.isFunction(user.getUID(), ISecurityAPI.FUNC_TOTALSUPERVISOR))
		{
			request.setAttribute("onlyRead","true" );
		}


		return mapping.findForward("view_list");
	}

	/**
	 * Restaura una lista de expediente 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ActionForward restoreExps(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		EntityForm entityForm= (EntityForm) form;
		String[] processIds = entityForm.getMultibox();
		entityForm.setMultibox(new String[0]);
		restoreExps(processIds, cct);
		return mapping.findForward("list");
	}

	/**
	 * Elimina definitivamente una lista de expedientes 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteExps(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		EntityForm entityForm= (EntityForm) form;
		String[] processIds = entityForm.getMultibox();
		entityForm.setMultibox(new String[0]);
		deleteExps(processIds, cct);
		return mapping.findForward("list");
	}

	/**
	 * Restura un único expediente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ActionForward restoreExp(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		String processId=request.getParameter("processId");
		if (StringUtils.isNotBlank(processId)){
			String[] processIds=new String[1];
			processIds[0]=processId;
			restoreExps(processIds, cct);
		}

		else{
			if(logger.isInfoEnabled()){
				logger.info("No se ha restaurado ningún expediente");
			}
		}

		return mapping.findForward("showExp");
	}


	/**
	 * Elimina un único expediente
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteExp(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		String processId=request.getParameter("processId");
		if (StringUtils.isNotBlank(processId)){
			String[] processIds=new String[1];
			processIds[0]=processId;
			deleteExps(processIds, cct);
		}

		else{
			if(logger.isInfoEnabled()){
				logger.info("No se ha restaurado ningún expediente");
			}
		}
		return mapping.findForward("procedureList");

	}
	
	private void addProcedureVersion(List lista , String fieldNombre , String fieldVersion) throws ISPACException{
		Iterator itr=lista.iterator();
		while(itr.hasNext()){
			ItemBean element = (ItemBean) itr.next();
			element.setProperty(fieldNombre,element.getProperty(fieldNombre)+" (v"+element.getProperty(fieldVersion) +")");

		}
	}

	private void restoreExps(String [] processIds , ClientContext cct) throws NumberFormatException, ISPACException{
		// API de invesFlow
		IInvesflowAPI invesflowAPI =cct.getAPI();
		if (processIds!=null &&processIds.length>0){
			for (int i = 0; i < processIds.length; i++) {
				if(StringUtils.isNotBlank(processIds[i])){
					invesflowAPI.getTransactionAPI().restoreProcess(Integer.parseInt(processIds[i]));
				}
				if(logger.isInfoEnabled()){
					logger.info("Se ha restaurado el proceso"+processIds[i]);
				}
			}

		}
		else{
			if(logger.isInfoEnabled()){
				logger.info("No se ha restaurado ningún expediente");
			}
		}
	}
	private void deleteExps(String [] processIds , ClientContext cct) throws NumberFormatException, ISPACException{
		// API de invesFlow
		IInvesflowAPI invesflowAPI =cct.getAPI();
		if (processIds!=null &&processIds.length>0){
			for (int i = 0; i < processIds.length; i++) {
				if(StringUtils.isNotBlank(processIds[i])){
					invesflowAPI.getTransactionAPI().deleteProcess(Integer.parseInt(processIds[i]));
				}
				if(logger.isInfoEnabled()){
					logger.info("Se ha eliminado el proceso"+processIds[i]);
				}
			}

		}
		else{
			if(logger.isInfoEnabled()){
				logger.info("No se ha eliminado ningún expediente");
			}
		}

	}
}
