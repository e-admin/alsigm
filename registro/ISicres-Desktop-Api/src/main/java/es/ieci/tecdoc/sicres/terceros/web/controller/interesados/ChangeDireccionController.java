package es.ieci.tecdoc.sicres.terceros.web.controller.interesados;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import es.ieci.tecdoc.fwktd.web.controller.BaseFormController;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosFacade;

/**
 *
 * @author IECISA
 *
 */
public class ChangeDireccionController extends BaseFormController {

	@Override
	protected void doReferenceData(HttpServletRequest request, Object command,
			Errors errors, Map<String, Object> referenceData) {
		super.doReferenceData(request, command, errors, referenceData);

		try {
			String id = ServletRequestUtils.getStringParameter(request,
					"tercero.id");
			TerceroValidadoVO tercero = new TerceroValidadoVO();
			tercero.setId(id);

			referenceData.put("direccionesTercero", getTercerosFacade()
					.getDirecciones(tercero));
			referenceData.put("tercero.id", id);

			String idRepresentante = ServletRequestUtils.getStringParameter(
					request, "representante.id");
			if (!StringUtils.isEmpty(idRepresentante)) {
				referenceData.put("representante.id", idRepresentante);
			}
		} catch (ServletRequestBindingException e) {
			logger.error(
					"Error recuperando los parametros para el cambio de direccion de un tercero",
					e);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ModelAndView modelAndView = super.processFormSubmission(request,
				response, command, errors);

		String idRepresentante = WebUtils.findParameterValue(request,
				"representante.id");

		// En caso de tener el identificador del representante
		if (!StringUtils.isEmpty(idRepresentante)) {
			modelAndView.addObject("tercero.id", idRepresentante);
		} else {
			// Exponemos los identificadores del tercero y la direccion a
			// modificar
			// para adjuntarlos en la redireccion
			modelAndView.addObject("tercero.id", ((BaseDireccionVO) command)
					.getTercero().getId());
		}
		modelAndView.addObject("direccion.id",
				((BaseDireccionVO) command).getId());

		return modelAndView;
	}

	public TercerosFacade getTercerosFacade() {
		return tercerosFacade;
	}

	public void setTercerosFacade(TercerosFacade tercerosFacade) {
		this.tercerosFacade = tercerosFacade;
	}

	protected TercerosFacade tercerosFacade;

}
