package es.ieci.tecdoc.fwktd.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.SimpleFormController;

@SuppressWarnings("deprecation")
public class BaseFormController extends SimpleFormController {

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {

		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(
				Integer.class, null, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(
				Long.class, null, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));

		super.initBinder(request, binder);
	}

	@SuppressWarnings( { "unchecked" })
	@Override
	protected final Map referenceData(HttpServletRequest request,
			Object command, Errors errors) throws Exception {
		Map referenceData = super.referenceData(request, command, errors);

		if (MapUtils.isEmpty(referenceData)) {
			referenceData = new HashMap<String, Object>();
		}

		doReferenceData(request, command, errors, referenceData);

		return referenceData;
	}

	/**
	 * Permite exponer objetos en el modelo a través de
	 * <code>referenceData</code>.
	 * 
	 * @param request
	 *            petición HTTP en curso
	 * @param command
	 *            objeto formulario conteniendo los parámetros de la petición
	 *            HTTP en curso
	 * @param errors
	 *            envoltorio de los errores de valicación del formulario
	 * @param referenceData
	 *            referenceData mapa en el que almacenar los objetos a exponer
	 *            en el modelo
	 */
	protected void doReferenceData(HttpServletRequest request, Object command,
			Errors errors, Map<String, Object> referenceData) {

	}
}
