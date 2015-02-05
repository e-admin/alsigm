package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.NumeracionForm;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Contadores;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ConfigurarNumeracionLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ConfigurarNumeracionLibroAction.class);
	private static final int INTERVALO_ANOS = 20;
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		String tipoLibro = (String)request.getParameter("tipoLibro");
		String anyo = (String)request.getParameter("anyo");
		int anoActual = 0;

		if( anyo == null || anyo.equals("")) {
			Calendar c = new GregorianCalendar();
			anoActual = c.get(Calendar.YEAR);
		} else {
			anoActual = Integer.parseInt(anyo);
		}

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String central = String.valueOf(oServicio.obtenerContadorCentral(anoActual, Integer.parseInt(tipoLibro), entidad));
		Contadores contadores = oServicio.obtenerContadoresOficinas(anoActual, Integer.parseInt(tipoLibro), entidad);

		NumeracionForm numeracionForm = (NumeracionForm)form;
		try{
			//comprobamos si el valor central es un integer
			int validateCentral = Integer.parseInt(numeracionForm.getCentral());
		}catch (NumberFormatException e){
			if(logger.isDebugEnabled()){
				logger.debug("El valor introducido como Central no es de tipo Integer ["
						+ numeracionForm.getCentral() + "]");
			}
			// El valor introducido como numero central no cumple que sea un
			// integer con lo que mantenemos el valor anterior
			numeracionForm.setCentral(central);
		}

		if( numeracionForm.count() == 0 ||
			( numeracionForm.getContadores(0) != null &&
			anoActual != numeracionForm.getContadores(0).getAnyo())) {

				numeracionForm.setAnyo(String.valueOf(anoActual));
				numeracionForm.setCentral(central);
				numeracionForm.clear();
				for( int i = 0; i < contadores.count(); i++) {
					numeracionForm.add(contadores.get(i));
				}
		}

		request.setAttribute("contadores", contadores);
		request.setAttribute("contadoresSize", String.valueOf(contadores.count()));
		request.setAttribute("anos", obtenerAnos());
		request.setAttribute("tipoLibro", tipoLibro);

		return mapping.findForward("success");
	}

	public OptionsBean obtenerAnos() {
		OptionsBean anos = new OptionsBean();
		Calendar c = new GregorianCalendar();
		int anoActual = c.get(Calendar.YEAR);
		int ano;
		OptionBean option;
		for( int i = (anoActual - INTERVALO_ANOS); i < anoActual; i++ ) {
			ano = i + 1;
			option = new OptionBean();
			option.setCodigo(String.valueOf(ano));
			option.setDescripcion(String.valueOf(ano));
			anos.add(option);
		}

		for( int i = anoActual; i < (anoActual + INTERVALO_ANOS); i++ ) {
			ano = i + 1;
			option = new OptionBean();
			option.setCodigo(String.valueOf(ano));
			option.setDescripcion(String.valueOf(ano));
			anos.add(option);
		}
		return anos;
	}
}
