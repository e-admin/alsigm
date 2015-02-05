package es.ieci.tecdoc.fwktd.web.controller.crud;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.ieci.tecdoc.fwktd.core.model.Entity;

public abstract class CrudCommandValidator<T extends Entity> implements
		Validator {

	public boolean supports(Class<?> clazz) {
		return CRUDCommand.class.equals(clazz);
	}

	public final void validate(Object target, Errors errors) {
		errors.pushNestedPath("content");

		CRUDCommand<T> command = (CRUDCommand<T>) target;

		doBeforeValidation(command, errors);

		doValidation(command.getContent(), errors);

		errors.popNestedPath();
	}

	/**
	 * Permite actuar sobre el <code>CRUDCommand</code> antes de validar el
	 * contenido del mismo.
	 * 
	 * @param command
	 *            instancia de <code>CRUDCommand</code> a validar
	 * @param errors
	 *            errores de validación
	 */
	protected void doBeforeValidation(CRUDCommand<T> command, Errors errors) {
		// do nothing
	}

	/**
	 * 
	 * @param target
	 * @param errors
	 */
	protected abstract void doValidation(T target, Errors errors);

}
