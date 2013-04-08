package es.ieci.tecdoc.sicres.terceros.web.validator;

import javax.validation.Validator;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.web.controller.crud.CrudCommandValidator;

/**
 *
 * @author IECISA
 *
 */
public class JS303CRUDCommandValidator extends CrudCommandValidator<Entity> {

	public JS303CRUDCommandValidator(Validator validator) {
		Assert.notNull(validator);
		this.setValidator(new SpringValidatorAdapter(validator));
	}

	@Override
	protected void doValidation(Entity target, Errors errors) {
		getValidator().validate(target, errors);
	}

	public SpringValidatorAdapter getValidator() {
		return validator;
	}

	public void setValidator(SpringValidatorAdapter validator) {
		this.validator = validator;
	}

	// Members
	protected SpringValidatorAdapter validator;

}
