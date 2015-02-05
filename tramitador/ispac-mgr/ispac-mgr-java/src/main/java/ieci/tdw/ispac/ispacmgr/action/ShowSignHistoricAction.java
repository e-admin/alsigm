package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.common.constants.SignCircuitStates;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ShowSignHistoricAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		ClientContext cct = session.getClientContext();
		///////////////////////////////////////////////
		// Estado de tramitación
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		managerAPI.enterState(getStateticket(request), ManagerState.BATCHSIGNLIST, null);
		
		List list = null;
		//Si se han encontrado errores se redirige directamente
		if (getErrors(request).isEmpty()){
	
			EntityForm entityForm = (EntityForm)form;
			String ini = entityForm.getProperty("FECHAINICIO");
			String end = entityForm.getProperty("FECHAFIN");
			Date dtFechaInicio = null;
			Date dtFechaFin = null;
			if (StringUtils.isNotEmpty(ini)) 
				dtFechaInicio = DateUtil.parseDate(ini);
			if (StringUtils.isNotEmpty(end)) 
				dtFechaFin = DateUtil.parseDate(end);
	        
	        if (dtFechaFin != null && dtFechaInicio != null && dtFechaFin.before(dtFechaInicio)){
	    		ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.message.date.interval"));
				saveErrors(request,messages);
				return mapping.findForward("error");
	        }
	
	        ISignAPI signAPI = session.getAPI().getSignAPI();
	        IItemCollection itemcol = signAPI.getHistorics(session.getClientContext().getRespId(), dtFechaInicio, dtFechaFin, SignCircuitStates.FINALIZADO);
	        list = CollectionBean.getBeanList(itemcol);
		}
		request.setAttribute(ActionsConstants.BATCH_SIGN_LIST, list);
		
	    ///////////////////////////////////////////////
	    // Menus
		request.setAttribute("menus", MenuFactory.getSingHistoric(cct, getResources(request)));    		
		///////////////////////////////////////////////
	    // Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/historicsignlistformatter.xml"));
		request.setAttribute("Formatter", formatter);
		
		return mapping.findForward("success");
	}
}