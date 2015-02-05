package es.ieci.tecdoc.sicres.terceros.web.controller.direcciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import es.ieci.tecdoc.fwktd.web.controller.list.ListController;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 *
 * @author IECISA
 *
 */
public class DireccionTelematicaController extends
		ListController<DireccionTelematicaVO> {

	public DireccionTelematicaController() {
		setSupportedMethods(new String[] { METHOD_GET, METHOD_POST });
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String idTercero = WebUtils.findParameterValue(request, TERCERO);
		TerceroValidadoVO tercero = new TerceroValidadoVO();
		tercero.setId(idTercero);

		ModelAndView mav = new ModelAndView(getListView());

		mav.addObject(
				getEntityListName(),
				getTercerosFacade().getDirecciones(tercero,
						DireccionType.TELEMATICA));

		mav.addObject(TERCERO, idTercero);

		return mav;
	}

	public TercerosFacade getTercerosFacade() {
		return tercerosFacade;
	}

	public void setTercerosFacade(TercerosFacade tercerosFacade) {
		this.tercerosFacade = tercerosFacade;
	}

	protected TercerosFacade tercerosFacade;

	protected static final String TERCERO = "tercero";

}
