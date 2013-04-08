package ieci.tecdoc.sgm.catalogo_tramites.action;
/*
 * $Id: ListaOrganosDestinatariosAction.java,v 1.5.2.2 2008/04/29 11:04:00 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.OrganosDestinatariosDelegate;
import ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl.OrganosDestinatariosDelegateImpl;
import ieci.tecdoc.sgm.catalogo_tramites.form.OrganoDestinatarioForm;
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario;
import ieci.tecdoc.sgm.core.services.catalogo.OrganosDestinatarios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ListaOrganosDestinatariosAction extends CatalogoTramitesWebAction {
	private static final String SUCCESS_FORWARD =		"success";
	private static final String FAILURE_FORWARD =			"failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

		OrganoDestinatarioForm  addresseeForm = (OrganoDestinatarioForm)form;
		OrganoDestinatario addressee = null;
		Collection addressees = new ArrayList();
		String act = addresseeForm.getUserAction();
        
		try{
			ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
		   //comprobación de las acciones llevadas a cabo por el usuario
			if (act != null){
	       	  if (act.equals("add")){
				if (addresseeForm.getNewCode() != null && addresseeForm.getNewDescription()  != null){
				    if (!addresseeForm.getNewCode().equals("") && !addresseeForm.getNewDescription().equals("")){
				       addressee = new OrganoDestinatario(addresseeForm.getNewCode(),addresseeForm.getNewDescription());
                       oServicio.addAddressee(addressee, SesionAdminHelper.obtenerEntidad(request));
                       addresseeForm.setCode(addresseeForm.getNewCode());
                       addresseeForm.setNewCode(addresseeForm.getNewCode());
                       addresseeForm.setNewDescription(addresseeForm.getNewDescription());
                       addresseeForm.setUserAction("load");
					}
				 }
			  }else if (act.equals("delete")){
			     if (addresseeForm.getCode()!=null){
			     	if (!addresseeForm.getCode().equals("")){
                       oServicio.deleteAddressee(addresseeForm.getCode(), SesionAdminHelper.obtenerEntidad(request));	
			     	}
			     }
			  }else if (act.equals("update")){
			  		if (!addresseeForm.getCode().equals("") && !addresseeForm.getNewDescription().equals("")){
				       addressee = new OrganoDestinatario(addresseeForm.getCode(),addresseeForm.getNewDescription());
                       oServicio.updateAddressee(addressee, SesionAdminHelper.obtenerEntidad(request));
                       addresseeForm.setNewCode(addresseeForm.getNewCode());
                       addresseeForm.setDescription(addresseeForm.getNewDescription());
                       addresseeForm.setNewDescription(addresseeForm.getNewDescription());
                       addresseeForm.setUserAction("load");
					}
			  }else if (act.equals("import")){
			  		if (!addresseeForm.getCode().equals("")){
			  			ServicioRPAdmin servicioRPAdmin = LocalizadorServicios.getServicioRPAdmin();
			  			OrganosDestinatariosDelegate delegate = new OrganosDestinatariosDelegateImpl(oServicio, servicioRPAdmin);
						delegate.createUpdateOrganosDestinatariosForUnidadAdministrativa(
								addresseeForm.getCode(),
								addresseeForm.isImportSelectedDept(),
								SesionAdminHelper.obtenerEntidad(request));
					}
			  }
			}
		   OrganosDestinatarios addresseeObjects = oServicio.getAddressees(SesionAdminHelper.obtenerEntidad(request));
		   if (addresseeObjects.count() > 0) {
			  for (int i = 0; i < addresseeObjects.count() ; i++) {
			     addressee = (OrganoDestinatario)addresseeObjects.get(i);
				 addressees.add(new OrganoDestinatarioForm(addressee.getId(),addressee.getDescription()));
			  } 
		   }
		} catch (Exception e) {
	   		  request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_ORGANOS_DESTINATARIOS);
	 		  request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	 		  request.setAttribute(Defs.REDIRECCION, "addresseeList.do");
	 		  return mapping.findForward(FAILURE_FORWARD);
		}
		
		request.setAttribute("addressees", addressees);
		return mapping.findForward(SUCCESS_FORWARD);
	}
	
}
