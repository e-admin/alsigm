package es.ieci.tecdoc.sicres.terceros.web.controller.direcciones;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import es.ieci.tecdoc.fwktd.web.controller.crud.CrudCommandValidator;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;

/**
 *
 * @author IECISA
 *
 */
public class DireccionFisicaValidator extends
		CrudCommandValidator<BaseDireccionVO> {

	@Override
	protected void doValidation(BaseDireccionVO target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "direccion",
				"direccionFisica.direccion.required",
				"Direccion es obligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ciudad",
				"direccionFisica.ciudad.required", "Ciudad es obligatorio");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "provincia",
				"direccionFisica.provincia.required",
				"Provincia es obligatorio");
	}

}
