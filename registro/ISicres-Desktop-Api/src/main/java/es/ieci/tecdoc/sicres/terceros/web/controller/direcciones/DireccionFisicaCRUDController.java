package es.ieci.tecdoc.sicres.terceros.web.controller.direcciones;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

import es.ieci.tecdoc.fwktd.web.controller.crud.CRUDCommand;
import es.ieci.tecdoc.fwktd.web.controller.crud.CRUDController;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionFisicaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.MasterValuesDelegate;

/**
 *
 * @author IECISA
 *
 */
public class DireccionFisicaCRUDController extends
		CRUDController<DireccionFisicaVO> {

	public DireccionFisicaCRUDController() {
		setSupportedMethods(new String[] { METHOD_GET, METHOD_POST });
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		@SuppressWarnings("unchecked")
		CRUDCommand<DireccionFisicaVO> formBackingObject = (CRUDCommand<DireccionFisicaVO>) super
				.formBackingObject(request);

		if (isFormSubmission(request)) {
			String codigoCiudad = WebUtils.findParameterValue(request,
					"content.ciudad.codigo");
			if (!StringUtils.isEmpty(codigoCiudad)) {
				CiudadVO ciudad = getMasterValuesDelegate().getCiudad(
						codigoCiudad);
				formBackingObject.getContent().setCiudad(ciudad);
			}

			String codigoProvincia = WebUtils.findParameterValue(request,
					"content.provincia.codigo");
			if (!StringUtils.isEmpty(codigoProvincia)) {
				ProvinciaVO provincia = getMasterValuesDelegate().getProvincia(
						codigoProvincia);
				formBackingObject.getContent().setProvincia(provincia);
			}

			String codigoPais = WebUtils.findParameterValue(request,
					"content.pais.codigo");
			if (!StringUtils.isEmpty(codigoPais)) {
				PaisVO pais = getMasterValuesDelegate().getPais(codigoPais);
				formBackingObject.getContent().setPais(pais);
			}
		}

		return formBackingObject;
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

	@Override
	@SuppressWarnings("unchecked")
	protected void doReferenceData(HttpServletRequest request, Object command,
			Errors errors, Map<String, Object> referenceData) {
		super.doReferenceData(request, command, errors, referenceData);

		DireccionFisicaVO direccion = ((CRUDCommand<DireccionFisicaVO>) command)
				.getContent();

		if (null == direccion.getPais().getCodigo()
				|| StringUtils.equals(direccion.getPais().getNombre(), ESPAÑA)) {
			referenceData.put(PROVINCIAS, getMasterValuesDelegate()
					.getProvincias());

			String provinciaPorDefecto = Configurator
					.getInstance()
					.getProperty(
							ConfigurationKeys.KEY_DESKTOP_PROVINCIA_POR_DEFECTO);
			referenceData.put(PROVINCIA_POR_DEFECTO, provinciaPorDefecto);
			if (!StringUtils.isEmpty(provinciaPorDefecto)
					&& StringUtils.isEmpty(direccion.getCiudad().getNombre())) {
				ProvinciaVO provincia = new ProvinciaVO();
				provincia.setNombre(provinciaPorDefecto);
				referenceData.put(CIUDADES, getMasterValuesDelegate()
						.getCiudades(provincia));
			} else {
				referenceData.put(CIUDADES, getMasterValuesDelegate()
						.getCiudades(direccion.getProvincia()));
			}
		}

		referenceData.put(PAISES, getMasterValuesDelegate().getPaises());

	}

	/**
	 *
	 * @param request
	 * @param command
	 * @return
	 */
	private String getIdTercero(HttpServletRequest request, Object command) {
		@SuppressWarnings("unchecked")
		String idTercero = ((CRUDCommand<DireccionFisicaVO>) command)
				.getContent().getTercero().getId();

		return (!StringUtils.isEmpty(idTercero) ? idTercero : WebUtils
				.findParameterValue(request, TERCERO));
	}

	public MasterValuesDelegate getMasterValuesDelegate() {
		return masterValuesDelegate;
	}

	public void setMasterValuesDelegate(
			MasterValuesDelegate masterValuesDelegate) {
		this.masterValuesDelegate = masterValuesDelegate;
	}

	protected MasterValuesDelegate masterValuesDelegate;

	protected static final String PAISES = "paises";
	protected static final String PROVINCIAS = "provincias";
	protected static final String CIUDADES = "ciudades";
	protected static final String TERCERO = "tercero";
	protected static final String PROVINCIA_POR_DEFECTO = "defaultProvincia";
	protected static final String ESPAÑA = "España";

}
