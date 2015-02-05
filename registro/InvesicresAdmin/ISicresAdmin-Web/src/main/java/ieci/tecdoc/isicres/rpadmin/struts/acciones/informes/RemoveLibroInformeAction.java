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
import es.ieci.tecdoc.isicres.admin.beans.LibroInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class RemoveLibroInformeAction    extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		request.setAttribute("tiposInformes", tiposInformes);

		InformeForm informeForm = (InformeForm) form;

		String posLibro = informeForm.getPosLibro();
		String estadoLibro = informeForm.getEstadoLibro();

		int posicion = 0;

		if(StringUtils.isNotEmpty(posLibro)){
			posicion = Integer.parseInt(posLibro);
		}

		if(posicion > 0){
			List listaLibros = (List) getListaFromSession(request, InformesKeys.KEY_LISTA_LIBROS_INFORME);

			LibroInformeBean libro = (LibroInformeBean)listaLibros.get(posicion-1);

			libro.setEstado(Integer.parseInt(estadoLibro));

			//Si el documento esta en base de datos pasarlo a la lista de borrados
			if(Estados.isEliminado(libro.getEstado())){
				List listaLibrosEliminados = (List) getListaFromSession(request, InformesKeys.KEY_LISTA_LIBROS_ELIMINADOS_INFORME);
				listaLibrosEliminados.add(libro);
				setInSession(request, InformesKeys.KEY_LISTA_LIBROS_ELIMINADOS_INFORME,listaLibrosEliminados);
			}
			listaLibros.remove(posicion-1);
			setInSession(request, InformesKeys.KEY_LISTA_LIBROS_INFORME, listaLibros);
			informeForm.resetLibro();
			informeForm.change();
		}

		return mapping.findForward("success");
	}
}
