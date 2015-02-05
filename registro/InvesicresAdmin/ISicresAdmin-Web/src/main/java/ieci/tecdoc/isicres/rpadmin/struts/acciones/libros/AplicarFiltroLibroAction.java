package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.FiltrosForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Campos;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Filtro;
import es.ieci.tecdoc.isicres.admin.beans.Filtros;
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AplicarFiltroLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(AplicarFiltroLibroAction.class);

	private static final String OPERADORES_TIPO = "Operadores_tipo_";
	private static final String CAMPOS_TIPO = "Campos_tipo_";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String idLibro = (String)request.getParameter("idLibro") != null ? (String)request.getParameter("idLibro") : (String)request.getAttribute("idLibro");
		String nombreObjeto = (String)request.getParameter("nombreObjeto") != null ? (String)request.getParameter("nombreObjeto") : (String)request.getAttribute("nombreObjeto");
		String idObjeto = (String)request.getParameter("idObjeto") != null ? (String)request.getParameter("idObjeto") : (String)request.getAttribute("idObjeto");
		String tipoFiltroS = (String)request.getParameter("tipoFiltro") != null ? (String)request.getParameter("tipoFiltro") : (String)request.getAttribute("tipoFiltro");
		String fila = (String)request.getParameter("fila");
		String filaAnadir = (String)request.getParameter("filaAnadir");
		String filaEliminar = (String)request.getParameter("filaEliminar");
		String accion = (String)request.getParameter("accion");

		final int tipoFiltro = Integer.parseInt(tipoFiltroS);

		FiltrosForm filtrosForm = (FiltrosForm)form;
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		LibroBean libro = oServicio.obtenerLibroBean(Integer.parseInt(idLibro),  entidad);

		// Cargamos el combo de campos
		Campos campos=Utils.obtenerCampos(oServicio, libro.getTipo(), tipoFiltro, request, LocaleFilterHelper.getCurrentLocale(request));
		request.setAttribute("campos", campos);

		String idCampo = (String)request.getParameter("idCampo");
		String tipoCampo = Utils.dameTipoCampo(idCampo, libro.getTipo());

		OptionsBean operadores;
		OptionsBean nexos;
		if( fila == null )  fila = "0";

        // Cargamos el combo de los operadores
		if(tipoCampo != null && !"".equals(tipoCampo) && !tipoCampo.equals("null") ) {
			if( request.getSession(false).getAttribute(OPERADORES_TIPO + tipoCampo) != null ) {
				operadores = (OptionsBean)request.getSession(false).getAttribute(OPERADORES_TIPO + tipoCampo);
			} else {
				operadores = oServicio.obtenerOperadores(tipoCampo,  entidad);
				operadores = Utils.traduceOperadores(operadores, LocaleFilterHelper.getCurrentLocale(request));
				request.getSession(false).setAttribute(OPERADORES_TIPO + tipoCampo, operadores);
			}
			nexos = Utils.traduceNexos(LocaleFilterHelper.getCurrentLocale(request));
			rellenaCambioCampo(filtrosForm, tipoCampo, Integer.parseInt(idCampo), Integer.parseInt(fila), libro.getTipo(), request);
		} else {
			operadores = new OptionsBean();
            // Cargamos el combo con lo nexos
			nexos = new OptionsBean();
			tipoCampo = null;
			if( filaAnadir == null || filaAnadir.equals("")) {
				if( filtrosForm.getFiltros(Integer.parseInt(fila)) != null) {
					if( filaEliminar == null || filaEliminar.equals("null")) {
						((Filtro)filtrosForm.getFiltros(Integer.parseInt(fila))).setValor("");
					}
				}
			}
		}
		if( filaAnadir != null && !filaAnadir.equals("null"))
			fila = String.valueOf((Integer.parseInt(filaAnadir) + 1));

		if( filaEliminar != null && !filaEliminar.equals("null")) {
			if( !filaEliminar.equals("0"))
				fila = String.valueOf((Integer.parseInt(filaEliminar) - 1));
			else
				fila = filaEliminar;
			filtrosForm.remove(Integer.parseInt(filaEliminar));
			request.setAttribute("filtros", Utils.obtenerFiltros(String.valueOf(filtrosForm.count()-1)));
		} else {
			request.setAttribute("tipoCampo_" + fila, tipoCampo);
			request.setAttribute("idCampo_" + fila, idCampo);
			request.setAttribute("nexos["+fila+"]", nexos);
			request.setAttribute("operadores["+fila+"]", operadores);
			if( filaAnadir != null && !filaAnadir.equals("null"))
				request.setAttribute("filtros", Utils.obtenerFiltros(fila));
			else {
				if( filtrosForm.count() > 0)
					request.setAttribute("filtros", Utils.obtenerFiltros(String.valueOf(filtrosForm.count()-1)));
				else
					request.setAttribute("filtros", Utils.obtenerFiltros("0"));
			}
		}
		rellenaCombosFormulario(filtrosForm, Integer.parseInt(fila), libro.getTipo(), accion, request);
		request.setAttribute("filtrosSize", String.valueOf(((Filtros)request.getAttribute("filtros")).count()));
		request.setAttribute("libro", libro);
		request.setAttribute("idObjeto", idObjeto);
		request.setAttribute("nombreObjeto", nombreObjeto);
		request.setAttribute("tipoFiltro", tipoFiltroS);
		borrarFormulario( accion, filtrosForm, request);
		return mapping.findForward("success");
	}

	public void rellenaCombosFormulario(FiltrosForm filtrosForm,  int fila, int tipoLibro, String accion, HttpServletRequest request) throws Exception {
		Filtro filtro;
		String tipoCampo;
		OptionsBean operadores;
		int i = 0;
		if (accion == null || accion.equals("")) {
			for( i = 0; i < filtrosForm.count(); i++) {
				filtro = filtrosForm.getFiltros(i);
				if( filtro != null ) {
					if( filtro.getCampo() != null && !filtro.getCampo().equals("")) {
						filtro.setNexos(Utils.traduceNexos(LocaleFilterHelper.getCurrentLocale(request)));
						tipoCampo = Utils.dameTipoCampo(((Filtro)filtrosForm.getFiltros(i)).getCampo(), tipoLibro);
						if( request.getSession(false).getAttribute(OPERADORES_TIPO + tipoCampo) != null ) {
							operadores = (OptionsBean)request.getSession(false).getAttribute(OPERADORES_TIPO + tipoCampo);
							filtro.setOperadores(operadores);
						} else {
							ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
							//Se obtiene la entidad
							Entidad entidad = new Entidad();
							entidad.setIdentificador(MultiEntityContextHolder.getEntity());

							operadores = oServicio.obtenerOperadores(tipoCampo,  entidad);
							request.getSession(false).setAttribute(OPERADORES_TIPO + tipoCampo, operadores);
							filtro.setOperadores(operadores);
						}
						request.setAttribute("operadores["+i+"]", filtro.getOperadores());
						request.setAttribute("nexos["+i+"]", filtro.getNexos());
						request.setAttribute("tipoCampo_" + i, tipoCampo);
						request.setAttribute("idCampo_" + i, ((Filtro)filtrosForm.getFiltros(i)).getCampo());
						if( tipoCampo.equals(Filtro.TIPO_COMBO)) {
							Utils.fillComboBox(tipoLibro, Integer.parseInt(((Filtro)filtrosForm.getFiltros(i)).getCampo()),
									     i, filtro, request, false, LocaleFilterHelper.getCurrentLocale(request));
						}
					} else {
						request.setAttribute("tipoCampo_" + i, "");
						request.setAttribute("idCampo_" + i, "");
						request.setAttribute("nexos["+i+"]", new OptionsBean());
						request.setAttribute("operadores["+i+"]", new OptionsBean());
					}
				}
			}
		} else
			borrarFormulario( accion, filtrosForm, request);
	}

	/*************************
	 * Método que borrar todos los datos del formulario de filtros (FiltrosForm)
	 * @param accion - Accion borrar
	 * @param filtrosForm - Formulario de filtros
	 * @param request - Obj request
	 */

	public void borrarFormulario( String accion, FiltrosForm filtrosForm, HttpServletRequest request) {
		Filtro filtro;
		if( accion != null && accion.equals("BORRAR")) {
			for( int z = 0; z < filtrosForm.count(); z++) {
				filtro = filtrosForm.getFiltros(z);
				if( filtro != null ) {
					filtro.setCampo("");
					filtro.setNexo("");
					filtro.setOperador("");
					filtro.setValor("");
				}
			}
			request.setAttribute("filtrosSize", "1");
			request.setAttribute("filtros", Utils.obtenerFiltros("0"));
		}
	}


	public void rellenaCambioCampo(FiltrosForm filtrosForm, String tipo, int idCampo, int fila, int tipoLibro, HttpServletRequest request) throws Exception {
		Filtro filtro = filtrosForm.getFiltros(fila);
		if( tipo.equals(Filtro.TIPO_DATE)) {// Tipo Fecha
			filtro.setValor(Utils.formatDate(new Date()));
			filtrosForm.setFiltro(fila, filtro);
		} else if( tipo.equals(Filtro.TIPO_COMBO)) { //Combos
			Utils.fillComboBox(idCampo, fila, tipoLibro, filtro, request, true, LocaleFilterHelper.getCurrentLocale(request));
		} else if( tipo.equals(Filtro.TIPO_STRING) ||
				tipo.equals(Filtro.TIPO_OFICINAS) ||
				tipo.equals(Filtro.TIPO_UNIDADES_ADMIN) ||
				tipo.equals(Filtro.TIPO_ASUNTO)) {
			filtro.setValor("");
		}
	}
}