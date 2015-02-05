package es.ieci.tecdoc.sicres.terceros.web.controller.direcciones;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import es.ieci.tecdoc.fwktd.web.controller.crud.CRUDCommand;
import es.ieci.tecdoc.fwktd.web.controller.crud.CRUDController;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.MasterValuesDelegate;

/**
 *
 * @author IECISA
 *
 */
public class DireccionTelematicaCRUDController extends
		CRUDController<DireccionTelematicaVO> {

	public DireccionTelematicaCRUDController() {
		setSupportedMethods(new String[] { METHOD_GET, METHOD_POST });
	}

	@Override
	protected void doReferenceData(HttpServletRequest request,
			Map<String, Object> referenceData) {
		super.doReferenceData(request, referenceData);

		referenceData.put("tiposDirecciones", getMasterValuesDelegate()
				.getTiposDireccionesTelematicas());
	}

	@Override
	public ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		ModelAndView mav = super.processFormSubmission(request, response,
				command, errors);

		// Exponemos el identificador del tercero para filtrar el listado
		// detalle de documentos
		mav.addObject(TERCERO, getIdTercero(request, command));

		return mav;
	}

	public MasterValuesDelegate getMasterValuesDelegate() {
		return masterValuesDelegate;
	}

	public void setMasterValuesDelegate(
			MasterValuesDelegate masterValuesDelegate) {
		this.masterValuesDelegate = masterValuesDelegate;
	}

	/**
	 *
	 * @param request
	 * @param command
	 * @return
	 */
	private String getIdTercero(HttpServletRequest request, Object command) {
		@SuppressWarnings("unchecked")
		String idTercero = ((CRUDCommand<DireccionTelematicaVO>) command)
				.getContent().getTercero().getId();

		return (!StringUtils.isEmpty(idTercero) ? idTercero : WebUtils
				.findParameterValue(request, TERCERO));
	}

	protected static final String TERCERO = "tercero";

	protected MasterValuesDelegate masterValuesDelegate;

}
