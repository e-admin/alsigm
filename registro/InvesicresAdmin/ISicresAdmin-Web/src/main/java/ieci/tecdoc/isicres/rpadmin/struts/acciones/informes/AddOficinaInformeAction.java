package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.InformeForm;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Estados;
import es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AddOficinaInformeAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InformeForm informeForm  = (InformeForm) form;

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		request.setAttribute("tiposInformes", tiposInformes);

		List listaOficinas = (List) getListaFromSession(request, KEY_LISTA_OFICINAS_INFORMES);

		String idOficina = informeForm.getIdOficina();

		if(StringUtils.isNotBlank(idOficina)){
			//Comprobar que el documento no esta duplicado
			boolean duplicado = false;

			for (Iterator iterator = listaOficinas.iterator(); iterator.hasNext();) {
				OficinaInformeBean oficina = (OficinaInformeBean) iterator.next();

				if(oficina != null && idOficina.equals(new Integer(oficina.getIdOficina()).toString())){
					duplicado = true;
					break;
				}
			}

			if(!duplicado){
				OficinaInformeBean oficina = new OficinaInformeBean();

				oficina.setCodigoOficina(informeForm.getCodigoOficina());
				oficina.setIdOficina(Integer.parseInt(informeForm.getIdOficina()));
				oficina.setNombreOficina(informeForm.getNombreOficina());
				oficina.setEstado(Estados.NUEVO);

				listaOficinas.add(oficina);
				setInSession(request, KEY_LISTA_OFICINAS_INFORMES, listaOficinas);
				informeForm.resetOficina();
				informeForm.change();
			}
			else{
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.informes.elemento.oficina.duplicada", informeForm.getNombreOficina());
				errores.add("Error interno", error);
				saveErrors(request, errores);

			}
		}

		return mapping.findForward("success");

	}

}
