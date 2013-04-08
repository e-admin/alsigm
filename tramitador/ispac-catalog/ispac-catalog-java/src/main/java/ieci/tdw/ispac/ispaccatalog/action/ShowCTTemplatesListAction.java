package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.ActiveScreen;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Muestra el listado de plantillas de documentos.
 *
 */
public class ShowCTTemplatesListAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_TEMPLATES_READ,
				ISecurityAPI.FUNC_INV_TEMPLATES_EDIT });

		// Establece la pantalla inicial para la navegación
		setActiveScreen(request, ActiveScreen.CT_TEMPLATES);
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();

		// Obtener la lista de plantillas del catálogo
		IItemCollection itemcol = templateAPI.getTemplates(request.getParameter("property(criterio)"));
		List list = CollectionBean.getBeanList(itemcol);
		
        //calcular si son especificas de procedimiento o no
        for (Iterator iter = list.iterator(); iter.hasNext();) {
			 ItemBean element = (ItemBean) iter.next();
			 int id = element.getItem().getInt("CTTEMPLATE:ID");
			 if (!templateAPI.isProcedureTemplate(id)) {
				 element.setProperty("CTTEMPLATE:NOMBRE", 
						 element.getProperty("CTTEMPLATE:NOMBRE")
						 + getResources(request).getMessage("generic.template"));
			 }
			 element.setProperty("PROCEDURE_TEMPLATE", 
					 new Boolean(templateAPI.isProcedureTemplate(id)));	 
		}

        request.setAttribute("CTTemplatesList", list);

		// Establecer el formateador
		setFormatter(request, "CTTemplatesListFormatter", "/formatters/cttemplateslistformatter.xml");

		return mapping.findForward("success");
	}
}