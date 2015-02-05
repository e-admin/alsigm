package es.ieci.tecdoc.sicres.terceros.web.controller.terceros;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import es.ieci.tecdoc.fwktd.web.controller.crud.CrudCommandValidator;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.sicres.terceros.web.validator.NIEValidator;
import es.ieci.tecdoc.sicres.terceros.web.validator.NIFValidator;

/**
 * Validador para terceros físicos validados. Son obligatorios el nombre y
 * primer apellidos, así como el número de documento identificativo en caso de
 * especificar un tipo de documento.
 *
 * @author IECISA
 *
 */
public class TerceroFisicoValidator extends
		CrudCommandValidator<TerceroValidadoFisicoVO> {

	@Override
	protected void doValidation(TerceroValidadoFisicoVO target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre",
				"tercero.fisico.nombre.required", "Nombre es obligatorio");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "apellido1",
						"tercero.fisico.apellido1.required",
						"Apellido1 es obligatorio");
		if (!StringUtils.isEmpty(target.getTipoDocumento().getId())) {
			ValidationUtils.rejectIfEmpty(errors, "numeroDocumento",
					"tercero.fisico.numeroDocumento.required",
					"Numero de documento es obligatorio");
			if (CODIGO_NIF.equals(target.getTipoDocumento().getId())) {
				ValidationUtils.invokeValidator(getNifValidator(),
						target.getNumeroDocumento(), errors);
			} else if (CODIGO_NIE.equals(target.getTipoDocumento().getId())) {
				ValidationUtils.invokeValidator(getNieValidator(),
						target.getNumeroDocumento(), errors);
			}
		}
		if (!StringUtils.isEmpty(target.getNumeroDocumento())) {
			ValidationUtils.rejectIfEmpty(errors, "tipoDocumento.id",
					"tercero.fisico.tipoDocumento.required",
					"Tipo de documento es obligatorio");
		}
	}

	public NIFValidator getNifValidator() {
		return nifValidator;
	}

	public void setNifValidator(NIFValidator nifValidator) {
		this.nifValidator = nifValidator;
	}

	public NIEValidator getNieValidator() {
		return nieValidator;
	}

	public void setNieValidator(NIEValidator nieValidator) {
		this.nieValidator = nieValidator;
	}

	protected NIFValidator nifValidator;
	protected NIEValidator nieValidator;

	protected static final String CODIGO_NIF = "2";
	protected static final String CODIGO_NIE = "4";
}
