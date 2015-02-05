package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Obtiene los trámites de un expediente
 * @author IECISA
 * @since  6.3
 *
 */

public class ShowTaskListExpedientAction extends BaseAction{

	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(ShowTaskListExpedientAction.class);
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		// Estado actual de tramitación
		ClientContext cct=session.getClientContext();
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
        IState state = managerAPI.currentState(getStateticket(request));
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI= invesFlowAPI.getEntitiesAPI();
		
		String numexp=state.getNumexp();
		if (logger.isDebugEnabled()){
			logger.debug("Vamos a obtener la lista de trámites del expediente: " + numexp);
		}
		
		Map map = new LinkedHashMap();
	    map.put("SPAC_DT_TRAMITES", "SPAC_DT_TRAMITES");
	    map.put("SPAC_P_FASES", "SPAC_P_FASES");
	    IItemCollection collection = entitiesAPI.queryEntities(map,"WHERE SPAC_P_FASES.ID=SPAC_DT_TRAMITES.ID_FASE_PCD AND SPAC_DT_TRAMITES.NUMEXP= '" + DBUtil.replaceQuotes(numexp) + "'"); 
		if(logger.isDebugEnabled()){
			if(collection.next()){
				logger.debug("El expediente:" + cct.getStateContext().getNumexp()+" tiene trámites");
			}
			else{
				logger.debug("El expediente:" + cct.getStateContext().getNumexp()+" no tiene ningún trámite");
			}
		}
		request.setAttribute("taskListExp",
				CollectionBean.getBeanList(collection));
		
		request.setAttribute("stageId",cct.getStateContext().getStagePcdId()+"");
		
		// Presentación de los datos
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory
				.getFormatter(getISPACPath("/digester/tasklistexpedientformatter.xml"));
		request.setAttribute("formatter", formatter);
		return  mapping.findForward("success");
	}

}
