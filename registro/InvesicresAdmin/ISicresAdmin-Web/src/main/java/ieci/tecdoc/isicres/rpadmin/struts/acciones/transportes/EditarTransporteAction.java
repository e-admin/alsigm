package ieci.tecdoc.isicres.rpadmin.struts.acciones.transportes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.TransporteForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Transporte;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO;

public class EditarTransporteAction extends RPAdminWebAction {

//	private static final Logger logger = Logger.getLogger(EditarTransporteAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		String id = (String)request.getParameter("idTransporte");
		if(id == null || id.equals("")) {
			id = (String)request.getSession(false).getAttribute("idTransporte");
		}

		if( id != null && !id.equals("")) {

			//Mostramos la página de editarOficina.jsp
			request.setAttribute("idTransporte", id);
			request.getSession(false).setAttribute("idTransporte", id);
			TransporteForm transporteForm = (TransporteForm)form;
			//Si no tenemos transporte en el formulario es la primera vez que se carga, rellenar con datos de negocio

			if(transporteForm.getId()==null) {
				Entidad entidad = new Entidad();
				entidad.setIdentificador(MultiEntityContextHolder.getEntity());

				Transporte transporte = oServicio.obtenerTransporte(Integer.parseInt(id), entidad);

				TipoTransporteIntercambioRegistralManager manager = es.ieci.tecdoc.isicres.admin.business.spring.AdminIRManagerProvider.getInstance().getTipoTransporteIntercambioRegistralManager();

				TipoTransporteIntercambioRegistralVO tipoTransporte = manager.getTipoTransporteByDesc(transporte.getTransport().toUpperCase());
				if (tipoTransporte != null){
					transporteForm.setCodigoIntercambioRegistral(tipoTransporte.getCodigoSIR());
				}

				BeanUtils.copyProperties(transporteForm, transporte);
			}
		}
		return mapping.findForward("success");
	}

}
