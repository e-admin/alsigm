package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class RellenaCombosFiltrosAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(RellenaCombosFiltrosAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String tipoCampo = (String)request.getParameter("tipoCampo");
		if( tipoCampo != null && !tipoCampo.equals("")) {
			response.getWriter().print(obtenerOperadores(tipoCampo, request));
		}

		String nexo = (String)request.getParameter("nexo");
		if( nexo != null && !nexo.equals("")) {
			response.getWriter().print(obtenerNexos( request));
		}

		return null;
	}

	public String obtenerOperadores( String tipoCampo, HttpServletRequest request ) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean operadores = oServicio.obtenerOperadores(tipoCampo, entidad);
		OptionBean option;

		String codigo = "";
		String descripcion = "";
		String token = "|";
		String resultado = "";
		for (int i=0;i<operadores.count();i++)
		{
			option = (OptionBean)operadores.get(i);
			codigo += option.getCodigo() + token;
			descripcion += option.getDescripcion() + token;
			resultado = codigo+"#"+descripcion;
		}
		return resultado;
	}

	public String obtenerNexos( HttpServletRequest request ) throws Exception {
		String token = "|";
		String codigo = "";
		String descripcion = "";
		String resultado = "";
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource", LocaleFilterHelper.getCurrentLocale(request));
		codigo = "AND" + token;
		descripcion = rb.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.nexo.y" ) + token;
		codigo += "OR" + token;
		descripcion += rb.getString("ieci.tecdoc.sgm.rpadmin.libros.filtros.nexo.o" ) + token;
		resultado += codigo+"#"+descripcion;
		return resultado;
	}
}
