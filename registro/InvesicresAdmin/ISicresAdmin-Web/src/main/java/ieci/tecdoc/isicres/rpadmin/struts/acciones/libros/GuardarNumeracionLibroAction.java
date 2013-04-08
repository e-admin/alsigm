package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.NumeracionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarNumeracionLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarNumeracionLibroAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		NumeracionForm numeracionForm = (NumeracionForm)form;

		String tipoLibro = (String)request.getParameter("tipoLibro");

		try {

			int anyo = Integer.parseInt(numeracionForm.getAnyo());
			int central = Integer.parseInt(numeracionForm.getCentral());
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			oServicio.editarContadorCentral(anyo, Integer.parseInt(tipoLibro),
											central, entidad);

			oServicio.editarContadoresOficinas(Integer.parseInt(tipoLibro),
											   numeracionForm.getContadoresOficina(),
											   entidad);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = null;
			if( Integer.parseInt(tipoLibro) == Libro.LIBRO_ENTRADA) {
				mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.numeracion.entrada.guardadoOK");
			} else {
				mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.numeracion.salida.guardadoOK");
			}
			messages.add("Result: ", mesage);
			saveMessages(request, messages);

		} catch (ISicresRPAdminException e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
		}

		return  mapping.findForward("success");
	}

}
