package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.keys.InformesKeys;
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
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.PerfilInformeBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AddPerfilInformeAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InformeForm informeForm  = (InformeForm) form;

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		request.setAttribute("tiposInformes", tiposInformes);

		List listaPerfiles = (List) getListaFromSession(request, InformesKeys.KEY_LISTA_PERFILES_INFORME);

		String idPerfil = informeForm.getIdPerfil();

		if(StringUtils.isNotBlank(idPerfil)){
			//Comprobar que el documento no esta duplicado
			boolean duplicado = false;

			for (Iterator iterator = listaPerfiles.iterator(); iterator.hasNext();) {
				PerfilInformeBean perfil = (PerfilInformeBean) iterator.next();

				if(perfil != null && idPerfil.equals(new Integer(perfil.getIdPerfil()).toString())){
					duplicado = true;
					break;
				}
			}

			if(!duplicado){
				PerfilInformeBean perfil = new PerfilInformeBean();

				perfil.setIdPerfil(Integer.parseInt(informeForm.getIdPerfil()));
				perfil.setNombrePerfil(informeForm.getNombrePerfil());
				perfil.setEstado(Estados.NUEVO);

				listaPerfiles.add(perfil);
				setInSession(request, InformesKeys.KEY_LISTA_PERFILES_INFORME, listaPerfiles);
				informeForm.resetPerfil();
				informeForm.change();
			}
			else{
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.informes.elemento.perfil.duplicado", informeForm.getNombrePerfil());
				errores.add("Error interno", error);
				saveErrors(request, errores);

			}
		}

		return mapping.findForward("success");

	}
}
