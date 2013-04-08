package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.bean.PcdVersionBean;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispacweb.tag.context.StaticContext;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowPVersionsAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        //Se recoje el tipo de objeto y su identificador
		int groupId = 0;

		// Si no llegan los parámetros correctos informamos
		// al usuario y volvemos a la pantalla de origen.
		try{
		    String paramIdObj = request.getParameter("groupId");
		    groupId = Integer.parseInt(paramIdObj);
		}catch(NumberFormatException e){
			throw new ISPACInfo("Par&aacute;metros de entrada incorrectos.");
		}
        //Prepara las API's a utilizar
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		//IItem proc = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_PROCEDURE, pcdId);

		HashMap tablemap=new HashMap();
		tablemap.put("CTPROC",new Integer(ICatalogAPI.ENTITY_CT_PROCEDURE));
		tablemap.put("PPROC",new Integer(ICatalogAPI.ENTITY_P_PROCEDURE));

		//String filtro = " WHERE CTPROC.ID = PPROC.ID AND PPROC.ID_GROUP = " + proc.getInt("ID_GROUP");
		String filtro = " WHERE CTPROC.ID = PPROC.ID AND PPROC.ID_GROUP = " + groupId;
		IItemCollection itemcol=catalogAPI.queryCTEntities(tablemap,filtro);

	    List versionsList=CollectionBean.getBeanList(PcdVersionBean.class,itemcol);
	    String procname="No existe ninguna versión para el procedimiento indicado";

	    if (versionsList.size()>0)
	    {
	        ItemBean bean=(ItemBean)versionsList.get(0);
	        procname=bean.getString("PPROC:NOMBRE");
	    }

		request.setAttribute("versionsList", versionsList);
		request.setAttribute("groupId", String.valueOf(groupId));
		request.setAttribute("procName", procname);

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/procedure/selsversionformatter.xml"));
		request.setAttribute("Formatter", formatter);

        request.setAttribute("application", 
        					 StaticContext.rewritePage((String) servlet.getServletContext().getAttribute("ispacbase"), 
        					 						   "common/versions/selectVersion.jsp"));

        return mapping.findForward("success");
	}

}
