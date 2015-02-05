package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.beans.Libros;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoLibrosAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ListadoLibrosAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		Libros entrada = oServicio.obtenerLibros(Libro.LIBRO_ENTRADA, entidad);
		Libros salida = oServicio.obtenerLibros(Libro.LIBRO_SALIDA, entidad);
		List list = new LinkedList();
		list.addAll(entrada.getLista());
		list.addAll(salida.getLista());

		request.setAttribute("listaLibros", list);
		request.setAttribute("librosEntrada", oServicio.obtenerLibros(Libro.LIBRO_ENTRADA, entidad));
		request.setAttribute("librosSalida", oServicio.obtenerLibros(Libro.LIBRO_SALIDA, entidad));

		ActionErrors errores = request.getAttribute("errors") != null ? (ActionErrors)request.getAttribute("errors"): null;
		if(errores != null && !errores.isEmpty())
			saveErrors(request, errores);

		ActionMessages messages = request.getAttribute("messages") != null ? (ActionMessages)request.getAttribute("messages"): null;
		if(messages != null && !messages.isEmpty())
			saveMessages(request, messages);

		return mapping.findForward("success");
	}
}
