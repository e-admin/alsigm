package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.keys.InformesKeys;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class NuevoInformeAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ISicresServicioRPAdmin oServicio =new ISicresServicioRPAdminAdapter();
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);

		request.setAttribute(InformesKeys.TIPOS_INFORMES, tiposInformes);

		setInSession(request, InformesKeys.KEY_LISTA_OFICINAS_INFORME, new ArrayList());
		setInSession(request, InformesKeys.KEY_LISTA_LIBROS_INFORME, new ArrayList());
		setInSession(request, InformesKeys.KEY_LISTA_PERFILES_INFORME, new ArrayList());

		return mapping.findForward("success");
	}
}