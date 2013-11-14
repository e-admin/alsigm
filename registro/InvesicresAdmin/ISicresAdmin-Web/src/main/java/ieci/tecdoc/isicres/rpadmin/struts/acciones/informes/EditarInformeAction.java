package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.keys.InformesKeys;
import ieci.tecdoc.isicres.rpadmin.struts.forms.InformeForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.InformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EditarInformeAction extends RPAdminWebAction{
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		setInSession(request, InformesKeys.KEY_LISTA_OFICINAS_INFORME, null);
		setInSession(request, InformesKeys.KEY_LISTA_OFICINAS_ELIMINADAS_INFORME, null);
		setInSession(request, InformesKeys.KEY_LISTA_PERFILES_INFORME, null);
		setInSession(request, InformesKeys.KEY_LISTA_PERFILES_ELIMINADAS_INFORME, null);
		setInSession(request, InformesKeys.KEY_LISTA_LIBROS_INFORME, null);
		setInSession(request, InformesKeys.KEY_LISTA_LIBROS_ELIMINADOS_INFORME, null);

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		OptionsBean perfiles = oServicio.obtenerPerfilesReportCombo(entidad);

		String idInforme = (String)request.getParameter(InformesKeys.ID_INFORME);
		if(idInforme == null || idInforme.equals("")) {
			idInforme = (String)request.getSession(false).getAttribute(InformesKeys.ID_INFORME);
		}else{
			request.setAttribute("idInforme", idInforme);
			request.setAttribute(InformesKeys.TIPOS_INFORMES, tiposInformes);

			InformeForm informeForm = (InformeForm)form;
			//Si no tenemos oficina en el formulario es la primera vez que se carga, rellenar con datos de negocio
			if(informeForm.getId()==null) {
				InformeBean informe = oServicio.obtenerInforme(new Integer(idInforme).intValue(), entidad, perfiles);
				informeForm.set(informe);
				setInSession(request, InformesKeys.KEY_LISTA_OFICINAS_INFORME, informe.getOficinas().getLista());
				setInSession(request, InformesKeys.KEY_LISTA_PERFILES_INFORME, informe.getPerfiles().getLista());
				setInSession(request, InformesKeys.KEY_LISTA_LIBROS_INFORME, informe.getLibros().getLista());
				setInSession(request, InformesKeys.KEY_LISTA_LIBROS_ELIMINADOS_INFORME, null);
				setInSession(request, InformesKeys.KEY_LISTA_OFICINAS_ELIMINADAS_INFORME, null);
				setInSession(request, InformesKeys.KEY_LISTA_PERFILES_ELIMINADAS_INFORME, null);
			}
		}

		return mapping.findForward("success");
	}
}
