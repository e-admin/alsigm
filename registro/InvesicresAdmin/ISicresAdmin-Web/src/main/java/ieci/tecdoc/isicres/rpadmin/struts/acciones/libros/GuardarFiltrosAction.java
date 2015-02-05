package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.FiltrosForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Filtro;
import es.ieci.tecdoc.isicres.admin.beans.Filtros;
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarFiltrosAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarFiltrosAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		ActionForward af = null;
		ActionForward retorno = new ActionForward();
		String idLibro = (String)request.getParameter("idLibro");
		final String tipoFiltroS = (String)request.getParameter("tipoFiltro");
		String idObjeto = (String)request.getParameter("idObjeto");

		final int tipoFiltro = Integer.parseInt(tipoFiltroS);

		try {
			Filtros filtros = new Filtros();
			FiltrosForm filtrosForm = (FiltrosForm)form;
			FiltrosForm filtrosFormAux = new FiltrosForm();
			Filtro filtro;
			// Limpiamos los filtros que estén en blanco
			for( int i = 0; i < filtrosForm.count(); i++ ) {
				filtro = filtrosForm.getFiltros(i);
				if (filtro.getCampo() != null && !filtro.getCampo().equals("")
						&& StringUtils.isNotBlank(filtro.getValor())) {
					if (Utils.isDBCaseSensitive(request)) {
						filtro.setValor(filtro.getValor().toUpperCase());
					}
					filtrosFormAux.add(filtro);
				}
			}

			int id = -1;
			String msg = "";
			id = Integer.parseInt(idObjeto);

			int idBook = Integer.parseInt(idLibro);

			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			LibroBean libro = oServicio.obtenerLibroBean(idBook, entidad);
			BeanUtils.copyProperties(filtros, filtrosFormAux);
			oServicio.configurarFiltro(tipoFiltro, libro.getTipo(), idBook, id, filtros, entidad);
			request.setAttribute("libro", libro);

			if(tipoFiltro==Filtro.TIPO_FILTRO_OFICINAS){
				af = mapping.findForward("edit");
				retorno.setPath(af.getPath()+"?idLibro="+idLibro);
			} else if( tipoFiltro==Filtro.TIPO_FILTRO_USUARIOS ) {
				af = mapping.findForward("asociar");
				retorno.setPath(af.getPath()+"?idLibro="+idLibro+"&idOficina="+idObjeto);
			}

			retorno.setName(af.getName());
			retorno.setRedirect(true);
			return retorno;

		} catch (ISicresRPAdminException e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			return mapping.getInputForward();
		}
	}
}