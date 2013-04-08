package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.SicresConnectorFactory;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectTaskRegisterESAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		//APIS
    	ClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = cct.getAPI();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
		//String numRegister = request.getParameter("fieldValue");
		String numRegister = ((EntityForm)form).getProperty(request.getParameter("field"));
		String registerType = request.getParameter("registerType");

		//Estado actual
		IState state = managerAPI.currentState(getStateticket(request));
		
		//Se comprueba si el apunte ya esta asociado al expediente
		if (entitiesAPI.countResultQuery(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NUMEXP = '" + DBUtil.replaceQuotes(state.getNumexp()) + "' AND NREG = '" + numRegister + "' AND TP_REG = '" + registerType+"'") > 0){
			throw new ISPACInfo("exception.register.linked",false);
		}
		
		// Obtenemos el conector con sicres
		ISicresConnector sicresConnector = SicresConnectorFactory.getInstance().getSicresConnector(cct);
		// Se comprueba la existencia del apunte de registro antes de buscar los tramites abiertos
		RegisterInfo registerInfo = new RegisterInfo(null, numRegister, null, registerType);
		Register register = sicresConnector.readRegister(registerInfo);
		if (register == null){
        	if (RegisterType.ENTRADA.equalsIgnoreCase(registerType)) {
        		throw new ISPACInfo(getResources(request).getMessage("registro.entrada.notfound")+" "+numRegister,false);
        	} else {
        		throw new ISPACInfo(getResources(request).getMessage("registro.salida.notfound")+" "+numRegister,false);
        	}
		}
		
		//Listado de trámites ya iniciados
		IWorklist managerwl=managerAPI.getWorklistAPI();
		IItemCollection collection = managerwl.getTasks(state);
		List expTaskList = CollectionBean.getBeanList(collection);
		
		request.setAttribute("RegisterESTaskList",expTaskList);
		request.setAttribute("stageId", ""+state.getStageId());
		
		//Añadimos el formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request.setAttribute("Formatter", factory.getFormatter(getISPACPath("/digester/opentaskregesformatter.xml")));		

		String parameters = request.getParameter("parameters");
        Map sParameters = (Map)request.getSession().getAttribute(parameters);
        if (sParameters != null) {
        	request.setAttribute("parameters", sParameters);
        }
        
        request.setAttribute("registerType", registerType);
        request.setAttribute("numRegister", numRegister);
		request.setAttribute("parametersName", parameters);
		return mapping.findForward("success");
	}
}
