package es.ieci.tecdoc.sicres.terceros.web.controller.direcciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import es.ieci.tecdoc.fwktd.web.controller.list.ListController;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.DireccionesDelegate;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 *
 * @author IECISA
 *
 */
public class SetDireccionAsPrincipalController extends
		ListController<BaseDireccionVO> {

	public SetDireccionAsPrincipalController() {
		setSupportedMethods(new String[] { METHOD_GET, METHOD_POST });
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idDireccion = ServletRequestUtils.getStringParameter(request,
				"direccion");
		String idTercero = ServletRequestUtils.getStringParameter(request,
				"tercero");

		BaseDireccionVO direccionVO = ((DireccionesDelegate) getDelegate())
				.retrieve(idDireccion);
		Assert.state(
				StringUtils.equals(direccionVO.getTercero().getId(), idTercero),
				"La direccion especificada pertenece a otro tercero");

		((DireccionesDelegate) getDelegate()).selectAsPrincipal(direccionVO,
				direccionVO.getTercero());

		ModelAndView mav = new ModelAndView(getListView());
		mav.getModel().put(
				getEntityListName(),
				getTercerosFacade().getDirecciones(
						(TerceroValidadoVO) direccionVO.getTercero(),
						direccionVO.getTipo()));
		return mav;
	}

	public TercerosFacade getTercerosFacade() {
		return tercerosFacade;
	}

	public void setTercerosFacade(TercerosFacade tercerosFacade) {
		this.tercerosFacade = tercerosFacade;
	}

	protected TercerosFacade tercerosFacade;

}
