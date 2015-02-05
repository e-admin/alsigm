package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.wl.DeadLineDAO;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ShowExpiredTermsAction extends BaseAction{
	public boolean isValidDate(ActionMapping mapping, HttpServletRequest request, String fecha){ 
	if ((fecha != null && !fecha.equals(""))){
     	if (!GenericValidator.isDate(fecha, request.getLocale())){
	    		ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.message.date.interval"));
				saveErrors(request,messages);
				return false;
     	}
     }
		return true;
	}
	 
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
    		HttpServletRequest request, HttpServletResponse response, 
    		SessionAPI session) throws Exception {
        
    	ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		Map params = request.getParameterMap();
		IState state = managerAPI.enterState(getStateticket(request), ManagerState.TERMS , params);
		//Se actualiza el estado de tramitación.
		storeStateticket(state,response);
		List terms = null;

		if (getErrors(request).isEmpty()){		
	        IItemCollection collection =null;
	        String fechaFin = request.getParameter("fechaFin"); 
	        if (fechaFin == null )
	            fechaFin = ((EntityForm)form).getProperty("FECHAFIN");
	        else{
	        	//Entra por aki en el caso que se venda directamente del enlace existente en la bandeja de entrada, 
	        	//por lo que actualizamos el formulario con la fecha de fin recibida por parametro y se resetea la fecha de inicio
	        	((EntityForm)form).setProperty("FECHAINICIO", (String)null);
	        	((EntityForm)form).setProperty("FECHAFIN", fechaFin);
	        }
	        String fechaInicio = ((EntityForm)form).getProperty("FECHAINICIO");
//	        if (!isValidDate(mapping, request, fechaInicio)){
//	        	return mapping.findForward("error");
//	        }
//	        if (!isValidDate(mapping, request, fechaFin)){
//	        	return mapping.findForward("error");
//	        }
	        
	        Date dtFechaInicio = (fechaInicio != null && !fechaInicio.equals("")) ? DateUtil.parseDate(fechaInicio)  : null ;
	        Date dtFechaFin = (fechaFin != null && !fechaFin.equals("")) ? DateUtil.parseDate(fechaFin)  : null ;
	        
	        if (dtFechaFin != null && dtFechaInicio != null && dtFechaFin.before(dtFechaInicio)){
	    		ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.message.date.interval"));
				saveErrors(request,messages);
				return mapping.findForward("error");
	        }
	        	
	        IWorklistAPI workListAPI = session.getClientContext().getAPI().getWorkListAPI();
	        collection = workListAPI.getExpiredTerms(DeadLineDAO.TYPE_ALL, dtFechaInicio, dtFechaFin);
	        terms = CollectionBean.getBeanList(collection);
        }
        
        request.setAttribute(ActionsConstants.TERM_LIST,terms);
        
        ////////////////////////////
        // Menú
        request.setAttribute("menus", MenuFactory.getSingleMenu(cct, getResources(request)));        
        
	    // Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/termlistformatter.xml"));
		request.setAttribute("Formatter", formatter);
        
        return mapping.findForward("success");
    }	
	
}
