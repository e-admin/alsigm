package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.FiltrosForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class CargarFiltrosLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(CargarFiltrosLibroAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
//		String idEstado = (String)request.getParameter("idEstado");
		String idLibro = (String)request.getParameter("idLibro");
//		final String tipoLibroS = (String)request.getParameter("tipoLibro");
		final String tipoFiltroS = (String)request.getParameter("tipoFiltro");
//		String nombre = (String)request.getParameter("nombre");
		String nombreObjeto = (String)request.getParameter("nombreObjeto");
		String idObjeto = (String)request.getParameter("idObjeto");
		Filtros filtros = new Filtros();
		Filtro filtro;
		int id = Integer.parseInt(idObjeto);
		final int tipoFiltro = Integer.parseInt(tipoFiltroS);
//		final int tipoLibro = Integer.parseInt(tipoLibroS);

		int idBook = Integer.parseInt(idLibro);

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		LibroBean libro = oServicio.obtenerLibroBean(idBook, entidad);
		filtros = oServicio.obtenerFiltros(tipoFiltro, libro.getTipo(), idBook, id, entidad);
		if( filtros != null ) {
			if( filtros.count() > 0) {
				request.setAttribute("filtrosSize", String.valueOf(filtros.count()));
				FiltrosForm filtrosForm = (FiltrosForm)form;
				BeanUtils.copyProperties(filtrosForm, filtros);

				OptionsBean operadores;
				OptionsBean nexos;
				Campos campos;
				boolean bNoTieneFiltros = true;
				for( int i = 0; i < filtros.count(); i++) {
					filtro = (Filtro)filtros.get(i);
					if( filtro.getCampo() != null && !filtro.getCampo().equals("")) {
						campos = Utils.obtenerCampos(oServicio, libro.getTipo(), tipoFiltro, request, LocaleFilterHelper.getCurrentLocale(request));
						request.setAttribute("campos", campos);
						operadores = Utils.obtenerOperadores(i, filtro.getCampo(), libro.getTipo(), tipoFiltro, filtro, oServicio, request, LocaleFilterHelper.getCurrentLocale(request));
						filtro.setOperadores(operadores);
						request.setAttribute("operadores["+i+"]", operadores);
						nexos =  Utils.traduceNexos(LocaleFilterHelper.getCurrentLocale(request));
						filtro.setNexos(nexos);
						request.setAttribute("nexos["+i+"]",nexos);
						bNoTieneFiltros = false;
					}
				}
				if( bNoTieneFiltros )
					loadSinFiltros(libro.getTipo(), oServicio, request);

				request.setAttribute("filtros", filtros);
			} else {
				request.setAttribute("filtrosSize", "1");
				loadSinFiltros(libro.getTipo(), oServicio, request);
			}
		}
//		request.setAttribute("nombre", nombre);
//		request.setAttribute("estado", idEstado);
//		request.setAttribute("tipoLibro", tipoLibroS);
//		request.setAttribute("idLibro", idLibro);
		request.setAttribute("libro", libro);
		request.setAttribute("tipoFiltro", tipoFiltroS);
		request.setAttribute("nombreObjeto", nombreObjeto);
		request.setAttribute("idObjeto", idObjeto);

		return mapping.findForward("success");
	}

	public void loadSinFiltros( int tipoLibro, ISicresServicioRPAdmin oServicio, HttpServletRequest request ) throws Exception {
		Filtros filtros = new Filtros();
		filtros.add(new Filtro());
		Utils.loadCampos(tipoLibro, oServicio, request, LocaleFilterHelper.getCurrentLocale(request));
		request.setAttribute("filtros", filtros);
		request.setAttribute("operadores[0]", new OptionsBean());
		request.setAttribute("nexos[0]", new OptionsBean());
		request.setAttribute("titulo_0", null);
	}
}