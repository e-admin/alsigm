package es.ieci.tecdoc.sicres.terceros.web.controller.interesados;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.util.WebUtils;

import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 * Controller que recibe una cadena con los interesados (y representantes de
 * estos). Se encarga de decodificar esta cadena para convertirla a objetos de
 * dominio que son cargados en la sesión del usuario. Por último, le pasa el
 * testigo al formulario de búsqueda de terceros con los interesados que recibe
 * precargados.
 *
 * @author IECISA
 *
 */
public class LoadInteresadosController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		getInteresadosCRUDController().setInteresados(request,
				request.getSession());

		return new ModelAndView(getSuccessView());
	}

	public InteresadosCRUDController getInteresadosCRUDController() {
		return interesadosCRUDController;
	}

	public void setInteresadosCRUDController(
			InteresadosCRUDController interesadosCRUDController) {
		this.interesadosCRUDController = interesadosCRUDController;
	}

	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	protected InteresadosCRUDController interesadosCRUDController;

	protected String successView;
}
