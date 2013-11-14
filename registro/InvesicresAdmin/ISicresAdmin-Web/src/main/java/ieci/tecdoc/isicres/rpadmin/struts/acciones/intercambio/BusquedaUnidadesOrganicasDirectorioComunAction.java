package ieci.tecdoc.isicres.rpadmin.struts.acciones.intercambio;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.BusquedaDirectorioComunForm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosUnidadOrganicaDCVO;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class BusquedaUnidadesOrganicasDirectorioComunAction extends RPAdminWebAction {


	private static final Logger logger = Logger.getLogger(BusquedaUnidadesOrganicasDirectorioComunAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();


		BusquedaDirectorioComunForm busquedaForm = (BusquedaDirectorioComunForm)form;

		List<DatosBasicosUnidadOrganicaDCVO> listaUnidadesOrganicas = null;
		if(busquedaForm!=null)
		{
			if (StringUtils.isNotEmpty(busquedaForm.getCodEntity())) {
				listaUnidadesOrganicas = oServicio
						.findUnidadesOrganicasDirectorioComunByCodEntidad(
								busquedaForm.getCodEntity(), busquedaForm.getCodigo(),  busquedaForm.getNombre());
			} else {
				Criterios<CriterioUnidadOrganicaEnum> criterios= new Criterios<CriterioUnidadOrganicaEnum>();
				criterios = busquedaForm.setForUnidadOrganica(criterios);
				if (criterios.getCriterios() != null
						&& criterios.getCriterios().size() > 0) {
					listaUnidadesOrganicas = oServicio
							.findUnidadesOrganicasDirectorioComun(criterios);
				}
			}

			 request.setAttribute("listaUnidadesOrganicas", listaUnidadesOrganicas);
		}

		//Solo buscamos si hay algún criterio, para no permitir la lista de TODOS
//		if(criterios.getCriterios()!=null && criterios.getCriterios().size()>0)
//		{
//			List<DatosBasicosUnidadOrganicaDCVO> listaUnidadesOrganicas = oServicio.findUnidadesOrganicasDirectorioComun(criterios);
//	        request.setAttribute("listaUnidadesOrganicas", listaUnidadesOrganicas);
//		}
        return mapping.findForward("success");

	}

}
