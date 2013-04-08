package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;

public class NuevoLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(NuevoLibroAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Cargamos los combos Tipo de Libro y Numeración de Libro
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource", LocaleFilterHelper.getCurrentLocale(request));	
		
		OptionsBean optionsTipo = new OptionsBean();
		
		OptionBean optionTipoSeleccione = new OptionBean();
		optionTipoSeleccione.setCodigo("");
		optionTipoSeleccione.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.tipo.seleccione"));
		optionsTipo.add(optionTipoSeleccione);
		
		OptionBean optionTipoEntrada = new OptionBean();
		optionTipoEntrada.setCodigo(String.valueOf(Libro.LIBRO_ENTRADA));
		optionTipoEntrada.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.entrada"));
		optionsTipo.add(optionTipoEntrada);
		
		OptionBean optionTipoSalida = new OptionBean();
		optionTipoSalida.setCodigo(String.valueOf(Libro.LIBRO_SALIDA));
		optionTipoSalida.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.tipo.libro.salida"));
		optionsTipo.add(optionTipoSalida);
		
		OptionsBean optionsNumeracion = new OptionsBean();
		
		OptionBean optionNumSeleccione = new OptionBean();
		optionNumSeleccione.setCodigo("");
		optionNumSeleccione.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.numeracion.seleccione"));
		optionsNumeracion.add(optionNumSeleccione);
		
		OptionBean optionNumCentral = new OptionBean();
		optionNumCentral.setCodigo(String.valueOf(LibroBean.NUMERACION_CENTRAL));
		optionNumCentral.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.numeracion.central"));
		optionsNumeracion.add(optionNumCentral);
		
		OptionBean optionNumOficina = new OptionBean();
		optionNumOficina.setCodigo(String.valueOf(LibroBean.NUMERACION_POR_OFICINA));
		optionNumOficina.setDescripcion(rb.getString("ieci.tecdoc.sgm.rpadmin.libros.numeracion.oficina"));
		optionsNumeracion.add(optionNumOficina);
		
		request.setAttribute("tipoLibro", optionsTipo);
		request.setAttribute("numeracionLibro", optionsNumeracion);
			
		return mapping.findForward("success");
	}

}
