package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.keys.InformesKeys;
import ieci.tecdoc.isicres.rpadmin.struts.forms.InformeForm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

public class RemovePerfilInformeAction   extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		request.setAttribute("tiposInformes", tiposInformes);

		InformeForm informeForm = (InformeForm) form;

		String posPerfil = informeForm.getPosPerfil();
		String estadoPerfil = informeForm.getEstadoPerfil();

		int posicion = 0;

		if(StringUtils.isNotEmpty(posPerfil)){
			posicion = Integer.parseInt(posPerfil);
		}

		if(posicion > 0){
			List listaPerfiles = (List) getListaFromSession(request, InformesKeys.KEY_LISTA_PERFILES_INFORME);

			PerfilInformeBean perfil = (PerfilInformeBean)listaPerfiles.get(posicion-1);

			perfil.setEstado(Integer.parseInt(estadoPerfil));

			//Si el documento esta en base de datos pasarlo a la lista de borrados
			if(Estados.isEliminado(perfil.getEstado())){
				List listaPerfilesEliminados = (List) getListaFromSession(request, InformesKeys.KEY_LISTA_PERFILES_ELIMINADAS_INFORME);
				listaPerfilesEliminados.add(perfil);
				setInSession(request, InformesKeys.KEY_LISTA_PERFILES_ELIMINADAS_INFORME,listaPerfilesEliminados);
			}

			listaPerfiles.remove(posicion-1);
			setInSession(request, InformesKeys.KEY_LISTA_PERFILES_INFORME, listaPerfiles);
			informeForm.resetPerfil();
			informeForm.change();
		}

		return mapping.findForward("success");
	}
}
