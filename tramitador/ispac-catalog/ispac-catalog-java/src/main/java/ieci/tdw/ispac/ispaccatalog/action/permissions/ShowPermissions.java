package ieci.tdw.ispac.ispaccatalog.action.permissions;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.bean.permissions.TypePermissionsBean;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowPermissions extends BaseAction
{
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception
    {
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IInvesflowAPI invesflowapi= session.getAPI();
        IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();

        int procId = Integer.parseInt(request.getParameter("regId"));


        /*IItemCollection resps = respAPI.getRespPermissions(procId);
        Map respsMap=CollectionBean.getBeanMap(ItemBean.class,resps);

        IItemCollection permissionsProcedures = respAPI.getPermissions(procId);
        List permissionsProceduresList = CollectionBean.getBeanList(TypePermissionsBean.class, permissionsProcedures);*/

        IItemCollection permissionsProcedures = respAPI.getAllPermissionsByPcd(procId,null);
        List permissionsProceduresList = CollectionBean.getBeanList(TypePermissionsBean.class, permissionsProcedures);
        
        //Iterator permissionsProceduresIt = permissionsProceduresList.iterator();
        Map respsMap= respAPI.getResp(permissionsProcedures.toList(), "UID_USR");
        TypePermissionsBean tpbean = null;
        for (int ind=0; ind < permissionsProceduresList.size(); ind++){
            tpbean = (TypePermissionsBean)permissionsProceduresList.get(ind);
           // tpbean.uidName(respsMap);
            tpbean.setUidName(respsMap);
        }

        request.setAttribute("ProceduresList", permissionsProceduresList);

        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/permissions/permissionsprocedureslistformatter.xml"));
        request.setAttribute("ProceduresListFormatter", formatter);

        request.setAttribute("procId", String.valueOf(procId));

        request.setAttribute("application", 
        					 StaticContext.rewritePage((String) servlet.getServletContext().getAttribute("ispacbase"), 
        					 						   "common/permissions/permissionsproceduresList.jsp"));
       
		// Generamos la ruta de navegación hasta la pantalla actual.
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(invesflowapi.getCatalogAPI()).getBreadCrumbs(request);
 		request.setAttribute("BreadCrumbs", bcm.getList());

        return mapping.findForward("success");
    }
}
