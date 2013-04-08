package ieci.tdw.ispac.ispaccatalog.action.signprocess;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.sign.portafirmas.ProcessSignConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowSignProcessListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_READ,
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();



		StringBuffer sql = new StringBuffer();
		sql.append(" WHERE  (SISTEMA='"+DBUtil.replaceQuotes(ProcessSignConnectorFactory.getInstance().getProcessSignConnector().getIdSystem())+"'");
		//Almacenamos en sesión si el conector del portafirmas utilizado es el de por defecto
		if(ProcessSignConnectorFactory.getInstance().isDefaultConnector()){
			request.getSession().setAttribute("defaultPortafirmas", true);
			sql.append(" OR SISTEMA IS NULL ");
		}
		sql.append(" ) ");
		if(request.getParameter("property(criterio)")!=null){
			sql.append(" AND DESCRIPCION LIKE '%" + DBUtil.replaceQuotes(request.getParameter("property(criterio)")) + "%'");
		}
		sql.append(" ORDER BY DESCRIPCION");
        IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_HEADER,
                sql.toString());
        List signprocesslist=CollectionBean.getBeanList(itemcol);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/signprocess/signprocesslistformatter.xml"));
		request.setAttribute("SignProcessListFormatter", formatter);

   	 	request.setAttribute("SignProcessList",signprocesslist);

   	 	return mapping.findForward("success");
	}

}