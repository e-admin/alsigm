package es.ieci.tecdoc.sicres.terceros.web.controller.direcciones;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import es.ieci.tecdoc.fwktd.web.controller.crud.CrudCommandValidator;
import es.ieci.tecdoc.isicres.terceros.business.vo.DireccionTelematicaVO;

/**
 *
 * @author IECISA
 *
 */
public class DireccionTelematicaValidator extends
		CrudCommandValidator<DireccionTelematicaVO> {

	@Override
	protected void doValidation(DireccionTelematicaVO target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "direccion",
				"direccionTelematica.direccion.required",
				"Direccion es obligatorio");
	}

}
