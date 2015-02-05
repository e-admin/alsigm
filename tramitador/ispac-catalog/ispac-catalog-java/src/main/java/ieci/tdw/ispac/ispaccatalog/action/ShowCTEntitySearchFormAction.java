package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.dao.cat.CTFrmBusquedaOrgDAO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowCTEntitySearchFormAction extends ShowCTEntityAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form, 
									   HttpServletRequest request, 
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
		ActionForward forward = super.executeAction(mapping, form, request, response, session);
		
		EntityForm defaultForm = (EntityForm) form;
		IItem searchForm = defaultForm.getEntityApp().getItem();
		
		// Para los formularios de búsqueda específicos
		// obtener el listado de objetos de organizacion al que esta asociado el formulario
		int tipo = searchForm.getInt("TIPO");
		if (tipo == CTFrmBusquedaOrgDAO.SPECIFIC_TYPE) {
			
			ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();

			// Responsables asignados al formulario de búsqueda
			List permissionObjectOrganizactionList = catalogAPI.getSearchFormOrganization(searchForm.getKeyInt());
	        
			// Cargar el formateador de la lista de responsables
	   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctfrmsearchorglistformatter.xml"));
			request.setAttribute("CTFrmSearchOrgListFormatter", formatter);

	   	 	request.setAttribute("CTFrmSearchOrgList", permissionObjectOrganizactionList);			
		}
		
		return forward;
	}

}
