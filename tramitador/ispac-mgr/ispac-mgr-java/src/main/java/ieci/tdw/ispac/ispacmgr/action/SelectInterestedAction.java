package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectInterestedAction extends BaseAction
{
	static final int SPACT_DT_INTERVINIENTES = 3;

	public ActionForward executeAction(
	ActionMapping mapping, 
	ActionForm form, 
	HttpServletRequest request,
	HttpServletResponse response, 
	SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));
		String numexp = currentstate.getNumexp();

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

		String option = request.getParameter("option");

		IItem tercero = (IItem) request.getAttribute("ThirdParty");

		IItem interviniente;
		int rol = 1;
		
		if (option.equalsIgnoreCase("new"))
		{
			interviniente = entitiesAPI.createEntity(SPACT_DT_INTERVINIENTES);
			rol = 2;
		}
		else
		{
			rol = 1;
			IItemCollection collection = null;
	
			String strSQL = "WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "' AND ROL = 1";
			collection = entitiesAPI.queryEntities(SPACT_DT_INTERVINIENTES, strSQL);
	
			if (collection.next())
				interviniente = collection.value();
			else
				interviniente = entitiesAPI.createEntity(SPACT_DT_INTERVINIENTES);
		}
		interviniente.set("ID_EXT", tercero.get("ID"));
		interviniente.set("NUMEXP", numexp);
		interviniente.set("ROL", rol);
		interviniente.set("TIPO", tercero.get("TIPO"));
		interviniente.set("NDOC", tercero.get("NDOC"));
		interviniente.set("NOMBRE", tercero.get("NOMBRE") 
				+ ", "
				+ tercero.get("APELLIDOS"));
		interviniente.set("DIRNOT", tercero.get("DIRECCION"));
		interviniente.set("EMAIL", "");
		interviniente.set("LOCALIDAD", tercero.get("CIUDAD"));
		interviniente.set("CAUT", tercero.get("REGION"));
		interviniente.set("C_POSTAL", tercero.get("C_POSTAL"));
		interviniente.store(session.getClientContext());
		
		// Salva en la petición el bean del interviniente
		request.setAttribute("ThirdParty", new ItemBean(interviniente));

		return mapping.findForward("success");
	}
}