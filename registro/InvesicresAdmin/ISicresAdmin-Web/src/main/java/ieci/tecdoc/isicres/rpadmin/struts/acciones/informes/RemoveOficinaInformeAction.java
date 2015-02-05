package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
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
import es.ieci.tecdoc.isicres.admin.beans.OficinaInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class RemoveOficinaInformeAction  extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		request.setAttribute("tiposInformes", tiposInformes);

		InformeForm informeForm = (InformeForm) form;

		String posOficina = informeForm.getPosOficina();
		String estadoOficina = informeForm.getEstadoOficina();

		int posicion = 0;

		if(StringUtils.isNotEmpty(posOficina)){
			posicion = Integer.parseInt(posOficina);
		}

		if(posicion > 0){
			List listaOficinas = (List) getListaFromSession(request, KEY_LISTA_OFICINAS_INFORMES);

			OficinaInformeBean oficina = (OficinaInformeBean) listaOficinas.get(posicion-1);

			oficina.setEstado(Integer.parseInt(estadoOficina));

			//Si el documento esta en base de datos pasarlo a la lista de borrados
			if(Estados.isEliminado(oficina.getEstado())){
				List listaOficinasEliminados = (List) getListaFromSession(request, KEY_LISTA_OFICINAS_ELIMINADAS_INFORMES);
				listaOficinasEliminados.add(oficina);
				setInSession(request, KEY_LISTA_OFICINAS_ELIMINADAS_INFORMES,listaOficinasEliminados);
			}

			listaOficinas.remove(posicion-1);
			setInSession(request, KEY_LISTA_OFICINAS_INFORMES, listaOficinas);
			informeForm.resetOficina();
			informeForm.change();
		}

		return mapping.findForward("success");
	}
}
