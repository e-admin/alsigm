package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.dao.cat.CTReportDAO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FrmSearchReportAction extends BaseDispatchAction {
		
/**
 * Método que nos muestra el listado de los informes de tipo búsqueda y si estan asociados o no al formulario de búsqueda actual
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @param session
 * @return
 * @throws ISPACException
 */
	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_SEARCH_FORMS_READ,
				ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		String idFrmS=request.getParameter("frmId");
		request.setAttribute("regId", request.getParameter("regId"));
		request.setAttribute("entityId", request.getParameter("entityId"));
		if(StringUtils.isNotEmpty(idFrmS)){
			IItemCollection itemcol=catalogAPI.getReportByType(CTReportDAO.SEARCH_TYPE);
		    IItemCollection itemcolAsociate=catalogAPI.getAsociateReports(Integer.parseInt(idFrmS));
		    List itemBeans = CollectionBean.getBeanList(itemcol);
		  
		    Map asociados=itemcolAsociate.toMap("ID_INFORME");
		    //Hay que indicar en itemCol que informes estan asociados o no
		  
		    List selectedIdList = new ArrayList();
		    
		   for(int i=0; i<itemBeans.size(); i++) {
		    	if (asociados.containsKey(((ItemBean) itemBeans.get(i)).getItem().get("ID"))) {
		    		selectedIdList.add(((ItemBean) itemBeans.get(i)).getItem().getString("ID"));
		    	}
		   }
		   
		   EntityForm entityForm = (EntityForm) form;
		   entityForm.setMultibox((String[])selectedIdList.toArray(new String[selectedIdList.size()]));
		   
			CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctreportsSearchFormlistformatter.xml"));
			request.setAttribute("CTReportsListFormatter", formatter);
			request.setAttribute("CTReportsList", itemBeans);
		}

		return mapping.findForward("show");
	}
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT });
		
		EntityForm entityForm = (EntityForm) form;
		String idFrm=request.getParameter("frmId");
		// En multibox tendremos los ids de los informes que queremos que esten asociados al formulario de busqueda actual
        String [] multibox = entityForm.getMultibox();
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		if(StringUtils.isNotBlank(idFrm)){
			catalogAPI.updateAsociateReportToFrmBusqueda(Integer.parseInt(idFrm), multibox);
		}
       return show(mapping, form, request, response, session);
	}
	
	/**
	 * Vuelve a la página del formulario de búsqueda
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws ISPACException
	 */
	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		request.setAttribute("regId", request.getParameter("regId"));
		request.setAttribute("entityId", request.getParameter("entityId"));
		return mapping.findForward("sucess");
	}

}
