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
import es.ieci.tecdoc.isicres.admin.beans.LibroInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AddLibroInformeAction  extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		InformeForm informeForm  = (InformeForm) form;

		ISicresServicioRPAdmin oServicio =new ISicresServicioRPAdminAdapter();

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		request.setAttribute("tiposInformes", tiposInformes);

		List listaLibros = (List) getListaFromSession(request, InformesKeys.KEY_LISTA_LIBROS_INFORME);

		String idLibro = informeForm.getIdLibro();

		if(StringUtils.isNotBlank(idLibro)){
			//Comprobar que el documento no esta duplicado
			boolean duplicado = false;

			for (Iterator iterator = listaLibros.iterator(); iterator.hasNext();) {
				LibroInformeBean libro = (LibroInformeBean) iterator.next();

				if(libro != null && idLibro.equals(libro.getCodigoArchivo())){
					duplicado = true;
					break;
				}
			}

			if(!duplicado){
				LibroInformeBean libro = new LibroInformeBean();

				libro.setCodigoArchivo(informeForm.getIdLibro());
				libro.setNombreArchivo(informeForm.getNombreLibro());
				libro.setEstado(Estados.NUEVO);
				listaLibros.add(libro);
				setInSession(request, InformesKeys.KEY_LISTA_LIBROS_INFORME, listaLibros);
				informeForm.resetLibro();
				informeForm.change();
			}
			else{
				ActionErrors errores = new ActionErrors();
				ActionError error = null;
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.informes.elemento.libro.duplicado", informeForm.getNombreLibro());
				errores.add("Error interno", error);
				saveErrors(request, errores);

			}
		}

		return mapping.findForward("success");

	}
}