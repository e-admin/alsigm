package es.ieci.tecdoc.sicres.terceros.web.controller.terceros;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import es.ieci.tecdoc.fwktd.web.controller.crud.CrudCommandValidator;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.sicres.terceros.web.validator.CIFValidator;

/**
 *
 * @author IECISA
 *
 */
public class TerceroJuridicoValidator extends
		CrudCommandValidator<TerceroValidadoJuridicoVO> {

	@Override
	protected void doValidation(TerceroValidadoJuridicoVO target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "razonSocial",
				"tercero.juridico.nombre.required",
				"Razon social es obligatorio");
		if (!StringUtils.isEmpty(target.getTipoDocumento().getId())) {
			// Si el tipo de documento es otros, no es obligatorio indicar
			// número de documento
			if (!TIPO_DOCUMENTO_OTROS.equals(target.getTipoDocumento()
					.getId())) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,
						"numeroDocumento",
						"tercero.juridico.numeroDocumento.required",
						"Numero de documento es obligatorio");
			}
		}
		if (!StringUtils.isEmpty(target.getNumeroDocumento())) {
			ValidationUtils.rejectIfEmpty(errors, "tipoDocumento.id",
					"tercero.juridico.tipoDocumento.required",
					"Tipo de documento es obligatorio");
			if (CODIGO_CIF.equals(target.getTipoDocumento().getId())) {
				ValidationUtils.invokeValidator(getCifValidator(),
						target.getNumeroDocumento(), errors);
			}
		}
	}

	public CIFValidator getCifValidator() {
		return cifValidator;
	}

	public void setCifValidator(CIFValidator cifValidator) {
		this.cifValidator = cifValidator;
	}

	protected CIFValidator cifValidator;

	protected static final String TIPO_DOCUMENTO_OTROS = "5";
	protected static final String CODIGO_CIF = "1";

}
